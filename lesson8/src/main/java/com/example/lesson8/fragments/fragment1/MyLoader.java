package com.example.lesson8.fragments.fragment1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.lesson8.R;

public class MyLoader extends AsyncTaskLoader<Integer> {

    MyLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public Integer loadInBackground() {
        return R.color.colorAccent;
    }
}