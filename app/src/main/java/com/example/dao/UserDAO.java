package com.example.dao;

import com.example.model.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UserDAO extends DAO{

    public UserDAO() {
        super();
    }

    public boolean checkLogin(User user) {
        String sql = "select * from tbluser where username=? and password=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createAccount(User user) {
        String sql = "insert into tbluser(username, password, email, phone, address, dob, fullname, createat) value(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhoneNum());
            ps.setDate(5, user.getDateOfBirth());
            ps.setString(6, user.getUsername());
            ps.setDate(7, new Date(Calendar.getInstance().getTimeInMillis()));

            int rs = ps.executeUpdate();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteAccount(User user) {
        String sql = "delete from tbluser where id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, user.getId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean updateAccount(User user) {
        String sql = "update tbluser set password=?,email=?,fullname=?,address=?,dob=?,phone=? where id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getFullName());
            ps.setString(4, user.getAddress());
            ps.setDate(5, user.getDateOfBirth());
            ps.setString(6, user.getPhoneNum());
            ps.setInt(7, user.getId());

            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public User getUser(int id) {
        User user = new User();
        String sql = "select * from tbluser where id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setAddress(rs.getString("address"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNum(rs.getString("phone"));
                user.setDateOfBirth(rs.getDate("dob"));
                user.setFullName(rs.getString("fullname"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<User> findUsersByFullName(String fullname) {
        List<User> listUser = new ArrayList<>();
        String sql = "select id from tbluser where fullname like ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, fullname);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                User u = getUser(rs.getInt("id"));
                listUser.add(u);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listUser;
    }

    public List<User> findUsersByPhoneNum(String phoneNum) {
        List<User> listUser = new ArrayList<>();
        String sql = "select id from tbluser where phone like ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, phoneNum);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                User u = getUser(rs.getInt("id"));
                listUser.add(u);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listUser;
    }

    public List<User> findUsersByEmail(String email) {
        List<User> listUser = new ArrayList<>();
        String sql = "select id from tbluser where email like ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                User u = getUser(rs.getInt("id"));
                listUser.add(u);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listUser;
    }
}
