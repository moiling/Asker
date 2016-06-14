package com.moinut.asker.model.bean;

public class Student extends User implements Cloneable {
    private int studentId;
    private String college;
    private String academy;
    private int year;
    private String major;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

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

    @Override
    public boolean equals(Object o) {
        // if (this == o) return true;
        if (!(o instanceof Student)) return false;
        if (!super.equals(o)) return false;

        Student student = (Student) o;

        if (getStudentId() != student.getStudentId()) return false;
        if (getYear() != student.getYear()) return false;
        if (getCollege() != null ? !getCollege().equals(student.getCollege()) : student.getCollege() != null)
            return false;
        if (getAcademy() != null ? !getAcademy().equals(student.getAcademy()) : student.getAcademy() != null)
            return false;
        return getMajor() != null ? getMajor().equals(student.getMajor()) : student.getMajor() == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getStudentId();
        result = 31 * result + (getCollege() != null ? getCollege().hashCode() : 0);
        result = 31 * result + (getAcademy() != null ? getAcademy().hashCode() : 0);
        result = 31 * result + getYear();
        result = 31 * result + (getMajor() != null ? getMajor().hashCode() : 0);
        return result;
    }
}
