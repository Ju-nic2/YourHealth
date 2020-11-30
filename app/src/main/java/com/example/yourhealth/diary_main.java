package com.example.yourhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;

public class diary_main extends AppCompatActivity {

    InputMethodManager imm;
    Button addDiaryBoxBtn;
    Button deleteDiaryBoxBtn;
    LinearLayout diaryContainer;
    LinearLayout diaryBox;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_main);

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        addDiaryBoxBtn = findViewById(R.id.button_add_diary_box);
        diaryContainer = findViewById(R.id.container_diary);
        diaryBox = findViewById(R.id.diary_box);

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
                        ((LinearLayout)view.getParent()).removeView(view);
                    }
                });
                //객체화 된 뷰를 추가 시켜준다.
                diaryContainer.addView(view);
            }
        });

    }
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

