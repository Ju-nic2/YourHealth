package com.example.yourhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

public class mainmenuActivity extends AppCompatActivity {
    TextView username;
    TextView userpurpose;
    TextView userplace;

    ImageView userphoto;
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        username = findViewById(R.id.mainname);
        userpurpose = findViewById(R.id.mainpurpose);
        userplace = findViewById(R.id.mainpurpose2);
        userphoto = findViewById(R.id.mainPhoto);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("Users").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                       username.setText( document.getString("name"));
                        userpurpose.setText( document.getString("purpose"));
                        userplace.setText( document.getString("where"));

                            RequestOptions option1 = new RequestOptions().circleCrop();
                            Glide.with(getApplicationContext()).load(document.getString("userphothurl")).error(R.drawable.sample).apply(option1).into(userphoto);

                      //  Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                    //    Log.d(TAG, "No such document");
                    }
                } else {
                  //  Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });





    }
}