<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>学生详细信息</h2>
	<hr />
	<table width="400">
	<form action="stuServlet?pageNo=1" method="post" enctype="multipart/form-data" >
			<tr>
				<td>学号：</td>
				<td>${requestScope.student.id }</td>
			</tr>
			<tr>
				<td>姓名：</td>
				<td>${requestScope.student.name }</td>
			</tr>
			<tr>
				<td>年龄：</td>
				<td>${requestScope.student.age }</td>
			</tr>
			<tr>
				<td>地址：</td>
				<td>${requestScope.student.address }</td>
			</tr>
			<tr>
				<td>照片：</td>
				<td><img src="../photos/${requestScope.student.photo }.jpg" width="150" height="150" /></td>
			</tr>
			<tr>
				<td>
					<%--<input type="submit" value="返回" />--%>
					<input type="button" onclick="window.history.back();" value="返回" />
				</td>
			</tr>
		</table>
</body>
</html>