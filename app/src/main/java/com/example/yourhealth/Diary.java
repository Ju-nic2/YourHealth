package com.example.yourhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class Diary extends AppCompatActivity {

    Button pushBtn;
    Button popBtn;
    LinearLayout stateContainer;
    LinearLayout stateRow;
    //View view;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        pushBtn = findViewById(R.id.pushButton);
        stateContainer = findViewById(R.id.container);
        stateRow = findViewById(R.id.stateLinear);


        pushBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view = layoutInflater.inflate(R.layout.state_row, null);
                popBtn = view.findViewById(R.id.popButton);
                popBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((LinearLayout)view.getParent()).removeView(view);
                    }
                });
                stateContainer.addView(view);
            }
        });
    }
}