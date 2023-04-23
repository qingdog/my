// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MysqlClassesDao.java

package com.iflytek.stumanager.dao;

import com.iflytek.stumanager.po.Classes;
import com.iflytek.stumanager.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.iflytek.stumanager.dao:
//            ClassesDao

public class MysqlClassesDao
    implements ClassesDao
{

    public MysqlClassesDao()
    {
    }

    public List queryAllClasses()
    {
        List clsList;
        PreparedStatement pstmt;
        clsList = new ArrayList();
        String sql = "select * from classes";
        pstmt = DBUtil.getPreparedStatement(sql);
        try
        {
            ResultSet rs;
            Classes cls;
            for(rs = pstmt.executeQuery(); rs.next(); clsList.add(cls))
            {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                cls = new Classes(id, name);
            }

            rs.close();
            //break MISSING_BLOCK_LABEL_112;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        DBUtil.closeDBResources();
        //break MISSING_BLOCK_LABEL_115;
        Exception exception;
        //exception;
        DBUtil.closeDBResources();
        //throw exception;
        DBUtil.closeDBResources();
        return clsList;
    }
}
