<%@page import="com.iflytek.stumanager.po.Student" %>
<%@page import="com.iflytek.stumanager.business.StudentService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <%
        /*int id =Integer.parseInt(request.getParameter("id"));//内置对象
        StudentService stuService = new StudentService();
        Student stu = stuService.findStudentById(id);*///应该由控制器来完成

        //Student stu = (Student)request.getAttribute("stu");不用了
    %>

</head>
<body>
<h2>修改学生信息</h2>
<hr/>
<form action="stuServlet?param=update&pageNo=${param.pageNo }" method="post" enctype="multipart/form-data"> <!--表单-->
    <table width="400"> <!--表格-->
        <tr>
            <td>学号：</td>
            <td><input type="text" name="id" value="${requestScope.stu.id }" readonly="readonly"/></td>
        </tr>
        <tr>
            <td>姓名：</td>
            <td><input type="text" name="name" value="${requestScope.stu.name }"/></td>
        </tr>
        <tr>
            <td>年龄：</td>
            <td><input type="text" name="age" value="${requestScope.stu.age }"/></td>
        </tr>
        <tr>
            <td>地址：</td>
            <td><input type="text" name="address" value="${requestScope.stu.address }"/></td>
        </tr>
        <tr>
            <td>照片：</td>
            <td><input type="file" name="photo" value="photo"/></td>
        </tr>
        <tr>
            <td>班级：</td>
            <td>
                <select name="cid"><!-- request.getAttribute() -->
                    <c:forEach items="${requestScope.clsList }" var="cls">
                        <c:choose>
                            <c:when test="${cls.id eq requestScope.stu.classes.id }">
                                <option value="${cls.id }" selected="selected">${cls.name }</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${cls.id }">${cls.name }</option>
                            </c:otherwise>
                        </c:choose>

                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td colspan="2"> <!--跨列="2"-->
                <input type="submit" value="提交">
                <input type="reset" value="重置">
            </td>
        </tr>

    </table>
</form>
</body>
</html>