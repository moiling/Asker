package com.moinut.asker.model.bean;

public class ApiWrapper<T> {
    private int state;
    private String info;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getState() {
        return state;
    }

    public void setState(int status) {
        this.state = status;
    }

    @Override
    public String toString() {
        return "ApiWrapper{" +
                "data=" + data +
                ", status=" + state +
                ", info='" + info + '\'' +
                '}';
    }
}
