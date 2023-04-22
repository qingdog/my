<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	参数：			input带过去的参数 | ?带过去的参数
	requestScope：	setAttribute带过去的属性 | set属性
	
	<h2>通过EL展示各项数据</h2>
	<hr />
	p1:${param.p1 }<br />
	p2:${param.p2 }
	<hr />
	session中的保存的学生的姓名：${sessionScope.s.name }
	<hr />
	<c:if test="${empty sessionScope.s2 }">
	empty sessionScope.s2 
	</c:if>
	<br />
	<c:if test="${sessionScope.s.name eq '张三' }">
	sessionScope.s.name eq '张三'
	</c:if>
	<br />
	<c:if test="${sessionScope.s.age gt 20 }">
	sessionScope.s.age gt 20 
	</c:if>
	<c:if test="${sessionScope.s.age lt 20 }">
	sessionScope.s.age lt 20 
	</c:if>
	<hr />
	
	<c:choose>
		<c:when test="${sessionScope.s.age gt 60 }">
		sessionScope.s.age gt 60
		</c:when>
		<c:when test="${sessionScope.s.age gt 18 }">
		sessionScope.s.age gt 18 
		</c:when>
		
		<c:otherwise>
		else
		</c:otherwise>
	</c:choose>
	<hr />
	
	<ul>
	<c:forEach items="${sessionScope.stuList }" var="stu">
		<li>${stu.id }</li>
		<li>${stu.address }</li>
	</c:forEach>
	</ul>

</body>
</html>