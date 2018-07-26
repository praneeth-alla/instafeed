package com.instafeed.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.instafeed.AccountPreferences;
import com.instafeed.Application;
import com.instafeed.R;
import com.instafeed.ui.home.HomeFragment;
import com.instafeed.ui.login.LoginFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity implements BaseActivityContract.View {

    private BaseActivityPresenter mBaseActivityPresenter;

    @Inject
    AccountPreferences mAccountPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Application) getApplication()).getNetComponent().inject(this);

        ButterKnife.bind(this);

        mBaseActivityPresenter = new BaseActivityPresenter(this);

        String idToken = mAccountPreferences.getStringValue(AccountPreferences.ID_TOKEN, null);
        if (idToken != null) {
            mBaseActivityPresenter.addFragment(new HomeFragment(), null);
        } else {
            mBaseActivityPresenter.addFragment(new LoginFragment(), null);
        }
    }

    @Override
    public void setFragment(BaseFragment fragment, Bundle bundle) {
        fragment.attachPresenter(mBaseActivityPresenter);
        fragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
