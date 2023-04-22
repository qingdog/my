package com.iflytek.stumanager.dao.impl;

import com.iflytek.stumanager.dao.AdminDao;
import com.iflytek.stumanager.util.DBUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDaoImpl implements AdminDao {
    @Override
    public boolean isExists(String account, String password) {
        boolean isExists = false;

        String sql = "select * from admin where account=? and password='" + password + "'";

        PreparedStatement pstmt = DBUtil.getPreparedStatement(sql);
        try {
            pstmt.setString(1, account);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                isExists = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isExists;
    }
}
