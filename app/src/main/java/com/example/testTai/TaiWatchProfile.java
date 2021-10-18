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

import com.example.chatapp2.R;
import com.example.chatapp2.SplashScreen;
import com.example.controller.HomeController;
import com.example.controller.SocketCurrent;

import java.io.Serializable;

import model.ConnectionType;
import model.ObjectWrapper;
import model.User;

public class TaiWatchProfile extends AppCompatActivity {

    EditText fullname, address, username, mail;
    ImageButton logoutBtn;
    Button edit_accept, edit_decline, editProfile;
    LinearLayout groupedit;
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
        mail = findViewById(R.id.profile_email_id);

        username.setClickable(false);
        fullname.setClickable(false);
        address.setClickable(false);
        mail.setClickable(false);

        edit_decline = findViewById(R.id.profile_edit_decline_btn);
        edit_accept = findViewById(R.id.profile_edit_accept_btn);
        editProfile = findViewById(R.id.profile_edit_btn);
        logoutBtn = findViewById(R.id.profile_logout_btn);
        groupedit = findViewById(R.id.groupEditProfile);
        Intent i = getIntent();
        User u = (User)i.getSerializableExtra("user");
        if (u != null) {
            fullname.setText(u.getFullName());
            address.setText(u.getAddress());
            username.setText(u.getUsername());
            mail.setText(u.getEmail());
        }

        if (u.getId() != SocketCurrent.instance.getClient().getId()) {
            logoutBtn.setVisibility(View.GONE);
            groupedit.setVisibility(View.GONE);
            editProfile.setVisibility(View.GONE);

        }else {
            logoutBtn.setVisibility(View.VISIBLE);
            groupedit.setVisibility(View.GONE);
            editProfile.setVisibility(View.VISIBLE);
            logoutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(TaiWatchProfile.this, SplashScreen.class);
                    HomeController.getInstance().setRunning(false);
                    for (ObjectWrapper ob : HomeController.getInstance().getListTaskRunning()) {
                        if (ob.getData() != null) {
                            if (ob.getData() instanceof Activity) {
                                ((Activity)ob.getData()).finish();
                            }
                        }
                    }
                    HomeController.getInstance().logOut();
                    SocketCurrent.instance.logOut();
                    startActivity(i);
                    finish();
                }
            });

            editProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logoutBtn.setVisibility(View.GONE);
                    groupedit.setVisibility(View.VISIBLE);
                    editProfile.setVisibility(View.GONE);
                    fullname.setClickable(true);
                    address.setClickable(true);
                    mail.setClickable(true);
                }
            });

            edit_accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User u = SocketCurrent.instance.getClient();
                    u.setFullName(fullname.getText().toString());
                    u.setAddress(address.getText().toString());
                    u.setEmail(mail.getText().toString());
                    SocketCurrent.instance.setClient(u);

                    ObjectWrapper objectWrapper = new ObjectWrapper(u, ConnectionType.EDITPROFILE);
                    HomeController.getInstance().sendData(objectWrapper);
                    System.out.println("Send Request Edit Profile");
                    fullname.setClickable(false);
                    address.setClickable(false);
                    mail.setClickable(false);
                    logoutBtn.setVisibility(View.VISIBLE);
                    groupedit.setVisibility(View.GONE);
                    editProfile.setVisibility(View.VISIBLE);
                }
            });

            edit_decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User u = SocketCurrent.instance.getClient();

                    fullname.setText(u.getFullName());
                    address.setText(u.getAddress());
                    username.setText(u.getUsername());
                    mail.setText(u.getEmail());

                    fullname.setClickable(false);
                    address.setClickable(false);
                    mail.setClickable(false);

                    logoutBtn.setVisibility(View.VISIBLE);
                    groupedit.setVisibility(View.GONE);
                    editProfile.setVisibility(View.VISIBLE);
                }
            });
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}