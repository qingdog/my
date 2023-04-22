package com.iflytek.stumanager.dao.factory;

import com.iflytek.stumanager.dao.AdminDao;
import com.iflytek.stumanager.dao.ClassesDao;
import com.iflytek.stumanager.dao.StudentDao;

public abstract class DaoFactory {
    public DaoFactory() {
    }

    public abstract AdminDao createAdminDao();

    public abstract StudentDao createStudentDao();

    public abstract ClassesDao createClassesDao();
}
