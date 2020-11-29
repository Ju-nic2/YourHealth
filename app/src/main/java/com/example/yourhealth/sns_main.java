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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class sns_main extends AppCompatActivity implements View.OnClickListener{

    //데이터 처리 스레드 에서 전달된 객채를 다루는 핸들러
    private final Handler mHandler = new Handler() {
        private final int MSG_A = 0 ;
        private final int MSG_B = 1 ;

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_A ://받은객체이용하여 화면에 띄워준다
                    Log.d("중복안되서 넣음이라는 메세지 받음", "상윤형담아 넣었데 ");
                    getTestData((postBox) msg.obj);
                    break;
                    case MSG_B :
                    Log.d("중복되서 안넣음이라는 메세지 받음", "상윤형담아 안넣었데 "); break ;

                // TODO : add case.
            }
        }
    } ;

    Button uploadBtn;
    int check = 0;

    List<String> category1 = new ArrayList<String>();
    ArrayList list1 = new ArrayList();
    private sns_main_recyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //데이터 처리 스레드
        class categoryData extends Thread{
            categoryData(){}

            //받은 데이터 가공후 전달
            Handler handler = mHandler ;

            @Override
            public void run()
            {
                Log.d("데이터 가져오는중", " => " + "시작" + "카테고리");
                FirebaseFirestore db = FirebaseFirestore.getInstance();
               final CollectionReference PostContents = db.collection("PostContents");

                PostContents.get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {

                                        //카테고리 입력 받음
                                        String tmp = document.getData().get("category1").toString();
                                        //중복확인
                                        if(!list1.contains(tmp)) {
                                            list1.add(tmp);
                                            //원하는 카테고리에 있는 postcontent 가져옴
                                           PostContents.whereEqualTo("category1", tmp)
                                                    .get()
                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                            //postBox에 넣을 post 들 가공
                                                            post post1 = new post();
                                                            post post2 = new post();
                                                            post post3 = new post();
                                                            postBox pBox = new postBox();
                                                            //postBox 에는 3개의 post 만 넣을수 있음
                                                            int count = 0;
                                                            if (task.isSuccessful()) {
                                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                                    if(count ==0){
                                                                        post1.setName(document.getData().get("content").toString());
                                                                        post1.setTitle(document.getData().get("title").toString());
                                                                        pBox.setPost1(post1);
                                                                        pBox.setCategory(document.getData().get("category1").toString());
                                                                    }else if(count == 1){
                                                                        post2.setName(document.getData().get("content").toString());
                                                                        post2.setTitle(document.getData().get("title").toString());
                                                                        pBox.setPost2(post2);
                                                                    }else if(count == 2){
                                                                        post3.setName(document.getData().get("content").toString());
                                                                        post3.setTitle(document.getData().get("title").toString());
                                                                        pBox.setPost3(post3);
                                                                    }
                                                                    count++;
                                                                    Log.d("이게 데이타지", document.getData().get("category1").toString()+"여기제목"+document.getData().get("title").toString() + " => ");
                                                                }
                                                            } else {
                                                                Log.d("실패띠", "Error getting documents: ", task.getException());
                                                            }
                                                            //남은거 채워주기
                                                            if(count == 1 ){
                                                                post2.setName("준익");
                                                                post2.setTitle("준익루틴1");
                                                                pBox.setPost2(post2);
                                                                post3.setName("준익");
                                                                post3.setTitle("준익루틴2");
                                                                pBox.setPost3(post3);
                                                            }else if(count == 2){
                                                                post3.setName("준익");
                                                                post3.setTitle("준익루틴2");
                                                                pBox.setPost3(post3);
                                                            }
                                                            Message message = handler.obtainMessage() ;
                                                            message.what = 0;
                                                            message.obj = pBox;
                                                            //완성된 pBox전달
                                                            handler.sendMessage(message);
                                                        }
                                                    });

                                            }else{
                                            Message message = handler.obtainMessage() ;
                                                message.what = 1;
                                                Log.d("중복됨 안넣음", " => " + tmp + "카테고리");
                                                handler.sendMessage(message);
                                            }

                              // list1.add(tmp);
                                    }

                                } else {
                                    // Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                                go();
                                //getCategoryData();
                                Log.d("데이터 가져오는중", " => " + "진짜시작 =="+check + "카테고리");
                            }
                        });
            }
        }
        categoryData getcate = new categoryData();
        getcate.start();

     /*   Thread postThread = new Thread("postThread") {
            public void run() {
                Log.d("데이터 가져오는중", " => " + "시작" + "카테고리");
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference PostContents = db.collection("PostContents");
                for(int i=0;i<list1.size();i++) {
                    PostContents
                            .whereEqualTo("category1", list1.get(i))
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Log.d("이게 데이타지", document.getData().get("category1").toString()+"여기제목"+document.getData().get("title").toString() + " => ");
                                        }
                                    } else {
                                        Log.d("실패띠", "Error getting documents: ", task.getException());
                                    }
                                }
                            });
                }
         }

        };*/

       // getCategory();

    }
    public void getTestData(postBox pBox)
    {

            adapter.addItem(pBox);

        // adapter의 값이 변경되었다는 것을 알려줍니다.
        adapter.notifyDataSetChanged();
    }
    public void go(){
        setContentView(R.layout.sns_main);

        uploadBtn = findViewById(R.id.button_upload);
        uploadBtn.setOnClickListener(this);

        init();
       // getData();

    }

    public void init() {
        RecyclerView recyclerView = findViewById(R.id.sns_main_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new sns_main_recyclerAdapter();
        recyclerView.setAdapter(adapter);

    }
    public void getCategoryData()
    {

        Log.d("데이터 가져오는중", " => " + "시작" + "카테고리");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference PostContents = db.collection("PostContents");
        for(int i=0;i<list1.size();i++) {
            PostContents
                    .whereEqualTo("category1", list1.get(i))
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("이게 데이타지", document.getData().get("category1").toString()+"여기제목"+document.getData().get("title").toString() + " => ");
                                }
                            } else {
                                Log.d("실패띠", "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }


    }
    public void getCategory()
    {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference PostContents = db.collection("PostContents");
        PostContents.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                 @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                     if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String tmp = document.getData().get("category1").toString();
                             Log.d("데이터 가져오는중",  " => " +tmp+"카테고리");
                           // list1.add(tmp);
                       }

                     } else {
                    // Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                }
        });

    }
    public void getData()
    {

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

            pBox.setCategory(list1.get(i).toString());

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