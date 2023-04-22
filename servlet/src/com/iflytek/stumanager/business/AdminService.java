package com.iflytek.stumanager.business;

import com.iflytek.stumanager.dao.AdminDao;
import com.iflytek.stumanager.dao.factory.impl.MysqlDaoFactory;
import com.iflytek.stumanager.po.Admin;

public class AdminService {

    private AdminDao adminDao;

    public AdminService() {
        adminDao = MysqlDaoFactory.getInstance().createAdminDao();
    }

    /**
     * 用户是否合法
     *
     * @param admin
     * @return
     */
    public boolean legal(Admin admin) {
        return adminDao.isExists(admin.getAccount(), admin.getPassword());
    }
}
