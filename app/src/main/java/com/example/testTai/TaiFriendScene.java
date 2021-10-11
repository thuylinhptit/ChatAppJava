package com.example.testTai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.adapter.FriendAdapter;
import com.example.chatapp2.ChatScreen;
import com.example.chatapp2.R;
import com.example.controller.HomeController;
import com.example.controller.SocketCurrent;
import com.example.interfaces.IClickItem;

import model.ConnectionType;
import model.ObjectWrapper;
import model.Room;
import model.User;

public class TaiFriendScene extends AppCompatActivity implements IClickItem {
    FriendAdapter friendFriendAdapter;
    RecyclerView friendRecyclerView;
   // TextView nameUserTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tai_friend_scene);
        init();
        HomeController.getInstance().getListTaskRunning().add(new ObjectWrapper(this, ConnectionType.REPLY_GETROOMFRIEND));
        HomeController.getInstance().getListTaskRunning().add(new ObjectWrapper(this, ConnectionType.REPLY_ADDFRIEND));
        HomeController.getInstance().getListTaskRunning().add(new ObjectWrapper(this, ConnectionType.REPLY_DECLINEFRIEND));
    }

    private void init() {
        friendRecyclerView = findViewById(R.id.friend_list_id);
      //  nameUserTxt = findViewById(R.id.friend_name_user_id);
        friendFriendAdapter = new FriendAdapter(SocketCurrent.instance.getClient().getFriendList(), getApplicationContext(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        friendRecyclerView.setLayoutManager(layoutManager);
        friendRecyclerView.setAdapter(friendFriendAdapter);
    }

    @Override
    public void onClickListener(int position) {
        System.out.println("Click user on id: " + position);
        User user = new User();
        for (User u : SocketCurrent.instance.getClient().getFriendList()) {
            if (u.getId() == position) {
                user = u;
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

    public void gotoChat(Room roomid) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(300);
                    Intent myIntent = new Intent(TaiFriendScene.this, TaiChatSceneActivity.class);
                    myIntent.putExtra("roomid", roomid.getId());
                    startActivity(myIntent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void updateFriendList() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                friendFriendAdapter.setFriendList(SocketCurrent.instance.getClient().getFriendList());
            }
        });
    }
}