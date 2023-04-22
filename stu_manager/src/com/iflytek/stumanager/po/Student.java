// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Student.java

package com.iflytek.stumanager.po;

import net.sf.json.JSONObject;

// Referenced classes of package com.iflytek.stumanager.po:
//            Classes

public class Student
{

    public Classes getClasses()
    {
        return classes;
    }

    public void setClasses(Classes classes)
    {
        this.classes = classes;
    }

    public Student(int id, String name, int age, String address)
    {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public Student(int id, String name, int age, String address, Classes classes)
    {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.classes = classes;
    }

    public Student(int id, String name, int age, String address, String photo)
    {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.photo = photo;
    }

    public Student(int id, String name, int age, String address, String photo, Classes classes)
    {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.photo = photo;
        this.classes = classes;
    }

    public String getPhoto()
    {
        return photo;
    }

    public void setPhoto(String photo)
    {
        this.photo = photo;
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

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String toJSON()
    {
        JSONObject json = new JSONObject();
        json.put("id", Integer.valueOf(id));
        json.put("name", name);
        json.put("age", Integer.valueOf(age));
        json.put("address", address);
        json.put("classes", classes);
        return json.toString();
    }

    private int id;
    private String name;
    private int age;
    private String address;
    private String photo;
    private Classes classes;
}
