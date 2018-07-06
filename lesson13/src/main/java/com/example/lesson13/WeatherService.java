package com.example.lesson13;

import android.app.IntentService;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class WeatherService extends IntentService {

    public WeatherService() {
        super("WeatherService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        RetrofitHelper retrofitHelper = new RetrofitHelper();

        try {
            Response<List<Weather>> result = retrofitHelper.getService().getWeekWeather().execute();
            Intent broadcastIntent = new Intent().putParcelableArrayListExtra(MainActivity.WEATHERS, (ArrayList<? extends Parcelable>) result.body());
            sendBroadcast(broadcastIntent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
