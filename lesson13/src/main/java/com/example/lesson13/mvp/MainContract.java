package com.example.lesson13.mvp;

import com.example.lesson13.entities.Weather;

public interface MainContract {

    interface Model {
        Weather getWeather();
    }

    interface Presenter {
        void getWeather();
    }

    interface View {
        void showProgressBar();
        void hideProgressBar();
        void showWeather(Weather weather);
        void showEmptyMessage();
        void hideEmptyMessage();
    }
}
