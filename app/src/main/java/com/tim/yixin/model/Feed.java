package com.tim.yixin.model;

/**
 * Created by Zeng on 2017/8/14.
 */

public class Feed {
    public Feed(String alias, String title, int image) {
        this.alias = alias;
        this.title = title;
        this.image = image;
    }

    private String alias;
    private String title;
    private int image;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
