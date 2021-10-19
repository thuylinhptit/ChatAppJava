package com.example.testTai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.adapter.FriendRequestAdapter;
import com.example.chatapp2.R;
import com.example.controller.HomeController;
import com.example.interfaces.IClickItem;

import java.util.ArrayList;
import java.util.List;

import model.ConnectionType;
import model.ObjectWrapper;
import model.User;

public class TaiSearchUserActivity extends AppCompatActivity implements IClickItem {

    RecyclerView listResultView;
    EditText nameTxt;
    ImageButton searchBtn;
    FriendRequestAdapter searchFriendAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tai_search_user);
        HomeController.getInstance().getListTaskRunning().add(new ObjectWrapper(this, ConnectionType.REPLY_SEARCH));
        init();
    }

    private void init() {
        nameTxt = findViewById(R.id.search_name_friend_id);
        listResultView = findViewById(R.id.search_list_friend_id);
        searchBtn = findViewById(R.id.search_friend_btn_id);
        searchFriendAdapter = new FriendRequestAdapter(new ArrayList<User>(), getApplicationContext(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listResultView.setLayoutManager(layoutManager);
        listResultView.setAdapter(searchFriendAdapter);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = nameTxt.getText().toString();
                if (key.length() > 0) {
                    HomeController.getInstance().sendData(new ObjectWrapper(key, ConnectionType.SEARCH));
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClickListener(int position) {
        changeToWatchProfile(searchFriendAdapter.getList().get(position));
    }
    private void changeToWatchProfile(User user) {
        Intent intent = new Intent(this, TaiWatchProfile.class);
//        IntentPutExtra<> dataSend = new IntentPutExtra();
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
    public void updateSearchView(List<User> listS) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                searchFriendAdapter.setList(listS);
            }
        });
    }
}