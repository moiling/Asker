package com.moinut.asker.model.bean;

public class Teacher extends User {
    private int teacherId;
    private String college;
    private String academy;
    private String realName;
    private boolean authentication;

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

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "academy='" + academy + '\'' +
                ", teacherId=" + teacherId +
                ", college='" + college + '\'' +
                ", realName='" + realName + '\'' +
                ", authentication=" + authentication +
                "} " + super.toString();
    }
}
