// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClassesService.java

package com.iflytek.stumanager.business;

import com.iflytek.stumanager.dao.ClassesDao;
import com.iflytek.stumanager.dao.MysqlDaoFactory;
import java.util.List;

public class ClassesService
{

    public ClassesService()
    {
        classDao = MysqlDaoFactory.getInstance().createClassesDao();
    }

    public List queryAllClasses()
    {
        return classDao.queryAllClasses();
    }

    private ClassesDao classDao;
}
