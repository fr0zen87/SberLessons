package com.example.lesson13.presentation.mvp;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.lesson13.data.entities.DailyData;
import com.example.lesson13.data.entities.Weather;

import java.util.List;

public class Presenter implements MainContract.Presenter {

    private MainContract.View mView;
    private MainContract.Model mModel;

    public Presenter(MainContract.View view, MainContract.Model model) {
        mView = view;
        mModel = model;
    }

    @Override
    public void initData(ConnectivityManager connectivityManager) {
        mView.showProgressBar();

        NetworkInfo activeNetwork = null;
        boolean isConnected = false;
        if (connectivityManager != null) {
            activeNetwork = connectivityManager.getActiveNetworkInfo();
            if (activeNetwork != null) {
                isConnected = activeNetwork.isConnectedOrConnecting();
            }
        }
        if (isConnected) {
            mView.startService();
        } else {
            getWeather();
        }
    }

    private void getWeather() {
        Weather weather = mModel.getWeather();
        List<DailyData> data = weather.getDaily().getData();
        if (data != null && !data.isEmpty()) {
            mView.hideProgressBar();
            mView.showWeather(weather);
        } else {
            mView.hideProgressBar();
            mView.showEmptyMessage();
        }
    }
}
