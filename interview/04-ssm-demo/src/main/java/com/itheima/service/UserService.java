package com.itheima.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.dao.UserMapper;
import com.itheima.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Service
public class UserService {

    @Autowired
    private UserMapper userDao;

    public void save(User user) {
        userDao.save(user);
    }

    public void update(User user) {
        userDao.update(user);
    }

    @Transactional
    public void delete(Integer id) {
        try {
            userDao.delete(id);
//            int a = 1/0;
        } catch (Exception e) {
            e.printStackTrace();
//            throw new RuntimeException("删除用户失败");
        }
    }

    public User getById(Integer id) {
        return userDao.getById(id);
    }

    /**
     *
     * @param id
     * @param file
     * @throws FileNotFoundException
     */
    public void uploadPic(Integer id ,MultipartFile file) throws FileNotFoundException {
        //根据id获取用户
        User user = userDao.getById(id);
        //查询本地文件
        FileInputStream inputStream = new FileInputStream("aaa");

    }

    public Page getByPage(int page, int size, int name) {
        return null;
    }

    public User checkToken(String token) {
        return null;
    }
}
