// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Classes.java

package com.iflytek.stumanager.po;

import net.sf.json.JSONObject;

public class Classes
{

    public Classes(int id)
    {
        this.id = id;
    }

    public Classes(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String toJSON()
    {
        JSONObject json = new JSONObject();
        json.put("id", Integer.valueOf(id));
        json.put("name", name);
        return json.toString();
    }

    private int id;
    private String name;
}
