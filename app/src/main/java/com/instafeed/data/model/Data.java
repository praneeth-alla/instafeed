package com.instafeed.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {

    @SerializedName("data")
    private List<MediaFeed> data;

    public List<MediaFeed> getData() {
        return data;
    }

    public void setData(List<MediaFeed> data) {
        this.data = data;
    }
}
