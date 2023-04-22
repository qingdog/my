// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdminService.java

package com.iflytek.stumanager.business;

import com.iflytek.stumanager.dao.AdminDao;
import com.iflytek.stumanager.dao.MysqlDaoFactory;
import com.iflytek.stumanager.po.Admin;

public class AdminService
{

    public AdminService()
    {
        adminDao = MysqlDaoFactory.getInstance().createAdminDao();
    }

    public boolean legal(Admin admin)
    {
        return adminDao.isExists(admin.getAccount(), admin.getPassword());
    }

    private AdminDao adminDao;
}
