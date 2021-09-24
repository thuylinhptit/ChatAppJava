package com.example.dao;

import com.example.model.Room;
import com.example.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO extends DAO{

    public RoomDAO() {
        super();
    }

    public boolean changeRoleUserInRom(Room room, User user, String role) {
        UserInRoomDAO userInRoomDAO = new UserInRoomDAO();
        boolean foundUserInRoom = userInRoomDAO.checkUserInRoom(user, room);
        if (!foundUserInRoom) return false;
        try {
            String sql = "update tbluserinroom set role=? where roomid=? and userid=?";
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, room.getId());
            ps.setInt(2, user.getId());
            ps.setString(3, role);
            ps.executeUpdate();
            return true;
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return false;
    }
    public List<User> getUsers(Room room) {
        List<User> users = new ArrayList<>();

        return users;
    }
    public boolean createRoom(List<User> users, Room room) {
        String sql = "insert into tblroom(name) value(?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, room.getName());
            ps.executeUpdate();

            UserInRoomDAO userInRoomDAO = new UserInRoomDAO();

            for (User u : users) {
                userInRoomDAO.joinRoom(u, room);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Room getRoom(int id) {
        Room room = new Room();
        String sql = "select * from tblroom where id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                room.setId(rs.getInt("id"));
                room.setName(rs.getString("name"));
                room.setCreateDate(rs.getDate("createat"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return room;
    }

    public boolean deleteRoom(Room room) {
        String sql = "delete from tblroom where id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, room.getId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}
