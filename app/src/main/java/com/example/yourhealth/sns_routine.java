package com.example.yourhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

public class sns_routine extends AppCompatActivity {
    String name;
    String title;
    postContent postcontent;

    int check = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sns_routine2);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        title = intent.getStringExtra("title");


        //FB에서 받아올 postContent
        //여기서 postcontent 에 받아왔으니깐 알아서 정보 꺼내서 써
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("PostContents").document(title+name);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                 postcontent = documentSnapshot.toObject(postContent.class);
                Log.d("이거 카테고리",  postcontent.getCategory1());
                Log.d("이거 카테고리",  postcontent.getCategory2());
                Log.d("이거 카테고리",  postcontent.getCategory3());
                Log.d("이거 카테고리",  postcontent.getUserID());

            }
        });


        final Boolean heart_clicked = Boolean.FALSE;





        ImageView hartBtn = findViewById(R.id.heart);//하트 버튼

        TextView numOfHeart = findViewById(R.id.NumOfHeart);
        //이거 널 오류 뜨네 왠지는 모르겠음
        //getNum_heart() 하니간 널오류임 음 업로드때 안넣어놔서 그런듯
       // numOfHeart.setText(postcontent.getNum_heart()+"");
        hartBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                ImageView heart = findViewById(R.id.heart);
                if (heart_clicked==Boolean.FALSE){
                    heart.setImageResource(R.drawable.heart);
                    //heart_clicked = Boolean.TRUE;
                    postcontent.setNum_heart(postcontent.getNum_heart()+1);
                    //numOfHeart.setText(postcontent.getNum_heart()+"");
                }
                else if (heart_clicked==Boolean.TRUE){
                    heart.setImageResource(R.drawable.heart_black);
                    //heart_clicked = Boolean.FALSE;
                    postcontent.setNum_heart(postcontent.getNum_heart()-1);
                    //numOfHeart.setText(postcontent.getNum_heart()+"");
                }
            }
        });

    }
}