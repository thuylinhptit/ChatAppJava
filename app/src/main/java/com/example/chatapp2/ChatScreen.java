package com.example.chatapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.adapter.RoomAdapter;
import com.example.controller.SocketCurrent;


public class ChatScreen extends AppCompatActivity {

    private RecyclerView roomListViewRecylerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.iconProfile:
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT);
                return true;
            case R.id.iconGroup:
                Toast.makeText(this, "Group", Toast.LENGTH_SHORT);
                return true;
            case R.id.iconLight:
                Toast.makeText(this, "Light", Toast.LENGTH_SHORT);
                return true;
            case R.id.iconChangePass:
                Toast.makeText(this, "Change Pass", Toast.LENGTH_SHORT);
                return true;
            case R.id.iconLogout:
                Toast.makeText(this, "Log out", Toast.LENGTH_SHORT);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    //     //   TextView friendList = findViewById(R.id.idFriend);
//        friendList.append(SocketCurrent.instance.getClient().getUsername() + "\n");
//        for (User friend : SocketCurrent.instance.getClient().getFriendList()) {
//            friendList.append(friend.getUsername()+ ",");
//        }
    private void init() {
        roomListViewRecylerView = findViewById(R.id.room_recyleview_id);
        roomListViewRecylerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        roomListViewRecylerView.setLayoutManager(layoutManager);

        RoomAdapter roomAdapter = new RoomAdapter(SocketCurrent.instance.getClient().getRoomList(), getApplicationContext());
        roomListViewRecylerView.setAdapter(roomAdapter);
    }
}
