package com.instafeed.ui.home;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.instafeed.R;
import com.instafeed.data.model.MediaFeed;

import java.security.InvalidParameterException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder> {

    private List<MediaFeed> mMediaFeeds;

    private HomeRecyclerViewListener.OnItemClickListener mItemClickListener;
    private HomeRecyclerViewListener.OnPhotoLikeDislikeListener mPhotoLikeListener;

    private Context mContext;

    public HomeRecyclerAdapter(@NonNull List<MediaFeed> mMediaFeeds, Context context) {
        this.mMediaFeeds = mMediaFeeds;
        mContext = context;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.feed_item, viewGroup, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder viewHolder, final int position) {
        MediaFeed mediaFeed = mMediaFeeds.get(position);
        viewHolder.bind(mediaFeed, position);
    }

    @Override
    public int getItemCount() {
        return mMediaFeeds.size();
    }

    public void replaceData(List<MediaFeed> mediaFeeds) {
        this.mMediaFeeds.clear();
        this.mMediaFeeds.addAll(mediaFeeds);
        notifyDataSetChanged();
    }

    public MediaFeed getItem(int position) {
        if (position < 0 || position >= mMediaFeeds.size()) {
            throw new InvalidParameterException("Invalid item index");
        }
        return mMediaFeeds.get(position);
    }

    public void setOnItemClickListener(
            @NonNull HomeRecyclerViewListener.OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public void setPhotoLikeDisLikeListener(@NonNull HomeRecyclerViewListener.OnPhotoLikeDislikeListener photoLikeListener) {
        this.mPhotoLikeListener = photoLikeListener;
    }

    class HomeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.like_count)
        TextView likeCount;
        @BindView(R.id.feed_image)
        ImageView feedImage;
        @BindView(R.id.like_image)
        ImageView likeImage;

        private HomeViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void bind(final MediaFeed mediaFeed, final int position) {
            likeCount.setText(String.valueOf(mediaFeed.getLikes().getCount() + mContext.getResources().getString(R.string.likes)));
            Glide.with(feedImage).load(mediaFeed.getImage().getStandardImage().getUrl()).into(feedImage);

            if (mediaFeed.isPhotoLiked()) {
                likeImage.setColorFilter(itemView.getResources().getColor(R.color.orange), PorterDuff.Mode.SRC_IN);
            } else {
                likeImage.setColorFilter(itemView.getResources().getColor(R.color.cardview_dark_background), PorterDuff.Mode.SRC_IN);
            }

            if (mItemClickListener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemClickListener.OnItemClick(v, position);
                    }
                });
            }

            if (mPhotoLikeListener != null) {
                likeImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPhotoLikeListener.onPhotoLikeDisLike(mediaFeed, position);
                    }
                });
            }
        }
    }
}
