// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   StudentDao.java

package com.iflytek.stumanager.dao;

import com.iflytek.stumanager.po.Student;
import java.util.List;

public abstract class StudentDao
{

    public StudentDao()
    {
        isExists = true;
    }

    public abstract void saveStudent(Student student);

    public abstract boolean isExists();

    public abstract void updateStudent(Student student);

    public abstract void deleteStudent(int i);

    public abstract Student findStudentById(int i);

    public abstract List findAllStudents();

    public abstract List findStudentsByPageNo(int i);
    public abstract List findStudentsByPageNo(int i,int j);
    public abstract int queryTotalRecords();

    boolean isExists;
}
