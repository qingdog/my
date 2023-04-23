<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>

</head>
<body>
<h2>添加学生</h2>
<hr/>                                    <!-- 参数访问对象 -->                <!-- 表单数据有多种类型的文件 -->
<form action="stuServlet?param=add" method="post" enctype="multipart/form-data"
	  onsubmit="return checkForm();"> <!--表单-->
	<table width="400"> <!--表格-->
		<tr>
			<td>学号：</td>
			<td><input type="text" id="id" name="id"></td>
		</tr>
		<tr>
			<td>姓名：</td>
			<td><input type="text" id="name" name="name"></td>
		</tr>
		<tr>
			<td>年龄：</td>
			<td><input type="text" id="age" name="age"></td>
		</tr>
		<tr>
			<td>地址：</td>
			<td><input type="text" id="address" name="address"></td>
		</tr>
		<tr>
			<td>照片：</td>
			<td><input type="file" id="photo" name="photo"></td>
		</tr>
		<tr>
			<td>班级：</td>
			<td>
				<select name="cid"><!-- 提交的参数name值 -->
					<c:forEach items="${requestScope.stuList }" var="cls">
						<option value="${cls.id }">${cls.name }</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="2"> <!--跨列2-->
				<input type="submit" value="提交">
				<input type="reset" value="重置">
			</td>
		</tr>
	</table>
</form>
</body>

<script type="text/javascript">
	function checkForm() {
		var idNode = document.getElementById("id");//get input标签的id
		var nameNode = document.getElementById("name");
		var ageNode = document.getElementById("age");
		var addressNode = document.getElementById("address");

		var regId = /^\d{1,10}$/;//完全匹配一个到十个数字
		var regName = /\s+/;//一个或多个空字符串
		var regAge = /^\d{1,3}$/;
		var regAddress = /\s+/;
		//判断id是否为空
		if (idNode.value.length == 0) {
			alert("学号不能为空");
			return false;
		}			//id.value取输入的值
		if (!regId.test(idNode.value)) {
			alert("学号必须为1-10位数字");
			return false;
		}
		if (nameNode.value.replace(regName, "").length == 0) {
			alert("名字不能为空");
			return false;
		}
		if (ageNode.value.length == 0) {
			alert("年龄不能为空");
			return false;
		}
		if (!regAge.test(ageNode.value)) {
			alert("年龄必须为1-3位数字");
			return false;
		}
		if (addressNode.value.replace(regAddress, "").length == 0) {
			alert("地址不能为空");
			return false;
		}
		return true;
	}
</script>
</html>
