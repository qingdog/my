package com.iflytek.stumanager.business;

import com.iflytek.stumanager.dao.StudentDao;
import com.iflytek.stumanager.dao.factory.impl.MysqlDaoFactory;
import com.iflytek.stumanager.po.Student;

import java.util.List;

public class StudentService {
    private StudentDao stuDao;

    public StudentService() {
        stuDao = MysqlDaoFactory.getInstance().createStudentDao();
    }

    /**
     * 查询学生总数
     *
     * @return
     */
    public int queryTotalRecords() {
        return stuDao.queryTotalRecords();
    }

    public void saveStudent(Student student) {
        stuDao.saveStudent(student);
    }

    public boolean isExists() {
        return stuDao.isExists();
    }

    public void updateStudent(Student student) {
        stuDao.updateStudent(student);
    }

    public void deleteStudent(int id) {
        stuDao.deleteStudent(id);
    }

    public Student findStudentById(int id) {
        return stuDao.findStudentById(id);
    }

    public List findAllStudents() {
        return stuDao.findAllStudents();
    }

    /**
     * 分页查询
     *
     * @param pageNo   当前页数
     * @param pageSize 一页总行数
     * @return
     */
    public List findStudentsByPageNo(int pageNo, int pageSize) {
        return stuDao.findStudentsByPageNo(pageNo, pageSize);
    }


}
