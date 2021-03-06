package com.example.lesson13.data.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

@Entity
public class Daily implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long dailyId;
    @ColumnInfo(name = "daily_summary")
    private String summary;
    @ColumnInfo(name = "daily_icon")
    private String icon;
    @Ignore
    private List<DailyData> data;

    public Daily() {
    }

    protected Daily(Parcel in) {
        summary = in.readString();
        icon = in.readString();
        data = in.createTypedArrayList(DailyData.CREATOR);
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

    public static final Creator<Daily> CREATOR = new Creator<Daily>() {
        @Override
        public Daily createFromParcel(Parcel in) {
            return new Daily(in);
        }

        @Override
        public Daily[] newArray(int size) {
            return new Daily[size];
        }
    };

    public long getDailyId() {
        return dailyId;
    }

    public void setDailyId(long dailyId) {
        this.dailyId = dailyId;
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

    public List<DailyData> getData() {
        return data;
    }

    public void setData(List<DailyData> data) {
        this.data = data;
    }
}