package com.example.yourhealth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;

import android.content.Context;

import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class sns_upload extends AppCompatActivity  {

    Button imageUplode;
    Button routinUploadBtn;

    ImageView snsimage;
    Uri snsimageUri;
    String snsphotourl;

    TextView routineText;


    postContent post = new postContent();
    tmpstorageService tmpsave;
    boolean mBound = false;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            Log.d("여기는 업로드다 오바 ", "데이터 전달 하고싶다" );
            tmpstorageService.MyBinder mb = (tmpstorageService.MyBinder ) service;
            tmpsave = mb.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            tmpsave = null;
            mBound = false;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sns_upload);
      //  Intent intent = new Intent(getApplicationContext(), tmpstorageService.class);
      //  startService(intent);
       // bindService(intent, // intent 객체
         //       mConnection, // 서비스와 연결에 대한 정의
       //         Context.BIND_AUTO_CREATE);
        Log.d("여기는 업로드다 오바 ", "서비스 시작 하고싶다" );



        //서비스 켜져있는지파악, 켜져있다면 데이타 있는지 파악. 있다면 받아옴

        imageUplode = findViewById(R.id.button_imageupload);
        routinUploadBtn = findViewById(R.id.button_routineupload);
        routineText = findViewById(R.id.routineText);

        snsimage = findViewById(R.id.snsimage);

        Button upload = findViewById(R.id.button_upload);
        Button tempSave = findViewById(R.id.button_tempSave);
        RadioGroup place = findViewById(R.id.RadioGroub_place);
        RadioGroup difficulty = findViewById(R.id.RadioGroub_difficulty);
        RadioGroup sex = findViewById(R.id.RadioGroub_sex);
        RadioGroup frequency = findViewById(R.id.RadioGroub_frequency);
        RadioGroup time = findViewById(R.id.RadioGroub_time);

        place.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.button_fitnessCenter) post.setPlace(post.PLACE_FITNESSCENTER);
                else if (i == R.id.button_home) post.setPlace(post.PLACE_HOME);
                else if (i == R.id.button_outdoor) post.setPlace(post.PLACE_OUTDOOR);
            }
        });

        difficulty.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.button_novice) post.setDifficulty(post.DIFFICULTY_NOVICE);
                else if (i == R.id.button_intermediate)
                    post.setDifficulty(post.DIFFICULTY_INTERMEDIATE);
                else if (i == R.id.button_advance) post.setDifficulty(post.DIFFICULTY_ADVANCE);
            }
        });
        sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.button_male) post.setSex(post.SEX_MALE);
                else if (i == R.id.button_female) post.setSex(post.SEX_FEMALE);
            }
        });
        frequency.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.button_twoToThree) post.setFrequency(post.FREQUENCY_TOTHREE);
                else if (i == R.id.button_fourToFive) post.setFrequency(post.FREQUENCY_FOURFIVE);
                else if (i == R.id.button_sixToSeven) post.setFrequency(post.FREQUENCY_SIXTOSEVEN);
            }
        });
        time.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.button_twentyToForty) post.setTime(post.TIME_TWENTYTOFORTY);
                else if (i == R.id.button_fortyToSeventy) post.setTime(post.TIME_FORTYTOSEVENTY);
                else if (i == R.id.button_aboveSeventy) post.setTime(post.TIME_ABOVESEVENTY);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                EditText title = (EditText) findViewById(R.id.text_title);
                EditText content = (EditText) findViewById(R.id.text_content);
                EditText category1 = (EditText) findViewById(R.id.category1);
                EditText category2 = (EditText) findViewById(R.id.category2);
                EditText category3 = (EditText) findViewById(R.id.category3);
                EditText category4 = (EditText) findViewById(R.id.category4);

                post.setTitle(title.getText().toString());
                post.setContent(content.getText().toString());
                post.setCategory1("#"+category1.getText().toString());
                post.setCategory2("#"+category2.getText().toString());
                post.setCategory3("#"+category3.getText().toString());
                post.setCategory4("#"+category4.getText().toString());
                post.setCompleted(true);

                /*  Log.d("Title", post.getTitle());
                Log.d("Content", post.getContent());
                Log.d("Place", "" + post.getPlace());
                Log.d("Difficulty", "" + post.getDifficulty());
                Log.d("Sex", "" + post.getSex());
                Log.d("Frequency", "" + post.getFrequency());

                Log.d("Time", "" + post.getTime());
                uploadsns();
                ComponentName componentName = new ComponentName(
                        "com.example.yourhealth",
                        "com.example.yourhealth.sns_main"
                );
                Intent intent1 = new Intent();
                intent1.setComponent(componentName);

                startActivity(intent1);

                Log.d("Time", "" + post.getTime());*/
                uploadsns();
                Intent intent = new Intent(getApplicationContext(), sns_main.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
               // post = tmpsave.getTmppost();
                Log.d("여기는 업로드다 오바 ", "데이터 받아왔다 하고싶다"+post.getSex() );



                //post 객체 FB로 업로드

            }
        });
        imageUplode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), chooseActivity.class);
                startActivityForResult(intent, 1235);
            }
        });
        routinUploadBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //루틴 제목 불러오기
                Intent intent = new Intent(getApplicationContext(), storage_start.class);
                startActivityForResult(intent, 1212);
                //set Post 유저 UiD+불러온 루틴 제목
            }
        });
        tempSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                EditText title = (EditText) findViewById(R.id.text_title);
                EditText content = (EditText) findViewById(R.id.text_content);
                EditText category1 = (EditText) findViewById(R.id.category1);
                EditText category2 = (EditText) findViewById(R.id.category2);
                EditText category3 = (EditText) findViewById(R.id.category3);
                EditText category4 = (EditText) findViewById(R.id.category4);

                post.setTitle(title.getText().toString());
                post.setContent(content.getText().toString());
                post.setCategory1("#"+category1.getText().toString());
                post.setCategory2("#"+category2.getText().toString());
                post.setCategory3("#"+category3.getText().toString());
                post.setCategory4("#"+category4.getText().toString());

                post.setCompleted(false);

                Log.d("Title", post.getTitle());
                Log.d("Content", post.getContent());
                Log.d("Place", "" + post.getPlace());
                Log.d("Difficulty", "" + post.getDifficulty());
                Log.d("Sex", "" + post.getSex());
                Log.d("Frequency", "" + post.getFrequency());
                Log.d("Time", "" + post.getTime());
                tmpsave.setTmppost(post);

                Intent intent = new Intent(getApplicationContext(), tmpstorageService.class);
                intent.putExtra("postContent",post);
                startService(intent);


                ComponentName componentName = new ComponentName(
                        "com.example.yourhealth",
                        "com.example.yourhealth.sns_main"
                );
                Intent intent1 = new Intent();
                intent1.setComponent(componentName);

                startActivity(intent1);



               // uploadsns();

                //post 객체 FB로 업로드

            }
        });

    }
    public void name(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.d("이게 유아디지", user.getUid());
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                //현재 로그인한사람의 이름 가져와야함
                String name = profile.getDisplayName();
             //   Log.d("이게 유아디지",name);
                post.setUserUid(user.getUid().toString());
                post.setUserID(name);

            }
        }
    }



    public void uploadsns(){

        name();
        FirebaseFirestore db = FirebaseFirestore.getInstance();// title+name 이 게시물 고유키
        db.collection("PostContents").document(post.getTitle()+post.getUserID()).set(post)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        showToast("저장굳");
                      //  Intent intent = new Intent(getApplicationContext(), logInActivity.class);
                       // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                       // startActivity(intent);
                        // Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showToast("저장배드");
                        //    Log.w(TAG, "Error writing document", e);
                    }
                });

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1212 && resultCode == RESULT_OK){
            routineText.setText(data.getStringExtra("data"));
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            post.setRoutine(user.getUid()+"#"+data.getStringExtra("data"));
            Log.d("text", routineText.getText().toString());
        }



        if (requestCode == 1235 && resultCode == RESULT_OK) {
            snsimageUri=data.getData();
            RequestOptions option1 = new RequestOptions().circleCrop();
            Glide.with(getApplicationContext()).load(snsimageUri).apply(option1).into(snsimage);
            post.setPhoto(snsimageUri.toString());

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            final  StorageReference riversRef = storageRef.child("snsImages/"+user.getUid()+"snsImage");
            UploadTask uploadTask = riversRef.putFile(snsimageUri);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                }
            });

            //저장후 url 받아오기
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return riversRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        snsphotourl = downloadUri.toString();
                        post.setPhoto(snsphotourl);
                    } else {
                        //howToast("업로드 실패");
                        // Handle failures
                        // ...
                    }
                }
            });
        }


        else if (requestCode == 1235 && resultCode == RESULT_CANCELED) {
            RequestOptions option1 = new RequestOptions().circleCrop();
            Glide.with(getApplicationContext()).load(R.drawable.sample).apply(option1).into(snsimage);
            post.setPhoto(null);
            // Create a storage reference from our app
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();

            // Create a reference to the file to delete
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            StorageReference desertRef = storageRef.child("snsImages/"+user.getUid()+"snsImage");

            // Delete the file
            desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    showToast("기본 이미지");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    showToast("원래 기본 이미지");
                }
            });

        }


    }
    private void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}