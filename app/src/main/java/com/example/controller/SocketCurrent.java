package com.example.controller;

import java.net.Socket;

import model.IPAddress;
import model.User;

public class SocketCurrent {
    public static SocketCurrent instance;
    private Socket mySocket;
    private IPAddress ipAddress;
    private User client;

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public SocketCurrent(IPAddress ip) {
        if (instance == null) {
            instance = this;
        }
        try {
            this.ipAddress = ip;
            mySocket = new Socket(ip.getHost(), ip.getPort());
            System.out.println("Run here");
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public Socket getMySocket() {
//        if (mySocket == null) {
//            try {
//                mySocket = new Socket(ipAddress.getHost(), ipAddress.getPort());
//            }
//            catch ( Exception e ) {
//                e.printStackTrace();
//            }
//        }
        return mySocket;
    }

}
