package com.example.lesson13.presentation.mvp;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Presenter implements MainContract.Presenter {

    private MainContract.View mView;
    private MainContract.Model mModel;

    @Inject
    public Presenter(MainContract.View view, MainContract.Model model) {
        mView = view;
        mModel = model;
    }

    @Override
    public void initData(ConnectivityManager connectivityManager, boolean isSettingsChanged) {
        mView.showProgressBar();
        mView.restoreSettingsCheck();

        boolean isConnected = false;
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            if (activeNetwork != null) {
                isConnected = activeNetwork.isConnectedOrConnecting();
            }
        }

        mModel.getWeather(isConnected, isSettingsChanged)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(w -> {
                    mView.hideProgressBar();
                    mView.showWeather(w);
                })
                .subscribe();
    }
}
