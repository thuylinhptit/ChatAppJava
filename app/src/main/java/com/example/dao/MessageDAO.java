package com.example.dao;

import com.example.model.Message;
import com.example.model.Room;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO extends DAO{

    public MessageDAO() {
        super();
    }

    public boolean saveMessage(Message message) {
        String sql = "insert into tblmessage(authorid, roomid, chatfileid, content, status, createat) value(?,?,?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getAuthor().getId());
            ps.setInt(2, message.getRoom().getId());
            ps.setInt(3, message.getFileChat() == null ? -1 : message.getFileChat().getId());
            ps.setString(4, message.getContent());
            ps.setString(5, message.getStatus());
            ps.setDate(6, message.getCreateDate());
            ps.executeUpdate();
            return true;
        }catch ( Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Message> getMessages(Room room) {
        List<Message> mess = new ArrayList<>();

        String sql = "select * from tblmessage where roomid=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, room.getId());
            ResultSet rs = ps.executeQuery();
            UserDAO userDAO = new UserDAO();
            RoomDAO roomDAO = new RoomDAO();
            ChatFileDAO chatFileDAO = new ChatFileDAO();
            while (rs.next()) {
                Message message = new Message();
                message.setId(rs.getInt("id"));
                message.setAuthor(userDAO.getUser(rs.getInt("authorid")));
                message.setRoom(roomDAO.getRoom(rs.getInt("roomid")));
                message.setFileChat(chatFileDAO.getFile(rs.getInt("chatfileid")));
                message.setCreateDate(rs.getDate("createat"));
                message.setStatus(rs.getString("content"));
                mess.add(message);
            }

        }catch ( Exception e) {
            e.printStackTrace();
        }
        return mess;
    }

    public boolean deleteMessage(Message message) {
        String sql = "delete from tblmessage where id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getId());
            ps.executeUpdate();
            return true;
        }catch ( Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
