package com.example.lesson13.data.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.lesson13.data.entities.Data;
import com.example.lesson13.data.entities.Weather;

import java.util.List;

@Dao
public interface WeatherDao {

    @Insert
    void addWeather(Weather weather);

    @Insert
    void addData(List<Data> data);

    @Query("SELECT * FROM weather")
    Weather getWeather();

    @Query("SELECT * FROM data WHERE time >= :time")
    List<Data> getData(long time);
}
