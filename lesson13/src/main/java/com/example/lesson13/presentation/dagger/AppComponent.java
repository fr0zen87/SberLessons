package com.example.lesson13.presentation.dagger;

import com.example.lesson13.presentation.mvp.Presenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = MainModule.class)
public interface AppComponent {

    Presenter getPresenter();
}