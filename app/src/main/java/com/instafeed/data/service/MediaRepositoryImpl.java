package com.instafeed.data.service;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.instafeed.AccountPreferences;
import com.instafeed.Application;
import com.instafeed.data.api.MediaNetworkService;
import com.instafeed.data.api.MediaRepository;
import com.instafeed.data.model.Data;
import com.instafeed.data.model.MediaFeed;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MediaRepositoryImpl implements MediaRepository.GetUserFeedInteractor {

    @Inject
    MediaNetworkService mMediaNetworkService;

    @Inject
    AccountPreferences mAccountPreferences;

    public MediaRepositoryImpl(Activity context) {
        ((Application) context.getApplication()).getNetComponent().inject(this);
    }

    @Override
    public void postMediaDislike(final OnMediaDisLikedListener onFinishedListener, final MediaFeed mediaFeed) {

        String idToken = mAccountPreferences.getStringValue(AccountPreferences.ID_TOKEN, null);

        Call<Void> dislikeCall = mMediaNetworkService.postDisLike(mediaFeed.getMediaId(), idToken);
        dislikeCall.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    onFinishedListener.onMediaDisLikedFinished(mediaFeed);
                } else {
                    onFinishedListener.sessionExpired();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                call.cancel();
                onFinishedListener.onMediaDisLikedFailure(t);
            }
        });
    }

    @Override
    public void postMediaLike(final OnMediaLikedListener onMediaLikedListener, final MediaFeed mediaFeed) {

        String idToken = mAccountPreferences.getStringValue(AccountPreferences.ID_TOKEN, null);

        Call<Void> likeCall = mMediaNetworkService.postLike(mediaFeed.getMediaId(), idToken);
        likeCall.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    onMediaLikedListener.onMediaLikedFinished(mediaFeed);
                } else {
                    onMediaLikedListener.sessionExpired();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                call.cancel();
                onMediaLikedListener.onMediaLikedFailure(t);
            }
        });
    }

    @Override
    public void getUserFeedList(final OnGetFeedFinishedListener onGetFeedFinishedListener) {

        String idToken = mAccountPreferences.getStringValue(AccountPreferences.ID_TOKEN, null);

        Call<Data> userFeedCall = mMediaNetworkService.getMedia((idToken));
        userFeedCall.enqueue(new Callback<Data>() {

            @Override
            public void onResponse(@NonNull Call<Data> call, @NonNull Response<Data> response) {
                if (response.isSuccessful()) {
                    List<MediaFeed> mediaFeedList = response.body().getData();
                    onGetFeedFinishedListener.onGetFeedFinished(mediaFeedList);
                } else {
                    onGetFeedFinishedListener.sessionExpired();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data> call, @NonNull Throwable t) {
                call.cancel();
                onGetFeedFinishedListener.onGetFeedFailure(t);
            }
        });
    }
}
