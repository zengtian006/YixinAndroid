package com.tim.yixin.model;

/**
 * Created by Zeng on 2017/8/17.
 */

public class Health {
    private String data;
    private String time;

    public Health(String data, String time) {
        this.data = data;
        this.time = time;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
