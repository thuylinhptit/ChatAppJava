package com.example.controller;

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
                                                    // Show Message
//                                                if (task.getData() instanceof TaiFriendRequestActivtity) {
//                                                    ((TaiFriendRequestActivtity) task.getData()).updateUIFriendRequest();
//                                                }
                                                }
                                                break;
                                            case ONLINE_INFORM:
                                                // Get User
//                                                int userId = (int) data.getData();
//                                                // Check User is my friend?
//                                                if (task.getData() instanceof TaiFriendScene) {
//                                                    for (User u : SocketCurrent.instance.getClient().getFriendList()) {
//                                                        if (u.getId() == userId) {
//                                                            ((TaiFriendScene)task.getData()).updateFriendOnlineOffline(userId, true);
//                                                            break;
//                                                        }
//                                                    }
//                                                }
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
//                                            else if (task.getData() instanceof TaiChatSceneActivity){
//                                                TaiChatSceneActivity taiChatSceneActivity = (TaiChatSceneActivity) task.getData();
//                                                taiChatSceneActivity.updateUI(roomList);
//                                            }

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

//                                            List<Object> listFriend = (List<Object>) (data.getData());
////                                            System.out.println((List<User>)listFriend.get(1));
//                                            if ((int)(listFriend.get(0)) == SocketCurrent.instance.getClient().getId()) {
//                                                // Client nhan la th nay
//                                                Object ob = listFriend.get(1);
//                                                List<User> listF = (List<User>)ob;
//                                                System.out.println(listFriend.get(1));
//                                                System.out.println(ob);
//                                                SocketCurrent.instance.getClient().setFriendList(listF);
//                                                if (task.getData() instanceof TaiFriendScene) {
//                                                    ((TaiFriendScene)task.getData()).updateFriendList();
//                                                } else if (task.getData() instanceof TaiFriendRequestActivtity) {
//
//                                                }
//                                            }
                                                break;
                                            case REPLY_DECLINEFRIEND:
//                                            List<Object> listFriend1 = (List<Object>) (data.getData());
//                                            if ((int)(listFriend1.get(0)) == SocketCurrent.instance.getClient().getId()) {
//                                                // Client nhan la th nay
//                                                Object ob = listFriend1.get(1);
//                                                List<User> listF = (List<User>)ob;
//                                                SocketCurrent.instance.getClient().setFriendList(listF);
//                                                if (task.getData() instanceof TaiFriendRequestActivtity) {
//
//                                                }
//                                            }
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
