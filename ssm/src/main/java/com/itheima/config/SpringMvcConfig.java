package com.itheima.config;

import com.itheima.config.support.SpringMvcSupport;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan({"com.itheima.controller"})
//@ComponentScan({"com.itheima.config.support"})
@Import(SpringMvcSupport.class)
@EnableWebMvc
public class SpringMvcConfig {
}
