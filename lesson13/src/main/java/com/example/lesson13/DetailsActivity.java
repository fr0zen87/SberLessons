package com.example.lesson13;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lesson13.entities.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

//        ActivityDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_details);
//        Data data = getIntent().getParcelableExtra(MainActivity.WEATHER_DATA);
//        binding.setData(data);

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

        titleView.setText(titleDateFormatter(data.getTime()));
        weatherIconView.setImageResource(data.getImageResource());
        weatherDescriptionView.setText(data.getSummary());
        temperatureHighView.setText(String.valueOf((int) data.getTemperatureHigh()));
        temperatureLowView.setText(String.valueOf((int) data.getTemperatureLow()));
        sunriseView.setText(otherDateFormatter(data.getSunriseTime()));
        sunsetView.setText(otherDateFormatter(data.getSunsetTime()));

        double windSpeed = 1.6093 * data.getWindSpeed();
        windSpeedView.setText(String.format(Locale.getDefault(), getString(R.string.wind_speed_value), windSpeed));

        int humidity = (int)(data.getHumidity() * 100);
        humidityView.setText(String.format(Locale.getDefault(), "%d%%", humidity));

        int pressure = (int)((data.getPressure() * 7.501) / 10);
        pressureView.setText(String.format(Locale.getDefault(), getString(R.string.pressure_value), pressure));

        int color = ContextCompat.getColor(this, data.getBackgroundColor());
        layout.setBackgroundColor(color);
    }

    private String titleDateFormatter(long date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, d MMMM yyyy", Locale.getDefault());
        return simpleDateFormat.format(new Date(date * 1000));
    }

    private String otherDateFormatter(long date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());
        return simpleDateFormat.format(new Date(date * 1000));
    }
}
