// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdminDao.java

package com.iflytek.stumanager.dao;


public abstract class AdminDao {

    public AdminDao() {
    }

    public abstract boolean isExists(String account, String password);
}
