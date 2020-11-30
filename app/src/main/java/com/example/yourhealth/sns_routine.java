package com.example.yourhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class sns_routine extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sns_routine2);
        Intent intent = getIntent();
        int postID = intent.getIntExtra("postID", 0);
        final postContent postcontent = new postContent();//FB에서 받아올 postContent

        final Boolean heart_clicked = Boolean.FALSE;


        TextView numOfHeart = findViewById(R.id.NumOfHeart);//하트 버튼

        numOfHeart.setText(postcontent.getNum_heart()+"");//좋아요 수

        numOfHeart.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                ImageView heart = findViewById(R.id.heart);
                if (heart_clicked==Boolean.FALSE){
                    heart.setImageResource(R.drawable.heart);
                    //heart_clicked = Boolean.TRUE;
                    postcontent.setNum_heart(postcontent.getNum_heart()+1);
                    //numOfHeart.setText(postcontent.getNum_heart()+"");
                }
                else if (heart_clicked==Boolean.TRUE){
                    heart.setImageResource(R.drawable.heart_black);
                    //heart_clicked = Boolean.FALSE;
                    postcontent.setNum_heart(postcontent.getNum_heart()-1);
                    //numOfHeart.setText(postcontent.getNum_heart()+"");
                }
            }
        });

    }
}