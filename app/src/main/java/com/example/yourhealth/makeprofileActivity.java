package com.example.yourhealth;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class makeprofileActivity extends AppCompatActivity implements View.OnClickListener {

    RadioGroup sexSelect;
    Button saveBtn;
    String sex;
    String name;
    String purpose;
    String where;
   String userphotourl;

    ImageView userProfilePhoth;
    Uri imageUri;


    EditText username;

    CheckedTextView diet;
    CheckedTextView strength;
    CheckedTextView pumping;
    CheckedTextView bulkup;
    CheckedTextView home;
    CheckedTextView healthplace;
    CheckedTextView both;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makeprofile);

        username = findViewById(R.id.uesrName);
        username.addTextChangedListener(textWatcher);

        userProfilePhoth = findViewById(R.id.userphoto);
        userProfilePhoth.setOnClickListener(this);

        RequestOptions option1 = new RequestOptions().circleCrop();
        Glide.with(getApplicationContext()).load(R.drawable.sample).apply(option1).into(userProfilePhoth);

        saveBtn = findViewById(R.id.nextBtn2);
        saveBtn.setOnClickListener(this);

        sexSelect =  findViewById(R.id.sexSelect);
        sexSelect.setOnCheckedChangeListener(radioGroupButtonChangeListener);

        diet = findViewById(R.id.deit);
        diet.setOnClickListener(this);
        strength = findViewById(R.id.strength);
        strength.setOnClickListener(this);
        bulkup = findViewById(R.id.bulkup);
        bulkup.setOnClickListener(this);
        pumping = findViewById(R.id.pumping);
        pumping.setOnClickListener(this);
        home = findViewById(R.id.hometraining);
        home.setOnClickListener(this);
        healthplace = findViewById(R.id.healthplace);
        healthplace.setOnClickListener(this);
        both = findViewById(R.id.bothplace);
        both.setOnClickListener(this);

    }
       RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener(){
            @Override public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if(i == R.id.maleBtn){
                    sex = "남자";

                } else if(i == R.id.femaleBtn){
                    sex = "여자";
                }
            }
        };

    private final TextWatcher textWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            name = "NULL";

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        public void afterTextChanged(Editable s) {
            name = username.getText().toString();
        }
    };

    @Override
    public void onClick(View view) {//0xFF989696
        if(view == userProfilePhoth){
            Intent intent = new Intent(this, chooseActivity.class);
            startActivityForResult(intent, 1234);
        }

        if(view == strength ){
            if(!(strength.isChecked())){
                strength.setChecked(true);
                strength.setTextColor(0xFF3FC8BB);
                purpose = strength.getText().toString();
                diet.setChecked(false);
                diet.setTextColor(0xFF989696);
                pumping.setChecked(false);
                pumping.setTextColor(0xFF989696);
                bulkup.setChecked(false);
                bulkup.setTextColor(0xFF989696);
            }else {
                strength.setChecked(false);
                strength.setTextColor(0xFF989696);
                purpose = "NULL";
            }

        }else if(view == diet){
            if(!(diet.isChecked())){
                diet.setChecked(true);
                diet.setTextColor(0xFF3FC8BB);
                purpose = diet.getText().toString();
                strength.setChecked(false);
                strength.setTextColor(0xFF989696);
                pumping.setChecked(false);
                pumping.setTextColor(0xFF989696);
                bulkup.setChecked(false);
                bulkup.setTextColor(0xFF989696);
            }else {
                diet.setChecked(false);
                diet.setTextColor(0xFF989696);
                purpose = "NULL";
            }
        }else if(view == pumping) {
            if (!(pumping.isChecked())) {
                pumping.setChecked(true);
                pumping.setTextColor(0xFF3FC8BB);
                purpose = pumping.getText().toString();
                strength.setChecked(false);
                strength.setTextColor(0xFF989696);
                diet.setChecked(false);
                diet.setTextColor(0xFF989696);
                bulkup.setChecked(false);
                bulkup.setTextColor(0xFF989696);
            } else {
                pumping.setChecked(false);
                pumping.setTextColor(0xFF989696);
                purpose = "NULL";
            }
        }else if(view == bulkup) {
            if (!(bulkup.isChecked())) {
                bulkup.setChecked(true);
                bulkup.setTextColor(0xFF3FC8BB);
                purpose = bulkup.getText().toString();
                strength.setChecked(false);
                strength.setTextColor(0xFF989696);
                diet.setChecked(false);
                diet.setTextColor(0xFF989696);
                pumping.setChecked(false);
                pumping.setTextColor(0xFF989696);
            } else {
                bulkup.setChecked(false);
                bulkup.setTextColor(0xFF989696);
                purpose = "NULL";
            }
        }
        if(view == home){
            if (!(home.isChecked())) {
                home.setChecked(true);
                home.setTextColor(0xFF3FC8BB);
                where = home.getText().toString();
                healthplace.setChecked(false);
                healthplace.setTextColor(0xFF989696);
                both.setChecked(false);
                both.setTextColor(0xFF989696);

            } else {
                home.setChecked(false);
                home.setTextColor(0xFF989696);
                where = "NULL";
            }
        }else if(view == healthplace){
            if (!(healthplace.isChecked())) {
                healthplace.setChecked(true);
                healthplace.setTextColor(0xFF3FC8BB);
                where = healthplace.getText().toString();
                home.setChecked(false);
                home.setTextColor(0xFF989696);
                both.setChecked(false);
                both.setTextColor(0xFF989696);

            } else {
                healthplace.setChecked(false);
                healthplace.setTextColor(0xFF989696);
                where = "NULL";
            }
        }else if(view == both){
            if (!(both.isChecked())) {
                both.setChecked(true);
                both.setTextColor(0xFF3FC8BB);
                where = both.getText().toString();
                home.setChecked(false);
                home.setTextColor(0xFF989696);
                healthplace.setChecked(false);
                healthplace.setTextColor(0xFF989696);

            } else {
                both.setChecked(false);
                both.setTextColor(0xFF989696);
                where = "NULL";
            }
        }
        if(!(TextUtils.isEmpty(name)) && !("NULL".equals(purpose))&&!("NULL".equals(where))){
            saveBtn.setClickable(true);
            saveBtn.setEnabled(true);
        }else{
            saveBtn.setClickable(false);
            saveBtn.setEnabled(false);
        }
        if(view == saveBtn){
            makeProfile();
        }

       String test = name+" "+sex+" "+purpose+" "+where;
        showToast(test);
    };
    public void makeProfile(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        profile memberprofile = new profile(name,sex,purpose,where,userphotourl);
        db.collection("Users").document(user.getUid()).set(memberprofile)
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                showToast("저장굳");
                Intent intent = new Intent(getApplicationContext(), logInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1234 && resultCode == RESULT_OK){
            imageUri=data.getData();
            RequestOptions option1 = new RequestOptions().circleCrop();
            Glide.with(getApplicationContext()).load(imageUri).apply(option1).into(userProfilePhoth);
            //서버에 사진 저장
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            final  StorageReference riversRef = storageRef.child("profileImages/"+user.getUid()+"profileImage");
            UploadTask uploadTask = riversRef.putFile(imageUri);
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
                        userphotourl = downloadUri.toString();
                    } else {
                        showToast("업로드 실패");
                        // Handle failures
                        // ...
                    }
                }
            });

// Register observers to listen for when the download is done or if it fails


        }else if(requestCode == 1234 && resultCode == RESULT_CANCELED){
            RequestOptions option1 = new RequestOptions().circleCrop();
            Glide.with(getApplicationContext()).load(R.drawable.sample).apply(option1).into(userProfilePhoth);
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