package com.example.yourhealth;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;


import java.util.ArrayList;

public class diary_data_box implements Parcelable {

    private String date;
    private String memo;
    private ArrayList<diary_data> day = new ArrayList<diary_data>();

    public diary_data_box() {
    }


    protected diary_data_box(Parcel in) {
        date = in.readString();
        memo = in.readString();
        day = in.createTypedArrayList(diary_data.CREATOR);
    }

    public static final Creator<diary_data_box> CREATOR = new Creator<diary_data_box>() {
        @Override
        public diary_data_box createFromParcel(Parcel in) {
            return new diary_data_box(in);
        }

        @Override
        public diary_data_box[] newArray(int size) {
            return new diary_data_box[size];
        }
    };

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public ArrayList<diary_data> getDay() {
        return this.day;
    }

    public void initDay() {
        this.day.clear();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(date);
        parcel.writeString(memo);
        parcel.writeTypedList(day);
    }
}

