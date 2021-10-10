package com.example.chatapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.adapter.RoomAdapter;
import com.example.controller.HomeController;
import com.example.controller.SocketCurrent;
import com.example.interfaces.IClickItem;
import com.example.testTai.TaiChatSceneActivity;
import com.example.testTai.TaiFriendRequestActivtity;
import com.example.testTai.TaiFriendScene;

import java.util.List;

import model.ConnectionType;
import model.ObjectWrapper;
import model.Room;

public class ChatScreen extends AppCompatActivity implements IClickItem {

    private RecyclerView roomListViewRecylerView;
    private RoomAdapter roomAdapter;
    private Button friendWatchbtn, friendRequestWatchBtn;
    private boolean canSendRequest = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        init();
        if (HomeController.getInstance() != null) {
            HomeController.getInstance().setChatScreenActivity(this);
            HomeController.getInstance().getListTaskRunning().add(new ObjectWrapper(this, ConnectionType.REPLY_GETROOM));
        }
//        HomeController.getInstance().getListTaskRunning().add(new ObjectWrapper(this, ConnectionType.REPLY_GETROOMFRIEND));
        createThreadUpdateRoom();

    }

    private void init() {
        roomListViewRecylerView = findViewById(R.id.room_recyleview_id);
        friendWatchbtn = findViewById(R.id.home_friendbtn_id);
        roomListViewRecylerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        roomListViewRecylerView.setLayoutManager(layoutManager);

        roomAdapter = new RoomAdapter(SocketCurrent.instance.getClient().getRoomList(), getApplicationContext(), this);
        roomListViewRecylerView.setAdapter(roomAdapter);

        friendWatchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChatScreen.this, TaiFriendScene.class);
                startActivity(i);
            }
        });

        friendRequestWatchBtn = findViewById(R.id.home_friendrequestbtn_id);
        friendRequestWatchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChatScreen.this, TaiFriendRequestActivtity.class);
                startActivity(i);
            }
        });
    }

    public void activeOnlineFriend(int userId) {
        roomListViewRecylerView.getAdapter().notifyItemChanged(userId);
    }

    @Override
    public void onClickListener(int position) {
        System.out.println(position);
        changeIntent(position);
    }

    private void changeIntent(int position) {
        Intent myIntent = new Intent(ChatScreen.this, TaiChatSceneActivity.class);
        myIntent.putExtra("roomid", position);
        startActivity(myIntent);
    }

    private void createThreadUpdateRoom() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (canSendRequest) {
                        HomeController.getInstance().sendData(new ObjectWrapper(SocketCurrent.instance.getClient().getId(), ConnectionType.GETROOM));
                        System.out.println("Send Update Room");
                        canSendRequest = false;
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    public void updateRoom(List<Room> listRoom) {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                System.out.println("ListRoom: " + listRoom.size());
                roomAdapter.setRoom(listRoom);
                // Stuff that updates the UI

            }
        });
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//            }
//        }).start();

    }

    public void setCanSendRequest(boolean b) {
        this.canSendRequest = b;
    }
}