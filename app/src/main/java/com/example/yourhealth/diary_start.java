package com.example.yourhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class diary_start extends AppCompatActivity {

    Button writeDiaryBtn;
    private CalendarView mCalendarView;
    String date;
    //그날 한 운동이 있으면 여기에 가져와서 저장
    diary_data_box dailydata ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_start);

        writeDiaryBtn = findViewById(R.id.button_write_diary);
        mCalendarView = findViewById(R.id.calendarView);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
         final FirebaseFirestore db = FirebaseFirestore.getInstance();


       mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // 달력에서 선택한 날짜와
                date = year + "." + (month + 1) + "." + dayOfMonth;
                //그 날짜에 해당하는 운동 데이터가 있는지 확인
                DocumentReference docRef = db.collection(user.getUid()+"Daily").document(date);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        //데이터 가져오기 성공해
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                //그 날짜에 해당하는 운동 데이터가 있다면 dailydata 에 diary_data_boxg객체 고대로 저장.
                               dailydata = document.toObject(diary_data_box.class);
                                for(int i = 0; i < dailydata.getDay().size(); i++)
                                {
                                    Log.d("exercise", dailydata.getDay().get(i).getExercise());
                                    Log.d("weight", dailydata.getDay().get(i).getWeight());
                                    Log.d("set", dailydata.getDay().get(i).getSet());
                                    Log.d("rep", dailydata.getDay().get(i).getRep());
                                }
                                //운동일지 불러오기로 버튼 바꿔짐
                                writeDiaryBtn.setText( "운동일지 불러오기");
                             //   Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            } else {
                                //그 날짜에 해당하는 운동 데이터가 없다면 작성 해
                                writeDiaryBtn.setText(date + " 운동일지 작성");
                               // Log.d(TAG, "No such document");
                            }
                        } else {
                            //아예 문서가 없데그 날짜에 해당하는 운동  작성 해`
                            writeDiaryBtn.setText(date + " 운동일지 작성");
                           // Log.d(TAG, "get failed with ", task.getException());
                        }
                    }

                });

            }
        });

        writeDiaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(date != null)
                {
                    Intent intent = new Intent(getApplicationContext(), diary_main.class);
                    intent.putExtra("cur_date", date);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(diary_start.this, "날짜를 선택해주세요.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}