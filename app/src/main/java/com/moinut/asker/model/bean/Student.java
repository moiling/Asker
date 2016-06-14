package com.moinut.asker.model.bean;

public class Student extends User {
    private int studentId;
    private String college;
    private String academy;
    private int year;
    private String major;

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

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
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
                ", studentId=" + studentId +
                ", college='" + college + '\'' +
                ", year=" + year +
                ", major='" + major + '\'' +
                "} " + super.toString();
    }
}
