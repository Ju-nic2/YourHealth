package com.example.yourhealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class sns_weekly extends AppCompatActivity {
    private sns_weekly_recyclerAdapter.RecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sns_weekly);
        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        TextView title = findViewById(R.id.Category);
        title.setText(category);
        init();

        getData();
    }

    private void init() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new sns_weekly_recyclerAdapter.RecyclerAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void getData() {

        //여기서 FB로부터 제목, 작성자, 프로필사진, 게시글 번호 가져옴. 20개.
        for (int i = 0; i < 20; i++) {
            // 각 List의 값들을 data 객체에 set 해줍니다.
            post data = new post();
            data.setTitle("루틴 제목 " + i);
            data.setName("이상윤");
            data.setResId(R.drawable.heart);
            data.setPostID(10241);
            // 각 값이 들어간 data를 adapter에 추가
            // FB에서 가져와야함.
            adapter.addItem(data);
        }

        // adapter의 값이 변경되었다는 것을 알려줍니다.
        adapter.notifyDataSetChanged();
    }
}