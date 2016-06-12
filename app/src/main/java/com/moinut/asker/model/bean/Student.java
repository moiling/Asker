package com.moinut.asker.model.bean;
/*
    "id": 3,
    "college": "CQUPT",
    "academy": "SW",
    "year": 2014,
    "major": "SW",
    "user": {
 */
public class Student {
    private int id;
    private String college;
    private String academy;
    private int year;
    private String major;
    private User user;

    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
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

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Student{" +
                "academy='" + academy + '\'' +
                ", id=" + id +
                ", college='" + college + '\'' +
                ", year=" + year +
                ", major='" + major + '\'' +
                ", user=" + user +
                '}';
    }
}
