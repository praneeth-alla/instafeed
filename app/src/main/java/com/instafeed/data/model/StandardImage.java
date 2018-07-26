package com.instafeed.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StandardImage implements Serializable {

    @SerializedName("url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
