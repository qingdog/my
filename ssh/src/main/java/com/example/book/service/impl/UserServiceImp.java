package com.example.book.service.impl;

import com.example.book.dao.UserDao;
import com.example.book.domain.User;
import com.example.book.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserDao userDao;

    @Transactional
    public void save(User user) {
        userDao.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> list() {
        return userDao.list();
    }

    @Transactional
    public List<User> list(int pageNo, int pageSize) {
        return userDao.list(pageNo, pageSize);
    }

    @Transactional
    public List<User> list(User user) {
        return userDao.list(user);
    }
}
