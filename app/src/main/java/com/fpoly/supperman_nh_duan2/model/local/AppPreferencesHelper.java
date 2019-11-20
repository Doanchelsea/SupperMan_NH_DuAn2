package com.fpoly.supperman_nh_duan2.model.local;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferencesHelper implements PreferencesHelper {

    private static final String ID = "ID";
    private static final String NAME = "NAME";
    private static final String IMAGE = "IMAGE";
    private static final String LOGGED = "LOGGED";
    private static final String TOKEN = "TOKEN";


    private SharedPreferences mPrefs;
    Context context;

    public AppPreferencesHelper(SharedPreferences mPrefs, Context context) {
        this.mPrefs = mPrefs;
        this.context = context;
    }

    @Override
    public String token() {
        return mPrefs.getString(TOKEN,"");
    }

    @Override
    public void setToken(String token) {
        mPrefs.edit().putString(TOKEN,token).apply();
    }

    @Override
    public void setLoggedIn(boolean isLoggedIn) {
        mPrefs.edit().putBoolean(LOGGED, isLoggedIn).apply();
    }

    @Override
    public boolean IsLoggedIn() {
        return mPrefs.getBoolean(LOGGED, false);
    }

    @Override
    public void setID(String id) {
        mPrefs.edit().putString(ID, id).apply();
    }

    @Override
    public String getID() {
        return mPrefs.getString(ID, "");
    }

    @Override
    public void clearID() {
        mPrefs.edit().remove(ID).apply();
    }

    @Override
    public void setName(String name) {
        mPrefs.edit().putString(NAME, name).apply();
    }

    @Override
    public String getName() {
        return mPrefs.getString(NAME, "");
    }

    @Override
    public void clearName() {
        mPrefs.edit().remove(NAME).apply();
    }

    @Override
    public void setImage(String image) {
        mPrefs.edit().putString(IMAGE, image).apply();
    }

    @Override
    public String getImage() {
        return mPrefs.getString(IMAGE, "");
    }

    @Override
    public void clearImage() {
        mPrefs.edit().remove(IMAGE).apply();
    }
}
