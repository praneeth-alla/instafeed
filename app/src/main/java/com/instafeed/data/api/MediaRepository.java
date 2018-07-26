package com.instafeed.data.api;

import com.instafeed.data.model.MediaFeed;

import java.util.List;

public interface MediaRepository {

    interface GetUserFeedInteractor {

        interface OnGetFeedFinishedListener {
            void onGetFeedFinished(List<MediaFeed> mediaFeedList);

            void onGetFeedFailure(Throwable t);

            void sessionExpired();
        }

        interface OnMediaLikedListener {
            void onMediaLikedFinished(MediaFeed mediaFeed);

            void onMediaLikedFailure(Throwable t);

            void sessionExpired();
        }

        interface OnMediaDisLikedListener {
            void onMediaDisLikedFinished(MediaFeed mediaFeed);

            void onMediaDisLikedFailure(Throwable t);

            void sessionExpired();
        }

        void postMediaDislike(OnMediaDisLikedListener onFinishedListener, MediaFeed mediaFeed);

        void postMediaLike(OnMediaLikedListener onMediaLikedListener, MediaFeed mediaFeed);

        void getUserFeedList(OnGetFeedFinishedListener onGetFeedFinishedListener);
    }
}
