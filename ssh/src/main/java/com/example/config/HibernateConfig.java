package com.example.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.support.OpenSessionInViewFilter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan("com.example.*.dao")
public class HibernateConfig {
    /*
在定制Bean中讲到过FactoryBean，LocalSessionFactoryBean是一个FactoryBean，它会再自动创建一个SessionFactory，
在Hibernate中，Session是封装了一个JDBC Connection的实例，而SessionFactory是封装了JDBC DataSource的实例，
即SessionFactory持有连接池，每次需要操作数据库的时候，SessionFactory创建一个新的Session，相当于从连接池获取到一个新的Connection。
SessionFactory就是Hibernate提供的最核心的一个对象，但LocalSessionFactoryBean是Spring提供的为了让我们方便创建SessionFactory的类。

注意到上面创建LocalSessionFactoryBean的代码，首先用Properties持有Hibernate初始化SessionFactory时用到的所有设置，常用的设置请参考Hibernate文档，这里我们只定义了3个设置：

hibernate.hbm2ddl.auto=update：表示自动创建数据库的表结构，注意不要在生产环境中启用；
hibernate.dialect=org.hibernate.dialect.HSQLDialect：指示Hibernate使用的数据库是HSQLDB。Hibernate使用一种HQL的查询语句，
它和SQL类似，但真正在“翻译”成SQL时，会根据设定的数据库“方言”来生成针对数据库优化的SQL；
hibernate.show_sql=true：让Hibernate打印执行的SQL，这对于调试非常有用，我们可以方便地看到Hibernate生成的SQL语句是否符合我们的预期。
除了设置DataSource和Properties之外，注意到setPackagesToScan()我们传入了一个package名称，它指示Hibernate扫描这个包下面的所有Java类，
自动找出能映射为数据库表记录的JavaBean。后面我们会仔细讨论如何编写符合Hibernate要求的JavaBean。
     */
//    @Bean
    @Bean(name = "sessionFactory")
    LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        Properties props = new Properties();
        // 生产环境不要使用update，自动生成类
        props.setProperty("hibernate.hbm2ddl.auto", "update");
//        props.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
        //数据库mysql8方言org.hibernate.dialect.MySQL8Dialect
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL55Dialect");//使用的mysql引擎为 InnoDB，自动创建表时，可以创建外键、事物等。
        props.setProperty("hibernate.show_sql", "true");
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        // 扫描指定的package获取所有entity class:
        sessionFactoryBean.setPackagesToScan("com.example.*.domain");
        sessionFactoryBean.setHibernateProperties(props);
        return sessionFactoryBean;
    }

    /*
        HibernateTransactionManager是配合Hibernate使用声明式事务所必须的。
        将Hibernate Session从指定的工厂绑定到线程，可能允许每个工厂有一个线程绑定的Session。 此事务管理器适用于使用单个Hibernate
        事务管理器可以处理数据库操作过程中的异常和回滚，可以确保在多线程环境下对数据库的访问是同步的，确保了事务的完整性和一致性。

如果没有配置事务管理器，那么所有的数据库操作都会被视为单独的事务，这会导致一些潜在的问题，
例如，在进行复杂的数据库操作时失败或抛出意外异常时，数据库就可能处于不一致的状态，另外，如果有多个用户同时访问相同的数据，则有可能产生数据冲突的问题。
     */

    @Bean
    PlatformTransactionManager createTxManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }

}