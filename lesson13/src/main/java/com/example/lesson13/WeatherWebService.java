package com.example.lesson13;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WeatherWebService {

    @GET("daily?q=Moscow,RU&cnt=7")
    Call<List<Weather>> getWeekWeather();
}
