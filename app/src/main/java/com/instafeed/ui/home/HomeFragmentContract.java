package com.instafeed.ui.home;

import android.os.Bundle;

import com.instafeed.data.model.MediaFeed;

import java.util.List;

public interface HomeFragmentContract {

    interface View {
        void showFeedDetail(Bundle bundle);

        void stopLoadingIndicator();

        void showLoadingIndicator();

        void showMediaFeed(List<MediaFeed> mediaFeeds);

        void showToast(String message);

        void showProfilePicture(List<MediaFeed> mediaArrayList);

        void logoutUser();

        void setPhotoColor(MediaFeed mediaFeed);
    }

    interface Presenter {
        void getMediaFeed();

        void postPhotoLike(MediaFeed mediaFeed);

        void postPhotoDisLike(MediaFeed mediaFeed);
    }
}
