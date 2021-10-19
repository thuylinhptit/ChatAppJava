package com.example.testTai;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chatapp2.ChatScreen;
import com.example.chatapp2.R;
import com.example.chatapp2.SetProfile;
import com.example.chatapp2.SplashScreen;
import com.example.controller.HomeController;
import com.example.controller.SocketCurrent;

import java.io.Serializable;

import model.ConnectionType;
import model.ObjectWrapper;
import model.User;

public class TaiWatchProfile extends AppCompatActivity {

    Button  editProfile;
    LinearLayout groupedit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tai_watch_profile);

        init();
    }

    private void init() {
        editProfile = findViewById(R.id.profile_edit_btn);
        Intent i = getIntent();
        User u = (User)i.getSerializableExtra("user");

        editProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(TaiWatchProfile.this, SetProfile.class);
                    startActivity(i);
                }
            });


        }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}