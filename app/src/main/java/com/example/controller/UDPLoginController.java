package com.example.controller;

import com.example.chatapp2.LoginActivity;
import com.example.chatapp2.SplashScreen;
import com.example.testTai.RegisterActivity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Connection;
import java.util.Random;

import model.ConnectionType;
import model.IPAddress;
import model.ObjectWrapper;
import model.User;

public class UDPLoginController {
    private static UDPLoginController instance = null;
    private DatagramSocket mySocket;
    private LoginActivity loginActivityFrm;
    private RegisterActivity registerActivity;
    private LoginListening myListening;
    private IPAddress ipAddress = new IPAddress(SplashScreen.host, 1000);
    private IPAddress ipAddressServer = new IPAddress(SplashScreen.host, 1000);

    public boolean isLoggin = false;


    public UDPLoginController() {
        int portClient = new Random().nextInt() % 4000 + 1500;
        ipAddress.setPort(portClient);
        if (instance == null) {
            instance = this;
        }
    }
    public UDPLoginController(LoginActivity loginActivityFrm) {
        if (instance == null) {
            instance = this;
        }
        int portClient = new Random().nextInt(4000) + 1500;;
        ipAddress.setPort(portClient);

//        mySocket = SocketCurrent.instance.getMySocket();
        this.loginActivityFrm = loginActivityFrm;
    }

    public void openConnection() {
        try {
            isLoggin = false;
//            myListening = new LoginListening();
            mySocket = new DatagramSocket(ipAddress.getPort());
            // UDP not use multithread so I comment this code below.
            // For TCP you've just uncomment this code below;
//            myListening.start();
        } catch (Exception e) {
            loginActivityFrm.showToast("error when connect to server :(");
            e.printStackTrace();
        }
    }

    public static UDPLoginController getInstance() {
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


    public DatagramSocket getMySocket() {
        return mySocket;
    }

    public void setMySocket(DatagramSocket mySocket) {
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



    public boolean sendData(ObjectWrapper data){
        try {
            //prepare the buffer and write the data to send into the buffer
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(data);
            oos.flush();

            //create data package and send
            byte[] sendData = baos.toByteArray();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName(ipAddressServer.getHost()), ipAddressServer.getPort());
            mySocket.send(sendPacket);

        } catch (Exception e) {
            e.printStackTrace();
            loginActivityFrm.showToast("Error in sending data package");
            return false;
        }
        return true;
    }

    public ObjectWrapper receiveData(){
        ObjectWrapper result = null;
        try {
            //prepare the buffer and fetch the received data into the buffer
            byte[] receiveData = new byte[1024 * 5];
            DatagramPacket receivePacket = new  DatagramPacket(receiveData, receiveData.length);
            mySocket.receive(receivePacket);

            //read incoming data from the buffer
            ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);
            ObjectInputStream ois = new ObjectInputStream(bais);
            result = (ObjectWrapper)ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            loginActivityFrm.showToast("Error in receiving data package");
        }
        return result;
    }


    class LoginListening extends Thread {

        public LoginListening() {
            super();
        }

        @Override
        public void run() {
            try {
                while (!isLoggin) {
                    //Receiver Data
                    byte[] receiveData = new byte[1024 * 5];
                    DatagramPacket receivePacket = new  DatagramPacket(receiveData, receiveData.length);
                    mySocket.receive(receivePacket);

                    //read incoming data from the buffer
                    ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    ObjectWrapper data = (ObjectWrapper)ois.readObject();

                    //processing
                    ObjectWrapper resultData = new ObjectWrapper();

                    if (data.getChoice() == ConnectionType.REPLY_LOGIN) {
                        System.out.println("Reply_login");
                        User result = (User)data.getData();
                        if (result != null) {
                            SocketCurrent.instance.setClient(result);
                            loginActivityFrm.changeScreenToMain();
                            isLoggin = false;
                            loginActivityFrm.showToast("Login Successful");
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


                    if (resultData.getData() != null) {
                        //prepare the buffer and write the data to send into the buffer
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ObjectOutputStream oos = new ObjectOutputStream(baos);
                        oos.writeObject(resultData);
                        oos.flush();

                        //create data package and send
                        byte[] sendData = baos.toByteArray();
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
                        mySocket.send(sendPacket);
                    }

                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
