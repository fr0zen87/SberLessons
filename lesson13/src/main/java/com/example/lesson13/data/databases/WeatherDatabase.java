package com.example.lesson13.data.databases;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.lesson13.data.daos.WeatherDao;
import com.example.lesson13.data.entities.Daily;
import com.example.lesson13.data.entities.DailyData;
import com.example.lesson13.data.entities.HourlyData;
import com.example.lesson13.data.entities.Weather;

@Database(entities = {Weather.class, Daily.class, DailyData.class, HourlyData.class}, version = 1, exportSchema = false)
public abstract class WeatherDatabase extends RoomDatabase {

    private static WeatherDatabase instance;

    public static WeatherDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (WeatherDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context, WeatherDatabase.class, "weather_db").build();
                }
            }
        }
        return instance;
    }

    public abstract WeatherDao weatherDao();
}
