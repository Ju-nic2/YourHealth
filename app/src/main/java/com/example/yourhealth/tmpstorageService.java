package com.example.yourhealth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class tmpstorageService extends Service {
    postContent tmppost;


    @Override
    public void onCreate() {
        super.onCreate();


    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        // 여기서 처리 한다. 시스템 특성상 아래 특징 확인
        if(intent == null){
            return Service.START_STICKY;
            // 끈적끈적하게 계쏙 서비스가 종료되더라도 자동으로 실행되도록한다.
        }else{          // Null이 아닐경우
            processCommand(intent); // 메소드를 분리하는게 보기가 좋다. 여기서 처리한다.
        }


        return START_REDELIVER_INTENT;

    }
    private void processCommand(Intent intent){
        // 전달 받은 데이터 찍기 위함.
        this.tmppost = (postContent) intent.getSerializableExtra("postContent");

        Log.d("여기는 서비스다 오바 데이타 받음", "" + tmppost.getSex());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
