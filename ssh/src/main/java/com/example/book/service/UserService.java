package com.example.book.service;

import com.example.book.domain.User;

import java.util.List;

public interface UserService {
    void save(User user);

    List<User> list();

    List<User> list(int pageNo, int pageSize);

    public List<User> list(User user);
}
