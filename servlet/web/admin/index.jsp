<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理页面</title>
</head>
<body>

<%--<c:choose>
    <c:when test="${empty requestScope.pageModel.list  }">
        <c:redirect url="stuServlet?param=list&pageNo=1"></c:redirect>
    </c:when>
</c:choose>--%>

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
<table style="text-align: center;width: 800px;margin: 0 auto;" id="stuTb" >
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
            <td><a style="text-decoration: none;" href="stuServlet?id=${stu.id} "> ${stu.name }</a>
            </td>
            <td>${stu.age }</td>
            <td>${stu.address }</td>
            <td>${stu.classes.name }</td><!-- student对象.getClass().getName() -->

            <td >
                <a href="stuServlet?param=modify&id=${stu.id }&pageNo=${requestScope.pageModel.pageNo }">修改</a>
                &nbsp;&nbsp;|&nbsp;&nbsp;<a href="" onclick="confirmDel(${stu.id })"> 删除</a>
            </td>
        </tr>
    </c:forEach>

</table>
<%--<table width="800" align="center" style="margin-top: 20px">--%>
    <table style="text-align: center;width: 800px;margin: 20px auto 0;"  >
    <tr>
        <td>
            <input type="button" value="首页" onclick="topPage();"/>&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="button" value="上一页" onclick="previousPage();"/>&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="button" value="下一页" onclick="nextPage();"/>&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="button" value="尾页" onclick="lastPage();"/>&nbsp;&nbsp;&nbsp;
            <br/><br/>

            <div style="float: left; margin-left: 150px">
                总共<span style="color: red; ">${ requestScope.pageModel.totalPages }</span>页 /
                当前第<span style="color: red; ">${ requestScope.pageModel.pageNo }</span>页
            </div>
            <form action="stuServlet" method="post">
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
        window.location.href = "stuServlet?param=add";
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
        window.location.href = "stuServlet?pageNo=${ requestScope.pageModel.topPage }";
    }

    function previousPage() {
        window.location.href = "stuServlet?pageNo=${ requestScope.pageModel.previousPage }";
        //window.location.href = "stuServlet?pageNo=${ requestScope.pageModel.pageNo -1}";
    }

    function nextPage() {
        window.location.href = "stuServlet?pageNo=${ requestScope.pageModel.nextPage }";
    }

    function lastPage() {
        window.location.href = "stuServlet?pageNo=${ requestScope.pageModel.lastPage }";
    }

</script>
<style>
    #stuTb td:not(:nth-of-type(0)){
        border: 1px solid silver;
        border-bottom: 0;
        border-right: 0;
    }
    #stuTb td:nth-of-type(1){
        border-left: 0;
    }
    /*#stuTb th:not(:nth-of-type(0)){
        border: 0 solid silver;
        border-left-width: 1px;
    }*/
    #stuTb th:nth-of-type(1){
        border-left: 0;
    }
    #stuTb{
        border: 1px solid silver;
    }
</style>
</html>
