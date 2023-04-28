package com.example.book.service.impl;

import com.example.book.dao.UserDao;
import com.example.book.dao.UserDetailsDaoImp;
import com.example.book.domain.User;
import com.example.book.domain.UserDetails;
import com.example.book.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserDetailsServiceImp {
    @Autowired
    private UserDetailsDaoImp userDetailsDaoImp;


    @Transactional(readOnly = true)
    public List<UserDetails> list() {
        return userDetailsDaoImp.list();
    }
}
