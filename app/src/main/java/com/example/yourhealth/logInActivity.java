package com.example.yourhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

public class logInActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "logIn";
    TextView newMember;
    Button singUpBtn;
    TextView findpassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);



        newMember = findViewById(R.id.newMember);
        newMember.setOnClickListener(this);

        findpassword = findViewById(R.id.findPassword);
        findpassword.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        singUpBtn = findViewById(R.id.signUpBtn);
        singUpBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == newMember){
            Intent intent = new Intent(getApplicationContext(), memberActivity.class);
            startActivity(intent); // 다음화면으로 넘어가기
        }else if(view == singUpBtn){
            logIn();

        }else if(view == findpassword){
            Intent intent = new Intent(getApplicationContext(), repasswordActivity.class);
            startActivity(intent);
        }

    }
    public void logIn(){
        String email = ((EditText) findViewById(R.id.emailText)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordText)).getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            showToast("로그인!");
                            Intent intent = new Intent(getApplicationContext(), mainmenuActivity.class);
                            startActivity(intent);
                        //    updateUI(user);
                        } else {
                            showToast("이메일,비밀번호를 확인해주세요");
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());

                          //  updateUI(null);
                            // ...
                        }

                        // ...
                    }
                });

    }
    private void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
       // updateUI(currentUser);
    }

}