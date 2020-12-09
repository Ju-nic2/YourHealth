package com.example.yourhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class today_start extends AppCompatActivity {

    InputMethodManager imm;
    //Button addDiaryBoxBtn;
    //Button deleteDiaryBoxBtn;
    //Button saveDiaryBoxBtn;
    LinearLayout todayContainer;
    //LinearLayout diaryBox;
    EditText diaryMemo;


    //파베가 어케 넘어오는지 몰라서 걍 객체 변수로 일단 코드짬
    //박스가 넘어와
    diary_data_box d;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_start);

        todayContainer = findViewById(R.id.container_today);
        diaryMemo = findViewById(R.id.today_memo);

        //넘어온 박스에 Day어레이리스트(데이터) 사이즈만큼 돌아
        for (int i = 0; i < d.getDay().size(); i++ )
        {
            //임시tmp에 데이에있는 원소 하나 박아
            diary_data tmp = d.getDay().get(i);
            //뷰 생성해
            LayoutInflater layoutInflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            todayContainer.addView(view);
        }
    }
}