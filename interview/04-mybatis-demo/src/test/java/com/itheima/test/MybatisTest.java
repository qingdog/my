package com.itheima.test;

import com.itheima.mapper.UserMapper;
import com.itheima.pojo.Order;
import com.itheima.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class MybatisTest {

    @Test
    public void testSelectById2() throws IOException {
        //1. 加载mybatis的核心配置文件，获取 SqlSessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        //2. 获取SqlSession对象，用它来执行sql
        SqlSession sqlSession1 = sqlSessionFactory.openSession();

        //3. 执行sql
        //3.1 获取UserMapper接口的代理对象
        UserMapper userMapper1 = sqlSession1.getMapper(UserMapper.class);

        User user1 = userMapper1.selectById(6);
        System.out.println(user1);
        sqlSession1.close();

        SqlSession sqlSession2 = sqlSessionFactory.openSession();

        System.out.println("---------------------");
        UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);
        User user2 = userMapper2.selectById(6);
        System.out.println(user2);

        //4.关闭资源
        sqlSession2.close();
    }


    @Test
    public void testSelectById() throws IOException {
        //1. 加载mybatis的核心配置文件，获取 SqlSessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        //2. 获取SqlSession对象，用它来执行sql
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //3. 执行sql
        //3.1 获取UserMapper接口的代理对象
        UserMapper userMapper1 = sqlSession.getMapper(UserMapper.class);
        UserMapper userMapper2 = sqlSession.getMapper(UserMapper.class);

        User user = userMapper1.selectById(6);
        System.out.println(user);

        System.out.println("---------------------");
        User user1 = userMapper2.selectById(6);
        System.out.println(user1);

        //4.关闭资源
        sqlSession.close();
    }

}
