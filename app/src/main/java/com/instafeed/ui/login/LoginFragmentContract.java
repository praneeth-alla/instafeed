package com.instafeed.ui.login;

public interface LoginFragmentContract {

    interface View {
        void showWebViewUI();
    }

    interface Presenter {
        void showWebView();
    }
}
