package com.example.chatapp2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.controller.HomeController;
import com.example.controller.SocketCurrent;

import de.hdodenhof.circleimageview.CircleImageView;
import model.ConnectionType;
import model.ObjectWrapper;
import model.User;

public class SetProfile extends AppCompatActivity {
    CircleImageView circleImageView;
    ImageButton imageButton;
    Button edit_accept, edit_decline;
    Uri uri;
    TextView name;
    EditText fullname, address, username, mail;
    public static final int IMAGE_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile);
        circleImageView = findViewById(R.id.circle_avt);
        imageButton = findViewById( R.id.btn_image);
        fullname = findViewById(R.id.profile_fullname_id);
        username = findViewById(R.id.profile_username_id);
        edit_decline = findViewById(R.id.profile_edit_decline_btn);
        edit_accept = findViewById(R.id.profile_edit_accept_btn);
        name = findViewById(R.id.textFullName);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageForm();

            }
        });
        edit_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User u = SocketCurrent.instance.getClient();
                u.setFullName(fullname.getText().toString());
                u.setUsername(username.getText().toString());
                name.setText(fullname.getText().toString());
                SocketCurrent.instance.setClient(u);

                ObjectWrapper objectWrapper = new ObjectWrapper(u, ConnectionType.EDITPROFILE);
                HomeController.getInstance().sendData(objectWrapper);
                System.out.println("Send Request Edit Profile");
            }
        });

        edit_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User u = SocketCurrent.instance.getClient();
                fullname.setText(u.getFullName());
                username.setText(u.getUsername());

            }
        });
    }

    private void openImageForm(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction( Intent.ACTION_GET_CONTENT );
        startActivityForResult( intent, IMAGE_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( requestCode == IMAGE_CODE && resultCode == RESULT_OK &&
                data != null && data.getData()  != null );
        uri = data.getData();
        circleImageView.setImageURI(uri);
    }



}