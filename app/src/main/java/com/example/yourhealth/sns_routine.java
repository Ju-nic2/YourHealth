package com.example.yourhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import de.hdodenhof.circleimageview.CircleImageView;

public class sns_routine extends AppCompatActivity {
    String name;
    String title;
    postContent postcontent;
    Button downloadBtn;
    String routineimage;

    int check = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sns_routine2);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        title = intent.getStringExtra("title");
        downloadBtn = findViewById(R.id.button_download);

        final Boolean heart_clicked = Boolean.FALSE;
        ImageView hartBtn = findViewById(R.id.heart);
        //하트 버튼        TextView numOfHeart = findViewById(R.id.NumOfHeart);
        //이거 널 오류 뜨네 왠지는 모르겠음
        //getNum_heart() 하니간 널오류임 음 업로드때 안넣어놔서 그런듯
        // numOfHeart.setText(postcontent.getNum_heart()+"");
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
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
                if(postcontent.getPhoto() != null){
                    RequestOptions option1 = new RequestOptions().circleCrop();
                    Glide.with(getApplicationContext()).load(postcontent.getPhoto()).placeholder(R.drawable.loading).into(image);

                }
                else{
                RequestOptions option1 = new RequestOptions().circleCrop();
                Glide.with(getApplicationContext()).load(R.drawable.sample).placeholder(R.drawable.loading).apply(option1).into(image);
                }

                TextView uploaderName = findViewById(R.id.name);
                TextView place = findViewById(R.id.text_place);
                TextView difficulty = findViewById(R.id.text_difficulty);
                TextView gender = findViewById(R.id.text_gender);
                TextView frequency = findViewById(R.id.text_frequency);
                TextView time = findViewById(R.id.text_time);
                CircleImageView imgView_place = findViewById(R.id.icon_place);
                CircleImageView imgView_gender = findViewById(R.id.icon_gender);

                String tmp = new String();
                routineTitle.setText(title);
                content.setText(postcontent.getContent());
                uploaderName.setText(name);
                if (postcontent.getPlace() == postcontent.PLACE_FITNESSCENTER){
                    tmp = "헬스장";
                    imgView_place.setImageResource(R.drawable.icon_gym);
                }
                else if (postcontent.getPlace() == postcontent.PLACE_HOME){
                    imgView_place.setImageResource(R.drawable.icon_home);
                    tmp = "집";
                }
                else if (postcontent.getPlace() == postcontent.PLACE_OUTDOOR){
                    imgView_place.setImageResource(R.drawable.icon_outdoor);
                    tmp = "야외";
                }
                place.setText(tmp);
                if (postcontent.getDifficulty()==postcontent.DIFFICULTY_NOVICE){
                    tmp = "초급";
                }
                else if(postcontent.getDifficulty()==postcontent.DIFFICULTY_INTERMEDIATE){
                    tmp = "중급";
                }
                else if(postcontent.getDifficulty()==postcontent.DIFFICULTY_ADVANCE){
                    tmp = "상급";
                }
                difficulty.setText(tmp);

                if(postcontent.getSex()==postcontent.SEX_MALE){
                    tmp = "남성";
                    imgView_gender.setImageResource(R.drawable.icon_male);
                }
                else if(postcontent.getSex()==postcontent.SEX_FEMALE){
                    tmp = "여성";
                    imgView_gender.setImageResource(R.drawable.icon_women);
                }
                gender.setText(tmp);


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

                if (postcontent.getTime()==postcontent.TIME_TWENTYTOFORTY){
                    tmp = "20~40분";
                }
                else if (postcontent.getTime()==postcontent.TIME_FORTYTOSEVENTY){
                    tmp = "40~70분";
                }
                else if (postcontent.getTime()==postcontent.TIME_ABOVESEVENTY){
                    tmp = "70분이상";
                }
               time.setText(tmp);


            }
        });
        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference washingtonRef = db.collection("Users").document(user.getUid());
                washingtonRef.update("storage", FieldValue.arrayUnion(postcontent.getRoutine()));
                Intent intent = new Intent(sns_routine.this, mainmenuActivity.class);
                startActivity(intent);
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