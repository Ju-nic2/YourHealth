package com.example.yourhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class sns_weekly extends AppCompatActivity {

    private final Handler mHandler = new Handler() {
        private final int MSG_A = 0 ;
        private final int MSG_B = 1 ;

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_A ://받은객체이용하여 화면에 띄워준다
                    getfbData((post)msg.obj);
                    Log.d("중복안되서 넣음이라는 메세지 받음", "상윤형담아 넣었데 ");

                    break;
                case MSG_B :
                    Log.d("중복되서 안넣음이라는 메세지 받음", "상윤형담아 안넣었데 "); break ;

                // TODO : add case.
            }
        }
    } ;
    private sns_weekly_recyclerAdapter.RecyclerAdapter adapter;
    String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        category = intent.getStringExtra("category");

        //데이터 받아오는 쓰레드 임 해당 카테고리에
        class weeklypost extends Thread{
            Handler handler = mHandler ;

            weeklypost(){}
            @Override
            public void run(){
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                final CollectionReference PostContents = db.collection("PostContents");
                PostContents.whereEqualTo("category1", category)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        post data = new post();
                                        data.setName(document.getData().get("userID").toString());
                                        data.setTitle(document.getData().get("title").toString());
                                        Message message = handler.obtainMessage() ;
                                        message.what = 0;
                                        message.obj = data;
                                        handler.sendMessage(message);
                                    }
                                } else {
                                }
                            }

                        });
                go();

            }
        }
        weeklypost getfdpost = new weeklypost();
        getfdpost.start();






    }
    private void go(){
        setContentView(R.layout.sns_weekly);
        TextView title = findViewById(R.id.Category);
        title.setText(category);
        init();

    }

    private void init() {


        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new sns_weekly_recyclerAdapter.RecyclerAdapter();
        recyclerView.setAdapter(adapter);
    }
     private void getfbData(post data) {


        //준익 수정 일단 있는만큼만.
         data.setResId(R.drawable.heart);
            data.setPostID(10241);

            adapter.addItem(data);
        adapter.notifyDataSetChanged();
    }

   /* private void getData() {

        //여기서 FB로부터 제목, 작성자, 프로필사진, 게시글 번호 가져옴. 20개.
        //준익 수정 일단 있는만큼만.
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

        adapter.notifyDataSetChanged();
    }*/
}