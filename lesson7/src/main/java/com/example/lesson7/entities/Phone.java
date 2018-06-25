package com.example.lesson7.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Phone implements Parcelable {

    private String name;
    private String company;
    private int image;

    public Phone(String name, String company, int image){
        this.name=name;
        this.company = company;
        this.image = image;
    }

    protected Phone(Parcel in) {
        name = in.readString();
        company = in.readString();
        image = in.readInt();
    }

    public static final Creator<Phone> CREATOR = new Creator<Phone>() {
        @Override
        public Phone createFromParcel(Parcel in) {
            return new Phone(in);
        }

        @Override
        public Phone[] newArray(int size) {
            return new Phone[size];
        }
    };

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getImage() {
        return this.image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(company);
        parcel.writeInt(image);
    }
}
