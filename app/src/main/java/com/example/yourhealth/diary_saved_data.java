package com.example.yourhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class diary_saved_data extends AppCompatActivity {

    InputMethodManager imm;
    diary_data_box d;

    //Button addSavedData;
    LinearLayout savedDataContainer;
    EditText savedDataMemo;
    String memo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_saved_data);
        Button saveSavedDataBtn = findViewById(R.id.button_save_saved_data);
        //addSavedData.findViewById(R.id.button_add_saved_data);

        Intent intent = getIntent();
        d = intent.getParcelableExtra("data");
        memo = d.getMemo();

        for(int i = 0; i < d.getDay().size(); i++)
        {
            Log.d("exercise", d.getDay().get(i).getExercise());
            Log.d("weight", d.getDay().get(i).getWeight());
            Log.d("set", d.getDay().get(i).getSet());
            Log.d("rep", d.getDay().get(i).getRep());
        }



        //setContentView(R.layout.activity_today_start);
        savedDataContainer = findViewById(R.id.container_saved_data);
        savedDataMemo = findViewById(R.id.saved_data_memo);

        //넘어온 박스에 Day어레이리스트(데이터) 사이즈만큼 돌아
        for (int i = 0; i < d.getDay().size(); i++) {
            //임시tmp에 데이에있는 원소 하나 박아
            diary_data tmp = d.getDay().get(i);
            //뷰 생성해
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View view = layoutInflater.inflate(R.layout.diary_box, null);

            //

            //생성한거에 에딧텍스트 가져와
            EditText exercise = view.findViewById(R.id.exercise);
            EditText weight = view.findViewById(R.id.weight);
            EditText set = view.findViewById(R.id.set);
            EditText rep = view.findViewById(R.id.rep);

            //설정 해줘
            savedDataMemo.setText(memo);
            exercise.setText(tmp.getExercise());
            weight.setText(tmp.getWeight());
            set.setText(tmp.getSet());
            rep.setText(tmp.getRep());

            //뷰 추가해
            savedDataContainer.addView(view);
        }

        saveSavedDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToStart = new Intent(getApplicationContext(), diary_start.class);
                startActivity(backToStart);
            }
        });
    }




    /*public void sex() {
        setContentView(R.layout.activity_today_start);
        savedDataContainer = findViewById(R.id.container_saved_data);
        savedDataMemo = findViewById(R.id.saved_data_memo);
        //넘어온 박스에 Day어레이리스트(데이터) 사이즈만큼 돌아
        for (int i = 0; i < d.getDay().size(); i++) {
            //임시tmp에 데이에있는 원소 하나 박아
            diary_data tmp = d.getDay().get(i);
            //뷰 생성해
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View view = layoutInflater.inflate(R.layout.diary_box, null);

            //생성한거에 에딧텍스트 가져와
            EditText exercise = view.findViewById(R.id.exercise);
            EditText weight = view.findViewById(R.id.weight);
            EditText set = view.findViewById(R.id.set);
            EditText rep = view.findViewById(R.id.rep);

            //설정 해줘
            exercise.setText(tmp.getExercise());
            weight.setText(tmp.getWeight());
            set.setText(tmp.getSet());
            rep.setText(tmp.getRep());

            //뷰 추가해
            savedDataContainer.addView(view);
        }

    }*/
}