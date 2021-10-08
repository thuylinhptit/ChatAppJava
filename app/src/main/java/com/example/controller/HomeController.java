package com.example.controller;

import com.example.chatapp2.ChatScreen;
import com.example.testTai.TaiChatSceneActivity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import model.ConnectionType;
import model.FriendRequest;
import model.Message;
import model.ObjectWrapper;
import model.Room;
import model.User;

public class HomeController {
    private static HomeController instance;
    private boolean isRunning = true;
    private HomeListening homeListening;
    private ChatScreen chatScreenActivity;
    private List<Room> roomList;
    private List<ObjectWrapper> listTaskRunning;
    public HomeController() {
        if (instance == null) {
            instance = this;
        }
        isRunning = true;
        roomList = new ArrayList<>();
        listTaskRunning = new ArrayList<>();
        homeListening = new HomeListening();
        homeListening.start();
    }
    public void StopHomeController() {
        isRunning = false;
        instance = null;
    }

    public static HomeController getInstance() {return instance;}
    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public HomeListening getHomeListening() {
        return homeListening;
    }

    public void setHomeListening(HomeListening homeListening) {
        this.homeListening = homeListening;
    }

    public ChatScreen getChatScreenActivity() {
        return chatScreenActivity;
    }

    public void setChatScreenActivity(ChatScreen chatScreenActivity) {
        this.chatScreenActivity = chatScreenActivity;
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }

    public List<ObjectWrapper> getListTaskRunning() {
        return listTaskRunning;
    }

    public void setListTaskRunning(List<ObjectWrapper> listTaskRunning) {
        this.listTaskRunning = listTaskRunning;
    }

    public void sendData(Object data) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(SocketCurrent.instance.getMySocket().getOutputStream());
            oos.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    class HomeListening extends Thread {
        public HomeListening() {
            super();
        }

        @Override
        public void run() {
            try {
                while (isRunning) {
                    ObjectInputStream ois = new ObjectInputStream(SocketCurrent.instance.getMySocket().getInputStream());
                    Object obj = ois.readObject();
                    if (obj instanceof ObjectWrapper) {
                        ObjectWrapper data = (ObjectWrapper) obj;

                        for (ObjectWrapper task : listTaskRunning) {
                            if (task.getData() != null) {
                                if (data.getChoice() == task.getChoice()) {
                                    switch (task.getChoice()) {
                                        case REPLY_FRIENDREQUEST:
                                            FriendRequest friendRequest = (FriendRequest) data.getData();
                                            if (friendRequest.getReceiver().getId() == SocketCurrent.instance.getClient().getId()) {
                                                // Show Message

                                            }
                                            break;
                                        case ONLINE_INFORM:
                                            // Get User
                                            User user = (User) data.getData();
                                            // Check User is my friend?
                                            if (SocketCurrent.instance.getClient().getFriendList().contains(user)) {
                                                // this user is friend
                                                // Update UI
                                                ChatScreen chatScreenActivity = (ChatScreen) task.getData();
                                                if (chatScreenActivity != null)
                                                    chatScreenActivity.activeOnlineFriend(user.getId());
                                            }
                                            break;
                                        case REPLY_CHAT:
                                            if (task.getData() instanceof TaiChatSceneActivity){
                                                System.out.println("Update List Message");
                                                List<Message> list = (List<Message>)data.getData();
                                                TaiChatSceneActivity taiChatSceneActivity = (TaiChatSceneActivity) task.getData();
                                                taiChatSceneActivity.updateUI(list);
                                            }
                                            break;
                                        case REPLY_GETROOM:
                                            roomList = (List<Room>) data.getData();
                                            SocketCurrent.instance.getClient().setRoomList(roomList);
                                            //Update UI
                                            if (task.getData() instanceof ChatScreen) {
                                                ChatScreen chatScreenActivity = (ChatScreen) task.getData();
                                                chatScreenActivity.updateRoom(roomList);
                                                chatScreenActivity.setCanSendRequest(true);
                                            }
//                                            else if (task.getData() instanceof TaiChatSceneActivity){
//                                                TaiChatSceneActivity taiChatSceneActivity = (TaiChatSceneActivity) task.getData();
//                                                taiChatSceneActivity.updateUI(roomList);
//                                            }

                                            break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
