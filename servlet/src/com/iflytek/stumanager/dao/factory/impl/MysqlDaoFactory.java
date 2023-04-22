package com.iflytek.stumanager.dao.factory.impl;

import com.iflytek.stumanager.dao.AdminDao;
import com.iflytek.stumanager.dao.ClassesDao;
import com.iflytek.stumanager.dao.StudentDao;
import com.iflytek.stumanager.dao.factory.DaoFactory;
import com.iflytek.stumanager.dao.impl.AdminDaoImpl;
import com.iflytek.stumanager.dao.impl.ClassesDaoImpl;
import com.iflytek.stumanager.dao.impl.StudentDaoImpl;

public class MysqlDaoFactory extends DaoFactory {
    private static final MysqlDaoFactory instance = new MysqlDaoFactory();

    public static MysqlDaoFactory getInstance() {
        return instance;
    }

    @Override
    public AdminDao createAdminDao() {
        return new AdminDaoImpl();
    }

    @Override
    public StudentDao createStudentDao() {
        return new StudentDaoImpl();
    }

    @Override
    public ClassesDao createClassesDao() {
        return new ClassesDaoImpl();
    }
}
