package com.iflytek.stumanager.dao;

import com.iflytek.stumanager.po.Student;

import java.util.List;

public interface StudentDao {
    boolean isExists = true;

    int queryTotalRecords();

    void saveStudent(Student student);

    boolean isExists();

    void updateStudent(Student student);

    void deleteStudent(int i);

    Student findStudentById(int i);

    List findAllStudents();

    List findStudentsByPageNo(int i, int pageSize);

}
