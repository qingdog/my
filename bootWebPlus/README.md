### boot
* 文件 - 新建 - Spring Iniializr - SpringBoot2.7.11、java8、Maven - spring web、mybatis、driver、lombok - 创建
* 删除（可选） .mvn、HELP.md、mvnw、mvnw.md
配置：
* 依赖：连接池、plus
```xml
        <!--版本管理-->
<properties>
    <java.version>1.8</java.version>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    <spring-boot.version>2.3.7.RELEASE</spring-boot.version>
    <spring-cloud.version>Hoxton.SR9</spring-cloud.version>
    <org.mapstruct.version>1.3.1.Final</org.mapstruct.version>
    <spring-cloud-alibaba.version>2.2.6.RELEASE</spring-cloud-alibaba.version>
    <org.projectlombok.version>1.18.8</org.projectlombok.version>
    <javax.servlet-api.version>4.0.1</javax.servlet-api.version>
    <fastjson.version>1.2.83</fastjson.version>
    <druid-spring-boot-starter.version>1.2.8</druid-spring-boot-starter.version>
    <mysql-connector-java.version>8.0.30</mysql-connector-java.version>
    <mybatis-plus-boot-starter.version>3.4.1</mybatis-plus-boot-starter.version>
    <commons-lang.version>2.6</commons-lang.version>
    <minio.version>8.4.3</minio.version>
    <xxl-job-core.version>2.3.1</xxl-job-core.version>
    <swagger-annotations.version>1.5.20</swagger-annotations.version>
    <commons-lang3.version>3.10</commons-lang3.version>
    <okhttp.version>4.8.1</okhttp.version>
    <swagger-spring-boot-starter.version>1.9.0.RELEASE</swagger-spring-boot-starter.version>
    <elasticsearch.version>7.12.1</elasticsearch.version>
</properties>

        <!-- 在 web 里排除 Spring Boot 依赖的Logback日志包冲突 -->
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-web</artifactId>
<exclusions>
    <exclusion>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-logging</artifactId>
    </exclusion>
</exclusions>
</dependency>

        <!-- mybatis plus 集成Spring Boot启动器 -->
<dependency>
<groupId>com.baomidou</groupId>
<artifactId>mybatis-plus-boot-starter</artifactId>
<version>${mybatis-plus-boot-starter.version}</version>
</dependency>
        <!-- druid 连接池管理 -->
<dependency>
<groupId>com.alibaba</groupId>
<artifactId>druid-spring-boot-starter</artifactId>
<version>${druid-spring-boot-starter.version}</version>
</dependency>

<!--以下依赖可选-->
        <!-- Spring Boot 集成 swagger3.0 -->
<dependency>
<groupId>com.spring4all</groupId>
<artifactId>swagger-spring-boot-starter</artifactId>
<version>2.0.2.RELEASE</version>
</dependency>
        <!--jsr303校验 底层是hibernate-->
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-validation</artifactId>
</dependency>
        <!-- Spring Boot 集成 log4j2 -->
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>
```
* 加扫描包注解（可在里启动类上加或者给dao接口加@Mapper注解）

```java
package com.example.bootwebplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.bootwebplus.*.mapper")
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }
}
```

* 创建application.yml并集成Swagger3：http://127.0.0.1:8080/swagger-ui/
```yaml
#微服务配置
spring:
  application:
    name: boot
  # spring数据源
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/ssm_db?serverTimezone=UTC
    username: root
    password: 123456
  # springfox-boot-starter (Swagger 3.0)Springfox 使用的路径匹配是基于AntPathMatcher的，
  # 而Spring Boot 2.6.X使用的是PathPatternMatcher。
  mvc:
    pathmatch:
      matching-strategy: ant_path_matcher
# swagger 文档配置
swagger:
  title: "书本管理系统"
  description: "管理数据"
  base-package: com.example.bootwebplus
  version: 1.0.0
  terms-of-service-url: http://127.0.0.1:8080/tblBook
  contact:
    email: book@mail.com
    name: 树人
    url: https://www.baidu.com
# 日志文件配置路径
logging:
  config: classpath:log4j2-dev.xml
```
#### 创建log4j日志配置文件
log4j2-dev.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration monitorInterval="180" packages="">
    <properties>
        <property name="logdir">logs</property>
        <property name="PATTERN">%date{YYYY-MM-dd HH:mm:ss,SSS} %level [%thread][%file:%line] - %msg%n%throwable</property>
    </properties>
    <Appenders>
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="${PATTERN}"/>
        </Console>

        <RollingFile name="ErrorAppender" fileName="${logdir}/error.log"
            filePattern="${logdir}/$${date:yyyy-MM-dd}/error.%d{yyyy-MM-dd-HH}.log" append="true">
            <PatternLayout pattern="${PATTERN}"/>
            <ThresholdFilter level="ERROR" onMatch="ACCEPT" onMismatch="DENY"/>
            <Policies>
                <TimeBasedTriggeringPolicy interval="1" modulate="true" />
            </Policies>
        </RollingFile>

        <RollingFile name="DebugAppender" fileName="${logdir}/info.log"
            filePattern="${logdir}/$${date:yyyy-MM-dd}/info.%d{yyyy-MM-dd-HH}.log" append="true">
            <PatternLayout pattern="${PATTERN}"/>
            <ThresholdFilter level="DEBUG" onMatch="ACCEPT" onMismatch="DENY"/>
            <Policies>
                <TimeBasedTriggeringPolicy interval="1" modulate="true" />
            </Policies>
        </RollingFile>
        
        <!--异步appender-->
         <Async name="AsyncAppender" includeLocation="true">
            <AppenderRef ref="ErrorAppender"/>
            <AppenderRef ref="DebugAppender"/>
        </Async>
    </Appenders>
    
    <Loggers>
         <!--过滤掉spring和mybatis的一些无用的debug信息-->
        <logger name="org.springframework" level="INFO">
        </logger>
        <logger name="org.mybatis" level="INFO">
        </logger>
        <logger name="cn.itcast.wanxinp2p.consumer.mapper" level="DEBUG">
        </logger>

        <logger name="springfox" level="INFO">
        </logger>
		<logger name="org.apache.http" level="INFO">
        </logger>
        <logger name="com.netflix.discovery" level="INFO">
        </logger>
        
        <logger name="RocketmqCommon"  level="INFO" >
		</logger>
		
		<logger name="RocketmqRemoting" level="INFO"  >
		</logger>
		
		<logger name="RocketmqClient" level="WARN">
		</logger>

        <logger name="org.dromara.hmily" level="WARN">
        </logger>

        <logger name="org.dromara.hmily.lottery" level="WARN">
        </logger>

        <logger name="org.dromara.hmily.bonuspoint" level="WARN">
        </logger>
		
        <!--OFF   0-->
        <!--FATAL   100-->
        <!--ERROR   200-->
        <!--WARN   300-->
        <!--INFO   400-->
        <!--DEBUG   500-->
        <!--TRACE   600-->
        <!--ALL   Integer.MAX_VALUE-->
        <Root level="ERROR" includeLocation="true">
            <AppenderRef ref="AsyncAppender"/>
            <AppenderRef ref="Console"/>
            <AppenderRef ref="DebugAppender"/>
        </Root>
    </Loggers>
</Configuration>
```
#### 代码生成器
使用mybatis-plus的generator工程生成PO类，地址在：https://github.com/baomidou/generator
```java
package com.xuecheng.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Arrays;

/**
 * MyBatis-Plus 代码生成类
 */
public class CodeGenerator {

	// TODO 修改服务名以及数据表名
	private static final String SERVICE_NAME = "";

	//数据库账号
	private static final String DATA_SOURCE_USER_NAME  = "root";
	//数据库密码
	private static final String DATA_SOURCE_PASSWORD  = "123456";
	//生成的表
	private static final String[] TABLE_NAMES = new String[]{
//			"mq_message",
//			"mq_message_history"
//			 "course_base",
//			 "course_market",
//			 "teachplan",
//			 "teachplan_media",
//			 "course_teacher",
//			"course_category"
//			 "course_publish",
//			 "course_publish_pre"
			"tbl_book"

	};

	// TODO 默认生成entity，需要生成DTO修改此变量
	// 一般情况下要先生成 DTO类 然后修改此参数再生成 PO 类。
	private static final Boolean IS_DTO = false;

	public static void main(String[] args) {
		// 代码生成器
		AutoGenerator mpg = new AutoGenerator();
		// 选择 freemarker 引擎，默认 Velocity
		mpg.setTemplateEngine(new FreemarkerTemplateEngine());
		// 全局配置
		GlobalConfig gc = new GlobalConfig();
		gc.setFileOverride(true);
		//生成路径
		gc.setOutputDir(System.getProperty("user.dir") + "/xuecheng-plus-generator/src/main/java");
		gc.setAuthor("itcast");
		gc.setOpen(false);
		gc.setSwagger2(false);
		gc.setServiceName("%sService");
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);

		if (IS_DTO) {
			gc.setSwagger2(true);
			gc.setEntityName("%sDTO");
		}
		mpg.setGlobalConfig(gc);

		// 数据库配置
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setDbType(DbType.MYSQL);
		dsc.setUrl("jdbc:mysql://127.0.0.1:3306/ssm_db" + SERVICE_NAME
				+ "?serverTimezone=UTC&useUnicode=true&useSSL=false&characterEncoding=utf8");
//		dsc.setDriverName("com.mysql.jdbc.Driver");
		dsc.setDriverName("com.mysql.cj.jdbc.Driver");
		dsc.setUsername(DATA_SOURCE_USER_NAME);
		dsc.setPassword(DATA_SOURCE_PASSWORD);
		mpg.setDataSource(dsc);

		// 包配置
		PackageConfig pc = new PackageConfig();
//		pc.setModuleName(SERVICE_NAME);
		pc.setModuleName("book");
		pc.setParent("com.xuecheng");

		pc.setServiceImpl("service.impl");
		pc.setXml("mapper");
		pc.setEntity("model.po");
		mpg.setPackageInfo(pc);


		// 设置模板
		TemplateConfig tc = new TemplateConfig();
		mpg.setTemplate(tc);

		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		strategy.setNaming(NamingStrategy.underline_to_camel);
		strategy.setColumnNaming(NamingStrategy.underline_to_camel);
		strategy.setEntityLombokModel(true);
		strategy.setRestControllerStyle(true);
		strategy.setInclude(TABLE_NAMES);
		strategy.setControllerMappingHyphenStyle(true);
		strategy.setTablePrefix(pc.getModuleName() + "_");
		// Boolean类型字段是否移除is前缀处理
		strategy.setEntityBooleanColumnRemoveIsPrefix(true);
		strategy.setRestControllerStyle(true);

		// 自动填充字段配置
		strategy.setTableFillList(Arrays.asList(
				new TableFill("create_date", FieldFill.INSERT),
				new TableFill("change_date", FieldFill.INSERT_UPDATE),
				new TableFill("modify_date", FieldFill.UPDATE)
		));
		mpg.setStrategy(strategy);

		mpg.execute();
	}

}
```
#### 修改controller

```java
package com.example.bootwebplus.book.controller;

@Slf4j
@RestController
@RequestMapping("tblBook")
@Api(value = "书本接口", tags = "书本")
public class TblBookController {

    @Autowired
    private TblBookService tblBookService;

    @ApiOperation("书本列表查询接口")
    @GetMapping("/")
    public List<TblBook> queryTreeNodes() {
        return tblBookService.list();
    }
}
```
测试service

```java
package com.example.bootwebplus;

@SpringBootTest
class SpringbootApplicationTests {

    @Autowired
    TblBookService tblBookService;

    @Test
    void contextLoads() {
        System.out.println(tblBookService.getById(1));
    }

}
```
* 测试controller
* 创建目录：httpclient
* 点击controller方法中的Mapper注解，生成http客户端请求

```java
package com.example.bootwebplus.book.controller;

@Slf4j
@RestController
@RequestMapping("tblBook")
@Api(value = "书本接口", tags = "书本")
public class TblBookController {

    @Autowired
    private TblBookService tblBookService;

    @ApiOperation("书本列表查询接口")
    @GetMapping
    public List<TblBook> queryTreeNodes() {
        return tblBookService.list();
    }
}
```
生成文件：generated-requests.http
```http request
###
GET http://127.0.0.1:8080/tblBook
```
Ctrl+Shift+F10执行get方法测试

#### 启动bootApplication
测试新版文档：http://localhost:8080/swagger-ui/
Controller - Get - Try it out - Execute - 看Response body是否有正确响应数据返回

#### mybatisplus分页
* 配置拦截器
```java
package com.example.bootwebplus.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bootwebplus.book.mapper.TblBookMapper;
import com.example.bootwebplus.book.model.po.TblBook;
import com.example.bootwebplus.book.service.TblBookService;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author itcast
 */
@Slf4j
@Service
public class TblBookServiceImpl extends ServiceImpl<TblBookMapper, TblBook> implements TblBookService {
    @Autowired
    TblBookMapper tblBookMapper;

    public Page<TblBook> getPage(int pageNo, int pageSize) {
        return tblBookMapper.selectPage(new Page<>(pageNo, pageSize), new LambdaQueryWrapper<>());
    }

    // 无法从 static 上下文引用非 static 方法
    public Page<TblBook> getPage(int pageNo, int pageSize, TblBook tblBook) {
//        Page<TblBook> page = tblBookMapper.selectPage(new Page<TblBook>(pageNo, pageSize), new LambdaQueryWrapper<TblBook>()
//                .eq(tblBook.getId() != null, new SFunction<TblBook, Integer>() {
//                    @Override
//                    public Integer apply(TblBook tblBook) {
//                        return tblBook.getId();
//                    }
//                }, id));
        Page<TblBook> page = tblBookMapper.selectPage(new Page<TblBook>(pageNo, pageSize), new LambdaQueryWrapper<TblBook>()
                .eq(StringUtils.checkValNotNull(tblBook.getId()), TblBook::getId, tblBook.getId())
                .eq(StringUtils.isNotBlank(tblBook.getName()), TblBook::getName, tblBook.getName())
                .eq(StringUtils.isNotBlank(tblBook.getType()), TblBook::getType, tblBook.getType())
                .eq(StringUtils.isNotBlank(tblBook.getDescription()), TblBook::getDescription, tblBook.getDescription()));
        return page;
    }
}
```
* 返回前端
```java
package com.example.bootwebplus.book.controller;

import com.example.bootwebplus.book.model.po.TblBook;
import com.example.bootwebplus.book.service.TblBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("tblBook")
@Api(value = "书本接口", tags = "书本")
public class TblBookController {

    @Autowired
    private TblBookService tblBookService;

    @ApiOperation("书本列表查询接口")
    @GetMapping
    public List<TblBook> queryTreeNodes() {
        return tblBookService.list();
    }

    @ApiOperation("书本分页查询接口")
    @GetMapping("/{pageSize}/{pageNo}")
    public Object getPage(@PathVariable Integer pageNo, @PathVariable Integer pageSize) {
        return tblBookService.getPage(pageNo, pageSize);
    }

    @ApiOperation("书本分页POST有条件查询接口")
    @PostMapping("/{pageSize}/{pageNo}")
    public Object getPage(@PathVariable Integer pageNo, @PathVariable Integer pageSize, @RequestBody TblBook tblBook) {
        return tblBookService.getPage(pageNo, pageSize, tblBook);
    }
}
```