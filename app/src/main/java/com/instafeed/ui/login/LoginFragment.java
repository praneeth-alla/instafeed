package com.instafeed.ui.login;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.instafeed.AccountPreferences;
import com.instafeed.Application;
import com.instafeed.R;
import com.instafeed.ui.base.BaseFragment;
import com.instafeed.ui.home.HomeFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.instafeed.AccountPreferences.ID_TOKEN;
import static com.instafeed.Config.AUTH_HOST;
import static com.instafeed.Config.LOG_OUT;

public class LoginFragment extends BaseFragment implements LoginFragmentContract.View {

    @BindView(R.id.webView)
    WebView mWebView;

    @Inject
    AccountPreferences mAccountPreferences;

    @Override
    protected int getLayout() {
        return R.layout.fragment_login;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        LoginFragmentPresenter loginFragmentPresenter = new LoginFragmentPresenter(this);

        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.login_title));
        }
        loginFragmentPresenter.showWebView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((Application) getActivity().getApplication()).getNetComponent().inject(this);
    }

    @Override
    public void showWebViewUI() {
        mWebView = mRootView.findViewById(R.id.webView);
        mWebView.setWebViewClient(new WebClient());
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.getBoolean("isLogout")) {
                mWebView.loadUrl(LOG_OUT);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.company));
            }
        } else {
            mWebView.loadUrl(AUTH_HOST);
        }
    }

    class WebClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);

            if (url.contains("=")) {
                String requiredParams = url.substring(url.indexOf('#') + 1);

                String[] queryParamsArray = requiredParams.split("&");

                for (String s : queryParamsArray) {
                    String key = s.split("=")[0];
                    String value = s.split("=")[1];

                    if (ID_TOKEN.equals(key)) {
                        mAccountPreferences.setValue(ID_TOKEN, value);
                    }
                }

                if (url.contains("#" + ID_TOKEN + "=")) {
                    mFragmentNavigationPresenter.addFragment(new HomeFragment(), null);
                }
            }

            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }
}
