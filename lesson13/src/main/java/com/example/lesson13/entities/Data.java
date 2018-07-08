package com.example.lesson13.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Data implements Parcelable {
    private String time;
    private String summary;
    private long sunriseTime;
    private long sunsetTime;
    private double temperatureHigh;
    private long temperatureHighTime;
    private double temperatureLow;
    private long temperatureLowTime;
    private double humidity;
    private double pressure;

    protected Data(Parcel in) {
        time = in.readString();
        summary = in.readString();
        sunriseTime = in.readLong();
        sunsetTime = in.readLong();
        temperatureHigh = in.readDouble();
        temperatureHighTime = in.readLong();
        temperatureLow = in.readDouble();
        temperatureLowTime = in.readLong();
        humidity = in.readDouble();
        pressure = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(time);
        dest.writeString(summary);
        dest.writeLong(sunriseTime);
        dest.writeLong(sunsetTime);
        dest.writeDouble(temperatureHigh);
        dest.writeLong(temperatureHighTime);
        dest.writeDouble(temperatureLow);
        dest.writeLong(temperatureLowTime);
        dest.writeDouble(humidity);
        dest.writeDouble(pressure);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public long getSunriseTime() {
        return sunriseTime;
    }

    public void setSunriseTime(long sunriseTime) {
        this.sunriseTime = sunriseTime;
    }

    public long getSunsetTime() {
        return sunsetTime;
    }

    public void setSunsetTime(long sunsetTime) {
        this.sunsetTime = sunsetTime;
    }

    public double getTemperatureHigh() {
        return temperatureHigh;
    }

    public void setTemperatureHigh(double temperatureHigh) {
        this.temperatureHigh = temperatureHigh;
    }

    public long getTemperatureHighTime() {
        return temperatureHighTime;
    }

    public void setTemperatureHighTime(long temperatureHighTime) {
        this.temperatureHighTime = temperatureHighTime;
    }

    public double getTemperatureLow() {
        return temperatureLow;
    }

    public void setTemperatureLow(double temperatureLow) {
        this.temperatureLow = temperatureLow;
    }

    public long getTemperatureLowTime() {
        return temperatureLowTime;
    }

    public void setTemperatureLowTime(long temperatureLowTime) {
        this.temperatureLowTime = temperatureLowTime;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }
}