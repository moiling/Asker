package com.moinut.asker.model.bean;

public class StarInfo {
    private String type;
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "StarInfo{" +
                "count=" + count +
                ", type='" + type + '\'' +
                '}';
    }
}
