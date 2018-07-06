package com.example.lesson10.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "notes")
public class MyNote implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String date;
    private String content;

    public MyNote() {
    }

    public MyNote(@NonNull String name, @NonNull String date, @NonNull String content) {
        this.name = name;
        this.date = date;
        this.content = content;
    }

    public MyNote(int id) {
        this.id = id;
    }

    protected MyNote(Parcel in) {
        id = in.readLong();
        name = in.readString();
        date = in.readString();
        content = in.readString();
    }

    public static final Creator<MyNote> CREATOR = new Creator<MyNote>() {
        @Override
        public MyNote createFromParcel(Parcel in) {
            return new MyNote(in);
        }

        @Override
        public MyNote[] newArray(int size) {
            return new MyNote[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(date);
        dest.writeString(content);
    }
}
