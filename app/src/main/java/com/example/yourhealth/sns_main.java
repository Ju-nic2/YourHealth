package com.example.yourhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class sns_main extends AppCompatActivity implements View.OnClickListener{

    Button uploadBtn;

    List<String> category1 = new ArrayList<String>();

    private sns_main_recyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sns_main);

        uploadBtn = findViewById(R.id.button_upload);
        uploadBtn.setOnClickListener(this);



        init();

        getCategory();

        getData();



    }
    public void init() {
        RecyclerView recyclerView = findViewById(R.id.sns_main_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new sns_main_recyclerAdapter();
        recyclerView.setAdapter(adapter);

    }
    public void getCategory(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference PostContents = db.collection("PostContents");
        int i=0;

        PostContents.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                 @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                     if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String tmp = document.getData().get("category1").toString();

                              Log.d("이게 섹스지",  " => " +tmp+"번 하자");

                         }
                     } else {
                    // Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                }
        });






    }
    public void getData()
    {

        Log.d("이게 섹스지",  " => " +category1.size()+"번 해야해");
        List<String> category = Arrays.asList("주간 인기 루틴", "#힙업", "#자세교정", "#홈트");
        for (int i = 0; i < 4; i++) {
            // 각 List의 값들을 data 객체에 set 해줍니다.
            post post1 = new post();
            post post2 = new post();
            post post3 = new post();
            postBox pBox = new postBox();

            post1.setName("이상윤");
            post1.setTitle("Title" + i);
            pBox.setPost1(post1);

            post2.setName("이상윤");
            post2.setTitle("Title_p2" + i);
            pBox.setPost2(post2);

            post3.setName("이상윤");
            post3.setTitle("Title_p3" + i);
            pBox.setPost3(post3);

            pBox.setCategory(category.get(i));

            // 각 값이 들어간 data를 adapter에 추가합니다.
            adapter.addItem(pBox);
        }

        // adapter의 값이 변경되었다는 것을 알려줍니다.
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if(view == uploadBtn){
            Intent intent = new Intent(getApplicationContext(), sns_upload.class);
            startActivity(intent);
        }
    }
    private void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}