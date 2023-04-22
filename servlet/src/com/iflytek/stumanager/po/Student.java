package com.iflytek.stumanager.po;

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

    private int id;
    private String name;
    private int age;
    private String address;
    private String photo;
    private Classes classes;
}
