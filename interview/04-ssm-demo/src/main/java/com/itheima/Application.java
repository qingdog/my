package com.itheima;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@MapperScan("com.itheima.dao")
@ServletComponentScan("com.itheima.filter")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
