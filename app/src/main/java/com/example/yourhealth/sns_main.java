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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class sns_main extends AppCompatActivity implements View.OnClickListener{
    Button uploadBtn;
    final List<String> category1 = new ArrayList<String>();
    private sns_main_recyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sns_main);

        uploadBtn = findViewById(R.id.button_upload);
        uploadBtn.setOnClickListener(this);

        init();

        getCategory();

    }
    private void init() {
        RecyclerView recyclerView = findViewById(R.id.sns_main_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new sns_main_recyclerAdapter();
        recyclerView.setAdapter(adapter);
    }
    private void getCategory(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("PostContents")
                //  .whereEqualTo("title", "sex")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                category1.add(document.getData().get("category1").toString());

                                Log.d("크기확임", "이것좀봐바"+ " => " +category1.size());
                            }
                        } else {
                            // Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        getData();
    }
    private void getData() {

        Log.d("데이터 가져올래", "이것좀봐바"+ " => " +category1.size());

        for(int j=0;j<category1.size();j++) {
           final ArrayList<post> postlist = new ArrayList<>();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("PostContents")
                    .whereEqualTo("category1", category1.get(j))
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                   postlist.add(new post(
                                           document.getData().get("title").toString(),
                                           document.getData().get("content").toString(),
                                           1,1));
                                   showToast(postlist.get(0).getName());
                                      Log.d("로그", "데이타확인" + " => " + document.getData().get("title"));
                                }
                            } else {
                                showToast("실패");
                                // Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });

        }


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

            pBox.setCategory(category.get(0));

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