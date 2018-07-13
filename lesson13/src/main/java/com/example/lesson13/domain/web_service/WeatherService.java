package com.example.lesson13.domain.web_service;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.example.lesson13.data.daos.WeatherDao;
import com.example.lesson13.data.databases.WeatherDatabase;
import com.example.lesson13.data.entities.Weather;
import com.example.lesson13.domain.helpers.RetrofitHelper;
import com.example.lesson13.presentation.activities.MainActivity;
import com.example.lesson13.presentation.activities.SettingsActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Response;

public class WeatherService extends IntentService {

    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";

    public WeatherService() {
        super("WeatherService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        RetrofitHelper retrofitHelper = new RetrofitHelper();
        WeatherDatabase weatherDatabase = WeatherDatabase.getInstance(getBaseContext());
        WeatherDao dao = weatherDatabase.weatherDao();

        try {
            List<Double> coordinates;
            boolean isChanged = false;

            if (intent != null) {
                isChanged = intent.getBooleanExtra(SettingsActivity.IS_PREFS_CHANGED, true);
            }
            if (isChanged) {
                coordinates = getNewCoordinates();
            } else {
                coordinates = getExistCoordinates();
            }

            Response<Weather> result = retrofitHelper.getService()
                    .getWeekWeather(coordinates.get(0), coordinates.get(1))
                    .execute();
            Weather weather = result.body();
            Intent broadcastIntent = new Intent(MainActivity.BROADCAST_ACTION)
                    .putExtra(MainActivity.WEATHER, weather);
            sendBroadcast(broadcastIntent);

            weatherDatabase.clearAllTables();
            dao.addWeather(weather);
            dao.addDailyData(weather.getDaily().getData());
            dao.addHourlyData(weather.getHourly().getData());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Double> getNewCoordinates() throws IOException {
        List<Double> result = new ArrayList<>();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String location = preferences.getString("location", "Moscow");

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = geocoder.getFromLocationName(location, 1);

        if (addresses != null && !addresses.isEmpty()) {
            double latitude = addresses.get(0).getLatitude();
            double longitude = addresses.get(0).getLongitude();

            result.add(latitude);
            result.add(longitude);

            SharedPreferences.Editor editor = preferences.edit();
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

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        result.add((double) preferences.getFloat(LATITUDE, 55.75222f));
        result.add((double) preferences.getFloat(LONGITUDE, 37.61556f));

        return result;
    }
}
