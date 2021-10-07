package com.example.chatapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.controller.LoginController;
import com.example.testTai.RegisterActivity;

import model.ConnectionType;
import model.IPAddress;
import model.ObjectWrapper;
import model.User;

import java.io.IOException;
import java.net.Socket;

public class LoginActivity extends AppCompatActivity {
    EditText usernameTxt;
    EditText passwordTxt;
    Button loginBtn, registerBtn;
    private LoginController loginController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (LoginController.getInstance() == null || (LoginController.getInstance() != null && !LoginController.getInstance().isLoggin)) {
            loginController = new LoginController(this);
            loginController.openConnection();
        }
        init();
    }

    private void init() {
        usernameTxt = (EditText) findViewById(R.id.activity_main_usernameEditText);
        passwordTxt = (EditText) findViewById(R.id.activity_main_passwordEditText);
        loginBtn = (Button) findViewById(R.id.activity_main_loginButton);
        registerBtn = (Button) findViewById(R.id.activity_main_registerButton);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User u = new User(usernameTxt.getText().toString(), passwordTxt.getText().toString());
                loginController.sendData(new ObjectWrapper(u, ConnectionType.LOGIN));
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeScreenToRegister();
//                new RegisterActivity().setVisible(true);
            }
        });
    }

    public void changeScreenToMain() {
        this.runOnUiThread(new Runnable() {
            public void run() {
                loginController.closeConnection();
                changeIntent();
            }
        });

    }

    private void changeScreenToRegister() {
        this.runOnUiThread(new Runnable() {
            public void run() {
                Intent myIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(myIntent);
            }
        });

    }
    public void showToast(String text) {
        this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void changeIntent() {
        Intent myIntent = new Intent(LoginActivity.this, ChatScreen.class);
        startActivity(myIntent);
        finish();
    }

}