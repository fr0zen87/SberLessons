package com.example.lesson13;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lesson13.adapter.WeatherAdapter;
import com.example.lesson13.entities.Data;
import com.example.lesson13.entities.Weather;
import com.example.lesson13.web_service.WeatherService;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements WeatherAdapter.MyCallback {

    public static final String BROADCAST_ACTION = "com.example.lesson13.WeatherReceiver";
    public static final String WEATHER = "weather";
    public static final String WEATHER_DATA = "weather data";

    private ProgressBar progressBar;
    private TextView titleView;
    private TextView descriptionView;

    private WeatherAdapter adapter;

    private WeatherReceiver receiver;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);
        titleView = findViewById(R.id.main_title);
        descriptionView = findViewById(R.id.main_description);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new WeatherAdapter(new ArrayList<Data>(), this);
        recyclerView.setAdapter(adapter);

        receiver = new WeatherReceiver();
        intentFilter = new IntentFilter(BROADCAST_ACTION);

        Intent intent = new Intent(this, WeatherService.class);
        startService(intent);

        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    public void onItemClick(Data data) {
        Intent intent = new Intent(this, DetailsActivity.class)
                .putExtra(WEATHER_DATA, data);
        startActivity(intent);
    }

    private class WeatherReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            progressBar.setVisibility(View.GONE);

            Weather weather = intent.getParcelableExtra(WEATHER);
            titleView.setText(weather.getTimezone());
            descriptionView.setText(weather.getDaily().getSummary());
            adapter.setWeather(weather.getDaily().getData());
            adapter.notifyDataSetChanged();
        }
    }
}
