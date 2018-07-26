package com.instafeed;

import com.instafeed.modules.AccountPreferencesModule;
import com.instafeed.modules.ContextModule;
import com.instafeed.modules.NetModule;

public class Application extends android.app.Application {

    private NetComponent mNetComponent;

    Application mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeDependencies();
    }

    private void initializeDependencies() {
        mApplication = new Application();

        mNetComponent = DaggerNetComponent.builder()
                .netModule(new NetModule())
                .accountPreferencesModule(new AccountPreferencesModule())
                .contextModule(new ContextModule(this))
                .build();

    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }

}
