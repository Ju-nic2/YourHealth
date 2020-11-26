package com.example.yourhealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;
import java.util.List;

public class sns_main extends AppCompatActivity implements View.OnClickListener{
    Button uploadBtn;

    private sns_main_recyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sns_main);

        uploadBtn = findViewById(R.id.button_upload);
        uploadBtn.setOnClickListener(this);

        init();

        getData();

    }
    private void init() {
        RecyclerView recyclerView = findViewById(R.id.sns_main_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new sns_main_recyclerAdapter();
        recyclerView.setAdapter(adapter);
    }
    private void getData() {


        List<String> category = Arrays.asList("#많은 사람들이 좋아한", "#힙업", "#자세교정", "#홈트");
        //FB에서 받아올 것 : 유저가 설정한 카테고리
        for (int i = 0; i < 4; i++) {
            //FB에서 받아올 것 : 게시글 제목이랑 작성자(일단)
            //하나의 postBox에 post 3개씩 넣을것임.
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
}