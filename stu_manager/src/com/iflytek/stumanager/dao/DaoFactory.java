// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DaoFactory.java

package com.iflytek.stumanager.dao;


// Referenced classes of package com.iflytek.stumanager.dao:
//            AdminDao, StudentDao, ClassesDao

public abstract class DaoFactory
{

    public DaoFactory()
    {
    }

    public abstract AdminDao createAdminDao();

    public abstract StudentDao createStudentDao();

    public abstract ClassesDao createClassesDao();
}
