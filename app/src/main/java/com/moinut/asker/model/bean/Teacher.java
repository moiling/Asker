package com.moinut.asker.model.bean;

public class Teacher extends User implements Cloneable {
    private int teacherId;
    private String college;
    private String academy;
    private String realName;
    private boolean authentication;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void setUser(User user) {
        setDate(user.getDate());
        setEmail(user.getEmail());
        setId(user.getId());
        setNickName(user.getNickName());
        setSex(user.getSex());
        setTel(user.getTel());
        setToken(user.getToken());
        setType(user.getType());
    }

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

    @Override
    public boolean equals(Object o) {
        // if (this == o) return true;
        if (!(o instanceof Teacher)) return false;
        if (!super.equals(o)) return false;

        Teacher teacher = (Teacher) o;

        if (getTeacherId() != teacher.getTeacherId()) return false;
        if (isAuthentication() != teacher.isAuthentication()) return false;
        if (getCollege() != null ? !getCollege().equals(teacher.getCollege()) : teacher.getCollege() != null)
            return false;
        if (getAcademy() != null ? !getAcademy().equals(teacher.getAcademy()) : teacher.getAcademy() != null)
            return false;
        return getRealName() != null ? getRealName().equals(teacher.getRealName()) : teacher.getRealName() == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getTeacherId();
        result = 31 * result + (getCollege() != null ? getCollege().hashCode() : 0);
        result = 31 * result + (getAcademy() != null ? getAcademy().hashCode() : 0);
        result = 31 * result + (getRealName() != null ? getRealName().hashCode() : 0);
        result = 31 * result + (isAuthentication() ? 1 : 0);
        return result;
    }
}
