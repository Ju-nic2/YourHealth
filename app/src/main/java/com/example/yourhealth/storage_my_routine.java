package com.example.yourhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.util.Log;
import android.widget.TextView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;

public class storage_my_routine extends AppCompatActivity {

    Button addMyRoutineBoxBtn;
    Button deleteMyRoutineBoxBtn;
    Button saveRoutineBoxBtn;
    Button addDiaryBoxBtn;
    Button deleteDiaryBoxBtn;
    LinearLayout myRoutineContainer;
    LinearLayout diaryContainer;
    LinearLayout myRoutineBox;
    int index = 0;
    TextView myRoutineMemo;


    EditText routineTitle;
    String title;


    ArrayList<diary_data_box> day_list = new ArrayList<diary_data_box>();
    String memo;

    Routine myRoutine;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_my_routine);
        user = FirebaseAuth.getInstance().getCurrentUser();
        saveRoutineBoxBtn = findViewById(R.id.button_save_routine_box);
        routineTitle = findViewById(R.id.routineTitle);

        addMyRoutineBoxBtn = findViewById(R.id.button_add_my_routine_box);
        //deleteMyRoutineBoxBtn = findViewById(R.id.button_delete_my_routine_box);
        //addDiaryBoxBtn = findViewById(R.id.button_add_diary_box_myR);
        //deleteDiaryBoxBtn = findViewById(R.id.button_delete_diary_box);
        myRoutineContainer = findViewById(R.id.container_storage_my_routine);
        //diaryContainer = findViewById(R.id.container_diary_myR);
        myRoutineBox = findViewById(R.id.my_routine_Box);


        addMyRoutineBoxBtn.setOnClickListener(new View.OnClickListener() {
            int index=-1;
            @Override
            public void onClick(View v) {
                index+=1;
                LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view = layoutInflater.inflate(R.layout.my_routine_box, null);
                diaryContainer = view.findViewById(R.id.container_diary_myR);
                deleteMyRoutineBoxBtn = view.findViewById(R.id.button_delete_my_routine_box);


                myRoutineMemo = view.findViewById(R.id.my_routine_memo);


                view.setOnClickListener(new View.OnClickListener(){
                   int i=index;


                    @Override
                    public void onClick(View view) {
                        //원래 니코드인데 오류남 this 써서 그런거같은데 니꺼에선 안남?
                        //Intent intent = new Intent(this, storage_my_routine_diary.class);
                        //밑에걸로 수정함 그러니까 돌아는감

                        myRoutineMemo = view.findViewById(R.id.my_routine_memo);
                        Intent intent = new Intent(getApplicationContext(), storage_my_routine_diary.class);

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

        saveRoutineBoxBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                title = routineTitle.getText().toString();
                myRoutine = new Routine(title,user.getUid(),day_list,-1);
                DocumentReference newCityRef = db.collection("Users").document(user.getUid());

                newCityRef.update("storage", FieldValue.arrayUnion(user.getUid()+"#"+title))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //Log.d(TAG, "DocumentSnapshot successfully updated!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //  Log.w(TAG, "Error updating document", e);
                            }
                        });

                db.collection("Routins").document(user.getUid()+"#"+title).set(myRoutine)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                 Log.d("루틴저장", "루틴 저장함"+title);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("루틴 저장 실패", "루틴 저장못함"+title);
                            }
                        });
                finish();
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode==RESULT_OK) {
            diary_data_box d = data.getParcelableExtra("data");
            //diary_data_box d = (diary_data_box) data.getSerializableExtra("data");

            Log.d("hi","hi");
            //Log.d("memo", d.getMemo());
           
            myRoutineMemo.setText(d.getMemo());

            day_list.add(d);
            Log.d("hi","hi");
        }


    }


}