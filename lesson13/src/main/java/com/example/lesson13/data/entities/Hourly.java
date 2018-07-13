package com.example.lesson13.data.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

@Entity
public class Hourly implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long hourlyId;
    @ColumnInfo(name = "hourly_summary")
    private String summary;
    @ColumnInfo(name = "hourly_icon")
    private String icon;
    @Ignore
    private List<HourlyData> data;

    public Hourly() {
    }

    protected Hourly(Parcel in) {
        summary = in.readString();
        icon = in.readString();
        data = in.createTypedArrayList(HourlyData.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(summary);
        dest.writeString(icon);
        dest.writeTypedList(data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Hourly> CREATOR = new Creator<Hourly>() {
        @Override
        public Hourly createFromParcel(Parcel in) {
            return new Hourly(in);
        }

        @Override
        public Hourly[] newArray(int size) {
            return new Hourly[size];
        }
    };

    public long getHourlyId() {
        return hourlyId;
    }

    public void setHourlyId(long hourlyId) {
        this.hourlyId = hourlyId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<HourlyData> getData() {
        return data;
    }

    public void setData(List<HourlyData> data) {
        this.data = data;
    }
}
