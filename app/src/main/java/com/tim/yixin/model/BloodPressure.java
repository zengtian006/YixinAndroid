package com.tim.yixin.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Zeng on 2017/8/17.
 */

public class BloodPressure extends RealmObject {
    @PrimaryKey
    private String id;
    private Date createdAt = new Date();
    private double high;
    private double low;
    private double heart_rate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getHeart_rate() {
        return heart_rate;
    }

    public void setHeart_rate(double heart_rate) {
        this.heart_rate = heart_rate;
    }
}
