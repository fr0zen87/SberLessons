package com.example.lesson13.web_service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private static final String BASE_URL = "https://api.darksky.net/forecast/3af04d96725daf8d45251e7b082fdb62/";

    public WeatherWebService getService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(WeatherWebService.class);
    }
}
