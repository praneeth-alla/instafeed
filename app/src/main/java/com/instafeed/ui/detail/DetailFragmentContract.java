package com.instafeed.ui.detail;

import com.instafeed.data.model.MediaFeed;

public interface DetailFragmentContract {

    interface View {
        void showToast(String message);

        void logoutUser();

        void setLikePhotoColor(MediaFeed mediaFeed);
    }

    interface Presenter {
        void postPhotoLike(MediaFeed mediaFeed);

        void postPhotoDisLike(MediaFeed mediaFeed);

        String getRelativeTime(long relativeTime);
    }
}
