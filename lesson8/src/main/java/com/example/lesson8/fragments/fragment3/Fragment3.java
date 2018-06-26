package com.example.lesson8.fragments.fragment3;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lesson8.R;

import java.util.ArrayList;
import java.util.List;

public class Fragment3 extends Fragment implements LoaderManager.LoaderCallbacks<Void> {

    private List<String> strings = new ArrayList<>();
    private RecyclerView.Adapter adapter;

    private Handler handler;

    @SuppressLint("HandlerLeak")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.list);
        adapter = new ViewAdapter(strings);
        recyclerView.setAdapter(adapter);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                strings.add(String.valueOf(msg.what));
                adapter.notifyDataSetChanged();
            }
        };

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        getLoaderManager().initLoader(1, null, this).forceLoad();
    }

    @NonNull
    @Override
    public Loader<Void> onCreateLoader(int id, Bundle args) {
        return new MyLoader3(getContext(), handler);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Void> loader, Void data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Void> loader) {

    }
}
