package com.example.lesson13.domain.web_service;

import com.example.lesson13.data.entities.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WeatherWebService {

    @GET("{latitude},{longitude}?exclude=currently,minutely,alerts,flags&units=auto&lang=ru")
    Call<Weather> getWeekWeather(@Path("latitude") double latitude, @Path("longitude") double longitude);
}
