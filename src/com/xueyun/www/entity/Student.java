package com.xueyun.www.entity;

/**
 * @author 86135
 */
public class Student {
    private String name;
    private String user_sid;
    private String colloge;
    private String time;
    private int t_status;
    private int id;
    private int rid;


    public Student() {
    }

    public Student(String name, String user_sid, String colloge, String time, int t_status, int id, int rid) {
        this.name = name;
        this.user_sid = user_sid;
        this.colloge = colloge;
        this.time = time;
        this.t_status = t_status;
        this.id = id;
        this.rid = rid;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", user_sid='" + user_sid + '\'' +
                ", colloge='" + colloge + '\'' +
                ", time='" + time + '\'' +
                ", t_status=" + t_status +
                ", id=" + id +
                ", rid=" + rid +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_sid() {
        return user_sid;
    }

    public void setUser_sid(String user_sid) {
        this.user_sid = user_sid;
    }

    public String getColloge() {
        return colloge;
    }

    public void setColloge(String colloge) {
        this.colloge = colloge;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }
}
