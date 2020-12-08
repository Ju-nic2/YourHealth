package com.example.yourhealth;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class diary_data implements Parcelable {

    private String exercise;
    private String weight;
    private String set;
    private String rep;

    public diary_data() {
    }

    protected diary_data(Parcel in) {
        exercise = in.readString();
        weight = in.readString();
        set = in.readString();
        rep = in.readString();
    }

    public static final Creator<diary_data> CREATOR = new Creator<diary_data>() {
        @Override
        public diary_data createFromParcel(Parcel in) {
            return new diary_data(in);
        }

        @Override
        public diary_data[] newArray(int size) {
            return new diary_data[size];
        }
    };

    public String getExercise() { return exercise; }
    public void setExercise(String exercise) { this.exercise = exercise; }

    public String getWeight() { return weight; }
    public void setWeight(String weight) { this.weight = weight; }

    public String getSet() { return set; }
    public void setSet(String set) { this.set = set; }

    public String getRep() { return rep; }
    public void setRep(String rep) { this.rep = rep; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(exercise);
        parcel.writeString(weight);
        parcel.writeString(set);
        parcel.writeString(rep);
    }
}
