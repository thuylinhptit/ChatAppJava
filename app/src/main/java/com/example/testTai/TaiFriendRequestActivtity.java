package com.example.testTai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.adapter.FriendRequestAdapter;
import com.example.chatapp2.R;
import com.example.controller.HomeController;
import com.example.controller.SocketCurrent;
import com.example.interfaces.IClickItem;

import java.util.List;

import model.ConnectionType;
import model.FriendRequest;
import model.ObjectWrapper;
import model.User;

public class TaiFriendRequestActivtity extends AppCompatActivity implements IClickItem {

    RecyclerView friendRequestRecyclerView;
    FriendRequestAdapter friendRequestAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tai_friend_request_activtity);
        HomeController.getInstance().getListTaskRunning().add(new ObjectWrapper(this, ConnectionType.REPLY_ADDFRIEND));
        HomeController.getInstance().getListTaskRunning().add(new ObjectWrapper(this, ConnectionType.REPLY_DECLINEFRIEND));
        HomeController.getInstance().getListTaskRunning().add(new ObjectWrapper(this, ConnectionType.REPLY_GETFRIENDREQUEST));

        init();
        createThreadGetFriendRequest();
    }

    private void init() {
        friendRequestRecyclerView = findViewById(R.id.friend_request_listview_id);
        friendRequestAdapter = new FriendRequestAdapter(SocketCurrent.instance.getClient().getFriendRequestList(), getApplicationContext(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        friendRequestRecyclerView.setLayoutManager(layoutManager);
        friendRequestRecyclerView.setAdapter(friendRequestAdapter);
    }

    public void updateUIFriendRequest(List<FriendRequest> fr) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                friendRequestAdapter.setFriendRequestList(fr);
            }
        });
    }

    @Override
    public void onClickListener(int position) {
        System.out.println("Click user on id: " + position);
        User user = new User();
        for (FriendRequest u : SocketCurrent.instance.getClient().getFriendRequestList()) {
            if (u.getSender().getId() == position) {
                user = u.getSender();
                break;
            }
        }
        changeToWatchProfile(user);
    }
    private void changeToWatchProfile(User user) {
        Intent intent = new Intent(this, TaiWatchProfile.class);
//        IntentPutExtra<> dataSend = new IntentPutExtra();
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
    public void createThreadGetFriendRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(HomeController.getInstance().isRunning()) {
                    HomeController.getInstance().sendData(new ObjectWrapper(SocketCurrent.instance.getClient().getId(), ConnectionType.GETFRIENDREQUEST));
                    System.out.println("Send Request get all friendRequest");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}