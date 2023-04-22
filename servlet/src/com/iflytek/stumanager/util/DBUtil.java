package com.iflytek.stumanager.util;

import java.sql.*;

public class DBUtil {

    private static Connection conn;
    private static Statement stmt;
    private static PreparedStatement pstmt;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public DBUtil() {
    }

    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/stu_db2?useSSL=false&serverTimezone=UTC";
            conn = DriverManager.getConnection(url, "", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static Statement getStatement() {
        Connection conn = getConnection();
        try {
            if (conn != null)
                stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stmt;
    }

    public static PreparedStatement getPreparedStatement(String sql) {
        Connection conn = getConnection();
        try {
            if (conn != null)
                pstmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pstmt;
    }

    public static void closeDBResources() {
        try {
            if (pstmt != null && !pstmt.isClosed())
                pstmt.close();
            if (stmt != null && !stmt.isClosed())
                stmt.close();
            if (conn != null && !conn.isClosed())
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
