package com.example.yourhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class mainmenuActivity extends AppCompatActivity implements View.OnClickListener {
    TextView username;
    TextView userpurpose;
    TextView userplace;

    ImageView userphoto;
    String image;

    CircleImageView snsBtn;
    CircleImageView todyaBtn;
    CircleImageView storageBtn;
    CircleImageView diaryBtn;

    profile  curuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        username = findViewById(R.id.mainname);
        userpurpose = findViewById(R.id.mainpurpose);
        userphoto = findViewById(R.id.mainPhoto);

        snsBtn = findViewById(R.id.snsmenubutton);
        todyaBtn = findViewById(R.id.todaymenubutton);
        storageBtn = findViewById(R.id.storagemenubutton);
        diaryBtn = findViewById(R.id.diarymenubutton);

        snsBtn.setOnClickListener(this);
        todyaBtn.setOnClickListener(this);
        storageBtn.setOnClickListener(this);
        diaryBtn.setOnClickListener(this);

        todyaBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), today_start.class);
                startActivity(intent);
            }
        });


        snsBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), sns_main.class);
                startActivity(intent);
            }
        });
        diaryBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), diary_start.class);
                startActivity(intent);
            }
        });
        storageBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), storage_my_routine.class);
                startActivity(intent);
            }
        });



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("Users").document(user.getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                curuser = documentSnapshot.toObject(profile.class);

                if(curuser.getUserphothurl()==null){
                    RequestOptions option1 = new RequestOptions().circleCrop();
                    Glide.with(getApplicationContext()).load(R.drawable.sample).apply(option1).into(userphoto);
                }else{
                    String tmp = curuser.getUserphothurl();
                    RequestOptions option1 = new RequestOptions().circleCrop();
                    Glide.with(getApplicationContext()).load(tmp).placeholder(R.drawable.loading).apply(option1).into(userphoto);
                }
                username.setText(curuser.getName()+" 님");
                userpurpose.setText(curuser.getPurpose());
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view == snsBtn){
            Intent intent = new Intent(getApplicationContext(), sns_main.class);
            startActivity(intent);
        }

    }
}