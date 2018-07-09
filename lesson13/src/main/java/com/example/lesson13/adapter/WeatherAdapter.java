package com.example.lesson13.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lesson13.R;
import com.example.lesson13.entities.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private List<Data> weather;
    private MyCallback myCallback;

    public WeatherAdapter(List<Data> weather, MyCallback myCallback) {
        this.weather = weather;
        this.myCallback = myCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final Data data = weather.get(i);
        viewHolder.weatherIconView.setImageResource(getImageResource(data));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String date = simpleDateFormat.format(new Date(data.getTime() * 1000));
        viewHolder.dateView.setText(date);
        viewHolder.weatherDescriptionView.setText(data.getSummary());
        viewHolder.temperatureLowView.setText(String.valueOf((int) data.getTemperatureLow()));
        viewHolder.temperatureHighView.setText(String.valueOf((int) data.getTemperatureHigh()));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCallback.onItemClick(data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return weather.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView weatherIconView;
        TextView dateView;
        TextView weatherDescriptionView;
        TextView temperatureHighView;
        TextView temperatureLowView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            weatherIconView = itemView.findViewById(R.id.weather_icon);
            dateView = itemView.findViewById(R.id.date);
            weatherDescriptionView = itemView.findViewById(R.id.weather_description);
            temperatureHighView = itemView.findViewById(R.id.temperature_high);
            temperatureLowView = itemView.findViewById(R.id.temperature_low);
        }
    }

    private int getImageResource(Data data) {
        int resource = 0;
        int backgroundColor = 0;
        switch (data.getIcon()) {
            case "clear-day": {
                //same as clear-night
            }
            case "clear-night": {
                resource = R.drawable.ic_clear;
                backgroundColor = R.color.background_sun;
                break;
            }
            case "rain": {
                resource = R.drawable.ic_rain;
                backgroundColor = R.color.background_rain;
                break;
            }
            case "sleet": {
                //same as snow
            }
            case "snow": {
                resource = R.drawable.ic_snow;
                backgroundColor = R.color.background_snow;
                break;
            }
            case "wind": {

            }
            case "fog": {
                resource = R.drawable.ic_fog;
                backgroundColor = R.color.background_fog;
                break;
            }
            case "cloudy": {
                resource = R.drawable.ic_cloudy;
                backgroundColor = R.color.background_cloud;
                break;
            }
            case "partly-cloudy-day": {
                //same as partly-cloudy-night
            }
            case "partly-cloudy-night": {
                resource = R.drawable.ic_light_clouds;
                backgroundColor = R.color.background_partly_cloudy;
                break;
            }
            default: {
                resource = R.drawable.ic_clear;
                backgroundColor = R.color.background_sun;
                break;
            }
        }
        data.setImageResource(resource);
        data.setBackgroundColor(backgroundColor);
        return resource;
    }

    public List<Data> getWeather() {
        return weather;
    }

    public void setWeather(List<Data> weather) {
        this.weather = weather;
    }

    public interface MyCallback {
        void onItemClick(Data data);
    }
}
