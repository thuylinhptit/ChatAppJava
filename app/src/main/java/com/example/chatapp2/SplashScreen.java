package com.example.chatapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.WindowManager;

import com.example.controller.SocketCurrent;

import model.IPAddress;

public class SplashScreen extends AppCompatActivity {
    public static String HOST = "192.168.1.21";
    private static int SPLASH_TIMER=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        new SocketCurrent(new IPAddress(HOST, 9086));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent=new Intent(SplashScreen.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIMER);

    }
}