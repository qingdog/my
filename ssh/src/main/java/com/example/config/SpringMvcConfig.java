package com.example.config;

import com.example.config.exception.ResultExceptionAdvice;
import com.example.config.support.SpringMvcSupport;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan({"com.example.*.controller", "com.example.*.aspect"})
//@ComponentScan({"com.example.config.support"})
@Import({ResultExceptionAdvice.class, SpringMvcSupport.class})
@EnableWebMvc
@EnableAspectJAutoProxy
public class SpringMvcConfig {
}
