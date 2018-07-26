package com.example.lesson13.presentation.activities;

import android.content.Context;
import android.content.Intent;
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

import com.example.lesson13.R;
import com.example.lesson13.data.entities.DailyData;
import com.example.lesson13.data.entities.Weather;
import com.example.lesson13.presentation.adapter.DailyAdapter;
import com.example.lesson13.presentation.adapter.HourlyAdapter;
import com.example.lesson13.presentation.dagger.DaggerAppComponent;
import com.example.lesson13.presentation.dagger.MainModule;
import com.example.lesson13.presentation.mvp.MainContract;
import com.example.lesson13.presentation.mvp.Presenter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DailyAdapter.MyCallback, MainContract.View {

    public static final String WEATHER = "weather";
    public static final String WEATHER_DATA = "weather data";
    public static final int REQUEST_CODE = 1;

    private ProgressBar progressBar;
    private TextView titleView;
    private TextView descriptionView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private DailyAdapter dailyAdapter;
    private HourlyAdapter hourlyAdapter;

    private Presenter presenter;
    private Weather weather;
    private boolean isSettingsChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = DaggerAppComponent.builder()
                .mainModule(new MainModule(this)).build().getPresenter();

        initViews();

        swipeRefreshLayout.setOnRefreshListener(this::initData);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(WEATHER, weather);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        weather = savedInstanceState.getParcelable(WEATHER);
        if (weather == null) {
            initData();
        } else {
            showWeather(weather);
        }
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
                startActivityForResult(intent, REQUEST_CODE);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            isSettingsChanged = data.getBooleanExtra(SettingsActivity.IS_PREFS_CHANGED, false);
            if (isSettingsChanged) {
                initData();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (weather == null) {
            initData();
        }
    }

    @Override
    public void onItemClick(DailyData dailyData) {
        Intent intent = new Intent(this, DetailsActivity.class)
                .putExtra(WEATHER_DATA, dailyData);
        startActivity(intent);
    }

    @Override
    public void showProgressBar() {
        if (swipeRefreshLayout.isRefreshing()) {
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showWeather(Weather weather) {
        this.weather = weather;

        titleView.setText(weather.getTimezone());
        descriptionView.setText(weather.getDaily().getSummary());

        dailyAdapter.setDailyData(weather.getDaily().getData());
        dailyAdapter.notifyDataSetChanged();

        hourlyAdapter.setHourlyData(weather.getHourly().getData());
        hourlyAdapter.notifyDataSetChanged();
    }

    @Override
    public void restoreSettingsCheck() {
        isSettingsChanged = false;
    }

    private void initViews() {
        progressBar = findViewById(R.id.progress_bar);
        titleView = findViewById(R.id.main_title);
        descriptionView = findViewById(R.id.main_description);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);

        RecyclerView dailyRecyclerView = findViewById(R.id.daily_recycler_view);
        dailyAdapter = new DailyAdapter(new ArrayList<>(), this);
        dailyRecyclerView.setAdapter(dailyAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL);
        dailyRecyclerView.addItemDecoration(dividerItemDecoration);

        RecyclerView hourlyRecyclerView = findViewById(R.id.hourly_recycler_view);
        hourlyAdapter = new HourlyAdapter(new ArrayList<>());
        hourlyRecyclerView.setAdapter(hourlyAdapter);
    }

    private void initData() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        presenter.initData(cm, isSettingsChanged);
    }
}
