package com.example.lesson13.presentation.mvp;

import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;

import com.example.lesson13.data.daos.WeatherDao;
import com.example.lesson13.data.databases.WeatherDatabase;
import com.example.lesson13.data.entities.DailyData;
import com.example.lesson13.data.entities.HourlyData;
import com.example.lesson13.data.entities.Weather;
import com.example.lesson13.domain.retrofit.RetrofitHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

public class MainModel implements MainContract.Model {

    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";

    private String location;

    private WeatherDatabase weatherDatabase;
    private Geocoder geocoder;
    private SharedPreferences preferences;

    @Inject
    public MainModel(WeatherDatabase weatherDatabase, Geocoder geocoder, SharedPreferences preferences) {
        this.weatherDatabase = weatherDatabase;
        this.geocoder = geocoder;
        this.preferences = preferences;
    }

    @Override
    public Single<Weather> getWeather(boolean isConnectedToInternet, boolean isSettingsChanged) {
        if (isConnectedToInternet) {
            return getWeatherFromInternet(isSettingsChanged);
        } else {
            return getWeatherFromDatabase();
        }
    }

    private Single<Weather> getWeatherFromDatabase() {
        WeatherDao dao = weatherDatabase.weatherDao();
        Weather weather = dao.getWeather();

        long time = new Date().getTime();
        long dailyTime = (time - 86400000) / 1000;
        long hourlyTime = (time - 3600000) / 1000;

        List<DailyData> dailyData = dao.getDailyData(dailyTime);
        List<HourlyData> hourlyData = dao.getHourlyData(hourlyTime);

        weather.getDaily().setData(dailyData);
        weather.getHourly().setData(hourlyData);

        return Single.just(weather);
    }

    private Single<Weather> getWeatherFromInternet(boolean isSettingsChanged) {
        WeatherDao dao = weatherDatabase.weatherDao();
        RetrofitHelper retrofitHelper = new RetrofitHelper();
        try {
            List<Double> coordinates;

            if (isSettingsChanged) {
                coordinates = getNewCoordinates();
            } else {
                coordinates = getExistCoordinates();
            }

            Observable<Weather> result = retrofitHelper.getService()
                    .getWeekWeather(coordinates.get(0), coordinates.get(1))
                    .flatMap(weather -> {
                        weather.setTimezone(location);
                        List<HourlyData> data = weather.getHourly().getData().subList(0, 25);
                        weather.getHourly().setData(data);

                        weatherDatabase.clearAllTables();
                        dao.addAll(weather);

                        return Observable.fromArray(weather);
                    });

            return Single.fromObservable(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Double> getNewCoordinates() throws IOException {
        List<Double> result = new ArrayList<>();

        String locationProp = preferences.getString("location", "Москва");

        List<Address> addresses = geocoder.getFromLocationName(locationProp, 1);

        if (addresses != null && !addresses.isEmpty()) {
            String subLoc = addresses.get(0).getSubAdminArea() != null ?
                    addresses.get(0).getSubAdminArea() : locationProp;
            double latitude = addresses.get(0).getLatitude();
            double longitude = addresses.get(0).getLongitude();

            location = subLoc;

            result.add(latitude);
            result.add(longitude);

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("location", subLoc);
            editor.putFloat(LATITUDE, (float) latitude);
            editor.putFloat(LONGITUDE, (float) longitude);
            editor.apply();
        } else {
            return getExistCoordinates();
        }
        return result;
    }

    private List<Double> getExistCoordinates() {
        List<Double> result = new ArrayList<>();

        location = preferences.getString("location", "Москва");
        result.add((double) preferences.getFloat(LATITUDE, 55.75222f));
        result.add((double) preferences.getFloat(LONGITUDE, 37.61556f));

        return result;
    }
}
