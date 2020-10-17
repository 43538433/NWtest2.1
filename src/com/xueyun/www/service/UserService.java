package com.xueyun.www.service;

import com.xueyun.www.entity.Result;
import com.xueyun.www.entity.Time;
import com.xueyun.www.entity.Tutor;
import com.xueyun.www.entity.User;
import com.xueyun.www.exception.CustomerErrorMsgException;
import com.xueyun.www.util.DBUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * @author 86135
 */
public class UserService {

    /**
     * 实现登录方法
     */
    public User login(String name, String sid, String password) throws CustomerErrorMsgException {

        String sql = "SELECT * FROM user WHERE sid=?";
        List<User> list = DBUtils.query(sql, User.class, sid);
        User loginUser = list.get(0);

        //如果查到数据有该用户，则比对密码
        if (loginUser != null) {
            if (loginUser.getSid().equals(sid) && loginUser.getName().equals(name) && loginUser.getPassword().equals(password)) {
                return loginUser;
            } else {
                throw new CustomerErrorMsgException("用户名或密码错误！");
            }
        } else {
            throw new CustomerErrorMsgException("该用户不存在！");
        }
    }

    /**
     * 添加时间
     */
    public boolean time(Time time) {
        //直接向数据库的time表中插入数据
        String sql = "INSERT INTO time VALUES (NULL,?,?,0)";
        int user_id = time.getUser_id();
        String time01 = time.getTime();

        DBUtils.update(sql, user_id, time01);
        return true;
    }

    /**
     * 模糊导师查询
     */
    public List<Tutor> searchTeacher(List<Tutor> list, String value, String key) {

        String value1 = "name";
        String value2 = "colloge";
        String value3 = "time";

        //按姓名查询，依次比对所有导师时间集合list，将名字不为关键值（key）的移除
        if (value.equals(value1)) {
            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).getName().equals(key)) {
                    list.remove(i);
                    i--;
                }
            }
        }
        //按学院查询，依次比对所有导师时间集合list，将学院不为关键值（key）的移除
        if (value.equals(value2)) {
            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).getColloge().equals(key)) {
                    list.remove(i);
                    i--;

                }
            }
        }
        //按时间查询，依次比对所有导师时间集合list，将时间不为关键值（key）的移除
        if (value.equals(value3)) {
            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).getTime().equals(key)) {
                    list.remove(i);
                    i--;
                }
            }
        }
        return list;

    }

    /**
     * 处理预约请求
     */
    public boolean orderTime(String userId, int timeId) throws CustomerErrorMsgException {

        //在数据库的result查看该学生的预约情况stu_status
        String sql0 = "SELECT * FROM result WHERE user_sid=?";
        List<Result> list = DBUtils.query(sql0, Result.class, userId);

        boolean listEmpty = list.isEmpty();

        //状态1，已有预约，但老师未审批
        if (!listEmpty && (list.get(0).getStu_status() == 1)) {
            throw new CustomerErrorMsgException("您已有预约申请，待老师审核后才可继续预约！");
            //其他状态均可预约
        } else {
            //可以预约，将预约结果插入result表
            String sql1 = "INSERT INTO result VALUES (NULL,?,?,1)";
            DBUtils.update(sql1, userId, timeId);
            return true;
        }
    }

    /**
     * 查看预约记录
     */
    public List<Tutor> apointTime(String sid) {
        //三张表联表查询，用该学生的sid去查找对应的预约信息
        String sql = "SELECT u.`name`,u.colloge,t.time,r.stu_status\n" +
                "FROM `result` r\n" +
                "INNER JOIN `time` t\n" +
                "ON r.`time_id`=t.`id`\n" +
                "INNER JOIN `user` u\n" +
                "ON t.`user_id`=u.`id`\n" +
                "WHERE r.`user_sid`=?";

        //通过query函数获得该学生的所有预约信息
        List<Tutor> list = DBUtils.query(sql, Tutor.class, sid);
        //初始化一个集合，用于返回集合
        List<Tutor> list2 = new ArrayList<Tutor>();

        //需要将int型的stu_status转换为String类型的status，方便在前端输出
        for (Tutor tutor : list) {
            String name = tutor.getName();
            String colloge = tutor.getColloge();
            String time = tutor.getTime();
            int status0 = tutor.getStu_status();

            String status = "审批中";
            switch (status0) {
                case 0:
                    status = "审批未通过";
                    break;
                case 1:
                    status = "待审批";
                    break;
                case 2:
                    status = "审批已通过";
                    break;
                default:
                    status = "状态出错";
            }

            //将老师的信息再次封装到tutor实体类中
            Tutor tutor2 = new Tutor(name, colloge, time, status);
            list2.add(tutor2);
        }
        //测试在后台输出list2
        list2.forEach(System.out::println);
        return list2;
    }

    /**
     * 管理员删除用户
     */
    public boolean searchAllUser(int id, String sid) {
        String sql1 = "DELETE FROM `user` WHERE id=?";
        String sql2 = "DELETE FROM `time` WHERE user_id=?";
        String sql3 = "DELETE FROM `result` WHERE user_sid=?";

        //删除user表和time表里的用户
        DBUtils.update(sql1, id);
        DBUtils.update(sql2, id);
        DBUtils.update(sql3,sid);

        return true;
    }
}
