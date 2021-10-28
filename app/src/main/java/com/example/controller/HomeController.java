package com.example.controller;

import android.graphics.Bitmap;
import android.net.Uri;
import android.graphics.Matrix;

import com.example.chatapp2.ChatScreen;
import com.example.testTai.TaiChatSceneActivity;
import com.example.testTai.TaiFriendRequestActivtity;
import com.example.testTai.TaiFriendScene;
import com.example.testTai.TaiSearchUserActivity;

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
    //Fake
    private Bitmap avatarImg;
    public HomeController() {
        if (instance == null) {
            instance = this;
        }
        isRunning = true;
        roomList = new ArrayList<>();
        listTaskRunning = new ArrayList<>();


        homeListening = new HomeListening();
        homeListening.start();
        //sendData(new ObjectWrapper(SocketCurrent.instance.getClient().getId(), ConnectionType.ONLINE_INFORM));
    }
    public void StopHomeController() {
        isRunning = false;
        instance = null;
    }

    public static HomeController getInstance() {return instance;}

    public Bitmap getAvatarImg() {
        return avatarImg;
    }

    public void setAvatarImg(Bitmap avatarImg) {
        this.avatarImg = avatarImg;
    }

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

    public void logOut() {
        isRunning = false;
        instance = null;
        homeListening.interrupt();
    }

    class HomeListening extends Thread {
        public HomeListening() {
            super();
        }

        @Override
        public void run() {
            try {
                while (isRunning && HomeController.getInstance() != null && SocketCurrent.instance != null) {
                    ObjectInputStream ois = new ObjectInputStream(SocketCurrent.instance.getMySocket().getInputStream());
                    {
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

                                                }
                                                break;
                                            case ONLINE_INFORM:
                                                break;
                                            case REPLY_CHAT:
                                                if (task.getData() instanceof TaiChatSceneActivity) {
                                                    System.out.println("Update List Message");
                                                    List<Message> list = (List<Message>) data.getData();
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


                                                break;

                                            case REPLY_GETROOMFRIEND:
                                                Room r = (Room) data.getData();
//                                            SocketCurrent.instance.getClient().getRoomList().add(r);
//                                            roomList = SocketCurrent.instance.getClient().getRoomList();
                                                if (task.getData() instanceof TaiFriendScene) {
                                                    TaiFriendScene taiFriendScene = (TaiFriendScene) task.getData();
                                                    taiFriendScene.gotoChat(r);
                                                }
//                                            if (chatScreenActivity != null)
//                                                chatScreenActivity.updateRoom(roomList);
                                                break;
                                            case REPLY_ADDFRIEND:

                                                break;
                                            case REPLY_DECLINEFRIEND:

                                                break;
                                            case REPLY_GETFRIENDREQUEST:
                                                List<FriendRequest> list = (List<FriendRequest>) data.getData();
                                                SocketCurrent.instance.getClient().setFriendRequestList(list);
                                                if (task.getData() instanceof TaiFriendRequestActivtity) {
                                                    ((TaiFriendRequestActivtity) task.getData()).updateUIFriendRequest(list);
                                                    System.out.println("Update all friendRequest");
                                                }
                                                break;
                                            case REPLY_GETFRIEND:
                                                List<User> listF = (List<User>)data.getData();
                                                SocketCurrent.instance.getClient().setFriendList(listF);
                                                if (task.getData() instanceof  TaiFriendScene) {
                                                    ((TaiFriendScene)task.getData()).updateFriendList();
                                                    ((TaiFriendScene)task.getData()).canRequest = true;

                                                    System.out.println("Get Friend Response");
                                                }
                                                break;
                                            case REPLY_SEARCH:
                                                List<User> listS = (List<User>)data.getData();
                                                if (task.getData() instanceof TaiSearchUserActivity) {
                                                    ((TaiSearchUserActivity)task.getData()).updateSearchView(listS);
                                                    System.out.println("Update View Search");
                                                }
                                                else if (task.getData() instanceof ChatScreen) {
                                                    ((ChatScreen)task.getData()).updateSearchUserToCreateGroup(listS);
                                                    System.out.println("Update view search in main activity");
                                                }
                                                break;
                                        }
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
