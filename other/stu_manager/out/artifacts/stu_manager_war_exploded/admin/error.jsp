<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>账号或密码错误</h2>
	你的错误信息如下：<br/>
	账号：<%=request.getParameter("account") %>---${param.account}<br/>
	密码：<%=request.getParameter("password") %>---${param.password}<br/>
	<hr/>
	请重新<a href="login.jsp">登陆</a>
	<!-- </%=exception.getMessage() %> -->

</body>
</html>
