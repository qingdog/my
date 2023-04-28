package com.example.book.dao;

import com.example.book.domain.User;
import com.example.book.domain.UserDetails;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public List<User> list() {
        @SuppressWarnings("unchecked")
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    public List<User> list(int pageNo, int pageSize) {
        String sql= "select * from User";
        List list = sessionFactory.getCurrentSession().createSQLQuery(sql).list();


        Query<User> query = sessionFactory.getCurrentSession().createQuery("from User ");
        query.setFirstResult((pageNo - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public List<User> list(User user) {
        String sql = "SELECT * FROM TBL_USERS WHERE USER_NAME = :name";
        List<User> users = sessionFactory.getCurrentSession()
                .createNativeQuery(sql, User.class)
                .setParameter("name", user.getName())
                .getResultList();
        return users;
    }


}
