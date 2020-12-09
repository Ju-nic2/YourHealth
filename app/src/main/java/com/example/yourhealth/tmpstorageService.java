package com.example.yourhealth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;



import androidx.annotation.Nullable;

        public class tmpstorageService extends Service {
            postContent tmppost;
            public static boolean SERVICE_CONNECTED = false;

            IBinder mBinder = new MyBinder();

            class MyBinder extends Binder {
                tmpstorageService getService() { // 서비스 객체를 리턴
                    return tmpstorageService.this;
                }
            }

            @Nullable
            @Override
            public IBinder onBind(Intent intent) {

                return mBinder;
            }

            public postContent getTmppost(){return tmppost;}
            public void setTmppost(postContent tmppost){this.tmppost=tmppost;}

            @Override
            public void onCreate() {
                super.onCreate();
                Log.d("여기는 서비스다 오바 ", "실행 완료" );
                SERVICE_CONNECTED=true;


            }
            @Override
            public int onStartCommand(Intent intent, int flags, int startId) {
                super.onStartCommand(intent, flags, startId);
                return START_REDELIVER_INTENT;

            }
            private void processCommand(Intent intent){
        // 전달 받은 데이터 찍기 위함.
        this.tmppost = (postContent) intent.getSerializableExtra("postContent");


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
