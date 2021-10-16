package com.example.chatapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.adapter.RoomAdapter;
import com.example.adapter.UserCreateGroupAdapter;
import com.example.controller.HomeController;
import com.example.controller.SocketCurrent;
import com.example.interfaces.IClickItem;
import com.example.testTai.TaiChatSceneActivity;
import com.example.testTai.TaiFriendRequestActivtity;
import com.example.testTai.TaiFriendScene;
import com.example.testTai.TaiSearchUserActivity;
import com.example.testTai.TaiWatchProfile;

import java.util.ArrayList;
import java.util.List;

import model.ConnectionType;
import model.ObjectWrapper;
import model.Room;
import model.User;

public class ChatScreen extends AppCompatActivity implements IClickItem {

    private RecyclerView roomListViewRecylerView;
    private RoomAdapter roomAdapter;
    private ImageButton friendWatchbtn, friendRequestWatchBtn, searchUserBtn, watchProfileBtn, createGrBtn;
    private boolean canSendRequest = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        init();
        if (HomeController.getInstance() != null) {
            HomeController.getInstance().setChatScreenActivity(this);
            HomeController.getInstance().getListTaskRunning().add(new ObjectWrapper(this, ConnectionType.REPLY_GETROOM));
            HomeController.getInstance().getListTaskRunning().add(new ObjectWrapper(this, ConnectionType.REPLY_SEARCH));

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

        searchUserBtn = findViewById(R.id.home_search_btn_id);
        searchUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChatScreen.this, TaiSearchUserActivity.class);
                startActivity(i);
            }
        });

        watchProfileBtn = findViewById(R.id.watch_profile_id);
        watchProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChatScreen.this, TaiWatchProfile.class);
                i.putExtra("user", SocketCurrent.instance.getClient());
                startActivity(i);
            }
        });

        createGrBtn = findViewById(R.id.home_create_gr_btn_id);
        createGrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Click to group create");
                showCustomDialogAddUserCreateGroup();
            }
        });

    }
    private Dialog dialog;
    private RecyclerView listUserInSearch;
    private void showCustomDialogAddUserCreateGroup() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.tai_create_group);
        Button accept, cancel;
        ImageButton findBtn;
        accept = dialog.findViewById(R.id.create_gr_accept_id);
        cancel = dialog.findViewById(R.id.create_gr_cancel_id);
        findBtn = dialog.findViewById(R.id.create_gr_btn_id);

        EditText fullnamesearch = dialog.findViewById(R.id.create_gr_user_name_id);

        listUserInSearch = dialog.findViewById(R.id.create_gr_listview);
        UserCreateGroupAdapter userCreateGroupAdapter = new UserCreateGroupAdapter(SocketCurrent.instance.getClient().getFriendList(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listUserInSearch.setLayoutManager(layoutManager);
        listUserInSearch.setAdapter(userCreateGroupAdapter);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<User> choosed = userCreateGroupAdapter.listChoosedUser();
                choosed.add(SocketCurrent.instance.getClient());
                if (userCreateGroupAdapter.listChoosedUser().size() > 2) {
                    HomeController.getInstance().sendData(new ObjectWrapper(userCreateGroupAdapter.listChoosedUser(), ConnectionType.CREATEROOM));
                    dialog.dismiss();
                    dialog = null;
                    System.out.println("Dispose");
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog = null;
                System.out.println("Dispose");

            }
        });

        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String key = fullnamesearch.getText().toString();
                HomeController.getInstance().sendData(new ObjectWrapper(key, ConnectionType.SEARCH));
                System.out.println("Send Search with: " + key);
            }
        });
        dialog.show();

    }

    public void updateSearchUserToCreateGroup(List<User> users) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                UserCreateGroupAdapter userCreateGroupAdapter = new UserCreateGroupAdapter(users, ChatScreen.this);
                listUserInSearch.setAdapter(userCreateGroupAdapter);
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