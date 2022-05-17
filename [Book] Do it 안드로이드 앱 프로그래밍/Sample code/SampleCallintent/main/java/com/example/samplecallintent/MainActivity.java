package com.example.samplecallintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);

        Button button1 = findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = editText.getText().toString();

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(data));
                // 여기 꼭 체크하자 무슨 일이 일어나는건지
                // 전화걸기 화면을 보여줄 인텐트 객체 생성
                startActivity(intent);
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                ComponentName name = new ComponentName("com.example.samplecallintent", "com.example.samplecallintent.MenuActivity");
                intent.setComponent(name);
                startActivityForResult(intent, 101);
            }
        });
    }
}