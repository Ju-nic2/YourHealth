package com.example.yourhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class memberActivity extends AppCompatActivity implements View.OnClickListener  {
    public static final String TAG = "logIn";
    Button nextBtn;
    EditText newEmail;
    EditText newPass;
    EditText newPassCheck;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        mAuth = FirebaseAuth.getInstance();

        nextBtn = findViewById(R.id.sendBtn);
        nextBtn.setOnClickListener(this);
        nextBtn.setClickable(false);
        nextBtn.setEnabled(false);

        newEmail=findViewById(R.id.newEmail);
        newPass=findViewById(R.id.newPass);
        newPassCheck=findViewById(R.id.newPassCheck);

        newEmail.addTextChangedListener(textWatcher);
        newPass.addTextChangedListener(textWatcher);
        newPassCheck.addTextChangedListener(textWatcher);
    }
    private final TextWatcher textWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String email = ((EditText) findViewById(R.id.newEmail)).getText().toString();
            String password = ((EditText) findViewById(R.id.newPass)).getText().toString();
            String passwordcheck = ((EditText) findViewById(R.id.newPassCheck)).getText().toString();
            if ((email.length() > 0) && (password.length() > 0) && (passwordcheck.length() > 0)) {
                nextBtn.setEnabled(true);
                nextBtn.setClickable(true);

            } else {
                nextBtn.setClickable(false);
                nextBtn.setEnabled(false);
            }
        }

        public void afterTextChanged(Editable s) {
            String email = ((EditText) findViewById(R.id.newEmail)).getText().toString();
            String password = ((EditText) findViewById(R.id.newPass)).getText().toString();
            String passwordcheck = ((EditText) findViewById(R.id.newPassCheck)).getText().toString();
            if ((email.length() > 0) && (password.length() > 0) && (passwordcheck.length() > 0)) {
                nextBtn.setClickable(true);
                nextBtn.setEnabled(true);


            } else {
                nextBtn.setClickable(false);
                nextBtn.setEnabled(false);
            }
        }


    };

    @Override
    public void onClick(View view) {
        if (view == nextBtn) {


            String password = ((EditText) findViewById(R.id.newPass)).getText().toString();
            String passwordcheck = ((EditText) findViewById(R.id.newPassCheck)).getText().toString();

            if (!(passwordcheck.equals(password)))
                showToast("비밀번호 확인해 주세요");
            else if (password.length() <= 6) {
                showToast("비밀번호는 6자리 이상");
            }
            else {
                newMember();
            }
        }


    }
    public void newMember() {
        String email = ((EditText) findViewById(R.id.newEmail)).getText().toString();
        String password = ((EditText) findViewById(R.id.newPass)).getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            showToast("회원가입 완료");
                            logIn();
                            showToast("로그인~~");


                            //updateUI(user);
                        } else {
                            showToast("이메일 형식을 지켜주세요");
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            //  Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                            //  Toast.LENGTH_SHORT).show();
                            // updateUI(null);
                        }

                        // ...
                    }
                });

    }
    public void logIn(){
        String email = ((EditText) findViewById(R.id.newEmail)).getText().toString();
        String password = ((EditText) findViewById(R.id.newPass)).getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(), makeprofileActivity.class);
                            startActivity(intent);
                            showToast("로그인!");
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
}