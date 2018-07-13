package com.example.lesson13.presentation.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lesson13.R;
import com.example.lesson13.data.entities.HourlyData;
import com.example.lesson13.presentation.utils.FormatUtils;

import java.util.List;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.ViewHolder> {

    private List<HourlyData> hourlyData;

    public HourlyAdapter(List<HourlyData> hourlyData) {
        this.hourlyData = hourlyData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HourlyData data = hourlyData.get(position);
        holder.dateView.setText(FormatUtils.otherDateFormatter(data.getTime()));
        holder.imageView.setImageResource(FormatUtils.getImageResource(data.getIcon()));
        holder.temperatureView.setText(FormatUtils.formatTemperature(data.getTemperature()));
    }

    @Override
    public int getItemCount() {
        return hourlyData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dateView;
        ImageView imageView;
        TextView temperatureView;

        public ViewHolder(View itemView) {
            super(itemView);
            dateView = itemView.findViewById(R.id.hourly_date);
            imageView = itemView.findViewById(R.id.hourly_image);
            temperatureView = itemView.findViewById(R.id.hourly_temp);
        }
    }

    public List<HourlyData> getHourlyData() {
        return hourlyData;
    }

    public void setHourlyData(List<HourlyData> hourlyData) {
        this.hourlyData = hourlyData;
    }
}
