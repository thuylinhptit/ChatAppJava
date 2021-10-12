package com.example.testTai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.chatapp2.R;
import com.example.chatapp2.SplashScreen;
import com.example.controller.HomeController;
import com.example.controller.SocketCurrent;

import java.io.Serializable;

import model.User;

public class TaiWatchProfile extends AppCompatActivity {

    TextView fullname, address, username;
    ImageButton logoutBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tai_watch_profile);

        init();
    }

    private void init() {
        fullname = findViewById(R.id.profile_fullname_id);
        address = findViewById(R.id.profile_address_id);
        username = findViewById(R.id.profile_username_id);
//        logoutBtn = findViewById(R.id.profile_logout_btn);
        Intent i = getIntent();
        User u = (User)i.getSerializableExtra("user");
        if (u != null) {
            fullname.setText(u.getFullName());
            address.setText(u.getAddress());
            username.setText(u.getUsername());
        }

//        if (u.getId() != SocketCurrent.instance.getClient().getId()) {
//            logoutBtn.setVisibility(View.GONE);
//        }else {
//            logoutBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent i = new Intent(TaiWatchProfile.this, SplashScreen.class);
//                    HomeController.getInstance().setRunning(false);
//                    SocketCurrent.instance.logOut();
//                    startActivity(i);
//                    finish();
//                }
//            });
//        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}