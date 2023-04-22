## quick start

文件 - 新建项目 - 创建

右键项目名称 - 添加框架支持

设置 - 项目结构 - 库 - 新建项目库（java）  - 本地 repository 选择 mysql.jar - 将 lib 添加到工件一起打包编译运行

设置 - 项目结构 - 库 - 新建项目库（java）  - （项目名 - web - WEB-INF - lib）

### 1.拦截器

编辑 web/WEB-INF/web.xml，访问根路径下的admin会被拦截

```java
<!--servlet拦截器-->
<filter>
    <filter-name>permissionFilter</filter-name>
    <filter-class>com.iflytek.stumanager.filter.PermissionCheckFilter</filter-class>
</filter>
<filter-mapping><!-- filter-mapping的位置确定拦截顺序 -->
    <filter-name>permissionFilter</filter-name>
    <url-pattern>/admin/*</url-pattern>
</filter-mapping>
```

创建 java 类 实现 servlet 拦截器

```java
package com.iflytek.stumanager.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PermissionCheckFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println(this.getClass().getName() + " servlet 过滤器初始化...");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        System.out.println(this.getClass().getName() + " servlet 过滤器执行中...");

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        Object obj = httpRequest.getSession().getAttribute("admin");
        if (obj != null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            httpResponse.sendRedirect("/login.jsp");
        }
    }

    @Override
    public void destroy() {
        System.out.println(this.getClass().getName() + " servlet 过滤器被销毁前工作...");
    }
}
```

### 2.servlet

```java
<servlet> <!--servlet的配置信息-->
    <servlet-name>LoginServlet</servlet-name>
    <servlet-class>com.iflytek.stumanager.servlet.LoginServlet</servlet-class>
</servlet>
<servlet-mapping><!--servlet映射-->
    <servlet-name>LoginServlet</servlet-name>
    <url-pattern>/login</url-pattern><!--访问路径-->
</servlet-mapping>
```

编写 servlet

```java
package com.iflytek.stumanager.servlet;


import com.iflytek.stumanager.business.AdminService;
import com.iflytek.stumanager.po.Admin;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MultipartConfig
public class LoginServlet extends HttpServlet {
    private final AdminService adminService;

    public LoginServlet() {
        adminService = new AdminService();
    }

    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        /* 允许跨域的主机地址 */
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");//解决浏览器的同源策略问题
        /* 允许跨域的请求方法GET, POST, HEAD 等 */
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
        /* 重新预检验跨域的缓存时间 (s) */
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        /* 允许跨域的请求头 */
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
        /* 是否携带cookie */
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");

        String account = httpServletRequest.getParameter("account");
        String password = httpServletRequest.getParameter("password");
        System.out.println(account + "    " + password);
        Admin admin = new Admin(account, password);
        boolean legal = adminService.legal(admin);
        if (legal) {
            httpServletRequest.getSession().setAttribute("admin", admin);
            httpServletResponse.sendRedirect("admin/stuServlet?param=list&pageNo=1");

//            httpServletResponse.getWriter().write("success");
        } else {
            httpServletRequest.getRequestDispatcher("admin/error.jsp").forward(httpServletRequest, httpServletResponse);
        }
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        super.service(req, res);
    }
}
```

数据库查询成功转发到 index.jsp

```java
package com.iflytek.stumanager.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StudentServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String param = request.getParameter("param");
        System.out.println(this.getClass() + " param: " + param);


        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
```

编写service

```java
package com.iflytek.stumanager.business;

import com.iflytek.stumanager.dao.AdminDao;
import com.iflytek.stumanager.dao.factory.impl.MysqlDaoFactory;
import com.iflytek.stumanager.po.Admin;

public class AdminService {

    private AdminDao adminDao;

    public AdminService() {
        adminDao = MysqlDaoFactory.getInstance().createAdminDao();
    }

    /**
     * 用户是否合法
     *
     * @param admin
     * @return
     */
    public boolean legal(Admin admin) {
        return adminDao.isExists(admin.getAccount(), admin.getPassword());
    }
}
```

#### 编写抽象工厂

```java
package com.iflytek.stumanager.dao.factory;

import com.iflytek.stumanager.dao.AdminDao;

public abstract class DaoFactory {
    public DaoFactory() {
    }

    public abstract AdminDao createAdminDao();
}
```

mysql实现工厂

```java
package com.iflytek.stumanager.dao.factory.impl;

import com.iflytek.stumanager.dao.AdminDao;
import com.iflytek.stumanager.dao.factory.DaoFactory;
import com.iflytek.stumanager.dao.impl.AdminDaoImpl;

public class MysqlDaoFactory extends DaoFactory {
    private static final MysqlDaoFactory instance = new MysqlDaoFactory();

    public static MysqlDaoFactory getInstance() {
        return instance;
    }

    @Override
    public AdminDao createAdminDao() {
        return new AdminDaoImpl();
    }
}
```

编写接口dao

```java
package com.iflytek.stumanager.dao;

public interface AdminDao {
    boolean isExists(String account, String password);
}
```

编写dao实现类

```java
package com.iflytek.stumanager.dao.impl;

import com.iflytek.stumanager.dao.AdminDao;
import com.iflytek.stumanager.util.DBUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDaoImpl implements AdminDao {
    @Override
    public boolean isExists(String account, String password) {
        boolean isExists = false;

        String sql = "select * from admin where account=? and password='" + password + "'";

        PreparedStatement pstmt = DBUtil.getPreparedStatement(sql);
        try {
            pstmt.setString(1, account);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                isExists = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isExists;
    }
}
```

#### 编写jdbc工具类

```java
package com.iflytek.stumanager.util;

import java.sql.*;

public class DBUtil {

    private static Connection conn;
    private static Statement stmt;
    private static PreparedStatement pstmt;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public DBUtil() {
    }

    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/stu_db2?useSSL=false&serverTimezone=UTC";
            conn = DriverManager.getConnection(url, "", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static Statement getStatement() {
        Connection conn = getConnection();
        try {
            if (conn != null)
                stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stmt;
    }

    public static PreparedStatement getPreparedStatement(String sql) {
        Connection conn = getConnection();
        try {
            if (conn != null)
                pstmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pstmt;
    }

    public static void closeDBResources() {
        try {
            if (pstmt != null && !pstmt.isClosed())
                pstmt.close();
            if (stmt != null && !stmt.isClosed())
                stmt.close();
            if (conn != null && !conn.isClosed())
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

三层搭建完成，表单提交测试登录接口

```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>用户登陆</h2>
<hr/>
<form action="login" method="get"> <!--提交表单到login-->
    账号：<input type="text" name="account"/><br/>
    密码：<input type="password" name="password"/><br/>
    <input type="submit" value="提交"/><br/>
    <hr/>
</form>
</body>
</html>
```

### 3.查询列表

更新 StudentServlet 新增查询数据

```java
package com.iflytek.stumanager.servlet;

import com.iflytek.stumanager.business.ClassesService;
import com.iflytek.stumanager.business.StudentService;
import com.iflytek.stumanager.util.PageModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class StudentServlet extends HttpServlet {
    private final StudentService stuService;
    private final ClassesService clsService;

    public StudentServlet() {
        stuService = new StudentService();
        clsService = new ClassesService();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int totalRecords = stuService.queryTotalRecords();

        String param = request.getParameter("param");
        System.out.println(this.getClass() + " param: " + param);
        if ("list".equals(param)) {
            int pageNo = Integer.parseInt(request.getParameter("pageNo"));
            int pageSize = 5;
            List studentList = stuService.findStudentsByPageNo(pageNo, pageSize);

            PageModel pageModel = new PageModel(pageSize);
            pageModel.setTotalRecords(totalRecords);
            pageModel.setPageNo(pageNo);
            pageModel.setTotalPages((totalRecords - 1) / pageSize + 1);

            pageModel.setList(studentList);
            request.setAttribute("pageModel", pageModel);
            request.getRequestDispatcher("index.jsp").forward(request, response);

        } else {

            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}
```

#### 分页参数

```java
package com.iflytek.stumanager.util;

import java.util.List;

public class PageModel {

    private List list;
    private int totalRecords;
    private int pageSize;
    private int totalPages;
    private int pageNo;
    public PageModel(int pageSize) {
        this.pageSize = pageSize;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTopPage() {
        return 1;
    }

    public int getLastPage() {
        return totalPages;
    }

    public int getPreviousPage() {
        return pageNo > 1 ? pageNo - 1 : 1;
    }

    public int getNextPage() {
        return pageNo < getTotalPages() ? pageNo + 1 : getTotalPages();
    }
}
```



#### 编写 jsp

并且复制jstl.jar到约定的lib目录中

```html
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理员</title>
</head>
<body>

<c:choose>
    <c:when test="${empty requestScope.pageModel.list  }">
        <c:redirect url="stuServlet?param=list&pageNo=1"></c:redirect>
    </c:when>
    <c:otherwise> </c:otherwise>
</c:choose>

<h2 style="text-align: center">学生信息列表</h2>
<hr/>
<%--<table align="center" style="text-align: center;width: 800px">--%>
<table style="width: 800px;margin: 0 auto;">
    <tr>
        <%--<td align="right"><input type="button" value="添加" onclick="addForm();"></td>--%>
        <td style="float: right;"><input type="button" value="添加" onclick="addForm();"></td>
        <!--onclick单击事件-->
    </tr>
</table>
<%--<table id="stuTb" width="800" border="1" align="center">--%>
<table style="text-align: center;width: 800px;margin: 0 auto;border: 0;" id="stuTb" border="1px" >
    <tr>
        <th>编号</th>
        <th>姓名</th>
        <th>年龄</th>
        <th>地址</th>
        <th>班级</th>
        <th>操作</th><!--jsp隐式对象 pageContext.request -->
    </tr>            <!--作用域访问对象 requestScope.getPageModel().getList() -->
    <c:forEach items="${requestScope.pageModel.list }" var="stu">
        <tr>        <%--  中括号操作符：适用于特殊字符、变量、复选框 ${requestScope['pageModel']["list"].[0] } --%>
            <td>${stu.id }</td>
            <td><a style="text-decoration: none;" href="stuServlet?param=detail&id=${stu.id} "> ${stu.name }</a>
            </td>
            <td>${stu.age }</td>
            <td>${stu.address }</td>
            <td>${stu.classes.name }</td><!-- student对象.getClass().getName() -->

            <td align="center">
                <a href="stuServlet?param=modify&id=${stu.id }&pageNo=${requestScope.pageModel.pageNo }">修改</a>
                &nbsp;&nbsp;|&nbsp;&nbsp;<a href="" onclick="confirmDel(${stu.id })"> 删除</a></td>
        </tr>
    </c:forEach>

</table>
<table width="800" align="center" style="margin-top: 20px">
    <tr>
        <td align="center">
            <input type="button" value="首页" onclick="topPage();"/>&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="button" value="上一页" onclick="previousPage();"/>&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="button" value="下一页" onclick="nextPage();"/>&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="button" value="尾页"
                   onclick="lastPage();"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/><br/>

            <div style="float: left; margin-left: 150px">
                总共<span style="color: red; ">${ requestScope.pageModel.totalPages }</span>页 /
                当前第<span style="color: red; ">${ requestScope.pageModel.pageNo }</span>页
            </div>
            <form action="stuServlet?param=list" method="post">
                跳转至
                <label>
                    <input type="text" name="pageNo" size="1"/><!-- 把pageNo作为参数带到stuServlet -->
                </label>
                <input type="submit" value="Go"/><br/>
            </form>
        </td>
    </tr>
</table>
</body>

<script type="text/javascript" src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript">
    function addForm() {//声明addForm函数
        //window.location.href = "addForm.jsp?pageNo=${ requestScope.pageModel.pageNo }";//跳转到addForm.jsp		pageNo->servlet->jsp
        window.location.href = "stuServlet?pageNo=${ requestScope.pageModel.pageNo }&param=stuForm";
    }

    function confirmDel(id) {
        const result = window.confirm("确认删除吗？");
        if (result) {
            //window.location.href = "stuServlet?param=delete&id="+id+"&pageNo=${ requestScope.pageModel.pageNo }";
            $.get("stuServlet?param=delete&id=" + id + "&pageNo=${ requestScope.pageModel.pageNo }", function (jsonArray) {

                //清空原有表格数据
                $("#stuTb").find("tr:not(:first)").remove();
                const i = 0;
                $(jsonArray).each(function (i, element) {//jsonArray的jquery方式的each()循环
                    const id = jsonArray[i++].id;
                    const name = this.name;
                    const age = this.age;
                    const address = this.address;
                    const cname = this.classes.name;
                    //创建tr结点（创建一行）
                    const trNode = $("<tr>").append("<td>" + id + "</td>")
                        .append("<td><a style='text-decoration: none;' href='stuServlet?param=detail&id=" + id + "'>" + name + "</a></td>")
                        .append("<td>" + age + "</td>")
                        .append("<td>" + address + "</td>")
                        .append("<td>" + cname + "</td>")
                        .append("<td align='center'><a href='stuServlet?param=modify&id=" + id +
                            "&pageNo=${requestScope.pageModel.pageNo }'>修改</a>&nbsp;&nbsp; | &nbsp;&nbsp;" +
                            "<a href='javascript:void();' onclick='confirmDel(" + id + ")'> 删除</a></td>");
                    //追加最新数据
                    $("#stuTb").append(trNode);
                });

            }, "json")
        }
    }

    function topPage() {
        window.location.href = "stuServlet?param=list&pageNo=${ requestScope.pageModel.topPage }";
    }

    function previousPage() {
        window.location.href = "stuServlet?param=list&pageNo=${ requestScope.pageModel.previousPage }";
        //window.location.href = "stuServlet?param=list&pageNo=${ requestScope.pageModel.pageNo -1}";
    }

    function nextPage() {
        window.location.href = "stuServlet?param=list&pageNo=${ requestScope.pageModel.nextPage }";
    }

    function lastPage() {
        window.location.href = "stuServlet?param=list&pageNo=${ requestScope.pageModel.lastPage }";
    }

</script>
</html>
```





