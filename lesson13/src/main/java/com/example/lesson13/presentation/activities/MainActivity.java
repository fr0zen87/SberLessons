package com.example.lesson13.presentation.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lesson13.R;
import com.example.lesson13.data.databases.WeatherDatabase;
import com.example.lesson13.data.entities.DailyData;
import com.example.lesson13.data.entities.HourlyData;
import com.example.lesson13.data.entities.Weather;
import com.example.lesson13.domain.web_service.WeatherService;
import com.example.lesson13.presentation.adapter.DailyAdapter;
import com.example.lesson13.presentation.adapter.HourlyAdapter;
import com.example.lesson13.presentation.mvp.MainContract;
import com.example.lesson13.presentation.mvp.MainModel;
import com.example.lesson13.presentation.mvp.Presenter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DailyAdapter.MyCallback,
        MainContract.View {

    public static final String BROADCAST_ACTION = "com.example.lesson13.WeatherReceiver";
    public static final String WEATHER = "weather";
    public static final String WEATHER_DATA = "weather data";

    private ProgressBar progressBar;
    private TextView titleView;
    private TextView descriptionView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private DailyAdapter dailyAdapter;
    private HourlyAdapter hourlyAdapter;

    private WeatherReceiver receiver;
    private IntentFilter intentFilter;

    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);
        titleView = findViewById(R.id.main_title);
        descriptionView = findViewById(R.id.main_description);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);

        RecyclerView dailyRecyclerView = findViewById(R.id.daily_recycler_view);
        dailyAdapter = new DailyAdapter(new ArrayList<DailyData>(), this);
        dailyRecyclerView.setAdapter(dailyAdapter);
        DividerItemDecoration dividerItemDecoration= new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL);
        dailyRecyclerView.addItemDecoration(dividerItemDecoration);

        RecyclerView hourlyRecyclerView = findViewById(R.id.hourly_recycler_view);
        hourlyAdapter = new HourlyAdapter(new ArrayList<HourlyData>());
        hourlyRecyclerView.setAdapter(hourlyAdapter);

        receiver = new WeatherReceiver();
        intentFilter = new IntentFilter(BROADCAST_ACTION);

        WeatherDatabase weatherDatabase = WeatherDatabase.getInstance(this);
        MainModel mainModel = new MainModel(weatherDatabase);
        presenter = new Presenter(this, mainModel);

        initData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings: {
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
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
    public void onItemClick(DailyData dailyData) {
        Intent intent = new Intent(this, DetailsActivity.class)
                .putExtra(WEATHER_DATA, dailyData);
        startActivity(intent);
    }

    @Override
    public void showEmptyMessage() {
        Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressBar() {
        if(swipeRefreshLayout.isRefreshing()) {
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        if(swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showWeather(Weather weather) {
        titleView.setText(weather.getTimezone());
        descriptionView.setText(weather.getDaily().getSummary());

        dailyAdapter.setDailyData(weather.getDaily().getData());
        dailyAdapter.notifyDataSetChanged();

        hourlyAdapter.setHourlyData(weather.getHourly().getData());
        hourlyAdapter.notifyDataSetChanged();
    }

    @Override
    public void startService() {
        Intent intent = new Intent(this, WeatherService.class);
        startService(intent);
    }

    @Override
    public boolean onNavigateUpFromChild(Activity child) {
        initData();
        return super.onNavigateUpFromChild(child);
    }

    private void initData() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        presenter.initData(cm);
    }

    private class WeatherReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            hideProgressBar();

            Weather weather = intent.getParcelableExtra(WEATHER);
            titleView.setText(weather.getTimezone());
            descriptionView.setText(weather.getDaily().getSummary());

            dailyAdapter.setDailyData(weather.getDaily().getData());
            dailyAdapter.notifyDataSetChanged();

            hourlyAdapter.setHourlyData(weather.getHourly().getData());
            hourlyAdapter.notifyDataSetChanged();
        }
    }
}
