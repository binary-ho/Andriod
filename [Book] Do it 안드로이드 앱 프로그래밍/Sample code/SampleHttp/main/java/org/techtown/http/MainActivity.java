package org.techtown.http;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView textView;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextTextPersonName);
        textView = findViewById(R.id.textView);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String urlString = editText.getText().toString();

                new Thread(new Runnable () {
                    @Override
                    public void run() {
                        request(urlString);
                    }
                }).start();
            }
        });
    }

    public void request(String urlString) {
        StringBuilder output = new StringBuilder();
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // Http URL Connection 객체 만들기
            if(conn != null) {
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("GET");   // 이 객체에 'GET'방식으로 요청한다는 내용을 설정
                conn.setDoInput(true);  // 이 객체의 입력이 가능하도록 만들어줌.

                int responseCode = conn.getResponseCode();  // 이 시점에 내부적으로 웹 서버에 페이지를 요청하는 과정 수행.

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = null;
                while(true) {
                    line = reader.readLine();
                    if(line == null) {
                        break;
                    }
                    output.append(line + "\n");
                }
                reader.close();
                conn.disconnect();
            }
        } catch (Exception ex) {
            println("예외 발생: " + ex.toString());
        }
        println("응답-> " + output.toString());
    }
    public void println(final String data) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.append(data + "\n");
            }
        });
    }
}