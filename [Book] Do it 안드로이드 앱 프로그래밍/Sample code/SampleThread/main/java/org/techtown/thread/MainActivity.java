package org.techtown.thread;

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
    MainHandler handler;
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
        handler = new MainHandler();
    }

    class BackgroundThread extends Thread {
        public void run() {
            for(int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {}

                value += 1;
                Log.d(TAG, "value : " + value);

                Bundle bundle = new Bundle();
                bundle.putInt("value", value);

                Message message = handler.obtainMessage();
                message.setData(bundle);
                handler.sendMessage(message);
                // 번들 하나 만들어서 필요한거 넣음
                // 그 안에 이것저것 넣음
                // obtain message로 메세지를 가져옴
                // 그 안에 번들 넣음
                // 그다음 핸들러 통해서 보냄
            }
        }
    }

    class MainHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Bundle bundle = msg.getData();
            int value = bundle.getInt("value");
            textView.setText("value 값 : " + value);
        }
    }
}

