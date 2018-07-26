package com.instafeed.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.instafeed.Application;

public abstract class BaseFragment extends Fragment implements FragmentNavigation.View {

    protected View mRootView;
    protected FragmentNavigation.Presenter mFragmentNavigationPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        mRootView = inflater.inflate(getLayout(), container, false);
        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((Application) getActivity().getApplication()).getNetComponent().inject(this);
    }

    protected abstract int getLayout();

    @Override
    public void attachPresenter(FragmentNavigation.Presenter presenter) {
        mFragmentNavigationPresenter = presenter;
    }

}
