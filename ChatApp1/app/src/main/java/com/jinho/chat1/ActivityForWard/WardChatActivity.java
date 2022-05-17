package com.jinho.chat1.ActivityForWard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jinho.chat1.Adapter.MessageAdapter;
import com.jinho.chat1.Model.Chat;
import com.jinho.chat1.Model.Ward;
import com.jinho.chat1.R;

import java.util.ArrayList;
import java.util.HashMap;

public class WardChatActivity extends AppCompatActivity {

    Intent intent;

    ImageButton button_send;
    EditText text_send;

    private static String userId;
    private static String wardId;
    private static String isWard;
    ArrayList<Chat> chats;

    TextView userNameView;
    RecyclerView recyclerView;

    DatabaseReference reference;
    MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ward_chat);

        intent = getIntent();
        userId = intent.getStringExtra("userId");
        wardId = intent.getStringExtra("wardId");
        isWard = intent.getStringExtra("isWard");

        Log.d("WardChatActivity", userId);
        Log.d("WardChatActivity", wardId);
        Log.d("WardChatActivity", isWard);

        recyclerView = findViewById(R.id.chat_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        userNameView = findViewById(R.id.user_name_title);
        text_send = findViewById(R.id.text_send);
        button_send = findViewById(R.id.button_send);
        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = text_send.getText().toString();
                // 비었는지는 체크
                if(!message.equals("")) {
                    sendMessage(wardId, userId, message);
//                    if(isWard.equals("false")) {
//                        // 유저일때
//                        sendMessage(wardId, userId, message);
//                    } else {
//                        sendMessage(userId, wardId, message);
//                    }
                }
                text_send.setText("");
            }
        });


        

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("Wards").child(wardId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Ward ward = snapshot.getValue(Ward.class);
                readMessage(wardId, userId, "default");

//                profile_image.setImageResource(R.drawable.chicken);
//                if (isWard.equals("false")) {
//                    // ward가 아닐때 user일때
//                    readMessage(wardId, userId, "default");
//
//                } else {
//                    // ward일때
//                    readMessage(userId, wardId, "default");
//                }
                userNameView.setText(ward.getParentId());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendMessage(String sender, String receiver, String message) {

        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("Wards").child(wardId).child("Chats");
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        reference.push().setValue(hashMap);
    }

    private void readMessage(final String senderId, final String receiverId, final String imageUrl) {
        chats = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("Wards").child(wardId).child("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chats.clear();
                for(DataSnapshot elem : snapshot.getChildren()) {
                    Chat chat = elem.getValue(Chat.class);
                    // 내 경우엔 무조건 이 조건을 만족하는 것 같아.
//                    if(chat.getSender().equals(userId) && chat.getReceiver().equals(wardId) ||
//                    chat.getSender().equals(wardId) && chat.getReceiver().equals(userId)) {
//                        chats.add(chat);
//                    }
                    chats.add(chat);
                    messageAdapter = new MessageAdapter(getBaseContext(), chats, imageUrl);
                    //messageAdapter = new MessageAdapter(ChatActivity.this, chats, imageUrl);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static String getWardId() {
        return wardId;
    }
    public static String getUserId() {
        return userId;
    }
    public static String getIsWard() {
        return isWard;
    }
}