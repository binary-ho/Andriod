package com.jinho.chat1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// 첫 화면
public class StartActivity extends AppCompatActivity {
//    private static final String TAG = "Start";
//    private FirebaseAuth mAuth;

    Button login, register;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // 다크모드 비활성화
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        // 로그인 버튼 클릭 -> 로그인 화면 이동
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this, LoginActivity.class));
                finish();
            }
        });

        // 회원가입 버튼 클릭 -> 회원가입 화면 이동동
       register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this, RegisterActivity.class));
            }
        });
        //mAuth = FirebaseAuth.getInstance();
    }

    // 자동 로그인 기능을 위한 onStart
    // 굳이 없어도 될 것 같고, 오류 발생
    // 최근 접속자를 블러오는 방식 때문에 아무래도 최근에 만든 이용인 계정을 불러와버리는 듯.
    // getCurrentUser에 대한 이해 필요
//    @Override
//    protected void onStart() {
//        super.onStart();
//        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//
//        // null 체크
//        if(firebaseUser != null) {
//            Intent intent = new Intent(StartActivity.this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
//    }
}