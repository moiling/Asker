package com.moinut.asker.model.bean;

/*
    "id": 1,
    "college": "CQUPT",
    "academy": "SW",
    "realName": "AA",
    "user": {
 */
public class Teacher {
    private int id;
    private String college;
    private String academy;
    private String realName;
    private boolean authentication;
    private User user;

    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }

    public boolean isAuthentication() {
        return authentication;
    }

    public void setAuthentication(boolean authentication) {
        this.authentication = authentication;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "academy='" + academy + '\'' +
                ", id=" + id +
                ", college='" + college + '\'' +
                ", realName='" + realName + '\'' +
                ", authentication=" + authentication +
                ", user=" + user +
                '}';
    }
}
