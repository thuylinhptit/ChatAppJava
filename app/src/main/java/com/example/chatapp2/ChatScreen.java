package com.example.chatapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.adapter.RoomAdapter;
import com.example.controller.SocketCurrent;

import model.Room;
import model.User;

public class ChatScreen extends AppCompatActivity {

    private RecyclerView roomListViewRecylerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        init();
    }

    private void init() {
        roomListViewRecylerView = findViewById(R.id.room_recyleview_id);
        roomListViewRecylerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        roomListViewRecylerView.setLayoutManager(layoutManager);

        RoomAdapter roomAdapter = new RoomAdapter(SocketCurrent.instance.getClient().getRoomList(), getApplicationContext());
        roomListViewRecylerView.setAdapter(roomAdapter);
    }
}