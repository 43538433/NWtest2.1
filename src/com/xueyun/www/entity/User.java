package com.xueyun.www.entity;

/**
 * @author 86135
 */
public class User {
    private int id;
    private String name;
    private String sid;
    private String password;
    private String tel;
    private String colloge;
    private String grade;
    private int status;

    public User() {
    }

    public User(int id, String name, String sid, String password, String tel, String colloge, String grade, int status) {
        this.id = id;
        this.name = name;
        this.sid = sid;
        this.password = password;
        this.tel = tel;
        this.colloge = colloge;
        this.grade = grade;
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sid='" + sid + '\'' +
                ", password='" + password + '\'' +
                ", tel='" + tel + '\'' +
                ", colloge='" + colloge + '\'' +
                ", grade='" + grade + '\'' +
                ", status=" + status +
                '}';
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

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getColloge() {
        return colloge;
    }

    public void setColloge(String colloge) {
        this.colloge = colloge;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
