package com.xueyun.www.entity;

/**
 * @author 86135
 * @data 2020/10/3
 */
public class Time {
    private int id;
    private int user_id;
    private String time;
    private int t_status;

    public Time() {
    }

    public Time(int id, int user_id, String time, int t_status) {
        this.id = id;
        this.user_id = user_id;
        this.time = time;
        this.t_status = t_status;
    }

    @Override
    public String toString() {
        return "Time{" +
                "id=" + id +
                ", user_id='" + user_id + '\'' +
                ", time='" + time + '\'' +
                ", t_status=" + t_status +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getT_status() {
        return t_status;
    }

    public void setT_status(int t_status) {
        this.t_status = t_status;
    }
}
