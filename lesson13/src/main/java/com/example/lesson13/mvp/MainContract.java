package com.example.lesson13.mvp;

import android.net.ConnectivityManager;

import com.example.lesson13.entities.Weather;

public interface MainContract {

    interface Model {
        Weather getWeather();
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
