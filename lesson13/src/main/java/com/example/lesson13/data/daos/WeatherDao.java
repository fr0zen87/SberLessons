package com.example.lesson13.data.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.example.lesson13.data.entities.DailyData;
import com.example.lesson13.data.entities.HourlyData;
import com.example.lesson13.data.entities.Weather;

import java.util.List;

@Dao
public abstract class WeatherDao {

    @Insert
    public abstract void addWeather(Weather weather);

    @Insert
    public abstract void addDailyData(List<DailyData> data);

    @Insert
    public abstract void addHourlyData(List<HourlyData> data);

    @Query("SELECT * FROM weather")
    public abstract Weather getWeather();

    @Query("SELECT * FROM DailyData WHERE time >= :time")
    public abstract List<DailyData> getDailyData(long time);

    @Query("SELECT * FROM HourlyData WHERE time >= :time")
    public abstract List<HourlyData> getHourlyData(long time);

    @Transaction
    public void addAll(Weather weather) {
        addWeather(weather);
        addDailyData(weather.getDaily().getData());
        addHourlyData(weather.getHourly().getData());
    }
}
