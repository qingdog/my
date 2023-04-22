package com.iflytek.stumanager.business;

import com.iflytek.stumanager.dao.ClassesDao;
import com.iflytek.stumanager.dao.factory.impl.MysqlDaoFactory;

import java.util.List;

public class ClassesService {

    private final ClassesDao classDao;

    public ClassesService() {
        classDao = MysqlDaoFactory.getInstance().createClassesDao();
    }

    public List queryAllClasses() {
        return classDao.queryAllClasses();
    }
}
