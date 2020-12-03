package com.example.yourhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

public class diary_start extends AppCompatActivity {

    Button writeDiaryBtn;
    private CalendarView mCalendarView;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_start);

        writeDiaryBtn = findViewById(R.id.button_write_diary);
        mCalendarView = findViewById(R.id.calendarView);

       mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = year + "/" + (month + 1) + "/" + dayOfMonth;
            }
        });

        writeDiaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), diary_main.class);
                intent.putExtra("cur_date", date);
                startActivity(intent);
            }
        });
    }
}