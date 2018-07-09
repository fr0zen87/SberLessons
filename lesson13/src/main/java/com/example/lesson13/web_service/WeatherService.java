package com.example.lesson13.web_service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.lesson13.MainActivity;
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

        try {
            Response<Weather> result = retrofitHelper.getService().getWeekWeather().execute();
            Intent broadcastIntent = new Intent(MainActivity.BROADCAST_ACTION)
                    .putExtra(MainActivity.WEATHER, result.body());
            sendBroadcast(broadcastIntent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
