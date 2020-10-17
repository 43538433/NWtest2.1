<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<%
    String path = request.getContextPath();
    //获得应用的根url,以后寻找资源的相对路径就是<base>所描述的
    String basepath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%--
  Created by IntelliJ IDEA.
  User: 86135
  Date: 2020/5/9
  Time: 22:58
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>查询导师时间</title>
    <link rel="stylesheet" type="text/css" href="css/common.css">
    <link rel="stylesheet" href="css/home.css">
    <!--导入jquery-->
    <script src="js/jquery-3.3.1.min.js"></script>
    <!--导入将jq对象转换为json字符串插件-->
    <script type="text/javascript" src="js/json.js"></script>
    <style type="text/css">

        .box2 {
            float: left;
            padding: 20px 20px;
            letter-spacing: 30px
        }

        table {
            border: 5px solid;
            color: #9a0000;
            margin: auto;
            width: 1000px;
        }

        td, th {
            text-align: center;
            border: 2px solid;
        }

        div {
            text-align: center;
            margin: 20px;
        }

        input, select {
            color: #9b0202;
            box-sizing: border-box;
            border: 2px solid #9a0000;
            font-size: 15px;
            border-radius: 5px;
            padding: 9px 15px;
        }
        input[disabled] {
              color: #e8d5e0;
              font-size: 15px;
              opacity:1
          }
    </style>
    <script type="text/javascript">

        $(function () {
            $("#searchBtn").click(function () {
                //提交异步请求给后端
                $.post({
                    url: "UserServlet",
                    data: $("#searchForm").serialize(),
                    success: function (result) {
                        alert("查询成功")
                    },
                    error: function () {
                        alert("查询失败");
                    }
                });
            });
            $(".submit").each(function () {
                $(this).click(function () {
                    //获取变化的button的id，拼接起来成为表单的id
                    var keyId=$(this).attr('id');
                    var indexId='orderTimeForm'+keyId;

                    //让点击一次后的按钮不能再次点击
                    var btn=document.getElementById(keyId);
                    btn.disabled=true;

                    $.post({
                        url: "UserServlet",
                        data: $("#"+indexId).serialize(),
                        success: function (result) {
                            alert("预约成功")
                        },
                        error: function () {
                            alert("预约失败");
                        }
                    });
                })
            })
        });
    </script>

</head>
<body>
<hr color="#9a0000" width=100%/>
<div class="box2">
    <font color="#9a0000" size="20">欢迎登录导师预约系统</font>
</div>
<hr size="55px" noshade=false width="100%" color="#9a0000"/>



<form id="searchForm" method="post">
    <!--增加一个表单项存储请求标识符数据action-->
    <input type="hidden" name="action" value="searchTeacher">
    <div>
        <select id="lookFor" name="select">
            <option value="" disabled selected>请选择</option>
            <option value="all">查看全部
            <option value="name">按姓名查询
            <option value="colloge">按学院查询
            <option value="time">按时间查询
        </select>
        <input type="text" id="key" name="key">
        <input type="button" value="搜索" id="searchBtn" class="submit_btn">
    </div>
</form>


<%
    String sid1 = (String) session.getAttribute("sid");
%>

<table>
    <caption><b><font size="5px">导师时间信息表</font></b></caption>

    <tr>
        <th>编号</th>
        <th>姓名</th>
        <th>学院</th>
        <th>时间</th>
        <th>操作</th>
    </tr>

    <%--使用forEach遍历元素--%>

    <c:choose>
        <c:when test="${empty list}">
            <tr>
                <td colspan="5">暂无数据，请重新查询</td>
            </tr>
        </c:when>
        <c:otherwise>
            <c:forEach items="${list}" var="c" varStatus="row">

                <form id="orderTimeForm${row.index}" method="post">
                    <input type="hidden" id="orderTime${row.index}" name="action" value="orderTime">
                    <tr>
                        <td>${row.index+1}</td>
                        <td>${c.name}</td>
                        <td>${c.colloge}</td>
                        <td>${c.time}</td>
                        <input type="hidden" id="keyId${row.index}" name="timeId" value="${c.id}">
                        <input type="hidden" id="name${row.index}" name="name" value="${c.name}">
                        <input type="hidden" id="time${row.index}" name="time" value="${c.time}">
                        <input type="hidden" id="sid${row.index}" name="sid" value="<%=sid1%>">
                        <td>
                            <input id="${row.index}" type="button" class="submit" value="预约">
                        </td>
                    </tr>
                </form>

            </c:forEach>
        </c:otherwise>
    </c:choose>

</table>
<div>
    <a href="home01.jsp">返回首页</a>
</div>

</body>
</html>
