package com.example.chatapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.controller.SocketCurrent;

import model.User;

public class ChatScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        init();
    }

    private void init() {
        TextView friendList = findViewById(R.id.idFriend);
        friendList.append(SocketCurrent.instance.getClient().getUsername() + "\n");
        for (User friend : SocketCurrent.instance.getClient().getFriendList()) {
            friendList.append(friend.getUsername()+ ",");
        }
    }
}