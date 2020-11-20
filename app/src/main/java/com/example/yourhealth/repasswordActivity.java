package com.example.yourhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class repasswordActivity extends AppCompatActivity implements View.OnClickListener{
    TextView sendemail;
    Button sendbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repassword);

        sendemail = findViewById(R.id.newEmail);
        sendemail.setOnClickListener(this);
        sendemail.addTextChangedListener(textWatcher);

        sendbtn = findViewById(R.id.sendBtn);
        sendbtn.setOnClickListener(this);
    }
    private final TextWatcher textWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String email = ((EditText) findViewById(R.id.newEmail)).getText().toString();

            if ((email.length() > 0)) {
                sendbtn.setEnabled(true);
                sendbtn.setClickable(true);

            } else {
                sendbtn.setClickable(false);
                sendbtn.setEnabled(false);
            }
        }

        public void afterTextChanged(Editable s) {
            String email = ((EditText) findViewById(R.id.newEmail)).getText().toString();
            if ((email.length() > 0) ) {
                sendbtn.setClickable(true);
                sendbtn.setEnabled(true);


            } else {
                sendbtn.setClickable(false);
                sendbtn.setEnabled(false);
            }
        }


    };

    @Override
    public void onClick(View view) {
        if(view == sendbtn){
            send();
        }

    }

    public void send(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress =((EditText) findViewById(R.id.newEmail)).getText().toString();

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            showToast("이메일을 보냈습니다 재설정후 로그인 해주세요");
                            Intent intent = new Intent(getApplicationContext(), logInActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            finish();
                          //  Log.d(TAG, "Email sent.");
                        }
                    }
                });

    }
    private void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}