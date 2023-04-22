// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MysqlDaoFactory.java

package com.iflytek.stumanager.dao;


// Referenced classes of package com.iflytek.stumanager.dao:
//            DaoFactory, MysqlAdminDao, MysqlClassesDao, MysqlStudentDao, 
//            AdminDao, ClassesDao, StudentDao

public class MysqlDaoFactory extends DaoFactory
{

    public MysqlDaoFactory()
    {
    }

    public static MysqlDaoFactory getInstance()
    {
        return instance;
    }

    public AdminDao createAdminDao()
    {
        return new MysqlAdminDao();
    }

    public ClassesDao createClassesDao()
    {
        return new MysqlClassesDao();
    }

    public StudentDao createStudentDao()
    {
        return new MysqlStudentDao();
    }

    private static MysqlDaoFactory instance = new MysqlDaoFactory();

}
