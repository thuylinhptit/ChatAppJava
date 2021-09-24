package com.example.dao;

import com.example.model.Room;
import com.example.model.User;

import java.sql.PreparedStatement;

public class UserInRoomDAO extends DAO{

    public boolean joinRoom(User uer, Room room) {
        String sql = "insert into tbluserinroom(userid, roomid, role) value(?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, uer.getId());
            ps.setInt(2, room.getId());
            ps.setString(3, "");
            ps.executeUpdate();
            return true;
        }catch ( Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean outRoom(User user, Room room) {
        String sql = "delete from tbluserinroom where userid=? and roomid=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, user.getId());
            ps.setInt(2, room.getId());
            ps.executeUpdate();
            return true;
        }catch ( Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
