package com.example.lesson13.presentation.activities;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lesson13.R;
import com.example.lesson13.data.entities.Data;
import com.example.lesson13.presentation.utils.FormatUtils;

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

    private ConstraintLayout layout;

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

        layout = findViewById(R.id.main_details);
    }

    private void initValues() {
        Data data = getIntent().getParcelableExtra(MainActivity.WEATHER_DATA);

        titleView.setText(FormatUtils.titleDateFormatter(data.getTime()));
        weatherIconView.setImageResource(data.getImageResource());
        weatherDescriptionView.setText(data.getSummary());
        temperatureHighView.setText(FormatUtils.formatTemperature(data.getTemperatureHigh()));
        temperatureLowView.setText(FormatUtils.formatTemperature(data.getTemperatureLow()));
        sunriseView.setText(FormatUtils.otherDateFormatter(data.getSunriseTime()));
        sunsetView.setText(FormatUtils.otherDateFormatter(data.getSunsetTime()));
        windSpeedView.setText(FormatUtils.formatWind(data.getWindSpeed(), getString(R.string.wind_speed_value)));
        humidityView.setText(FormatUtils.formatHumidity(data.getHumidity()));
        pressureView.setText(FormatUtils.formatPressure(data.getPressure(), getString(R.string.pressure_value)));

        layout.setBackgroundColor(ContextCompat.getColor(this, data.getBackgroundColor()));
    }
}
