package com.example.lesson13.presentation.dagger;

import com.example.lesson13.data.databases.WeatherDatabase;
import com.example.lesson13.presentation.activities.MainActivity;
import com.example.lesson13.presentation.mvp.MainModel;
import com.example.lesson13.presentation.mvp.Presenter;

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
    MainModel provideMainModel() {
        return new MainModel(provideWeatherDatabase());
    }

    @Provides
    @Singleton
    Presenter providePresenter() {
        return new Presenter(mainActivity, provideMainModel());
    }
}
