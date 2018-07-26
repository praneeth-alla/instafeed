package com.instafeed.ui.login;

public class LoginFragmentPresenter implements LoginFragmentContract.Presenter {
    private LoginFragmentContract.View view;

    LoginFragmentPresenter(LoginFragmentContract.View view) {
        this.view = view;
    }

    @Override
    public void showWebView() {
        view.showWebViewUI();
    }
}