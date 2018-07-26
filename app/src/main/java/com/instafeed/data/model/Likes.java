package com.instafeed.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Likes implements Serializable {

    @SerializedName("count")
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
