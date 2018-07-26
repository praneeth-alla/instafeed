package com.instafeed.ui.detail;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.instafeed.Application;
import com.instafeed.R;
import com.instafeed.data.model.MediaFeed;
import com.instafeed.data.service.MediaRepositoryImpl;
import com.instafeed.ui.base.BaseFragment;
import com.instafeed.ui.home.HomeFragment;
import com.instafeed.ui.login.LoginFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailFragment extends BaseFragment implements DetailFragmentContract.View {

    @BindView(R.id.detail_date)
    TextView detailDate;
    @BindView(R.id.detail_image)
    ImageView detailImage;
    @BindView(R.id.detail_like_text)
    TextView detailLikeText;
    @BindView((R.id.detail_like_image))
    ImageView detailLikeImage;

    private DetailFragmentContract.Presenter mDetailFragmentPresenter;

    private static final String MEDIA_FEED = "MediaFeed";

    @Override
    protected int getLayout() {
        return R.layout.fragment_detail;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.detail_title));
        }

        Bundle bundle = getArguments();
        final MediaFeed mediaFeed = (MediaFeed) bundle.getSerializable(MEDIA_FEED);

        ButterKnife.bind(this, view);

        mDetailFragmentPresenter = new DetailFragmentPresenter(this, new MediaRepositoryImpl(getActivity()), getContext());
        String relativeTime = mDetailFragmentPresenter.getRelativeTime(Long.parseLong(mediaFeed.getCreatedTime()));

        detailDate.setText(String.valueOf(relativeTime));
        detailLikeText.setText(String.valueOf(mediaFeed.getLikes().getCount() + getResources().getString(R.string.likes)));

        Glide.with(detailImage).load(mediaFeed.getImage().getStandardImage().getUrl()).into(detailImage);

        if (mediaFeed.isPhotoLiked()) {
            detailLikeImage.setColorFilter(getResources().getColor(R.color.orange), PorterDuff.Mode.SRC_IN);
        } else {
            detailLikeImage.setColorFilter(getResources().getColor(R.color.cardview_dark_background), PorterDuff.Mode.SRC_IN);
        }

        detailLikeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaFeed.isPhotoLiked()) {
                    mDetailFragmentPresenter.postPhotoDisLike(mediaFeed);
                } else {
                    mDetailFragmentPresenter.postPhotoLike(mediaFeed);
                }
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((Application) getActivity().getApplication()).getNetComponent().inject(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mFragmentNavigationPresenter.addFragment(new HomeFragment(), null);
                return true;
        }
        return false;
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void logoutUser() {
        mFragmentNavigationPresenter.addFragment(new LoginFragment(), null);
    }

    @Override
    public void setLikePhotoColor(MediaFeed mediaFeed) {
        if (mediaFeed.isPhotoLiked()) {
            detailLikeImage.setColorFilter(getResources().getColor(R.color.cardview_dark_background), PorterDuff.Mode.SRC_IN);
            mediaFeed.setPhotoLiked(false);
        } else {
            detailLikeImage.setColorFilter(getResources().getColor(R.color.orange), PorterDuff.Mode.SRC_IN);
            mediaFeed.setPhotoLiked(true);
        }
    }
}
