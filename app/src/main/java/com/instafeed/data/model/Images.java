package com.instafeed.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Images implements Serializable {

    @SerializedName("standard_resolution")
    private StandardImage standardImage;

    public StandardImage getStandardImage() {
        return standardImage;
    }

    public void setStandardImage(StandardImage standardImage) {
        this.standardImage = standardImage;
    }
}
