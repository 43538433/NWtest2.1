<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%--
  Created by IntelliJ IDEA.
  User: 86135
  Date: 2020/5/13
  Time: 18:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>导师预约系统</title>
    <link rel="shortcut icon" href="images/bitbug_favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" type="text/css" href="css/common.css">
    <link rel="stylesheet" href="css/home.css">

    <!--导入jquery-->
    <script src="js/jquery-3.3.1.min.js"></script>
    <!--导入json转换js-->
    <script src="js/json.js"></script>

    <style type="text/css">
        .box1 {
            float: right;
            padding: 40px 20px;
            letter-spacing: 5px
        }

        .box2 {
            float: left;
            padding: 20px 20px;
            letter-spacing: 30px
        }

        .box3 {
            float: left;
            background-color: #9a0000;
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            box-sizing: border-box;
            width: 130px;
            height: 545px;
            border: 10px
        }
    </style>

    <script type="text/javascript">
        $(function () {
            $("#outBtn").click(function () {
                var r = confirm("请确定是否退出登录");
                if (r == true) {
                    //提交异步请求给后端，让后台销毁当前session域里的所有值
                    $.post({
                        url: "UserServlet",
                        data: $("#loginOutForm").serialize(),
                        success: function (result) {
                            //当前页面打开http...页面跳转到登录页面
                            window.location.href = "http://localhost:8080/register.html"
                        },
                        error: function () {
                            alert("服务器繁忙，请稍后再试...");
                        }
                    });
                } else {
                    alert("已取消")
                }
            })
        });
    </script>
</head>
<body>
<hr color="#9a0000" width=100%/>
<div class="box1">
    <%--显示登录时间和当前用户的姓名--%>
    <%String name1 = (String) session.getAttribute("name");%>
    <%
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = dateFormat.format(date);
    %>
    <tr>
        <td>
            <font color="#9a0000" size="4"><%=name1%>Welcome,</font>
            <font color="#9a0000" size="4">登录时间:<%=now%>
            </font>
        </td>
    </tr>

</div>
<div class="box2">
    <tr>
        <td>
            <font color="#9a0000" size="20">欢迎登录导师预约系统</font>
        </td>
    </tr>
</div>
<hr size="55px" noshade=false width="100%" color="#9a0000"/>
<br>
<div class="content_body">
    <div class="box3" id="leftNav">
        <ul class="outside">
            <br>
            <br>
            <li id="search">
                <a href="searchList.jsp"><b>&nbsp;&nbsp;&nbsp;&nbsp;<font color="#ABEED9">导师时间查询</font></b></a>
            </li>
            <br>
            <br>
            <li id="show">
                <a href="sApointTime.jsp"><b>&nbsp;&nbsp;&nbsp;&nbsp;<font color="#ABEED9">查询预约请求</font></b></a>
            </li>
            <br>
            <br>
            <li id="massge">
                <a href="#"><b>&nbsp;&nbsp;&nbsp;&nbsp;<font color="#ABEED9">通知信息</font></b></a>
            </li>

            <br>
            <br>
            <form id="loginOutForm" method="post">
                <input type="hidden" id="loginOut" name="action" value="loginOut">
                &nbsp;&nbsp;&nbsp;&nbsp;<input type="button" id="outBtn" class="submit" value="退出登录">
            </form>

        </ul>
    </div>
</div>
</body>
</html>