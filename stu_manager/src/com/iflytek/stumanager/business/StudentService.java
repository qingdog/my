// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   StudentService.java

package com.iflytek.stumanager.business;

import com.iflytek.stumanager.dao.MysqlDaoFactory;
import com.iflytek.stumanager.dao.StudentDao;
import com.iflytek.stumanager.po.Student;
import java.util.List;

public class StudentService
{

    public StudentService()
    {
        stuDao = MysqlDaoFactory.getInstance().createStudentDao();
    }

    public void saveStudent(Student student)
    {
        stuDao.saveStudent(student);
    }

    public boolean isExists()
    {
        return stuDao.isExists();
    }

    public void updateStudent(Student student)
    {
        stuDao.updateStudent(student);
    }

    public void deleteStudent(int id)
    {
        stuDao.deleteStudent(id);
    }

    public Student findStudentById(int id)
    {
        return stuDao.findStudentById(id);
    }

    public List findAllStudents()
    {
        return stuDao.findAllStudents();
    }

    public List findStudentsByPageNo(int pageNo)
    {
        return stuDao.findStudentsByPageNo(pageNo);
    }
    public List findStudentsByPageNo(int pageNo,int pageSize)
    {
        return stuDao.findStudentsByPageNo(pageNo,pageSize);
    }

    public int queryTotalRecords()
    {
        return stuDao.queryTotalRecords();
    }

    private StudentDao stuDao;
}
