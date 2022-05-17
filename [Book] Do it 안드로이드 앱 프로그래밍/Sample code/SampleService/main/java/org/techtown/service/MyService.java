package org.techtown.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private static final String TAG = "MyService";

    public MyService() {}

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() 호출");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 여기 저 intent가 startService 통해서 전달 받은 정보들
        Log.d(TAG, "onStartCommand 호출");

        if(intent == null) {
            return Service.START_STICKY;
            // 서비스가 자동으로 재시작 하게 해준다.
        }
        else {
            processCommand(intent);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void processCommand(Intent intent) {
        // 정보를 받는 부분.
        String command = intent.getStringExtra("command");
        String name = intent.getStringExtra("name");

        Log.d(TAG, "command : " + command + ", name : " + name);

        for(int i = 0; i < 5; i++) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {};
            Log.d(TAG, "Waiting" + i + " seconds.");
        }
        
        // 정보를 보내는 부분. 받은지 5초 후에 전달하게 되는가 봄.
        Intent showIntent = new Intent(getApplicationContext(), MainActivity.class);

        showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        showIntent.putExtra("command", "show");
        showIntent.putExtra("name", name + " from service.");
        startActivity(showIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}