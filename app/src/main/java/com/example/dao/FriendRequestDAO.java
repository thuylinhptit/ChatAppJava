package com.example.dao;

import com.example.model.FriendRequest;
import com.example.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FriendRequestDAO extends DAO{

    public List<FriendRequest> getFriendRequests (User user) {
        List<FriendRequest> requests = new ArrayList<>();
        UserDAO userDAO = new UserDAO();
        String sql = "select * from tblfriendrequest where receiver=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user.getId());
            ResultSet rs=ps.executeQuery();
            while(rs.next()) {
                FriendRequest fr = new FriendRequest();
                fr.setId(rs.getInt("id"));
                fr.setReceiver(user);
                fr.setSender(userDAO.getUser(rs.getInt("sender")));
                fr.setCreateDate(rs.getDate("createat"));
                requests.add(fr);
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        return requests;
    }

    public boolean deleteRequest(FriendRequest request) {
        String sql = "delete from tblfriendrequest where receiver=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, request.getReceiver().getId());
            ps.executeUpdate();
            return true;
        } catch ( Exception e ) {
            e.printStackTrace();;
        }
        return false;
    }

    public boolean acceptRequest(FriendRequest request) {
        deleteRequest(request);
        FriendDAO friendDAO = new FriendDAO();

        return friendDAO.addFriend(request);
    }

}
