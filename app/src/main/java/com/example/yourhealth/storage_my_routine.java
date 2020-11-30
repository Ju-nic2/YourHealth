package com.example.yourhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class storage_my_routine extends AppCompatActivity {

    Button addMyRoutineBoxBtn;
    Button deleteMyRoutineBoxBtn;
    Button addDiaryBoxBtn;
    Button deleteDiaryBoxBtn;
    LinearLayout myRoutineContainer;
    LinearLayout diaryContainer;
    LinearLayout myRoutineBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_my_routine);

        addMyRoutineBoxBtn = findViewById(R.id.button_add_my_routine_box);
        //deleteMyRoutineBoxBtn = findViewById(R.id.button_delete_my_routine_box);
        //addDiaryBoxBtn = findViewById(R.id.button_add_diary_box_myR);
        //deleteDiaryBoxBtn = findViewById(R.id.button_delete_diary_box);
        myRoutineContainer = findViewById(R.id.container_storage_my_routine);
        //diaryContainer = findViewById(R.id.container_diary_myR);
        myRoutineBox = findViewById(R.id.my_routine_Box);

        addMyRoutineBoxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view = layoutInflater.inflate(R.layout.my_routine_box, null);
                diaryContainer = view.findViewById(R.id.container_diary_myR);
                deleteMyRoutineBoxBtn = view.findViewById(R.id.button_delete_my_routine_box);
                addDiaryBoxBtn = view.findViewById(R.id.button_add_diary_box_myR);
                deleteMyRoutineBoxBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((LinearLayout)view.getParent()).removeView(view);
                    }});

                addDiaryBoxBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        final View view1 = layoutInflater.inflate(R.layout.diary_box_my_routine, null);
                        deleteDiaryBoxBtn = view1.findViewById(R.id.button_delete_diary_box_myR);
                        deleteDiaryBoxBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ((LinearLayout)view1.getParent()).removeView(view1);
                            }
                        });
                        diaryContainer.addView(view1);
                    }
                });
                myRoutineContainer.addView(view);
            }
        });
    }
}