package com.xueyun.www.servlet;

import com.xueyun.www.entity.Time;
import com.xueyun.www.entity.Tutor;
import com.xueyun.www.entity.User;
import com.xueyun.www.exception.CustomerErrorMsgException;
import com.xueyun.www.service.UserService;
import com.xueyun.www.util.DBUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 86135
 */
@WebServlet(name = "UserServlet", urlPatterns = "/UserServlet")
public class UserServlet extends BaseServlet {

    private UserService userService = new UserService();

    /**
     * 处理用户退出请求
     */
    private void loginOut(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        //销毁session
        request.getSession().invalidate();
       //跳转登录页面
       //response.sendRedirect(request.getContextPath()+"/index.jsp");
    }

    /**
     * 用户登录
     */
    private void login(HttpServletRequest request, HttpServletResponse response) throws CustomerErrorMsgException, IOException {

        try {
            //获取登录的用户名与密码
            String name = request.getParameter("name");
            String sid = request.getParameter("sid");
            String password = request.getParameter("password");

            //调用业务层根据用户名与密码进行登录
            User loginUser = userService.login(name, sid, password);

            //获取返回数据判断登录用户是否为空
            if (loginUser != null) {
               //若返回的数据不为空，登录时成功将登录数据写入session
                request.getSession().setAttribute("name", loginUser.getName());
                request.getSession().setAttribute("sid", loginUser.getSid());
                request.getSession().setAttribute("colloge", loginUser.getColloge());
                request.getSession().setAttribute("id", loginUser.getId());

                //根据不同的status状态进入不同的页面
                int status = loginUser.getStatus();
                if (status == 0) {
                    response.getWriter().print("student");
                } else if (status == 1) {
                    response.getWriter().print("tutor");
                } else if (status == 2) {
                    response.getWriter().print("administrators");
                }
            }
        } catch (CustomerErrorMsgException e) {
            //登录失败，返回自定义异常，直接将失败消息输出
            response.getWriter().print(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理添加时间的请求
     */
    private void time(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            //获取表单提交所有用户数据封装到Time对象中
            Time time = new Time();
            BeanUtils.populate(time, request.getParameterMap());

            //调用业务层实现数据插入
            boolean addTime = userService.time(time);

            //获取业务层返回的结果
            response.getWriter().print(addTime);
        } catch (Exception e) {
            e.printStackTrace();
            //结果3：返回系统异常,直接抛给前端
            throw new RuntimeException(e);
        } finally {
            response.getWriter().close();
        }
    }

    /**
     * 处理模糊查询请求
     */
    private void searchTeacher(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, CustomerErrorMsgException {
        //获取前端页面传过来的参数
        String value = request.getParameter("select");
        String key = request.getParameter("key");
        String value0 = "all";

        //联表查询时间状态为0和1的，即未被预约的时间
        String sql="SELECT t.`id`,`name`,colloge,`time`\n" +
                "FROM `time` t\n" +
                "INNER JOIN `user` u\n" +
                "ON t.`user_id`=u.`id`\n" +
                "WHERE t.`t_status` IN (0,1)";
        List<Tutor> list01 = DBUtils.query(sql,Tutor.class);

        if (value.equals(value0)) {
            if (!list01.isEmpty()) {
                //放进session域中
                HttpSession session = request.getSession();
                session.setAttribute("list", list01);
                //重定向
                response.sendRedirect("/searchList.jsp");
            } else {
                throw new CustomerErrorMsgException("不存在该数据！请重新查询");
            }
        } else {

            /**
             * 调用业务层根据选择查询的方式和输入的关键字查询
             */
            List<Tutor> list = new ArrayList<Tutor>();
            list = userService.searchTeacher(list01, value, key);

            //测试在后台输出list
            list.forEach(System.out::println);

            if (!list.isEmpty()) {
                //放进session域中
                HttpSession session = request.getSession();
                session.setAttribute("list", list);
                //重定向
                response.sendRedirect("/searchList.jsp");
            } else {
                throw new CustomerErrorMsgException("不存在该数据！请重新查询");
            }
        }
    }

    /**
     * 处理预约请求
     */
    private void orderTime(HttpServletRequest request,HttpServletResponse response) throws IOException {
        try {
            int timeId = -1;
            //因为request.getParameter获取的数据为String类型，强制转换timeId为Integer型
            if (!"".equals(request.getParameter("timeId")) && request.getParameter("timeId") != null) {
                Integer id = Integer.parseInt(request.getParameter("timeId"));
                timeId = id;
            }

            String sid = request.getParameter("sid");
            boolean result=userService.orderTime(sid,timeId);

            //获取业务层返回的结果
            response.getWriter().print(result);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            response.getWriter().close();
        }
    }

    /**
     * 查看预约记录
     */
    private void sApointTime(HttpServletRequest request,HttpServletResponse response) throws IOException, CustomerErrorMsgException {
        String sid=request.getParameter("sid");

        List<Tutor> list=userService.apointTime(sid);

        if (!list.isEmpty()) {
            //放进session域中
            HttpSession session = request.getSession();
            session.setAttribute("list2", list);
            //重定向
            response.sendRedirect("/sApointTime.jsp");
        } else {
            throw new CustomerErrorMsgException("不存在该数据！请重新查询");
        }

    }

    /**
     * 导师审批
     */
    private void dealApoint(HttpServletRequest request,HttpServletResponse response){
        String timeId=request.getParameter("timeId");
        String resultId=request.getParameter("resultId");

        //将time表和result表的status值改为2，即表示该预约申请已通过
        String sql1="UPDATE `time` SET t_status=2 WHERE id=?";
        String sql2="UPDATE `result` SET stu_status=2 WHERE rid=?";

        DBUtils.update(sql1,timeId);
        DBUtils.update(sql2,resultId);
    }

    /**
     * 管理员删除用户
     */
    private void searchAllUser(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String sid=request.getParameter("sid");
        int id = -1;
        //因为request.getParameter获取的数据为String类型，强制转换timeId为Integer型
        if (!"".equals(request.getParameter("id")) && request.getParameter("id") != null) {
            Integer id2 = Integer.parseInt(request.getParameter("id"));
            id = id2;
        }

        boolean result=userService.searchAllUser(id,sid);
        response.getWriter().print(result);
        response.getWriter().close();
    }
}
