package com.instafeed;

import android.content.Context;
import android.content.SharedPreferences;

public class AccountPreferences {

    public static final String ID_TOKEN = "id_token";

    private static AccountPreferences mAccountPrefs;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mSharedPreferencesEditor;

    AccountPreferences(Context context) {
        mSharedPreferences = context.getSharedPreferences("insta_feed_account_prefs", Context.MODE_PRIVATE);
        mSharedPreferencesEditor = mSharedPreferences.edit();
    }

    public static synchronized AccountPreferences getInstance(Context context) {

        if (mAccountPrefs == null) {
            mAccountPrefs = new AccountPreferences(context);
        }
        return mAccountPrefs;
    }

    public void setValue(String key, String value) {
        mSharedPreferencesEditor.putString(key, value);
        mSharedPreferencesEditor.commit();
    }

    public String getStringValue(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }

    public void clear() {
        mSharedPreferencesEditor.clear().commit();
    }
}


