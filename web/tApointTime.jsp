<%@ page import="java.util.List" %>
<%@ page import="com.xueyun.www.util.DBUtils" %>
<%@ page import="com.xueyun.www.entity.Student" %><%--
  Created by IntelliJ IDEA.
  User: 86135
  Date: 2020/10/16
  Time: 17:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>处理预约请求</title>
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

        input {
            box-sizing: border-box;
            border: 2px solid #9b0202;
            border-radius: 10px;
            font-size: 15px;
            padding: 10px 40px;
            letter-spacing: 3px
        }
        input[disabled] {
            color: #e8d5e0;
            font-size: 15px;
            opacity:1
        }
    </style>

    <script type="text/javascript">
        $(function () {
            $(".submit").each(function () {
                $(this).click(function () {
                    //获取变化的button的id，拼接起来成为表单的id
                    var keyId = $(this).attr('id');
                    var indexId = 'dealApointForm' + keyId;

                    //让点击一次后的按钮不能再次点击
                    var btn = document.getElementById(keyId);
                    btn.disabled = true;

                    $.post({
                        url: "UserServlet",
                        data: $("#" + indexId).serialize(),
                        success: function (result) {
                            alert("审批成功")
                        },
                        error: function () {
                            alert("审批失败");
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

<%
    int id=(int) session.getAttribute("id");
%>

<table>

    <tr>
        <th>编号</th>
        <th>姓名</th>
        <th>学号</th>
        <th>学院</th>
        <th>预约时间</th>
        <th>预约状态</th>
        <th>处理</th>
    </tr>

    <%
        String sql="SELECT u.`name`,r.user_sid,colloge,`time`,t_status,t.`id`,r.`rid`\n" +
                "FROM result r\n" +
                "INNER JOIN `user` u\n" +
                "ON r.`user_sid`=u.`sid`\n" +
                "INNER JOIN `time` t\n" +
                "ON r.`time_id`=t.`id`\n" +
                "WHERE t.`user_id`=?";
        List<Student> list= DBUtils.query(sql,Student.class,id);
        int i=1;
        for(Student student:list){
            int k=student.getT_status();
            String status="待审批";
            switch (k){
                case 0:
                    status="审批未通过";
                    break;
                case 1:
                    status="待审批";
                    break;
                case 2:
                    status="审批通过";
                    break;
            }
    %>
    <form id="dealApointForm<%=i%>" method="post">
        <input type="hidden" id="orderTime<%=i%>" name="action" value="dealApoint">
    <tr>
        <td><%=i%></td>
        <td><%=student.getName()%></td>
        <td><%=student.getUser_sid()%></td>
        <td><%=student.getColloge()%></td>
        <td><%=student.getTime()%></td>
        <td><%=status%></td>
        <input type="hidden" id="timeId<%=i%>" name="timeId" value="<%=student.getId()%>">
        <input type="hidden" id="resultId<%=i%>" name="resultId" value="<%=student.getRid()%>">
        <%
            if(k==1){
        %>
        <td>
            <input id="<%=i%>" type="button" class="submit" value="审批">
        </td>
        <%
            }else {
        %>
        <td>取消</td>
        <%
            }
        %>
    </tr>
    </form>
    <%
        i++;}
    %>

</table>
<div>
    <a href="home02.jsp">返回首页</a>
</div>
</body>
</html>
