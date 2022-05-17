package org.techtown.database2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    EditText editText2;
    TextView textView;

    DatabaseHelper dbHelper;
    SQLiteDatabase database;
    String tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        textView = findViewById(R.id.textView);

        // 버튼1 데이터 베이스 만들어줘요
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String databaseName = editText.getText().toString();
                createDatabase(databaseName);
            }
        });

        // 버튼2 테이블이랑 레코드 만들어줘요
        Button button2 =  findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableName = editText2.getText().toString();
                createTable(tableName);
                insertRecord();
            }
        });

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeQuery();
            }
        });
    }

    public void executeQuery() {
        println("executeQuery 호출됨.");
        Cursor cursor = database.rawQuery("select _id, name, age, mobile from emp", null);
        // SQL 실행하고, Cursor 객체 반환 받기.
        // int recordCount = cursor.getCount();
        // println("레코드 개수: " + recordCount);
        int cnt = 0;
        while(cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int age = cursor.getInt(2);
            String mobile = cursor.getString(3);

            println("레코드#" + cnt + " : " + id + ", " + name + ", " + age + ", " + mobile);
            cnt++;
        }
        cursor.close();
    }
    // 데이터 베이스 만들기 짧네
    private void createDatabase(String name) {
        //database = openOrCreateDatabase(name, MODE_PRIVATE, null);
        // 데이터 베이스 생성
        // Helper 이용한 버젼
        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();
        // DatabaseHelper 객체 생성, SQLiteDatabase 객체 참조하기.

        println("create database 호출");
        println("데이터 베이스 생성 : " + name);
    }
    
    private void createTable(String name) {
        println("create Table 호출");
        if(database == null) {
            println("데이터베이스를 먼저 생성하세요.");
            return;
        }
        // 테이블 만들기
        database.execSQL("create table if not exists " + name + "(" + "_id integer PRIMARY KEY autoincrement, "
        + " name text, " + " age integer, " + " mobile text)");
        
        println("테이블 생성함 : " + name);
    }
    
    private void insertRecord() {
        println("insert record 호출");
        if(database == null) {
            println("데이터베이스를 먼저 생성하세요.");
            return;
        }
        if(tableName ==null) {
            println("테이블을 먼저 생성하세요");
            return;
        }
        
        // 레코드 만들기 ( 데이터베이스 -> 테이블 -> 레코드)
        // 직원 테이블 만든 다음 -> 레코드에서 임의의 데이터 생성
        database.execSQL("insert into " + tableName + "(name, age, mobile) " + " values " + "( ' jinho ', 26, '010-7625-4687 ')");
        database.execSQL("insert into " + tableName + "(name, age, mobile) " + " values " + "( ' Jhun ', 26, '010-1234-5678 ')");
        database.execSQL("insert into " + tableName + "(name, age, mobile) " + " values " + "( ' Pig ', 99, '010-8765-4321 ')");
        println("레코드 추가함");
    }
    
    public void println(String data) {textView.append(data + "\n");}
    
}