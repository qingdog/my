// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MysqlAdminDao.java

package com.iflytek.stumanager.dao;

import com.iflytek.stumanager.util.DBUtil;
import java.sql.*;

// Referenced classes of package com.iflytek.stumanager.dao:
//            AdminDao

public class MysqlAdminDao extends AdminDao
{

    public MysqlAdminDao()
    {
    }

    public boolean isExists(String account, String password)
    {
        boolean isExists = false;
        String sql = (new StringBuilder("select * from admin where account=? and password='")).append(password).append("'").toString();
        PreparedStatement pstmt = DBUtil.getPreparedStatement(sql);
        try
        {
            pstmt.setString(1, account);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
                isExists = true;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return isExists;
    }
}
