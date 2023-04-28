package com.example.book.dao;

import com.example.book.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserDao {
    void save(User user);

    List<User> list();

    List<User> list(int pageNo, int pageSize);

    List<User> list(User user);
}
