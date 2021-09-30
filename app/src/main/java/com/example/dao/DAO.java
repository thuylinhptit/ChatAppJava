package com.example.dao;

import static java.sql.DriverManager.*;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {
    protected static Connection con = null;
//=c33x>d[tcw]}U)h
//    private static final String dbUrl = "jdbc:mysql://192.168.1.104//:3306/chatapp/";
//    private static final String userDB="root";
//    private static final String passwordDB="123456789";
//    private static final String dbClass="com.mysql.jdbc.Driver";
    public DAO() {
        initDAO();
    }

    private void initDAO() {
        if (con == null) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            String dbUrl = "jdbc:mysql://google/chatapp?cloudSqlInstance=atomic-legacy-327513:asia-east1:root&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=root&password=123456789";
            String dbClass = "com.mysql.jdbc.Driver";

            try {
                Class.forName(dbClass);
                con = DriverManager.getConnection (dbUrl);
            }catch(Exception e) {
                e.printStackTrace();
            }

        }
    }
}
