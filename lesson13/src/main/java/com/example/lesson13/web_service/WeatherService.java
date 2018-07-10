package com.example.lesson13.web_service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.lesson13.MainActivity;
import com.example.lesson13.db.WeatherDatabase;
import com.example.lesson13.entities.Weather;

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

        try {
            Response<Weather> result = retrofitHelper.getService().getWeekWeather().execute();
            Weather weather = result.body();
            Intent broadcastIntent = new Intent(MainActivity.BROADCAST_ACTION)
                    .putExtra(MainActivity.WEATHER, weather);
            sendBroadcast(broadcastIntent);

            weatherDatabase.clearAllTables();
            weatherDatabase.weatherDao().addWeather(weather);
            weatherDatabase.weatherDao().addData(weather.getDaily().getData());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
