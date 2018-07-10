package com.example.lesson13.mvp;

import com.example.lesson13.db.WeatherDatabase;
import com.example.lesson13.entities.Data;
import com.example.lesson13.entities.Weather;

import java.util.Date;
import java.util.List;

public class MainModel implements MainContract.Model {

    private WeatherDatabase weatherDatabase;

    public MainModel(WeatherDatabase weatherDatabase) {
        this.weatherDatabase = weatherDatabase;
    }

    @Override
    public Weather getWeather() {
        Weather weather = weatherDatabase.weatherDao().getWeather();
        long time = new Date().getTime() - 86400000;
        List<Data> data = weatherDatabase.weatherDao().getData(time);
        weather.getDaily().setData(data);
        return weather;
    }
}
