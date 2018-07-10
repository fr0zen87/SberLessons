package com.example.lesson13.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.lesson13.entities.Daily;
import com.example.lesson13.entities.Data;
import com.example.lesson13.entities.Weather;

@Database(entities = {Weather.class, Daily.class, Data.class}, version = 1)
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
