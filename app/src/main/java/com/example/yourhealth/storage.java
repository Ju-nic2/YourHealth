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
import com.google.android.gms.tasks.OnFailureListener;
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

public class storage extends AppCompatActivity {
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
            final Intent intent = new Intent();
            switch (msg.what) {
                case MSG_A ://루틴목록 가져왔음
                    storaglist = getRealTitle(routinelist);
                    for (int i=0; i<storaglist.size(); i++){
                        Log.d("text", storaglist.get(i));
                        final Button button = new Button(storage.this);
                        button.setText(storaglist.get(i));
                        button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT){

                        });
                        button.setOnClickListener(new View.OnClickListener(){
                   @Override
                            public void onClick(View v) {
                                String text = button.getText().toString();
                                setroutine(text);
                                Intent intent = new Intent(storage.this, mainmenuActivity.class);
                                startActivity(intent);
                            }
                        });
                        lin.addView(button);
                        final String tmp = storaglist.get(i);

                   }
                 break;
                case MSG_B :
                    Log.d("저장소 목록 못불러옴", "이게 아닌데 "); break ;

                // TODO : add case.
            }
        }
    } ;
    ArrayList<String> routinelist = new ArrayList();
    ArrayList<String> storaglist = new ArrayList();

    Button writeRoutineBtn;
    Routine curroutine;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        Button button = findViewById(R.id.button);
        final Intent intent = new Intent(this, storage_my_routine.class);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(intent);

            }
        });


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
            tmp = tmp.substring(tmp.lastIndexOf("#")+1);
            cut.add(tmp);
            Log.d("저장소 목록 불러옴", cut.get(i)+" 이거지");
        }
        return cut;
    }
    public void setroutine(String tmp){
        int a=0;
        for(int i = 0; i<routinelist.size();i++){
            String t = (String) routinelist.get(i);
            t = t.substring(t.lastIndexOf("#")+1);
            if(tmp.equals(t))
                a=i;
          //  Log.d("저장소 목록 불러옴", cut.get(i)+" 이거지");
        }
        Log.d("이거루틴지정", routinelist.get(a));
        DocumentReference docRef = db.collection("Routins").document(routinelist.get(a));
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                //데이터 가져오기 성공해
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        curroutine = document.toObject(Routine.class);
                        updatduser();
                        Log.d("루틴 불러옴", curroutine.getTitle()+" 이거지");
                    } else {
                    }
                }
            }
        });


    }
    public void updatduser(){
        DocumentReference newCityRef = db.collection("Users").document(user.getUid());
        newCityRef.update("routine", curroutine)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("없데이트 불러옴", curroutine.getTitle()+" 이거지");
                        //Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //  Log.w(TAG, "Error updating document", e);
                    }
                });
    }



}
