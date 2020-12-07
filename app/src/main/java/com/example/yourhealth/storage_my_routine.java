package com.example.yourhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class storage_my_routine extends AppCompatActivity {

    Button addMyRoutineBoxBtn;
    Button deleteMyRoutineBoxBtn;
    Button addDiaryBoxBtn;
    Button deleteDiaryBoxBtn;
    LinearLayout myRoutineContainer;
    LinearLayout diaryContainer;
    LinearLayout myRoutineBox;
    ArrayList<diary_data_box> day_list = new ArrayList<diary_data_box>();

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
                view.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(this, storage_my_routine_diary.class);
                        startActivityForResult(intent, 11);
                    }
                });
                deleteMyRoutineBoxBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((LinearLayout)view.getParent()).removeView(view);
                    }});


                myRoutineContainer.addView(view);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode==RESULT_OK) {

            Intent intent = getIntent();
            diary_data_box d = (diary_data_box) intent.getSerializableExtra("data");

            day_list.add(d);
        }


    }


}