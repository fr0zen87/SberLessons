package com.example.lesson13;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private static final String VERSION_API = "2.5/forecast/";
    private static final String BASE_URL = "api.openweathermap.org/data/" + VERSION_API;

    public WeatherWebService getService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(WeatherWebService.class);
    }
}
