package com.example.lesson13.presentation.mvp;

import android.net.ConnectivityManager;

import com.example.lesson13.data.entities.Weather;

import io.reactivex.Single;

public interface MainContract {

    interface Model {
        Single<Weather> getWeather(boolean isConnectedToInternet, boolean isSettingsChanged);
    }

    interface Presenter {
        void initData(ConnectivityManager connectivityManager, boolean isSettingsChanged);
    }

    interface View {
        void showProgressBar();
        void hideProgressBar();
        void showWeather(Weather weather);
        void restoreSettingsCheck();
    }
}
