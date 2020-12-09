package com.example.yourhealth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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

import java.util.ArrayList;

public class storage_start extends AppCompatActivity {
    private class stringTMP{
        String string;

        public String getString() {
            return string;
        }

        public void setString(String string) {
            this.string = string;
        }
    }
    private final Handler mHandler = new Handler() {
        private final int MSG_A = 0 ;
        private final int MSG_B = 1 ;

        @Override
        public void handleMessage(Message msg) {
            final stringTMP s = new stringTMP();



            LinearLayout lin = findViewById(R.id.linear);
            final Intent intent = new Intent(getApplicationContext(), sns_upload.class);
            switch (msg.what) {
                case MSG_A ://루틴목록 가져왔음
                  routinelist = getRealTitle(routinelist);
                  for (int i=0; i<routinelist.size(); i++){

                      final Button button = new Button(storage_start.this);
                      button.setText(routinelist.get(i));
                      lin.addView(button);
                      s.setString(routinelist.get(i));
                      button.setOnClickListener(new View.OnClickListener(){

                          @Override
                          public void onClick(View view) {

                              intent.putExtra("data", s.getString());
                              setResult(RESULT_OK, intent); //응답 전달 후
                              finish();  //종료
                          }
                      });


                  }


                    break;
                case MSG_B :
                    Log.d("저장소 목록 못불러옴", "이게 아닌데 "); break ;

                // TODO : add case.
            }
        }
    } ;
    ArrayList<String> routinelist = new ArrayList();

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

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.d("저장소 목록보자 ", user.getUid());
        class weeklypost extends Thread{
                            Handler handler = mHandler ;
                            weeklypost(){}
                            @Override
                            public void run(){
                                Log.d("저장소 목록보자 ", "어디야 1 ");
                                final DocumentReference docRef = db.collection("Users").document(user.getUid());
                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                Log.d("저장소 목록보자 ", "어디야 2 ");
                                                routinelist = (ArrayList) document.get("storage");
                                                Message message = handler.obtainMessage();
                                                if(routinelist.size()>0) {
                                                    message.what = 0;
                                                }else{
                                                    message.what = 1;
                                                }
                                                handler.sendMessage(message);
                                            } else {
                                            }
                                        } else {
                                        }
                    }
                });
            }
        }
        Log.d("저장소 목록보자 ", "어디야 3 ");
        weeklypost getfdpost = new weeklypost();
        getfdpost.start();


    }
    public ArrayList getRealTitle( ArrayList tmplist){

        ArrayList cut = new ArrayList();
        for(int i = 0; i<tmplist.size();i++){
            String tmp = (String) tmplist.get(i);
            // "#" 문자 이후 문자열부터 끝까지
            tmp = tmp.substring(tmp.lastIndexOf("#")+1);
            cut.add(tmp);
            Log.d("저장소 목록 불러옴", cut.get(i)+" 이거지");
        }
        return cut;
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }


}
