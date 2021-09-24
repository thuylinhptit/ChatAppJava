package com.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAO {
    protected static Connection con = null;

    private static final String dbUrl = "jdbc:mysql://localhost:3306/chat?autoReconnect=true&useSSL=false";
    private static final String userDB="root";
    private static final String passwordDB="123456789";
    private static final String dbClass="com.mysql.jdbc.Driver";
    public DAO() {
        initDAO();
    }

    private void initDAO() {
        if (con == null) {
            try {
                Class.forName(dbClass);
                con = DriverManager.getConnection (dbUrl, userDB, passwordDB);
            }catch(Exception e) {
                e.printStackTrace();
            }

        }
    }


}
