package com.example.lesson13.presentation.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lesson13.R;
import com.example.lesson13.data.entities.DailyData;
import com.example.lesson13.presentation.utils.FormatUtils;

import java.util.List;

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.ViewHolder> {

    private List<DailyData> dailyData;
    private MyCallback myCallback;

    public DailyAdapter(List<DailyData> dailyData, MyCallback myCallback) {
        this.dailyData = dailyData;
        this.myCallback = myCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.daily_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        final DailyData dailyData = this.dailyData.get(position);
        viewHolder.weatherIconView.setImageResource(FormatUtils.getImageResource(dailyData.getIcon()));
        viewHolder.dateView.setText(FormatUtils.titleDateFormatter(dailyData.getTime()));
        viewHolder.weatherDescriptionView.setText(dailyData.getSummary());
        viewHolder.temperatureLowView.setText(FormatUtils.formatTemperature(dailyData.getTemperatureLow()));
        viewHolder.temperatureHighView.setText(FormatUtils.formatTemperature(dailyData.getTemperatureHigh()));

        viewHolder.itemView.setOnClickListener(v -> myCallback.onItemClick(dailyData));
    }

    @Override
    public int getItemCount() {
        return dailyData.size();
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

    public List<DailyData> getDailyData() {
        return dailyData;
    }

    public void setDailyData(List<DailyData> dailyData) {
        this.dailyData = dailyData;
    }

    public interface MyCallback {
        void onItemClick(DailyData dailyData);
    }
}
