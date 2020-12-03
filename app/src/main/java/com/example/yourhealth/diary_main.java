package com.example.yourhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class diary_main extends AppCompatActivity {

    InputMethodManager imm;
    Button addDiaryBoxBtn;
    Button deleteDiaryBoxBtn;
    Button saveDiaryBoxBtn;
    LinearLayout diaryContainer;
    LinearLayout diaryBox;
    EditText diaryMemo;
    //TextView diaryDate;

    String memo;
    diary_data data = new diary_data();
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_main);

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        addDiaryBoxBtn = findViewById(R.id.button_add_diary_box);
        diaryContainer = findViewById(R.id.container_diary);
        diaryBox = findViewById(R.id.diary_box);
        saveDiaryBoxBtn = findViewById(R.id.button_save_diary_box);
        diaryMemo = findViewById(R.id.diary_memo);
        //diaryDate =findViewById(R.id.diary_date)
        //diaryDate.setText(date.toString());

        Intent intent = getIntent();
        date = intent.getExtras().getString("cur_date");

        final ArrayList<View> view_list = new ArrayList<View>();//상윤 추가:각 행들을 담을 배열

        //운동일지 행 추가 이벤트
        addDiaryBoxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //추가할 뷰를 객체화
                LayoutInflater layoutInflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view = layoutInflater.inflate(R.layout.diary_box, null);
                //행에 존재하는 삭제 이벤트 등록
                deleteDiaryBoxBtn = view.findViewById(R.id.button_delete_diary_box);
                deleteDiaryBoxBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view_list.remove(view);
                        ((LinearLayout)view.getParent()).removeView(view);
                    }
                });
                //객체화 된 뷰를 추가 시켜준다.
                view_list.add(view);
                //상윤 추가: 이렇게 view_list에 행들을 담아놓으면 나중에 저장 버튼에 리스너 달아서
                //for문으로 반복하면서 각각의 textview의 값을 가져와서 객체에 넣으면 될듯
                //그 객체는 class 하나 파서 만들어야 할듯
                diaryContainer.addView(view);
            }
        });

        saveDiaryBoxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < view_list.size(); i++)
                {
                    View diary_box  = view_list.get(i);
                    memo = diaryMemo.getText().toString();

                    EditText exercise = diary_box.findViewById(R.id.exercise);
                    EditText weight = diary_box.findViewById(R.id.weight);
                    EditText set = diary_box.findViewById(R.id.set);
                    EditText rep = diary_box.findViewById(R.id.rep);

                    data.setDate(date);
                    data.setMemo(memo);
                    data.setExercise(exercise.getText().toString());
                    data.setWeight(weight.getText().toString());
                    data.setSet(set.getText().toString());
                    data.setRep(rep.getText().toString());

                    Log.d("date", data.getDate());
                    Log.d("memo", data.getMemo());
                    Log.d("exercise", data.getExercise());
                    Log.d("weight", data.getWeight());
                    Log.d("set", data.getSet());
                    Log.d("rep", data.getRep());
                }
            }
        });

    }

    //바깥 터치시 키보드 내림
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View focusView = getCurrentFocus();
        if (focusView != null) {
            Rect rect = new Rect();
            focusView.getGlobalVisibleRect(rect);
            int x = (int) ev.getX(), y = (int) ev.getY();
            if (!rect.contains(x, y)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
                focusView.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}

