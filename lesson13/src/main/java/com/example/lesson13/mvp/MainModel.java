package com.example.lesson13.mvp;

import android.os.AsyncTask;

import com.example.lesson13.db.WeatherDao;
import com.example.lesson13.db.WeatherDatabase;
import com.example.lesson13.entities.Data;
import com.example.lesson13.entities.Weather;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainModel implements MainContract.Model {

    private WeatherDatabase weatherDatabase;

    public MainModel(WeatherDatabase weatherDatabase) {
        this.weatherDatabase = weatherDatabase;
    }

    @Override
    public Weather getWeather() {
        try {
            return new GetWeather(weatherDatabase.weatherDao()).execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class GetWeather extends AsyncTask<Void, Void, Weather> {

        private WeatherDao dao;

        private GetWeather(WeatherDao dao) {
            this.dao = dao;
        }

        @Override
        protected Weather doInBackground(Void... voids) {
            Weather weather = dao.getWeather();
            long time = new Date().getTime() - 86400000;
            List<Data> data = dao.getData(time);
            weather.getDaily().setData(data);
            return weather;
        }
    }
}
