package com.example.testTai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.chatapp2.R;

import java.io.Serializable;

import model.User;

public class TaiWatchProfile extends AppCompatActivity {

    TextView fullname, address, username;
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
        Intent i = getIntent();
        User u = (User)i.getSerializableExtra("user");
        if (u != null) {
            fullname.setText(u.getFullName());
            address.setText(u.getAddress());
            username.setText(u.getUsername());
        }
    }
}