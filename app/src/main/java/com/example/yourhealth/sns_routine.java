package com.example.yourhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

        final Boolean heart_clicked = Boolean.FALSE;
        ImageView hartBtn = findViewById(R.id.heart);
        //하트 버튼        TextView numOfHeart = findViewById(R.id.NumOfHeart);
        //이거 널 오류 뜨네 왠지는 모르겠음
        //getNum_heart() 하니간 널오류임 음 업로드때 안넣어놔서 그런듯
        // numOfHeart.setText(postcontent.getNum_heart()+"");



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
                TextView routineTitle = findViewById(R.id.text_title);
                TextView content = findViewById(R.id.text_content);
                ImageView image = findViewById(R.id.main_image);
                TextView uploaderName = findViewById(R.id.name);
                TextView place = findViewById(R.id.text_place);
                TextView difficulty = findViewById(R.id.text_difficulty);
                TextView gender = findViewById(R.id.text_gender);
                TextView frequency = findViewById(R.id.text_frequency);
                TextView time = findViewById(R.id.text_time);

                routineTitle.setText(title);
                content.setText(postcontent.getContent());
                uploaderName.setText(name);
                place.setText(postcontent.getPlace() + "");
                difficulty.setText(postcontent.getDifficulty() + "");
                gender.setText(postcontent.getSex() + "");

                String tmp = new String();
                if(postcontent.getFrequency() == postcontent.FREQUENCY_FOURFIVE){
                    tmp = "주 4~5일";
                }
                else if (postcontent.getFrequency()==postcontent.FREQUENCY_SIXTOSEVEN){
                    tmp = "주 6~7일";
                }
                else if (postcontent.getFrequency()==postcontent.FREQUENCY_TOTHREE){
                    tmp = "주 2~3일";
                }

                frequency.setText(tmp);

                time.setText(postcontent.getTime()+"");


            }
        });



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