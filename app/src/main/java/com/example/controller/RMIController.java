package com.example.controller;


import com.example.chatapp2.SplashScreen;

import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Client;

import interfacecontrol.*;
import model.IPAddress;

public class RMIController {
    public static RMIController Instance = null;
    private IPAddress serverAddress = new IPAddress(SplashScreen.HOST, 9088);
    private String rmiKey = "rmi";
    private IService iService;

    public RMIController() {
        if (Instance == null) {
            Instance = this;
            start();
        }
    }

    public void start() {
        try{
            // get the registry
            // lookup the remote objects
            CallHandler callHandler = new CallHandler();
            Client client = new Client(serverAddress.getHost(), serverAddress.getPort(), callHandler);
            iService = (IService) client.getGlobal(IService.class);
            System.out.println("Got Registery");

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public IService getiService() {
        return iService;
    }

    public void setiService(IService iService) {
        this.iService = iService;
    }
}
