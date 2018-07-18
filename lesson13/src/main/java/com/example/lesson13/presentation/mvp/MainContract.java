package com.example.lesson13.presentation.mvp;

import android.net.ConnectivityManager;

import com.example.lesson13.data.entities.Weather;

public interface MainContract {

    interface Model {
        void getWeather();
        void onAttach(MainModel.WeatherCallback weatherCallback);
    }

    interface Presenter {
        void initData(ConnectivityManager connectivityManager);
    }

    interface View {
        void showProgressBar();
        void hideProgressBar();
        void showWeather(Weather weather);
        void showEmptyMessage();
        void startService();
    }
}
