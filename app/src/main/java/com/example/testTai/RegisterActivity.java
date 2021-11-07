package com.example.testTai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatapp2.ChatScreen;
import com.example.chatapp2.LoginActivity;
import com.example.chatapp2.R;
import com.example.controller.LoginController;
import com.example.controller.RMIController;

import model.ConnectionType;
import model.ObjectWrapper;
import model.User;

public class RegisterActivity extends AppCompatActivity {
    EditText firstNameTxt;
    EditText usernameTxt;
    EditText passwordTxt;
    Button registerBtn, loginBtn;
    LoginController loginController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        loginController = LoginController.getInstance();
        loginController.setRegisterActivity(this);
        firstNameTxt = findViewById(R.id.txtName);
        usernameTxt = findViewById(R.id.txtEmail);
        passwordTxt = findViewById(R.id.txtPwd);
        registerBtn = findViewById(R.id.btnRegister);
      //  loginBtn = findViewById(R.id.btnLogin);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setFullName(firstNameTxt.getText().toString());
                user.setUsername(usernameTxt.getText().toString());
                user.setPassword(passwordTxt.getText().toString());

//                loginController.sendData(new ObjectWrapper(user, ConnectionType.REGISTER));
                //RMI
                boolean checkRegis = RMIController.Instance.getiService().createAccount(user);
                if (checkRegis) {
                    login();
                }
            }
        });
//        loginBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                login();
//            }
//        });
    }

    public void receiveData(ObjectWrapper data) {

    }

    public void login() {
        finish();
    }

    private void changeIntent() {
        Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(myIntent);
        finish();
    }
    public void showToast(String text) {
        this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}