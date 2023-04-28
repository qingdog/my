## Maven
* 文件 - 项目 - 名称 - Java -Maven - 高级设置
* 加入spring-context依赖到pox.xml文件中
```xml
    <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.2.10.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
```
* 项目名 - src - main - resource - 右键新建 - xml配置文件 - applicationContext.xml

## MVC
* 文件 - 项目结构 - 模块 - 添加 - Web - 移除web.xml - 修改web资源目录为：项目名\src\main\webapp - 确定
> 或者（文件 - 新建 - 项目 - Maven Archetype - Archetype - 选择：webapp）
* 加入依赖
```xml
<dependencies>
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
        <version>3.1.0</version>
        <scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>5.2.10.RELEASE</version>
    </dependency>
</dependencies>

<build>
<plugins>
    <plugin>
        <groupId>org.apache.tomcat.maven</groupId>
        <artifactId>tomcat7-maven-plugin</artifactId>
        <version>2.1</version>
        <configuration>
            <port>80</port>
            <path>/</path>
            <uriEncoding>UTF-8</uriEncoding><!--访问路径编解码字符集-->
        </configuration>
    </plugin>
</plugins>
</build>
```
* 已删除web.xml，继承AbstractDispatcherServletInitializer，重写创建应用程序上下文方法和映射方法
```java
public class ServletConfig extends AbstractDispatcherServletInitializer {
    //加载springmvc配置类
    protected WebApplicationContext createServletApplicationContext() {
        //初始化WebApplicationContext对象
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        //加载指定配置类
        ctx.register(SpringMvcConfig.class);
        return ctx;
    }

    //设置由springmvc控制器处理的请求映射路径
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    //加载spring配置类
    protected WebApplicationContext createRootApplicationContext() {
        return null;
    }
}
```
* 新增MVC配置类
```java
@Configuration
@ComponentScan("com.example.controller")
public class SpringMvcConfig {
}
```
* 当前文件 - 编辑配置 - 添加新的运行配置 - Maven - 运行 - 命令行 - tomcat7:run - 确定
* gav 下增加打包方式war
```xml
    <groupId>org.example</groupId>
    <artifactId>ssm</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>war</packaging>
```
## SSM
* 文件 - 新建 - 项目 - Maven Archetype - Archetype - 选择：webapp

* 继承`AbstractAnnotationConfigDispatcherServletInitializer`重写以下方法
* getRootConfigClasses()	：返回Spring的配置类->需要==SpringConfig==配置类
* getServletConfigClasses() ：返回SpringMVC的配置类->需要==SpringMvcConfig==配置类
* getServletMappings()      : 设置SpringMVC请求拦截路径规则
* getServletFilters()       ：设置过滤器，解决POST请求中文乱码问题

#### 添加依赖
```xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.example</groupId>
  <artifactId>springmvc_08_ssm</artifactId>
  <version>1.0-SNAPSHOT</version>
  <packaging>war</packaging>

  <dependencies>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-webmvc</artifactId>
      <version>5.2.10.RELEASE</version>
    </dependency>

    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-jdbc</artifactId>
      <version>5.2.10.RELEASE</version>
    </dependency>

    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-test</artifactId>
      <version>5.2.10.RELEASE</version>
    </dependency>

    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis</artifactId>
      <version>3.5.6</version>
    </dependency>

    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis-spring</artifactId>
      <version>1.3.0</version>
    </dependency>

    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>5.1.47</version>
    </dependency>

    <dependency>
      <groupId>com.alibaba</groupId>
      <artifactId>druid</artifactId>
      <version>1.1.16</version>
    </dependency>

    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.12</version>
      <scope>test</scope>
    </dependency>

    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>javax.servlet-api</artifactId>
      <version>3.1.0</version>
      <scope>provided</scope>
    </dependency>

    <dependency>
      <groupId>com.fasterxml.jackson.core</groupId>
      <artifactId>jackson-databind</artifactId>
      <version>2.9.0</version>
    </dependency>
  </dependencies>

  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.tomcat.maven</groupId>
        <artifactId>tomcat7-maven-plugin</artifactId>
        <version>2.1</version>
        <configuration>
          <port>80</port>
          <path>/</path>
        </configuration>
      </plugin>
    </plugins>
  </build>
</project>
```
#### SpringConfig配置类
```java
@Configuration
@ComponentScan({"com.example.service"})
@PropertySource("classpath:jdbc.properties")
@Import({JdbcConfig.class,MyBatisConfig.class})
@EnableTransactionManagement
public class SpringConfig {
}
```
#### 步骤5:创建JdbcConfig配置类

```java
public class JdbcConfig {
    @Value("${jdbc.driver}")
    private String driver;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;

    @Bean
    public DataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource){
        DataSourceTransactionManager ds = new DataSourceTransactionManager();
        ds.setDataSource(dataSource);
        return ds;
    }
}
```

#### 步骤6:创建MybatisConfig配置类

```java
public class MyBatisConfig {
    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource){
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setTypeAliasesPackage("com.example.domain");
        return factoryBean;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer msc = new MapperScannerConfigurer();
        msc.setBasePackage("com.example.dao");
        return msc;
    }
}
```

#### 步骤7:创建jdbc.properties

在resources下提供jdbc.properties,设置数据库连接四要素

```properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/ssm_db
jdbc.username=root
jdbc.password=root
```

#### 步骤8:创建SpringMVC配置类

```java
@Configuration
@ComponentScan("com.example.controller")
@EnableWebMvc
public class SpringMvcConfig {
}
```

#### 步骤9:创建Web项目入口配置类

```java
public class ServletConfig extends AbstractAnnotationConfigDispatcherServletInitializer {
    //加载Spring配置类
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SpringConfig.class};
    }
    //加载SpringMVC配置类
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringMvcConfig.class};
    }
    //设置SpringMVC请求地址拦截规则
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
    //设置post请求中文乱码过滤器
    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("utf-8");
        return new Filter[]{filter};
    }
}

```
#### SSM整合分页
```xml
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper</artifactId>
    <version>5.1.10</version>
</dependency>
```

```java
package com.example.config;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.Properties;

public class MyBatisConfig {

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource, PageInterceptor pageInterceptor) {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setTypeAliasesPackage("com.example.domain");

        factoryBean.setPlugins(new Interceptor[]{pageInterceptor});
        return factoryBean;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer msc = new MapperScannerConfigurer();
        msc.setBasePackage("com.example.dao");
        return msc;
    }

    @Bean
    public PageInterceptor pageInterceptor() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("reasonable", "true");
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }

}
```
注意：Page继承的是ArrayList类，在转成json的时候会转成JsonArray，而这个过程自定义的这些私有变量全部不会转化，
    会造成分页信息丢失（jakson和fastjson都仅转换分页的列表）
解决：使用PageInfo返回前端，其中pageNum=0和pageNum=1是同一页
或者：手动把page转成json（手写set或者手写反射/序列化）、自己写一个类不继承ArrayList，把分页属性放到自定义的对象中BeanUtil.copy写入数据

* maven 热部署
* 启动服务：https://github.com/ilanyu/ReverseProxy/releases/tag/v1.4/ReverseProxy_windows_amd64.exe
* 文件 - 设置 - 插件 - JRebel - install
* 选择 JRebel activated 中的 Team URL
  第一行输入：http://127.0.0.1:8888/d3545f42-7b88-4a77-a2da-5242c46d4bc2
  第二行输入正确的邮箱格式（随意填写），例如：test@123.com
  勾选 I agree with… ，点击按钮验证激活
  提示：d3545f42-7b88-4a77-a2da-5242c46d4bc2 为 UUID，可以自己生成，但是必须是 UUID
* 生成UUID：https://www.guidgen.com
* 文件 - 设置 - JRebel & XRebel - off online 关闭联网（可选）
* 视图 - 工具窗口 - JRebel - 勾选项目
* 以 JRebel Run 或 JRebel debug 方式启动（不再使用idea原图标启动maven），修改 java文件 后编译即热更新
* 若热部署没有生效，手动删除项目下的.idea目录（包含子目录和.idea中所有文件），文件 - 清除缓存 - 并重启启动

#### 编写mapper.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.example.book.dao.BookDao">
    <!-- 通用查询映射结果 -->
    <resultMap id="BaseResultMap" type="com.example.book.domain.Book">
        <id column="id" property="id"/>
        <result column="type" property="type"/>
        <result column="name" property="name"/>
    </resultMap>

    <!-- 通用查询结果列 -->
    <sql id="Base_Column_List">
        id , name, type
    </sql>

    <select id="getAll" resultType="com.example.book.domain.Book">
        select * from tbl_book
    </select>
</mapper>
```
注意：默认情况下，src/main/java下的非java文件不会编译到target/classes/com/...
因为src/main/java目录默认只能编译java文件，不能编译mapper.xml，所以一开始把mapper文件放在src/main/java目录下就会找不到mapper文件，但是放在src/main/resources目录下就能找到。
需要在pom.xml配置一下：
```xml
<build>
    <resources>
        <resource>
            <directory>src/main/java</directory>
            <includes>
                <include>**/*.xml</include>
            </includes>
        </resource>
    </resources>
</build>
```

#### aop
引入aspectj依赖
```xml
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.9.7</version>
</dependency>
```
mvc返回增强（ResultExceptionAdvice统一拦截异常，包括aop中的异常）
```java
@Configuration
@ComponentScan({"com.example.*.controller","com.example.*.advice"})
//@ComponentScan({"com.example.config.support"})
@Import({ResultExceptionAdvice.class, SpringMvcSupport.class})
@EnableWebMvc
@EnableAspectJAutoProxy
public class SpringMvcConfig {
}
```
修改Controller里的方法返回值类型为Object
```java
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/{pageSize}/{pageNum}")
    public Object getAll(@PathVariable Integer pageSize, @PathVariable Integer pageNum) {
        PageInfo<Book> pageInfo = bookService.getPageInfo(pageSize, pageNum);
        return pageInfo;
    }
}
```
切点匹配方法给通知拦截增强

```java
package com.example.book.aspect;

@Component
@Aspect
public class ResultAspect {
    @Pointcut("execution(Object com.example.book.controller.*Controller.*(..))")
    private void point() {
    }

    @Around("point()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 表示对原始操作的调用
        Object proceed = proceedingJoinPoint.proceed();
        if (proceed instanceof Result) {
            return proceed;
        }

//        Integer code = proceed != null ? Code.GET_OK : Code.GET_ERR;
        String message = "";
        if (proceed == null) {
            //获取执行签名信息
            Signature signature = proceedingJoinPoint.getSignature();
            //通过签名获取执行操作名称(接口名)
            String className = signature.getDeclaringTypeName();
            //通过签名获取执行操作名称(方法名)
            String methodName = signature.getName();
            message = "数据查询失败！methodName：" + methodName + "args：" + Arrays.toString(proceedingJoinPoint.getArgs());
        }
        return new Result(Code.GET_OK, proceed, message);
    }
}
```

测试
```java
import com.example.book.service.BookService;
import com.example.config.SpringConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//设置类运行器
@RunWith(SpringJUnit4ClassRunner.class)
//设置Spring环境对应的配置类
@ContextConfiguration(classes = {SpringConfiguration.class}) //加载配置类
//@ContextConfiguration(locations={"classpath:applicationContext.xml"})//加载配置文件
public class BookTest {
    //支持自动装配注入bean
    @Autowired
    private BookService bookService;
    @Test
    public void testFindById(){
        System.out.println(bookService.getPageInfo(5, 1));

    }
    @Test
    public void testFindAll(){
        System.out.println(bookService.getAll());
    }
}
```
### MyBatis -> Hibernate
依赖保留：mybatis、mybatis-spring、pagehelper
引入 Hibernate 整合所需依赖
```xml
<properties>
    <hibernate.version>5.2.11.Final</hibernate.version>
    <c3p0.version>0.9.5.2</c3p0.version>
    <hibernate.validator>5.4.1.Final</hibernate.validator>
    <hsqldb.version>1.8.0.10</hsqldb.version>
    <spring.version>5.2.10.RELEASE</spring.version>
</properties>

        <!--追加依赖-->
        <!-- Spring ORM -->
<dependency>
<groupId>org.springframework</groupId>
<artifactId>spring-orm</artifactId>
<version>${spring.version}</version>
</dependency>
        <!-- Hibernate Core -->
<dependency>
<groupId>org.hibernate</groupId>
<artifactId>hibernate-core</artifactId>
<version>${hibernate.version}</version>
</dependency>

        <!-- Hibernate Validator -->
<dependency>
<groupId>org.hibernate</groupId>
<artifactId>hibernate-validator</artifactId>
<version>${hibernate.validator}</version>
</dependency>
        <!-- HSQL Dependency -->
<dependency>
<groupId>hsqldb</groupId>
<artifactId>hsqldb</artifactId>
<version>${hsqldb.version}</version>
</dependency>
        <!-- JSTL  -->
<dependency>
<groupId>javax.servlet</groupId>
<artifactId>jstl</artifactId>
<version>1.2</version>
</dependency>
```

配置类保留：com.example.config.MyBatisConfig
MyBatis 框架不用编写dao实现类，在 Hibernate 中会注入失败，把 Service实现类 中的 dao 注入移除（其他注解可保留）
```java
package com.example.book.service.impl;
@Service
public class BookServiceImpl implements BookService {
    //取消mybatis框架
//    @Autowired
    private BookDao bookDao;

    public boolean save(Book book) {
        return bookDao.save(book) > 0;
    }
}
```
在Spring容器中新增 HibernateConfig 替换 MyBatisConfig 配置类
```java
package com.example.config;

import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan({"com.example.*.service"})
@PropertySource("classpath:jdbc.properties")
@Import({JdbcConfig.class,/*MyBatisConfig.class,*/ HibernateConfig.class})
@EnableTransactionManagement
public class SpringConfiguration {
}
```
Hibernate 框架配置类
```java
package com.example.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
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
    @Bean
    LocalSessionFactoryBean createSessionFactory(DataSource dataSource) {
        Properties props = new Properties();
        // 生产环境不要使用update，自动生成类
        props.setProperty("hibernate.hbm2ddl.auto", "update");
//        props.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
        //数据库mysql8方言org.hibernate.dialect.MySQL8Dialect
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        props.setProperty("hibernate.show_sql", "true");
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        // 扫描指定的package获取所有entity class:
        sessionFactoryBean.setPackagesToScan("com.example.**.domain");
        sessionFactoryBean.setHibernateProperties(props);
        return sessionFactoryBean;
    }

    /*
        HibernateTransactionManager是配合Hibernate使用声明式事务所必须的。
        将Hibernate Session从指定的工厂绑定到线程，可能允许每个工厂有一个线程绑定的Session。 此事务管理器适用于使用单个Hibernate
     */

    @Bean
    PlatformTransactionManager createTxManager(@Autowired SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }
}
```
编写实体类，使用JPA注解标记。
类似 User 这样的用于ORM的Java Bean，我们通常称之为Entity Bean。
> 注意注解来自jakarta.persistence，它是JPA规范的一部分。配合JPA注解，无需任何额外的XML配置。
> 不使用传统的比较繁琐的XML配置，也不再需要hibernate.cfg.xml配置文件。
```java
package com.example.book.domain;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "TBL_USERS")
public class User {
    // 对于主键，还需要用@Id标识，自增主键再追加一个@GeneratedValue，以便Hibernate能读取到自增主键的值。
    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;

    // nullable指示列是否允许为NULL，updatable指示该列是否允许被用在UPDATE语句，length指示String类型的列的长度（如果没有指定，默认是255）。
    //@Column(nullable = false, updatable = false)
    @Column(name = "USER_NAME")
    @Size(max = 20, min = 3, message = "{user.name.invalid}")
    @NotEmpty(message = "Please Enter your name")
    private String name;
    @Column(name = "USER_EMAIL", unique = true)
    @Email(message = "{user.email.invalid}")
    @NotEmpty(message = "Please Enter your email")
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
```
#### 编写dao，使用hql
```java
package com.example.book.dao;

import com.example.book.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface UserDao {
    void save(User user);

    List<User> list();
}
```
```java
package com.example.book.dao;

import com.example.book.domain.User;
import org.hibernate.SessionFactory;
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
    // 分页写法
    public List<User> list(int pageNo, int pageSize) {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User ");
        query.setFirstResult((pageNo - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }
}
```
使用HQL查询 ,一种常用的查询是直接编写Hibernate内置的HQL查询：
```java
public class UserDaoImp implements UserDao {
    @Override
    public List<User> list() {
        List<User> list = sessionFactory.getCurrentSession()
                .createQuery("from User u where u.email = ?1 and u.password = ?2", User.class)
                .setParameter(1, email).setParameter(2, password)
                .list();
        return list;
    }
}
```
和SQL相比，HQL使用类名和属性名，由Hibernate自动转换为实际的表名和列名。详细的HQL语法可以参考Hibernate文档。
除了可以直接传入HQL字符串外，Hibernate还可以使用一种NamedQuery，它给查询起个名字，然后保存在注解中。使用NamedQuery时，我们要先在User类标注：
```java
@NamedQueries(
    @NamedQuery(
        // 查询名称:
        name = "login",
        // 查询语句:
        query = "SELECT u FROM User u WHERE u.email = :e AND u.password = :pwd"
    )
)
@Entity
public class User extends AbstractEntity {
//    ...
}
```
注意到引入的NamedQuery是jakarta.persistence.NamedQuery，它和直接传入HQL有点不同的是，占位符使用:e和:pwd。
使用NamedQuery只需要引入查询名和参数：
```java
public class UserDaoImp implements UserDao {
    public User login(String email, String password) {
        List<User> list = sessionFactory.getCurrentSession()
                .createNamedQuery("login", User.class) // 创建NamedQuery
                .setParameter("e", email) // 绑定e参数
                .setParameter("pwd", password) // 绑定pwd参数
                .list();
        return list.isEmpty() ? null : list.get(0);
    }
}
```
#### 编写service，注意方法要开启事务
```java
package com.example.book.service;

import com.example.book.domain.User;

import java.util.List;

public interface UserService {
    void save(User user);
    List<User> list();
}
```
```java
package com.example.book.service.impl;

import com.example.book.dao.UserDao;
import com.example.book.domain.User;
import com.example.book.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserDao userDao;

    @Transactional
    public void save(User user) {
        userDao.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> list() {
        return userDao.list();
    }
}
```
```java
package com.example.book.controller;

import com.example.book.domain.User;
import com.example.book.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Locale;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String userForm(Locale locale, Model model) {
        model.addAttribute("users", userService.list());
        return "editUsers";
    }

    @ModelAttribute("user")
    public User formBackingObject() {
        return new User();
    }

    @PostMapping("/addUser")
    public String saveUser(@ModelAttribute("user") @Valid User user,
                           BindingResult result, Model model) {
        // 不要给user设置id，因为使用了自增主键
        if (result.hasErrors()) {
            model.addAttribute("users", userService.list());
            return "editUsers";
        }

        userService.save(user);
        return "redirect:/";
    }
}
```
src/main/webapp/WEB-INF/views/editUsers.jsp
```html
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Spring5 MVC Hibernate Demo</title>
    <style type="text/css">
        .error {
            color: red;
        }

        table {
            width: 50%;
            border-collapse: collapse;
            border-spacing: 0px;
        }

        table td {
            border: 1px solid #565454;
            padding: 20px;
        }
    </style>
</head>
<body>
<h1>Input Form</h1>
<form:form action="addUser" method="post" modelAttribute="user">
    <table>
        <tr>
            <td>Name</td>
            <td>
                <form:input path="name"/> <br/>
                <form:errors path="name" cssClass="error"/>
            </td>
        </tr>
        <tr>
            <td>Email</td>
            <td>
                <form:input path="email"/> <br/>
                <form:errors path="email" cssClass="error"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <button type="submit">Submit</button>
            </td>
        </tr>
    </table>
</form:form>

<h2>Users List</h2>
<table>
    <tr>
        <td><strong>Name</strong></td>
        <td><strong>Email</strong></td>
    </tr>
    <c:forEach items="${users}" var="user">
        <tr>
            <td>${user.name}</td>
            <td>${user.email}000000</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
```
修改mvc support（addResourceHandlers可保留）
```java
package com.example.config.support;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;


//@Configuration
public class SpringMvcSupport implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/pages/**").addResourceLocations("/pages/");
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        registry.addResourceHandler("/plugins/**").addResourceLocations("/plugins/");
    }

    @Bean
    public InternalResourceViewResolver resolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setViewClass(JstlView.class);
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
}
```
启动测试：http://127.0.0.1/
* 如果出现mybais类失败，删除 target目录（class文件） 和 .idea目录及子目录与所有文件，文件 - 清除缓存 - 清除并重新启动
* 如果 jdbc.properties 找不到，右键 src/main/resources - 将目录标记为 - 测试资源根目录
* 如果 JRebel 启动出错，删除 src/main/resources/rebel.xml，单机左侧工具栏JRebel打开Panel，给项目名勾选JReble加入rebel.xml
* 确认：项目结构 - Facet - JReble - Enable JReble

如果jsp出现中文乱码，从servle中过滤
```java
package com.example.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

public class ServletConfig extends AbstractAnnotationConfigDispatcherServletInitializer {
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SpringConfiguration.class};
    }

    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringMvcConfig.class};
    }

    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        final CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);

        return new Filter[]{encodingFilter};
    }
}
```
用service测试，不要用dao注入
```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfiguration.class})
public class UserTest {
    @Autowired
    private UserService userService;

    @Test
    public void testUserAll() {
        List<User> list = userService.list(1,1);
        System.out.println(list);
    }
}
```
SQL语句在Hibernate5应该使用createNativeQuery()方法，写成标准的SQL语法，而不是Hibernate/HQL语法。
同时，由于执行的是SQL语句，所以需要特别注意SQL注入问题。可以使用JPA提供的参数化查询来避免SQL注入问题
```java
package com.example.book.dao;
@Repository
public class UserDaoImp implements UserDao {
    @Autowired
    private SessionFactory sessionFactory;
    public List<User> list(User user) {
        String sql = "SELECT * FROM TBL_USERS WHERE USER_NAME = :name";
        List<User> users = sessionFactory.getCurrentSession()
                .createNativeQuery(sql, User.class)
                .setParameter("name", user.getName())
                .getResultList();
        return users;
    }
}
```