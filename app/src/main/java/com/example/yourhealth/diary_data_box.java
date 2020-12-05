package com.example.yourhealth;

import android.view.View;

import java.util.ArrayList;

public class diary_data_box extends diary_data{

    private String date;
    private String memo;
    private ArrayList<diary_data> day;

    diary_data_box()
    {day = new ArrayList<diary_data>();}

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getMemo() { return memo; }
    public void setMemo(String memo) { this.memo = memo; }

    public ArrayList<diary_data> getDay() { return this.day; }
    public void initDay() { this.day.clear(); }

}
