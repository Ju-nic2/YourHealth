package com.example.yourhealth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class mainmenuActivity extends AppCompatActivity implements View.OnClickListener {
    TextView username;
    TextView userpurpose;
    TextView userplace;

    ImageView userphoto;
    Uri mainimageUri;
    String newuserUrl;

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

                Intent intent = new Intent(getApplicationContext(), storage.class);

                startActivity(intent);
            }
        });
        userphoto.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), chooseActivity.class);
                startActivityForResult(intent, 1111);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1111 && resultCode == RESULT_OK) {
            mainimageUri=data.getData();
            RequestOptions option1 = new RequestOptions().circleCrop();
            Glide.with(getApplicationContext()).load(mainimageUri).apply(option1).into(userphoto);


            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            final  StorageReference riversRef = storageRef.child("profileImages/"+user.getUid()+"profileImage");
            UploadTask uploadTask = riversRef.putFile(mainimageUri);
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
                        newuserUrl = downloadUri.toString();
                        updateuserphoth(newuserUrl);
                    } else {
                        //howToast("업로드 실패");
                        // Handle failures
                        // ...
                    }
                }
            });


        }
        else if (requestCode == 1111 && resultCode == RESULT_CANCELED) {
            RequestOptions option1 = new RequestOptions().circleCrop();
            Glide.with(getApplicationContext()).load(R.drawable.sample).apply(option1).into(userphoto);

            // Create a storage reference from our app
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();

            // Create a reference to the file to delete
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            StorageReference desertRef = storageRef.child("profileImages/"+user.getUid()+"profileImage");

            // Delete the file
            desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    updateuserphoth(null);
                  //  showToast("기본 이미지");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    //showToast("원래 기본 이미지");
                }
            });

        }


    }
    public void updateuserphoth(String newUrl){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(newUrl == null){
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setPhotoUri(null)
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                //Log.d(TAG, "User profile updated.");
                            }
                        }
                    });
        }else{ UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(Uri.parse(newUrl))
                .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                //Log.d(TAG, "User profile updated.");
                            }
                        }
                    });}

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference washingtonRef = db.collection("Users").document(user.getUid());
        washingtonRef
                .update("userphothurl", newUrl)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                       // Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Log.w(TAG, "Error updating document", e);
                    }
                });
    }
}