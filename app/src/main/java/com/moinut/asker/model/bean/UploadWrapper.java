package com.moinut.asker.model.bean;

public class UploadWrapper {
    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "UploadWrapper{" +
                "info='" + info + '\'' +
                '}';
    }
}
