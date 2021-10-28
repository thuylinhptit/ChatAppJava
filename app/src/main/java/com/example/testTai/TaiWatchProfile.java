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

import de.hdodenhof.circleimageview.CircleImageView;
import model.ConnectionType;
import model.ObjectWrapper;
import model.User;

public class TaiWatchProfile extends AppCompatActivity {

    Button  editProfile;
    LinearLayout groupedit;
    TextView name;
    CircleImageView avatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tai_watch_profile);

        init();
    }

    private void init() {
        avatar = findViewById(R.id.getImage);
        editProfile = findViewById(R.id.profile_edit_btn);
        name = findViewById(R.id.textFullName);
        Intent i = getIntent();
        // Khi nhan vao de xem profile cua nguoi khac
        User u = (User)(i.getSerializableExtra("user"));

        //Acc chinh cua minh
//        User u = SocketCurrent.instance.getClient();

        // Nếu mình ấn vào xem profile của mình thì có thể edit,
        // ngược lại thì không cho vì đây là profile của người khác
        if (u != null) {
            System.out.println("Watch Profile");
            name.setText(u.getFullName());
            if (u.getId() == SocketCurrent.instance.getClient().getId()) {
                editProfile.setVisibility(View.VISIBLE);
                try {
                    if (HomeController.getInstance().getAvatarImg() == null) {

                        avatar.setImageResource(R.drawable.user);
                    } else
                        avatar.setImageBitmap(HomeController.getInstance().getAvatarImg());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                editProfile.setVisibility(View.GONE);
                avatar.setImageResource(R.drawable.user);
            }
        } else {
            editProfile.setVisibility(View.VISIBLE);
            if (HomeController.getInstance().getAvatarImg() != null)
                avatar.setImageBitmap(HomeController.getInstance().getAvatarImg());
        }
        editProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(TaiWatchProfile.this, SetProfile.class);
                    startActivity(i);
                    finish();
                }
            });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}