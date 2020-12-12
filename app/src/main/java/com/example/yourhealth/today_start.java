package com.example.yourhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

public class today_start extends AppCompatActivity implements View.OnClickListener {
    private final Handler mHandler = new Handler() {
        private final int MSG_A = 0;
        private final int MSG_B = 1;

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_A://받은객체이용하여 화면에 띄워준다
                    myprofile = (profile) msg.obj;
                    curroutine =  myprofile.getRoutine();
                    if(curroutine ==  null  ) {
                        Intent intent = new Intent(getApplicationContext(), diary_main.class);
                        startActivity(intent);
                    }
                    else {
                        d = curroutine.getRoutine().get(curroutine.getLast());
                        Log.d("중복안되서 넣음이라는 메세지 받음", "상윤형담아 넣었데" + curroutine.getLast());
                        memo = d.getMemo();
                        sex();
                    }
                    break;
                case MSG_B:
                    Log.d("중복되서 안넣음이라는 메세지 받음", "상윤형담아 안넣었데 ");
                    break;

                // TODO : add case.
            }
        }
    };

    InputMethodManager imm;
    //Button addDiaryBoxBtn;
    //Button deleteDiaryBoxBtn;
    Button saveDiaryBoxBtn;
    LinearLayout todayContainer;
    //LinearLayout diaryBox;

    EditText diaryMemo;
    String memo;


    //파베가 어케 넘어오는지 몰라서 걍 객체 변수로 일단 코드짬
    //박스가 넘어와
    diary_data_box d;
    profile myprofile;
    Routine curroutine;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        class today extends Thread {
            Handler handler = mHandler;
            today() {
            }
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            @Override
            public void run() {
          DocumentReference docRef = db.collection("Users").document(user.getUid());
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("저장소 목록보자 ", "어디야 2 ");
                                profile  tmp= document.toObject(profile.class);
                                Message message = handler.obtainMessage();
                                message.obj = tmp;
                                handler.sendMessage(message);
                            } else {
                            }
                        } else {
                        }
                    }
                });
            }

        }
        today todayWorkout = new today();
        todayWorkout.start();


    }

    public void sex() {
        setContentView(R.layout.activity_today_start);
        saveDiaryBoxBtn = findViewById(R.id.button_save_today);
        saveDiaryBoxBtn.setOnClickListener(this);
        todayContainer = findViewById(R.id.container_today);
        diaryMemo = findViewById(R.id.today_memo);
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
            diaryMemo.setText(memo);
            exercise.setText(tmp.getExercise());
            weight.setText(tmp.getWeight());
            set.setText(tmp.getSet());
            rep.setText(tmp.getRep());

            //뷰 추가해
            todayContainer.addView(view);
        }

    }

    @Override
    public void onClick(View view) {
        if(view == saveDiaryBoxBtn){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat mFormat = new SimpleDateFormat("yyyy.MM.dd");
            String time = mFormat.format(date);
            Log.d("이거 저장 목록보자 ", time);
            d.setDate(time);
            db.collection(user.getUid()+"Daily").document(time).set(d);
            updatelast();
            Intent intent = new Intent(getApplicationContext(),mainmenuActivity.class);
            startActivity(intent);
        }
    }
    public void updatelast(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.d("이거 업데이트할꺼야  ", String.valueOf(curroutine.getLast()));
        if(curroutine.getLast()>=curroutine.getRoutine().size()){
            DocumentReference newCityRef = db.collection("Users").document(user.getUid());
            newCityRef.update("routine.last",0);
        }else{
            DocumentReference newCityRef = db.collection("Users").document(user.getUid());
            newCityRef.update("routine.last",curroutine.getLast()+1);
        }

    }
}