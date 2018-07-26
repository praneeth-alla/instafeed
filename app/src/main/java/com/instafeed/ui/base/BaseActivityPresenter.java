package com.instafeed.ui.base;

import android.os.Bundle;

public class BaseActivityPresenter implements
        BaseActivityContract.Presenter,
        FragmentNavigation.Presenter {

    private BaseActivityContract.View mView;

    public BaseActivityPresenter(BaseActivityContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void addFragment(BaseFragment fragment, Bundle bundle) {
        mView.setFragment(fragment, bundle);
    }
}
