package com.example.testTai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.adapter.MessageAdapter;
import com.example.chatapp2.R;
import com.example.controller.HomeController;

import model.ConnectionType;
import model.ObjectWrapper;

public class TaiChatSceneActivity extends AppCompatActivity {
    MessageAdapter messageAdapter;
    RecyclerView messageRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tai_chat_scene);
        init();
        HomeController.getInstance().sendData(new ObjectWrapper());
        HomeController.getInstance().getListTaskRunning().add(new ObjectWrapper(this, ConnectionType.REPLY_CHAT));
    }

    private void init() {
//        messageAdapter = new MessageAdapter();    \
        messageRecyclerView = findViewById(R.id.message_adapter_id);

    }



}