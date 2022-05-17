package org.techtown.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextTextPersonName);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                Intent intent = new Intent(getApplicationContext(), MyService.class);
                intent.putExtra("commend", "show");
                intent.putExtra("name", name);

                startService(intent);
            }
        });

        Intent passedIntent = getIntent();
        processIntent(passedIntent);
        // 액티비티가 새로 만들어질 때 전달된 인텐트 처리
    }

    private void processIntent(Intent intent) {
        if(intent != null) {
            String command = intent.getStringExtra("command");
            String name = intent.getStringExtra("name");

            Toast.makeText(this, "command : " + command + ", name : " + name, Toast.LENGTH_LONG).show();
        }
    }
    
    @Override
    protected void onNewIntent(Intent intent) {
        // 액티비티가 이미 만들어져 있을 떄 호출 되는 함수.
        // 그 떄 전달된 인텐트 처리하기
        processIntent(intent);
        super.onNewIntent(intent);
    }
}