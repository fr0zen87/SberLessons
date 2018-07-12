package com.example.lesson13.presentation.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lesson13.R;
import com.example.lesson13.data.entities.Data;
import com.example.lesson13.presentation.utils.FormatUtils;

import java.util.List;

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
        viewHolder.weatherIconView.setImageResource(FormatUtils.getImageResource(data));
        viewHolder.dateView.setText(FormatUtils.titleDateFormatter(data.getTime()));
        viewHolder.weatherDescriptionView.setText(data.getSummary());
        viewHolder.temperatureLowView.setText(FormatUtils.formatTemperature(data.getTemperatureLow()));
        viewHolder.temperatureHighView.setText(FormatUtils.formatTemperature(data.getTemperatureHigh()));

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
