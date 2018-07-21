package com.example.lesson13.presentation.mvp;

import android.os.AsyncTask;

import com.example.lesson13.data.daos.WeatherDao;
import com.example.lesson13.data.databases.WeatherDatabase;
import com.example.lesson13.data.entities.DailyData;
import com.example.lesson13.data.entities.HourlyData;
import com.example.lesson13.data.entities.Weather;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class MainModel implements MainContract.Model {

    private WeatherDatabase weatherDatabase;
    private WeatherCallback weatherCallback;

    @Inject
    public MainModel(WeatherDatabase weatherDatabase) {
        this.weatherDatabase = weatherDatabase;
    }

    @Override
    public void getWeather() {
        new GetWeather(weatherDatabase.weatherDao()).execute(weatherCallback);
    }

    private static class GetWeather extends AsyncTask<WeatherCallback, Void, Weather> {

        private WeatherDao dao;
        private WeatherCallback weatherCallback;

        private GetWeather(WeatherDao dao) {
            this.dao = dao;
        }

        @Override
        protected Weather doInBackground(WeatherCallback... weatherCallbacks) {
            weatherCallback = weatherCallbacks[0];
            Weather weather = dao.getWeather();

            long time = new Date().getTime();
            long dailyTime = (time - 86400000) / 1000;
            long hourlyTime = (time - 3600000) / 1000;

            List<DailyData> dailyData = dao.getDailyData(dailyTime);
            List<HourlyData> hourlyData = dao.getHourlyData(hourlyTime);

            weather.getDaily().setData(dailyData);
            weather.getHourly().setData(hourlyData);

            return weather;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);
            weatherCallback.onWeatherGet(weather);
        }
    }

    public interface WeatherCallback {
        void onWeatherGet(Weather weather);
    }

    @Override
    public void onAttach(WeatherCallback weatherCallback) {
        this.weatherCallback = weatherCallback;
    }
}
