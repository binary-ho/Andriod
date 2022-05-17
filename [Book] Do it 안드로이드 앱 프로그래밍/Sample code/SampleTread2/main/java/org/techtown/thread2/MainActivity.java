package org.techtown.thread2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "Thread";
    Handler handler = new Handler();
    TextView textView;
    int value = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackgroundThread thread = new BackgroundThread();
                thread.start();
                // thread run()이 호출 된다.
            }
        });
        // handler = new MainHandler();
    }

    class BackgroundThread extends Thread {
        public void run() {
            for(int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {}

                value += 1;
                Log.d(TAG, "value : " + value);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("value값 : " + value);
                    }
                });
                // post 안에 Runnable 만들어서 텍스트에 접근하도록 해줌
                // new로 새로 만들어서 이용해준 이유는, 이래야 스레드의 작업 결과물을 받아 볼 수 있기 떄문입니다.

//                Message message = handler.obtainMessage();
//                Bundle bundle = new Bundle();
//                bundle.putInt("value", value);
//                message.setData(bundle);
//
//                handler.sendMessage(message);
            }
        }
    }

//    class MainHandler extends Handler {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//
//            Bundle bundle = msg.getData();
//            int value = bundle.getInt("value");
//            textView.setText("value 값 : " + value);
//        }
//    }
}

