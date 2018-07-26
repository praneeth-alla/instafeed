package com.instafeed.ui.home;

import android.content.Context;

import com.instafeed.R;
import com.instafeed.data.api.MediaRepository;
import com.instafeed.data.model.MediaFeed;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomeFragmentPresenter implements HomeFragmentContract.Presenter,
        MediaRepository.GetUserFeedInteractor.OnGetFeedFinishedListener,
        MediaRepository.GetUserFeedInteractor.OnMediaLikedListener,
        MediaRepository.GetUserFeedInteractor.OnMediaDisLikedListener {

    private HomeFragmentContract.View mHomeFragmentView;

    private MediaRepository.GetUserFeedInteractor mUserFeedInteractor;

    private Context mContext;

    public HomeFragmentPresenter(HomeFragmentContract.View mHomeFragmentView, MediaRepository.GetUserFeedInteractor userFeedInteractor, Context context) {
        this.mHomeFragmentView = mHomeFragmentView;
        this.mUserFeedInteractor = userFeedInteractor;
        mContext = context;
        mHomeFragmentView.showLoadingIndicator();
    }

    @Override
    public void onGetFeedFinished(List<MediaFeed> mediaArrayList) {
        List<MediaFeed> sortedMediaFeeds = sortListByDate(mediaArrayList);
        mHomeFragmentView.showMediaFeed(sortedMediaFeeds);
        mHomeFragmentView.showProfilePicture(mediaArrayList);
    }

    @Override
    public void onMediaLikedFailure(Throwable t) {
        mHomeFragmentView.showToast(mContext.getResources().getString(R.string.like_failed));
    }

    @Override
    public void onGetFeedFailure(Throwable t) {
        mHomeFragmentView.showToast(mContext.getResources().getString(R.string.get_media_failed));
    }

    @Override
    public void sessionExpired() {
        mHomeFragmentView.logoutUser();
    }

    @Override
    public void getMediaFeed() {
        mUserFeedInteractor.getUserFeedList(this);
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
        mHomeFragmentView.setPhotoColor(mediaFeed);
    }

    @Override
    public void onMediaDisLikedFinished(MediaFeed mediaFeed) {
        mHomeFragmentView.setPhotoColor(mediaFeed);
    }

    @Override
    public void onMediaDisLikedFailure(Throwable t) {
        mHomeFragmentView.showToast(mContext.getResources().getString(R.string.dislike_failed));
    }

    private List<MediaFeed> sortListByDate(List<MediaFeed> mediaFeeds) {

        Collections.sort(mediaFeeds, new Comparator<MediaFeed>() {
            public int compare(MediaFeed obj1, MediaFeed obj2) {
                return Integer.valueOf(obj2.getCreatedTime()).compareTo(Integer.valueOf(obj1.getCreatedTime()));
            }
        });

        return mediaFeeds;
    }
}
