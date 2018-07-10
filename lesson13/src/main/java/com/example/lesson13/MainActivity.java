package com.example.lesson13;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lesson13.adapter.WeatherAdapter;
import com.example.lesson13.db.WeatherDatabase;
import com.example.lesson13.entities.Data;
import com.example.lesson13.entities.Weather;
import com.example.lesson13.mvp.MainContract;
import com.example.lesson13.mvp.MainModel;
import com.example.lesson13.mvp.Presenter;
import com.example.lesson13.web_service.WeatherService;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements WeatherAdapter.MyCallback, MainContract.View {

    public static final String BROADCAST_ACTION = "com.example.lesson13.WeatherReceiver";
    public static final String WEATHER = "weather";
    public static final String WEATHER_DATA = "weather data";

    private TextView emptyDataView;
    private ProgressBar progressBar;
    private TextView titleView;
    private TextView descriptionView;

    private WeatherAdapter adapter;

    private WeatherReceiver receiver;
    private IntentFilter intentFilter;

    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emptyDataView = findViewById(R.id.empty_data_view);
        progressBar = findViewById(R.id.progress_bar);
        titleView = findViewById(R.id.main_title);
        descriptionView = findViewById(R.id.main_description);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new WeatherAdapter(new ArrayList<Data>(), this);
        recyclerView.setAdapter(adapter);

        receiver = new WeatherReceiver();
        intentFilter = new IntentFilter(BROADCAST_ACTION);

        WeatherDatabase weatherDatabase = WeatherDatabase.getInstance(this);
        MainModel mainModel = new MainModel(weatherDatabase);
        presenter = new Presenter(this, mainModel);

        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh: {
                initData();
                break;
            }
            case R.id.settings: {
                Toast.makeText(this, "Not available yet", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        return true;
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

    @Override
    public void showEmptyMessage() {
        emptyDataView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyMessage() {
        emptyDataView.setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showWeather(Weather weather) {
        titleView.setText(weather.getTimezone());
        descriptionView.setText(weather.getDaily().getSummary());
        adapter.setWeather(weather.getDaily().getData());
        adapter.notifyDataSetChanged();
    }

    private void initData() {
        showProgressBar();
        hideEmptyMessage();

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        boolean isConnected = false;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
            isConnected = activeNetwork.isConnectedOrConnecting();
        }
        if (isConnected) {
            Intent intent = new Intent(this, WeatherService.class);
            startService(intent);
        } else {
            presenter.getWeather();
        }
    }

    private class WeatherReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            hideProgressBar();

            Weather weather = intent.getParcelableExtra(WEATHER);
            titleView.setText(weather.getTimezone());
            descriptionView.setText(weather.getDaily().getSummary());
            adapter.setWeather(weather.getDaily().getData());
            adapter.notifyDataSetChanged();
        }
    }
}
