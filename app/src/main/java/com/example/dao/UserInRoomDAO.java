package com.example.dao;

import com.example.model.Room;
import com.example.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserInRoomDAO extends DAO{

    public UserInRoomDAO() {
        super();
    }

    public boolean checkUserInRoom(int idUser, int idRoom) {
        String sql = "select * from tbluserinroom where userid=? and roomid=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idUser);
            ps.setInt(2, idRoom);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        }catch ( Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean joinRoom(int idUser, int idRoom) {
        String sql = "insert into tbluserinroom(userid, roomid, role) value(?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idUser);
            ps.setInt(2, idRoom);
            ps.setString(3, "member");
            ps.executeUpdate();
            return true;
        }catch ( Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean outRoom(int idUser, int idRoom) {
        String sql = "delete from tbluserinroom where userid=? and roomid=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idUser);
            ps.setInt(2, idRoom);
            ps.executeUpdate();
            return true;
        }catch ( Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<User> getUsersInRoom(int roomId) {
        List<User> listUser = new ArrayList<>();
        UserDAO userDAO = new UserDAO();
        String sql = "select userid from tbluserinroom where roomid=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, roomId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listUser.add(userDAO.getUser(rs.getInt("userid")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listUser;
    }

    public boolean changeRoleUserInRom(int room, int user, String role) {
        boolean foundUserInRoom = checkUserInRoom(user, room);
        if (!foundUserInRoom) return false;
        try {
            String sql = "update tbluserinroom set role=? where roomid=? and userid=?";
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, room);
            ps.setInt(2, user);
            ps.setString(3, role);
            ps.executeUpdate();
            return true;
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return false;
    }
}
