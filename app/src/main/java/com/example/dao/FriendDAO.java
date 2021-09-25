package com.example.dao;

import com.example.model.FriendRequest;
import com.example.model.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FriendDAO extends DAO{

    public FriendDAO() {
        super();
    }

    public List<User> getFriends(int user) {
        UserDAO userDAO = new UserDAO();

        List<User> users = new ArrayList<>();
        String sql = "select tblfriend.idfriend2 from tblfriend where idfriend1=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                User userr = userDAO.getUser(rs.getInt("idfriend2"));
                users.add(userr);
            }

        } catch ( Exception e) {

        }
        return users;
    }

    public boolean addFriend(FriendRequest fr) {

        String sql = "insert into tblfriend(idfriend1, idfriend2, createat) value(?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, fr.getReceiver().getId());
            ps.setInt(2, fr.getSender().getId());
            ps.setDate(3, new Date(Calendar.getInstance().getTimeInMillis()));
            ps.executeUpdate();
            return true;

        } catch ( Exception e) {

        }
        return false;
    }

    public boolean deleteFriend(User friend1, User friend2) {
        String sql = "delete from tblfriend where idfriend1=? or idfriend2=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, friend1.getId());
            ps.setInt(2, friend2.getId());
            ps.executeUpdate();
            return true;

        } catch ( Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
