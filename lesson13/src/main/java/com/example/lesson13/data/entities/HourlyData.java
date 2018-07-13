package com.example.lesson13.data.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity
public class HourlyData implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private long time;
    private String icon;
    private double temperature;

    public HourlyData() {

    }

    protected HourlyData(Parcel in) {
        id = in.readLong();
        time = in.readLong();
        icon = in.readString();
        temperature = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(time);
        dest.writeString(icon);
        dest.writeDouble(temperature);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HourlyData> CREATOR = new Creator<HourlyData>() {
        @Override
        public HourlyData createFromParcel(Parcel in) {
            return new HourlyData(in);
        }

        @Override
        public HourlyData[] newArray(int size) {
            return new HourlyData[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
