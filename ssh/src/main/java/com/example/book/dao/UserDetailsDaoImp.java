package com.example.book.dao;

import com.example.book.domain.UserDetails;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDetailsDaoImp {
    @Autowired
    private SessionFactory sessionFactory;

    public List<UserDetails> list() {
        TypedQuery<UserDetails> query = sessionFactory.getCurrentSession().createQuery("from UserDetails ");
        return query.getResultList();
    }
}
