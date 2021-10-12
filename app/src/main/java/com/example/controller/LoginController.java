package com.example.controller;

import com.example.chatapp2.LoginActivity;
import com.example.testTai.RegisterActivity;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;

import model.ConnectionType;
import model.IPAddress;
import model.ObjectWrapper;
import model.User;

public class LoginController {
    private static LoginController instance = null;
    private Socket mySocket;
    private LoginActivity loginActivityFrm;
    private RegisterActivity registerActivity;
    private LoginListening myListening;
    private IPAddress ipAddress = new IPAddress("localhost", 9978);
    public boolean isLoggin = false;

    public LoginController(LoginActivity loginActivityFrm) {
        if (instance == null) {
            instance = this;
        }
//        mySocket = SocketCurrent.instance.getMySocket();
        this.loginActivityFrm = loginActivityFrm;
    }

    public void openConnection() {
        try {
            isLoggin = false;
            myListening = new LoginListening();
            myListening.start();
        } catch (Exception e) {
            loginActivityFrm.showToast("error when connect to server :(");
            e.printStackTrace();
        }
    }

    public static LoginController getInstance() {
        return instance;
    }

    public RegisterActivity getRegisterActivity() {
        return registerActivity;
    }

    public void setRegisterActivity(RegisterActivity registerActivity) {
        this.registerActivity = registerActivity;
    }

    public void closeConnection() {
        instance = null;
        isLoggin = true;
    }


    public Socket getMySocket() {
        return mySocket;
    }

    public void setMySocket(Socket mySocket) {
        this.mySocket = mySocket;
    }

    public LoginActivity getLoginActivityFrm() {
        return loginActivityFrm;
    }

    public void setLoginActivityFrm(LoginActivity loginActivityFrm) {
        this.loginActivityFrm = loginActivityFrm;
    }

    public LoginListening getMyListening() {
        return myListening;
    }

    public void setMyListening(LoginListening myListening) {
        this.myListening = myListening;
    }

    public IPAddress getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(IPAddress ipAddress) {
        this.ipAddress = ipAddress;
    }
    public boolean sendData(Object obj){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(SocketCurrent.instance.getMySocket().getOutputStream());
            oos.writeObject(obj);

        } catch (Exception e) {
            e.printStackTrace();
            loginActivityFrm.showToast("Error when sending data to the server!");
            return false;
        }
        return true;
    }


    class LoginListening extends Thread {

        public LoginListening() {
            super();
        }

        @Override
        public void run() {
            try {
                while (!isLoggin) {
                    ObjectInputStream ois = new ObjectInputStream(SocketCurrent.instance.getMySocket().getInputStream());
                    Object obj = ois.readObject();
                    if (obj instanceof ObjectWrapper) {
                        ObjectWrapper data = (ObjectWrapper) obj;
                        if (data.getChoice() == ConnectionType.REPLY_LOGIN) {
                            System.out.println("Reply_login");
                            User result = (User)data.getData();
                            if (result != null) {
                                SocketCurrent.instance.setClient(result);
                                loginActivityFrm.changeScreenToMain();
                                isLoggin = false;
                                loginActivityFrm.showToast("Login Successful");
                                // Login Successfull
//                                ObjectWrapper online = new ObjectWrapper(result.getId(), ConnectionType.ONLINE_INFORM);
//                                sendData(online);
                                return;
                            } else {
                                loginActivityFrm.showToast("Username/password not correct");
                            }
                        } else if (data.getChoice() == ConnectionType.REPLY_REGISTER) {
                            String result = (String)data.getData();
                            if (result.equals("ok")) {
                                registerActivity.login();
                                registerActivity.showToast("Successful");
                            } else {
                                loginActivityFrm.showToast("Error connect server :(");
                            }
                        }
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
