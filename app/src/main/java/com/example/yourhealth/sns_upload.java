package com.example.yourhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class sns_upload extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sns_upload);

        Button upload = findViewById(R.id.button_upload);
        Button tempSave = findViewById(R.id.button_tempSave);

        final postContent post = new postContent();

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
                if (i==R.id.button_novice) post.setDifficulty(post.DIFFICULTY_NOVICE);
                else if (i==R.id.button_intermediate) post.setDifficulty(post.DIFFICULTY_INTERMEDIATE);
                else if (i==R.id.button_advance) post.setDifficulty(post.DIFFICULTY_ADVANCE);
            }
        });
        sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i==R.id.button_male) post.setSex(post.SEX_MALE);
                else if (i==R.id.button_female) post.setSex(post.SEX_FEMALE);
            }
        });
        frequency.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i==R.id.button_twoToThree) post.setFrequency(post.FREQUENCY_TOTHREE);
                else if (i==R.id.button_fourToFive) post.setFrequency(post.FREQUENCY_FOURFIVE);
                else if (i==R.id.button_sixToSeven) post.setFrequency(post.FREQUENCY_SIXTOSEVEN);
            }
        });
        time.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i==R.id.button_twentyToForty) post.setTime(post.TIME_TWENTYTOFORTY);
                else if (i==R.id.button_fortyToSeventy) post.setTime(post.TIME_FORTYTOSEVENTY);
                else if (i==R.id.button_aboveSeventy) post.setTime(post.TIME_ABOVESEVENTY);
            }
        });

        upload.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                EditText title = (EditText)findViewById(R.id.text_title);
                EditText content = (EditText)findViewById(R.id.text_content);
                post.setTitle(title.getText().toString());
                post.setContent(content.getText().toString());
                post.setCompleted(true);
                Log.d("Title", post.getTitle());
                Log.d("Content", post.getContent());
                Log.d("Place", ""+post.getPlace());
                Log.d("Difficulty", ""+post.getDifficulty());
                Log.d("Sex", ""+post.getSex());
                Log.d("Frequency", ""+post.getFrequency());
                Log.d("Time", ""+post.getTime());




                //post 객체 FB로 업로드

            }
        });
    }
}