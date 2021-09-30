package com.example.chatapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dao.UserDAO;
import com.example.model.User;

public class LoginActivity extends AppCompatActivity {
    EditText usernameTxt;
    EditText passwordTxt;
    Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        usernameTxt = (EditText) findViewById(R.id.activity_main_usernameEditText);
        passwordTxt = (EditText) findViewById(R.id.activity_main_passwordEditText);
        loginBtn = (Button) findViewById(R.id.activity_main_loginButton);
        UserDAO dao = new UserDAO();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User u = new User(usernameTxt.getText().toString(), passwordTxt.getText().toString());
                boolean login = dao.checkLogin(u);
                System.out.println("user: " + u.getUsername() + "- password: " + u.getPassword());
                if (login) {
                    changeIntent(v);
                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void changeIntent(View view) {
        Intent myIntent = new Intent(view.getContext(), ChatScreen.class);
        startActivity(myIntent);
        finish();
    }

}