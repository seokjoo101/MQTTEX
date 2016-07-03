package com.example.seokjoo.mqttex;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/*
Main onCreate 부분 추가

        Intent serviceIntent = new Intent(this.getBaseContext(),MqttService.class);
        // 새로운 서비스 실행
        this.startService(serviceIntent);

menifest 추가      <service android:name=".MqttService"/>*/

public class MqttService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //deprecated 되므로 현재 자바버전에서 안쓰이는듯.
    @Override
    public void onStart(Intent intent, int startId) {
         super.onStart(intent, startId);
    }

    //최초1회실행
    @Override
    public void onCreate(){
        super.onCreate();
//        Log.i(TAG,"Service Started");

    }

    //백그라운드에서 실행되는 동작
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent,flags,startId);
    }



    //서비스가 종료될 때 할 작업
    public void onDestroy() {
//        Log.i(TAG,"Service Destoried");

    }


}
