package com.example.lesson13;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.lesson13.adapter.WeatherAdapter;
import com.example.lesson13.entities.Data;
import com.example.lesson13.entities.Weather;
import com.example.lesson13.web_service.WeatherService;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String BROADCAST_ACTION = "com.example.lesson13.WeatherReceiver";
    public static final String WEATHER = "weather";

    private RecyclerView recyclerView;
    private WeatherAdapter adapter;

    private WeatherReceiver receiver;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new WeatherAdapter(new ArrayList<Data>());
        recyclerView.setAdapter(adapter);

        receiver = new WeatherReceiver();
        intentFilter = new IntentFilter(BROADCAST_ACTION);
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(receiver, intentFilter);

        Intent intent = new Intent(this, WeatherService.class);
        startService(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    private class WeatherReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Weather weather = intent.getParcelableExtra(WEATHER);
            adapter.setWeather(weather.getDaily().getData());
            adapter.notifyDataSetChanged();
        }
    }
}
