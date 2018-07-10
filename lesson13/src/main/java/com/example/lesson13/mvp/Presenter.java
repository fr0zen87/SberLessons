package com.example.lesson13.mvp;

import com.example.lesson13.entities.Data;
import com.example.lesson13.entities.Weather;

import java.util.List;

public class Presenter implements MainContract.Presenter {

    private MainContract.View mView;
    private MainContract.Model mModel;

    public Presenter(MainContract.View view, MainContract.Model model) {
        mView = view;
        mModel = model;
    }

    @Override
    public void getWeather() {
        Weather weather = mModel.getWeather();
        List<Data> data = weather.getDaily().getData();
        if (data != null && !data.isEmpty()) {
            mView.showWeather(weather);
        } else {
            mView.showEmptyMessage();
        }
        mView.hideProgressBar();
    }
}
