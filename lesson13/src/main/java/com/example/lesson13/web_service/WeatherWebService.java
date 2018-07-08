package com.example.lesson13.web_service;

import com.example.lesson13.entities.Weather;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WeatherWebService {

    @GET("55.75222,37.61556?exclude=currently,minutely,hourly,alerts,flags&units=auto&lang=ru")
    Call<Weather> getWeekWeather();
}
