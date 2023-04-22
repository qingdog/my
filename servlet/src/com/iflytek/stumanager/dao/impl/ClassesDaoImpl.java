package com.iflytek.stumanager.dao.impl;

import com.iflytek.stumanager.dao.ClassesDao;
import com.iflytek.stumanager.po.Classes;
import com.iflytek.stumanager.util.DBUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassesDaoImpl implements ClassesDao {

    public ClassesDaoImpl() {
    }

    public List<?> queryAllClasses() {
        List<Classes> clsList = new ArrayList<>();
        PreparedStatement pstmt;
        String sql = "select * from classes";
        pstmt = DBUtil.getPreparedStatement(sql);
        try {
            ResultSet rs;
            Classes cls;
            for (rs = pstmt.executeQuery(); rs.next(); clsList.add(cls)) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                cls = new Classes(id, name);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeDBResources();
        }
        return clsList;
    }
}
