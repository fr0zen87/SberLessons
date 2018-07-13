package com.example.lesson13.data.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.lesson13.data.entities.DailyData;
import com.example.lesson13.data.entities.HourlyData;
import com.example.lesson13.data.entities.Weather;

import java.util.List;

@Dao
public interface WeatherDao {

    @Insert
    void addWeather(Weather weather);

    @Insert
    void addDailyData(List<DailyData> data);

    @Insert
    void addHourlyData(List<HourlyData> data);

    @Query("SELECT * FROM weather")
    Weather getWeather();

    @Query("SELECT * FROM DailyData WHERE time >= :time")
    List<DailyData> getDailyData(long time);

    @Query("SELECT * FROM HourlyData WHERE time >= :time")
    List<HourlyData> getHourlyData(long time);
}
