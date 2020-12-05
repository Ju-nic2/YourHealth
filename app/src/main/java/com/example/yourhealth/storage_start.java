package com.example.yourhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class storage_start extends AppCompatActivity {

    Button writeRoutineBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_start);

        writeRoutineBtn = findViewById(R.id.button_write_routine);

        writeRoutineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), storage_my_routine.class);
                startActivity(intent);
            }
        });

    }
}