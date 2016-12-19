package com.moinut.asker.model.bean;

public class UploadWrapper {
    private int code;
    private String info;
    private String url;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "UploadWrapper{" +
                "code=" + code +
                ", info='" + info + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
