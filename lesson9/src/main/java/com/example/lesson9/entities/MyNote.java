package com.example.lesson9.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class MyNote implements Parcelable {

    private int id;
    private String name;
    private String date;
    private String content;

    public MyNote(int id, String name, String date, String content) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.content = content;
    }

    protected MyNote(Parcel in) {
        id = in.readInt();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(date);
        dest.writeString(content);
    }
}
