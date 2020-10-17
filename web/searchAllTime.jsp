<%@ page import="com.xueyun.www.entity.Tutor" %>
<%@ page import="java.util.List" %>
<%@ page import="com.xueyun.www.util.DBUtils" %><%--
  Created by IntelliJ IDEA.
  User: 86135
  Date: 2020/10/17
  Time: 20:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>查看预约信息</title>
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

        input{
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

</head>
<body>
<hr color="#9a0000" width=100%/>
<div class="box2">
    <font color="#9a0000" size="20">欢迎登录导师预约系统</font>
</div>
<hr size="55px" noshade=false width="100%" color="#9a0000"/>

<center><caption><b><font size="5px">学生预约信息表</font></b></caption></center>

<table>

    <tr>
        <th>编号</th>
        <th>姓名</th>
        <th>学号</th>
        <th>专业</th>
        <th>预约时间</th>
        <th>预约状态</th>
    </tr>

    <%
        String sql="SELECT u.`name`,u.sid,u.grade,t.`time`,r.stu_status\n" +
                "FROM result r\n" +
                "INNER JOIN `user` u\n" +
                "ON r.`user_sid`=u.`sid`\n" +
                "INNER JOIN `time` t\n" +
                "ON r.`time_id`=t.id";
        List<Tutor> list= DBUtils.query(sql,Tutor.class);
        int i=1;
        for(Tutor tutor:list){
            int k=tutor.getStu_status();
            String status="待审批";
            switch (k) {
                case 0:
                    status = "审批未通过";
                    break;
                case 1:
                    status = "待审批";
                    break;
                case 2:
                    status = "审批通过";
                    break;
            }
    %>
    <tr>
        <td><%=i%></td>
        <td><%=tutor.getName()%></td>
        <td><%=tutor.getSid()%></td>
        <td><%=tutor.getGrade()%></td>
        <td><%=tutor.getTime()%></td>
        <td><%=status%></td>
    </tr>
    <%
        i++;}
    %>

</table>
<div>
    <a href="home03.jsp">返回首页</a>
</div>
</body>
</html>
