package com.instafeed.ui.home;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.instafeed.AccountPreferences;
import com.instafeed.Application;
import com.instafeed.R;
import com.instafeed.data.model.MediaFeed;
import com.instafeed.data.service.MediaRepositoryImpl;
import com.instafeed.ui.base.BaseFragment;
import com.instafeed.ui.detail.DetailFragment;
import com.instafeed.ui.login.LoginFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment implements HomeFragmentContract.View {

    @BindView(R.id.user_feed_recycler)
    RecyclerView userFeedRecyclerView;
    @BindView(R.id.loading_indicator)
    SwipeRefreshLayout loadingIndicator;

    private HomeRecyclerAdapter mAdapter;
    private HomeFragmentContract.Presenter mHomeFragmentPresenter;
    private Menu mMenuActionBar;

    private static final String MEDIA_FEED = "MediaFeed";

    @Inject
    AccountPreferences mAccountPreferences;

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
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
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.home_title));
        }

        ButterKnife.bind(this, view);

        mHomeFragmentPresenter = new HomeFragmentPresenter(this, new MediaRepositoryImpl(getActivity()), getActivity().getApplicationContext());
        setupRecyclerView();
    }

    private void setupRecyclerView() {

        mAdapter = new HomeRecyclerAdapter(new ArrayList<MediaFeed>(), getContext());

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        userFeedRecyclerView.setLayoutManager(layoutManager);
        userFeedRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new HomeRecyclerViewListener.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                mAdapter.getItem(position);

                DetailFragment detailFragment = new DetailFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(MEDIA_FEED, mAdapter.getItem(position));
                detailFragment.setArguments(bundle);
                showFeedDetail(bundle);
            }
        });

        mAdapter.setPhotoLikeDisLikeListener(new HomeRecyclerViewListener.OnPhotoLikeDislikeListener() {
            @Override
            public void onPhotoLikeDisLike(MediaFeed mediaFeed, int position) {
                if (mediaFeed.isPhotoLiked()) {
                    mHomeFragmentPresenter.postPhotoDisLike(mediaFeed);
                } else {
                    mHomeFragmentPresenter.postPhotoLike(mediaFeed);
                }
            }
        });

        mHomeFragmentPresenter.getMediaFeed();

        loadingIndicator.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHomeFragmentPresenter.getMediaFeed();
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((Application) getActivity().getApplication()).getNetComponent().inject(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
        mMenuActionBar = menu;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile_picture:
                mAccountPreferences.clear();

                Bundle bundle = new Bundle();
                LoginFragment loginFragment = new LoginFragment();
                bundle.putBoolean("isLogout", true);
                loginFragment.setArguments(bundle);

                mFragmentNavigationPresenter.addFragment(loginFragment, bundle);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateActionBarProfilePic(String profilePicUrl) {
        if (mMenuActionBar != null) {
            final MenuItem menuItem = mMenuActionBar.findItem(R.id.profile_picture);
            if (menuItem != null) {
                Glide.with(this)
                        .asDrawable()
                        .load(profilePicUrl)
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                                menuItem.setIcon(resource);
                            }
                        });
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void showMediaFeed(List<MediaFeed> mediaFeeds) {
        if (loadingIndicator.isRefreshing()) {
            stopLoadingIndicator();
        }
        mAdapter.replaceData(mediaFeeds);
    }

    @Override
    public void showToast(String message) {
        stopLoadingIndicator();
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProfilePicture(List<MediaFeed> mediaArrayList) {
        updateActionBarProfilePic(mediaArrayList.get(0).getUser().getProfilePicture());
    }

    @Override
    public void logoutUser() {
        mFragmentNavigationPresenter.addFragment(new LoginFragment(), null);
    }

    @Override
    public void setPhotoColor(MediaFeed mediaFeed) {
        if (mediaFeed.isPhotoLiked()) {
            mediaFeed.setPhotoLiked(false);
        } else {
            mediaFeed.setPhotoLiked(true);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void stopLoadingIndicator() {
        if (loadingIndicator.isRefreshing()) {
            loadingIndicator.setRefreshing(false);
        }
    }

    @Override
    public void showLoadingIndicator() {
        loadingIndicator.setRefreshing(true);
    }

    @Override
    public void showFeedDetail(Bundle args) {
        mFragmentNavigationPresenter.addFragment(new DetailFragment(), args);
    }
}
