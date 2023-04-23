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
@ComponentScan("com.itheima.controller")
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

  <groupId>com.itheima</groupId>
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
@ComponentScan({"com.itheima.service"})
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
        factoryBean.setTypeAliasesPackage("com.itheima.domain");
        return factoryBean;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer msc = new MapperScannerConfigurer();
        msc.setBasePackage("com.itheima.dao");
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
@ComponentScan("com.itheima.controller")
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
package com.itheima.config;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.Properties;

public class MyBatisConfig {

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource, PageInterceptor pageInterceptor){
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setTypeAliasesPackage("com.itheima.domain");

        factoryBean.setPlugins(new Interceptor[]{pageInterceptor});
        return factoryBean;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer msc = new MapperScannerConfigurer();
        msc.setBasePackage("com.itheima.dao");
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
* 视图 - 工具窗口 - JRebel - 勾选项目
* 以 JRebel Run 或 JRebel debug 方式启动（不再使用idea原图标启动maven），修改 java文件 后编译即热更新
* 若热部署没有生效，手动删除项目下的.idea目录（包含子目录和.idea中所有文件），文件 - 清除缓存 - 并重启启动

#### 编写mapper.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.itheima.dao.BookDao">
    <!-- 通用查询映射结果 -->
    <resultMap id="BaseResultMap" type="com.itheima.domain.Book">
        <id column="id" property="id" />
        <result column="type" property="type" />
        <result column="name" property="name" />
    </resultMap>

    <!-- 通用查询结果列 -->
    <sql id="Base_Column_List">
        id , name, type
    </sql>

    <select id="getAll" resultType="com.itheima.domain.Book">
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