package com.tim.yixin.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Zeng on 2017/8/17.
 */

public class BloodSugar extends RealmObject {
    @PrimaryKey
    private String id;
    private Date createdAt = new Date();
    private double sugar;
    private String type;//fbs 空腹;bm 饭前;am1 饭后1小时;am2;am3;others

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

    public double getSugar() {
        return sugar;
    }

    public void setSugar(double sugar) {
        this.sugar = sugar;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
