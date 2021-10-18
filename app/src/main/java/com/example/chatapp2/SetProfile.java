package com.example.chatapp2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetProfile extends AppCompatActivity {
    CircleImageView circleImageView;
    ImageButton imageButton;
    Uri uri;
    public static final int IMAGE_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile);
        circleImageView = findViewById(R.id.circle_avt);
        imageButton = findViewById( R.id.btn_image);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageForm();
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