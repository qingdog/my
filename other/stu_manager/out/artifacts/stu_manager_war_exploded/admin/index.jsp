<%@ page import="com.iflytek.stumanager.business.StudentService" %>
<%@ page import="java.util.List" %>
<%@ page import="com.iflytek.stumanager.po.Student" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <script type="text/javascript" src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript">
        function addForm() {//声明addForm函数
            //window.location.href = "addForm.jsp?pageNo=${ requestScope.pageModel.pageNo }";//跳转到addForm.jsp		pageNo->servlet->jsp
            window.location.href = "stuServlet?pageNo=${ requestScope.pageModel.pageNo }&param=stuForm";
        }

        function confirmDel(id) {
            var result = window.confirm("确认删除吗？");
            if (result) {
                //window.location.href = "stuServlet?param=delete&id="+id+"&pageNo=${ requestScope.pageModel.pageNo }";
                $.get("stuServlet?param=delete&id=" + id + "&pageNo=${ requestScope.pageModel.pageNo }", function (jsonArray) {

                    //清空原有表格数据
                    $("#stuTb").find("tr:not(:first)").remove();
                    var i = 0;
                    $(jsonArray).each(function (i, element) {//jsonArray的jquery方式的each()循环
                        var id = jsonArray[i++].id;
                        var name = this.name;
                        var age = this.age;
                        var address = this.address;
                        var cname = this.classes.name;
                        //创建tr结点（创建一行）
                        var trNode = $("<tr>").append("<td>" + id + "</td>")
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
</head>
<body>
<%
    //判断用户是否已登陆
		/*Object obj = session.getAttribute("admin");
		if(obj == null){
			response.sendRedirect("../login.jsp");
			return;
		}*/

    //查询所有的学生数据
		/*StudentService stuService = new StudentService();
		List<Student> stuList = stuService.findAllStudents();*/


		/*Object obj = request.getAttribute("stuList");
		if(obj == null){//空指针异常
			response.sendRedirect("stuServlet?param=list");
			return;//结束当前类,避免空指针异常
		}
		List<Student> stuList = (List<Student>)obj;
		*/
%>

<c:choose>
    <c:when test="${empty requestScope.pageModel.stuList  }">
        <c:redirect url="stuServlet?param=list&pageNo=1"></c:redirect>
    </c:when>
    <c:otherwise>
    </c:otherwise>
</c:choose>

<h2 align="center">学生信息列表</h2>
<hr/>
<table width="800" align="center">
    <tr>
        <td align="right"><input type="button" value="添加" onclick="addForm();"></td> <!--onclick单击事件-->
    </tr>
    <table id="stuTb" width="800" border="1" align="center">
        <tr>
            <th>编号</th>
            <th>姓名</th>
            <th>年龄</th>
            <th>地址</th>
            <th>班级</th>
            <th>操作</th><!--jsp隐式对象 pageContext.request -->
        </tr>            <!--作用域访问对象 requestScope.getPageModel().getStuList() -->
        <c:forEach items="${requestScope.pageModel.stuList }" var="stu">
            <tr>        <%--  中括号操作符：适用于特殊字符、变量、复选框 ${requestScope['pageModel']["stuList"].[0] } --%>
                <td>${stu.id }</td>
                <td><a style="text-decoration: none;" href="stuServlet?param=detail&id=${stu.id} "> ${stu.name }</a>
                </td>
                <td>${stu.age }</td>
                <td>${stu.address }</td>
                <td>${stu.classes.name }</td><!-- student对象.getClass().getName() -->

                <td align="center"><a
                        href="stuServlet?param=modify&id=${stu.id }&pageNo=${requestScope.pageModel.pageNo }">修改</a>&nbsp;&nbsp;
                    |
                    &nbsp;&nbsp;<a href="javascript:void();" onclick="confirmDel(${stu.id })"> 删除</a></td>
            </tr>
        </c:forEach>

    </table>
    <table width="800" align="center" style="margin-top: 20px">
        <tr>
            <td align="center">
                <input type="button" value="首页" onclick="topPage();"/>&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" value="上一页" onclick="previousPage();"/>&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" value="下一页" onclick="nextPage();"/>&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" value="尾页" onclick="lastPage();"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/><br/>

                <div style="float: left; margin-left: 150px">
                    总共<font color="red">${ requestScope.pageModel.totalPages }</font>页 /
                    当前第<font color="red">${ requestScope.pageModel.pageNo }</font>页
                </div>
                <form action="stuServlet?param=list" method="post">
                    跳转至<input type="text" name="pageNo" size="1"/><!-- 把pggeNo作为参数带到stuServlet -->
                    <input type="submit" value="Go"/><br/>
                </form>
            </td>
        </tr>
    </table>
    <!-- <a href="stuServlet">其他操作</a> -->
</table>
</body>
</html>
