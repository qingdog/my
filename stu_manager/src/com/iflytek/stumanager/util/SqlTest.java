// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SqlTest.java

package com.iflytek.stumanager.util;

import java.io.PrintStream;
import java.sql.*;

public class SqlTest
{

    public SqlTest()
    {
    }

    public static Connection getConnection()
    {
        try
        {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/stu_db2?serverTimezone=UTC", "", "");
            System.out.println("MYSQL\u8FDE\u63A5\u6210\u529F\uFF01");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.print("MYSQL\u8FDE\u63A5\u5931\u8D25\uFF01");
        }
        return conn;
    }

    public static void main(String args[])
        throws SQLException
    {
        conn = getConnection();
        if(!conn.isClosed())
            System.out.println("Succeeded connecting to the Database!");
        String sql = "select * from admin where id = '1' ";
        PreparedStatement ps = conn.prepareStatement(sql);
        for(ResultSet rs = ps.executeQuery(); rs.next(); System.out.print((new StringBuilder(String.valueOf(rs.getString("id")))).append(" ").append(rs.getString("account")).append(" ").append(rs.getString("password")).append('\n').toString()));
    }

    private static Connection conn;

    static 
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("\u52A0\u8F7D\u9A71\u52A8\u6210\u529F\uFF01");
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
            System.out.println("\u52A0\u8F7D\u9A71\u52A8\u5931\u8D25\uFF01");
        }
    }
}
