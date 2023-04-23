<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.iflytek.stumanager.po.Student"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>用户登陆</h2>
	<hr/>
	<form action="login" method="get"> <!--提交表单到login-->
	账号：<input type="text" name="account" /><br/>
	密码：<input type="password" name="password"/><br/>
	<input type="submit" value="提交"/><br/>
	<hr />
	<%
		Student stu = new Student(100,"张三",26,"安徽芜湖");
		session.setAttribute("s", stu);

		Student stu1 = new Student(100,"张三1",26,"安徽芜湖1");
		Student stu2 = new Student(200,"张三2",26,"安徽芜湖2");
		Student stu3 = new Student(300,"张三3",26,"安徽芜湖3");
		Student stu4 = new Student(400,"张三4",26,"安徽芜湖4");
		Student stu5 = new Student(500,"张三5",26,"安徽芜湖5");
		List<Student> stuList = new ArrayList<Student>();
		stuList.add(stu1);
		stuList.add(stu2);
		stuList.add(stu3);
		stuList.add(stu4);
		stuList.add(stu5);

		session.setAttribute("stuList", stuList);
	%>
<%--	<a href="test.jsp?p1=jack&p2=tom">TEST</a>--%>
</body>
</html>
