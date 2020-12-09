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

import java.util.ArrayList;

public class storage_my_routine_diary extends AppCompatActivity {

    Button addDiaryBoxBtn;
    Button deleteDiaryBoxBtn;
    Button saveDiaryBoxBtn;
    LinearLayout diaryContainer;
    LinearLayout diaryBox;
    EditText diaryMemo;
    InputMethodManager imm;
    String memo;
    diary_data_box dataBox = new diary_data_box();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_my_routine_diary);

        addDiaryBoxBtn = findViewById(R.id.button_add_my_routine_diary_box);
        saveDiaryBoxBtn = findViewById(R.id.button_save_my_routine_diary_box);
        diaryContainer = findViewById(R.id.container_my_routine_diary);
        diaryBox = findViewById(R.id.my_routine_diary_box);
        diaryMemo = findViewById(R.id.storage_my_routine_diary_memo);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        final ArrayList<View> view_list = new ArrayList<View>();

        //운동일지 행 추가 버튼
        addDiaryBoxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //추가할 뷰 객체화
                LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view = layoutInflater.inflate(R.layout.my_routine_diary_box, null);

                //운동일지 행 삭제 버튼
                deleteDiaryBoxBtn = view.findViewById(R.id.button_delete_my_routine_box);
                deleteDiaryBoxBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view_list.remove(view);
                        ((LinearLayout)view.getParent()).removeView(view);
                    }
                });
                view_list.add(view);
                diaryContainer.addView(view);
            }

        });

        saveDiaryBoxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memo = diaryMemo.getText().toString();
                dataBox.setMemo(memo);

                for(int i = 0; i < view_list.size(); i++)
                {

                    View data_tmp  = view_list.get(i);
                    diary_data data = new diary_data();

                    EditText exercise = data_tmp.findViewById(R.id.exercise);
                    EditText weight = data_tmp.findViewById(R.id.weight);
                    EditText set = data_tmp.findViewById(R.id.set);
                    EditText rep = data_tmp.findViewById(R.id.rep);

                    data.setExercise(exercise.getText().toString());
                    data.setWeight(weight.getText().toString());
                    data.setSet(set.getText().toString());
                    data.setRep(rep.getText().toString());

                    //Log.d("exercise", data.getExercise());
                    //Log.d("weight", data.getWeight());
                    //Log.d("set", data.getSet());
                    //Log.d("rep", data.getRep());

                    dataBox.getDay().add(data);
                }

                for(int i = 0; i < dataBox.getDay().size(); i++)
                {
                    Log.d("exercise", dataBox.getDay().get(i).getExercise());
                    Log.d("weight", dataBox.getDay().get(i).getWeight());
                    Log.d("set", dataBox.getDay().get(i).getSet());
                    Log.d("rep", dataBox.getDay().get(i).getRep());
                }

                //객체 넘겨주고 화면전환
                Intent intent = new Intent();
                intent.putExtra("data", dataBox);
                setResult(RESULT_OK, intent); //응답 전달 후
                finish();  //종료

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