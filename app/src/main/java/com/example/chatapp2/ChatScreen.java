package com.example.chatapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.adapter.RoomAdapter;
import com.example.controller.HomeController;
import com.example.controller.SocketCurrent;
import com.example.interfaces.IClickRoom;
import com.example.testTai.TaiChatSceneActivity;

import java.util.List;

import model.ConnectionType;
import model.ObjectWrapper;
import model.Room;
import model.User;

public class ChatScreen extends AppCompatActivity implements IClickRoom {

    private RecyclerView roomListViewRecylerView;
    private RoomAdapter roomAdapter;
    private boolean canSendRequest = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        init();
        HomeController.getInstance().getListTaskRunning().add(new ObjectWrapper(this, ConnectionType.REPLY_GETROOM));
        createThreadUpdateRoom();

    }

    private void init() {
        roomListViewRecylerView = findViewById(R.id.room_recyleview_id);
        roomListViewRecylerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        roomListViewRecylerView.setLayoutManager(layoutManager);

        roomAdapter = new RoomAdapter(SocketCurrent.instance.getClient().getRoomList(), getApplicationContext(), this);
        roomListViewRecylerView.setAdapter(roomAdapter);

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