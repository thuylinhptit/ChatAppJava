package com.example.testTai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.adapter.MessageAdapter;
import com.example.chatapp2.R;
import com.example.controller.HomeController;
import com.example.controller.SocketCurrent;
import com.example.interfaces.IUpdateUI;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import model.ConnectionType;
import model.Message;
import model.ObjectWrapper;
import model.Room;
import model.User;

public class TaiChatSceneActivity extends AppCompatActivity {
    TextView chatRoomNameTxt;
    EditText messageTxt;
    ImageButton btnSend;
    MessageAdapter messageAdapter;
    RecyclerView messageRecyclerView;
    private int roomId = 0;
    private Room room;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tai_chat_scene);
        init();
//        HomeController.getInstance().getListTaskRunning().add(new ObjectWrapper(this, ConnectionType.REPLY_GETROOM));
        HomeController.getInstance().getListTaskRunning().add(new ObjectWrapper(this, ConnectionType.REPLY_CHAT));
//        createThreadUpdateMessage();
    }

    private void init() {

        Intent intent = getIntent();
        roomId = intent.getIntExtra("roomid", 0);
        for (Room room : HomeController.getInstance().getRoomList()) {
            if (room.getId() == roomId) {
                messageAdapter = new MessageAdapter(room.getMessages(), getApplicationContext());
                this.room = room;
                break;
            }
        }
        chatRoomNameTxt = findViewById(R.id.chat_name_room);
        messageTxt = findViewById(R.id.chat_text_id);
        btnSend = (ImageButton) findViewById(R.id.chat_btn_send_id);

        String nameroom = room.getName();
        if (room.getUserList().size() == 2) {
            if (room.getUserList().get(0).getId() == SocketCurrent.instance.getClient().getId()) {
                nameroom = room.getUserList().get(1).getFullName();
            } else {
                nameroom = room.getUserList().get(0).getFullName();
            }
        }
        chatRoomNameTxt.setText(nameroom);

        messageRecyclerView = findViewById(R.id.message_adapter_id);

        messageRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        messageRecyclerView.setLayoutManager(layoutManager);
        messageRecyclerView.setAdapter(messageAdapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mess = messageTxt.getText().toString();
                if (mess.length() > 0) {
                    messageTxt.setText("");
                    Message message = new Message();
                    message.setContent(mess);
                    message.setRoom(room);
                    User user;
                    user = SocketCurrent.instance.getClient();
                    message.setAuthor(user);
                    message.setCreateDate(new Date(Calendar.getInstance().getTimeInMillis()));
                    HomeController.getInstance().sendData(new ObjectWrapper(message, ConnectionType.CHAT));
                    System.out.println("Sent Message: " + message.getContent());
                }
            }
        });

    }


    private void createThreadUpdateMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (HomeController.getInstance() != null && HomeController.getInstance().isRunning()) {

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }



    public void updateUI(List<Message> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (list != null) {
                    if (list.get(0).getRoom().getId() == room.getId())
                        messageAdapter.setMessageList(list);
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}