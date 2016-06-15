package com.moinut.asker.model.bean;

/*
 "id": 5,
 "type": "student",
 "nickName": "MOILING",
 "date": "2016-06-01 17:47:21",
 "sex": "male",
 "tel": "110",
 "email": "super8moi@gmail.com",
 "token": "ca06eeddb640d398059eb9d827d11ca5fdcabe54"
 */

import com.moinut.asker.utils.TimeUtils;

public class User implements Cloneable {
    private int id;
    private String type;
    private String nickName;
    private String date;
    private String sex;
    private String tel;
    private String email;
    private String token;

    public User() {
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getDate() {
        return date;
    }

    public long getDateTimeStamp() {
        return TimeUtils.strToDate(date).getTime();
    }

    public String getDateFormat() {
        return TimeUtils.convertTimeToFormat(TimeUtils.strToDate(date));
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "User{" +
                "date=" + date +
                ", id=" + id +
                ", type='" + type + '\'' +
                ", nickName='" + nickName + '\'' +
                ", sex='" + sex + '\'' +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        // if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (getId() != user.getId()) return false;
        if (getType() != null ? !getType().equals(user.getType()) : user.getType() != null)
            return false;
        if (getNickName() != null ? !getNickName().equals(user.getNickName()) : user.getNickName() != null)
            return false;
        if (getDate() != null ? !getDate().equals(user.getDate()) : user.getDate() != null)
            return false;
        if (getSex() != null ? !getSex().equals(user.getSex()) : user.getSex() != null)
            return false;
        if (getTel() != null ? !getTel().equals(user.getTel()) : user.getTel() != null)
            return false;
        if (getEmail() != null ? !getEmail().equals(user.getEmail()) : user.getEmail() != null)
            return false;
        return getToken() != null ? getToken().equals(user.getToken()) : user.getToken() == null;

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        result = 31 * result + (getNickName() != null ? getNickName().hashCode() : 0);
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        result = 31 * result + (getSex() != null ? getSex().hashCode() : 0);
        result = 31 * result + (getTel() != null ? getTel().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getToken() != null ? getToken().hashCode() : 0);
        return result;
    }
}
