package com.fpoly.supperman_nh_duan2.model.local;

public class DataManager implements PreferencesHelper {

    private PreferencesHelper preferencesHelper;

    public DataManager(PreferencesHelper preferencesHelper) {
        this.preferencesHelper = preferencesHelper;
    }

    @Override
    public void setLoggedIn(boolean isLoggedIn) {
        preferencesHelper.setLoggedIn(isLoggedIn);
    }

    @Override
    public boolean IsLoggedIn() {
        return preferencesHelper.IsLoggedIn();
    }

    @Override
    public void setID(String id) {
        preferencesHelper.setID(id);
    }

    @Override
    public String getID() {
        return preferencesHelper.getID();
    }

    @Override
    public void clearID() {
        preferencesHelper.clearID();
    }

    @Override
    public void setName(String name) {
        preferencesHelper.setName(name);
    }

    @Override
    public String getName() {
        return preferencesHelper.getName();
    }

    @Override
    public void clearName() {
        preferencesHelper.clearName();
    }

    @Override
    public void setImage(String image) {
        preferencesHelper.setImage(image);
    }

    @Override
    public String getImage() {
        return preferencesHelper.getImage();
    }

    @Override
    public void clearImage() {
        preferencesHelper.clearImage();
    }

    public void updateUserInfoSharedPreference(String id,String name,String image, boolean isLoggedIn) {
        setID(id);
        setImage(image);
        setName(name);
        setLoggedIn(isLoggedIn);
    }
    public void clearAllUserInfo() {
        clearID();
        clearImage();
        clearName();
    }
}
