package com.instafeed.ui.home;

import android.view.View;

import com.instafeed.data.model.MediaFeed;

public interface HomeRecyclerViewListener {

    @FunctionalInterface
    interface OnItemClickListener {
        void OnItemClick(View view, int position);
    }

    interface OnPhotoLikeDislikeListener {
        void onPhotoLikeDisLike(MediaFeed mediaFeed, int position);
    }
}
