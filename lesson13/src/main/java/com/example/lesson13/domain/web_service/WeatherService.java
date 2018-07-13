package com.example.lesson13.domain.web_service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.lesson13.data.daos.WeatherDao;
import com.example.lesson13.data.databases.WeatherDatabase;
import com.example.lesson13.data.entities.Weather;
import com.example.lesson13.domain.helpers.RetrofitHelper;
import com.example.lesson13.presentation.activities.MainActivity;

import java.io.IOException;

import retrofit2.Response;

public class WeatherService extends IntentService {

    public WeatherService() {
        super("WeatherService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        RetrofitHelper retrofitHelper = new RetrofitHelper();
        WeatherDatabase weatherDatabase = WeatherDatabase.getInstance(getBaseContext());
        WeatherDao dao = weatherDatabase.weatherDao();

        try {
            Response<Weather> result = retrofitHelper.getService().getWeekWeather().execute();
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
}
