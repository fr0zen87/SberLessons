package com.example.lesson13.presentation.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lesson13.R;
import com.example.lesson13.data.entities.DailyData;
import com.example.lesson13.presentation.utils.FormatUtils;

import java.util.Locale;

public class DetailsActivity extends AppCompatActivity {

    private TextView titleView;
    private ImageView weatherIconView;
    private TextView weatherDescriptionView;
    private TextView temperatureHighView;
    private TextView temperatureLowView;
    private TextView sunriseView;
    private TextView sunsetView;
    private TextView windSpeedView;
    private TextView humidityView;
    private TextView pressureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        initViews();
        initValues();
    }

    private void initViews() {
        titleView = findViewById(R.id.details_title);
        weatherIconView = findViewById(R.id.details_weather_icon);
        weatherDescriptionView = findViewById(R.id.details_weather_description);
        temperatureHighView = findViewById(R.id.details_temp_high);
        temperatureLowView = findViewById(R.id.details_temp_low);
        sunriseView = findViewById(R.id.details_sunrise_value);
        sunsetView = findViewById(R.id.details_sunset_value);
        windSpeedView = findViewById(R.id.details_wind_speed_value);
        humidityView = findViewById(R.id.details_humidity_value);
        pressureView = findViewById(R.id.details_pressure_value);
    }

    private void initValues() {
        DailyData dailyData = getIntent().getParcelableExtra(MainActivity.WEATHER_DATA);

        titleView.setText(FormatUtils.titleDateFormatter(dailyData.getTime()));
        weatherIconView.setImageResource(FormatUtils.getImageResource(dailyData.getIcon()));
        weatherDescriptionView.setText(dailyData.getSummary());
        temperatureHighView.setText(FormatUtils.formatTemperature(dailyData.getTemperatureHigh()));
        temperatureLowView.setText(FormatUtils.formatTemperature(dailyData.getTemperatureLow()));
        sunriseView.setText(FormatUtils.otherDateFormatter(dailyData.getSunriseTime()));
        sunsetView.setText(FormatUtils.otherDateFormatter(dailyData.getSunsetTime()));
        windSpeedView.setText(String.format(Locale.getDefault(), getString(R.string.wind_speed_value), dailyData.getWindSpeed()));
        humidityView.setText(FormatUtils.formatHumidity(dailyData.getHumidity()));
        pressureView.setText(FormatUtils.formatPressure(dailyData.getPressure(), getString(R.string.pressure_value)));
    }
}
