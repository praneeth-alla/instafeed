package com.instafeed.ui.detail;

import android.content.Context;

import com.instafeed.R;
import com.instafeed.data.api.MediaRepository;
import com.instafeed.data.model.MediaFeed;

public class DetailFragmentPresenter implements DetailFragmentContract.Presenter,
        MediaRepository.GetUserFeedInteractor.OnMediaLikedListener,
        MediaRepository.GetUserFeedInteractor.OnMediaDisLikedListener {

    private DetailFragmentContract.View detailFragmentView;
    private MediaRepository.GetUserFeedInteractor mUserFeedInteractor;

    private Context mContext;

    DetailFragmentPresenter(DetailFragmentContract.View detailFragmentView, MediaRepository.GetUserFeedInteractor userFeedInteractor, Context context) {
        this.detailFragmentView = detailFragmentView;
        this.mUserFeedInteractor = userFeedInteractor;
        mContext = context;
    }

    @Override
    public void postPhotoLike(MediaFeed mediaFeed) {
        mUserFeedInteractor.postMediaLike(this, mediaFeed);
    }

    @Override
    public void postPhotoDisLike(MediaFeed mediaFeed) {
        mUserFeedInteractor.postMediaDislike(this, mediaFeed);
    }

    @Override
    public void onMediaLikedFinished(MediaFeed mediaFeed) {
        detailFragmentView.setLikePhotoColor(mediaFeed);
    }

    @Override
    public void onMediaLikedFailure(Throwable t) {
        detailFragmentView.showToast(mContext.getResources().getString(R.string.like_failed));
    }

    @Override
    public void sessionExpired() {
        detailFragmentView.logoutUser();
    }

    @Override
    public void onMediaDisLikedFinished(MediaFeed mediaFeed) {
        detailFragmentView.setLikePhotoColor(mediaFeed);
    }

    @Override
    public void onMediaDisLikedFailure(Throwable t) {
        detailFragmentView.showToast(mContext.getResources().getString(R.string.dislike_failed));
    }

    @Override
    public String getRelativeTime(long relativeTime) {
        Long currentTime = System.currentTimeMillis();

        Long seconds = (currentTime / 1000) - relativeTime;
        Long minutes = seconds / 60;
        Long hours = minutes / 60;
        Long days = hours / 24;
        Long weeks = days / 7;

        StringBuilder relativeTimeBuilder = new StringBuilder();

        if (seconds < 60) {
            relativeTimeBuilder.append(seconds.toString());
            relativeTimeBuilder.append("s");
        } else if (minutes > 1 && minutes < 60) {
            relativeTimeBuilder.append(minutes.toString());
            relativeTimeBuilder.append("m");
        } else if (hours > 1 && hours < 24) {
            relativeTimeBuilder.append(hours.toString());
            relativeTimeBuilder.append("h");
        } else if (days > 1 && days < 7) {
            relativeTimeBuilder.append(days.toString());
            relativeTimeBuilder.append("d");
        } else if (days > 7) {
            relativeTimeBuilder.append(weeks.toString());
            relativeTimeBuilder.append("w");
        }
        relativeTimeBuilder.append(mContext.getResources().getString(R.string.ago));
        return relativeTimeBuilder.toString();
    }
}
