package com.instafeed.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MediaFeed implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("media_id")
    private String mediaId;

    @SerializedName("created_time")
    private String createdTime;

    @SerializedName("likes")
    private Likes likes;

    @SerializedName("user")
    private User user;

    @SerializedName("images")
    private Images image;

    @SerializedName("user_has_liked")
    private boolean isPhotoLiked;

    public boolean isPhotoLiked() {
        return isPhotoLiked;
    }

    public void setPhotoLiked(boolean photoLiked) {
        isPhotoLiked = photoLiked;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public Likes getLikes() {
        return likes;
    }

    public void setLikes(Likes likes) {
        this.likes = likes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Images getImage() {
        return image;
    }

    public void setImage(Images image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
}
