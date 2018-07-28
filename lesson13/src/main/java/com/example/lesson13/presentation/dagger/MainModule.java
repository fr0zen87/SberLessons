package com.example.lesson13.presentation.dagger;

import android.content.SharedPreferences;
import android.location.Geocoder;
import android.preference.PreferenceManager;

import com.example.lesson13.data.databases.WeatherDatabase;
import com.example.lesson13.presentation.activities.MainActivity;
import com.example.lesson13.presentation.mvp.MainModel;
import com.example.lesson13.presentation.mvp.Presenter;

import java.util.Locale;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    private MainActivity mainActivity;

    public MainModule(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Provides
    @Singleton
    WeatherDatabase provideWeatherDatabase() {
        return WeatherDatabase.getInstance(mainActivity.getBaseContext());
    }

    @Provides
    @Singleton
    Geocoder provideGeocoder() {
        return new Geocoder(mainActivity.getBaseContext(), Locale.getDefault());
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(mainActivity.getBaseContext());
    }

    @Provides
    @Singleton
    MainModel provideMainModel(WeatherDatabase weatherDatabase, Geocoder geocoder, SharedPreferences sharedPreferences) {
        return new MainModel(weatherDatabase, geocoder, sharedPreferences);
    }

    @Provides
    @Singleton
    Presenter providePresenter(MainModel mainModel) {
        return new Presenter(mainActivity, mainModel);
    }
}
