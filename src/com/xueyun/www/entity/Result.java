package com.xueyun.www.entity;

/**
 * @author 86135
 */
public class Result {
    private int rid;
    private String user_sid;
    private int time_id;
    private int stu_status;

    public Result() {
    }

    public Result(int rid, String user_sid, int time_id, int stu_status) {
        this.rid = rid;
        this.user_sid = user_sid;
        this.time_id = time_id;
        this.stu_status = stu_status;
    }

    @Override
    public String toString() {
        return "Result{" +
                "rid=" + rid +
                ", user_sid='" + user_sid + '\'' +
                ", time_id=" + time_id +
                ", stu_status=" + stu_status +
                '}';
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getUser_sid() {
        return user_sid;
    }

    public void setUser_sid(String user_sid) {
        this.user_sid = user_sid;
    }

    public int getTime_id() {
        return time_id;
    }

    public void setTime_id(int time_id) {
        this.time_id = time_id;
    }

    public int getStu_status() {
        return stu_status;
    }

    public void setStu_status(int stu_status) {
        this.stu_status = stu_status;
    }
}
