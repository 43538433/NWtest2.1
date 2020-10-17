package com.xueyun.www.entity;

/**
 * @author 86135
 */
public class Tutor {
    private int id;
    private String sid;
    private String grade;
    private String name;
    private String colloge;
    private String time;
    private String status;
    private int stu_status;

    public Tutor() {
    }

    public Tutor(String name, String colloge, String time, int stu_status) {
        this.name = name;
        this.colloge = colloge;
        this.time = time;
        this.stu_status = stu_status;
    }

    public Tutor(String name, String colloge, String time, String status) {
        this.name = name;
        this.colloge = colloge;
        this.time = time;
        this.status = status;
    }

    public String getSid() {
        return sid;
    }

    public Tutor(String sid, String grade, String name, String time, int stu_status) {
        this.sid = sid;
        this.grade = grade;
        this.name = name;
        this.time = time;
        this.stu_status = stu_status;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Tutor(int id, String name, String colloge, String time) {
        this.id = id;
        this.name = name;
        this.colloge = colloge;
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Tutor{" +
                "id=" + id +
                ", sid='" + sid + '\'' +
                ", grade='" + grade + '\'' +
                ", name='" + name + '\'' +
                ", colloge='" + colloge + '\'' +
                ", time='" + time + '\'' +
                ", status='" + status + '\'' +
                ", stu_status=" + stu_status +
                '}';
    }

    public int getStu_status() {
        return stu_status;
    }

    public void setStu_status(int stu_status) {
        this.stu_status = stu_status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
