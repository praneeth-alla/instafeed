package com.instafeed;

import com.instafeed.data.service.MediaRepositoryImpl;
import com.instafeed.modules.AccountPreferencesModule;
import com.instafeed.modules.ContextModule;
import com.instafeed.modules.NetModule;
import com.instafeed.ui.base.BaseActivity;
import com.instafeed.ui.base.BaseFragment;
import com.instafeed.ui.detail.DetailFragment;
import com.instafeed.ui.home.HomeFragment;
import com.instafeed.ui.home.HomeFragmentPresenter;
import com.instafeed.ui.login.LoginFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetModule.class, AccountPreferencesModule.class, ContextModule.class})
public interface NetComponent {

    void inject(BaseActivity baseActivity);

    void inject(BaseFragment baseFragment);

    void inject(LoginFragment loginFragment);

    void inject(HomeFragment homeFragment);

    void inject(DetailFragment detailFragment);

    void inject(MediaRepositoryImpl mediaRepositoryImpl);

    void inject(HomeFragmentPresenter homeFragmentPresenter);
}
