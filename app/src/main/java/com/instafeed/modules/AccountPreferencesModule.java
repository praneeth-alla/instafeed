package com.instafeed.modules;

import android.content.Context;

import com.instafeed.AccountPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AccountPreferencesModule {

    @Provides
    @Singleton
    AccountPreferences provideAccountPreferences(Context context) {
        return AccountPreferences.getInstance(context);
    }
}
