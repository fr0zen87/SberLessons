package com.example.lesson8.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lesson8.R;

public class Fragment1 extends Fragment implements LoaderManager.LoaderCallbacks<Integer> {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment1, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().initLoader(666, null, this).forceLoad();
    }

    @NonNull
    @Override
    public Loader<Integer> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new MyLoader(view.getContext());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Integer> loader, Integer integer) {
        int color = ContextCompat.getColor(view.getContext(), integer);
        view.setBackgroundColor(color);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Integer> loader) {

    }
}
