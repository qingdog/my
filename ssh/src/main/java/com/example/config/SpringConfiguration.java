package com.example.config;

import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan({"com.example.*.service"})
@PropertySource("classpath:jdbc.properties")
@Import({JdbcConfig.class,/*MyBatisConfig.class,*/ HibernateConfig.class})
//@Import({JdbcConfig.class, HibernateConfig.class})
@EnableTransactionManagement
public class SpringConfiguration {
}
