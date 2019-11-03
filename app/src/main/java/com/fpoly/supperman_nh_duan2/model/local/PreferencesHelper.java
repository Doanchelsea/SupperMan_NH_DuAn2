package com.fpoly.supperman_nh_duan2.model.local;

public interface PreferencesHelper {

    void setLoggedIn(boolean isLoggedIn);

    boolean IsLoggedIn();

    void setID(String id);

    String getID();

    void clearID();

    void setName(String name);

    String getName();

    void clearName();

    void setImage(String image);

    String getImage();

    void clearImage();


}
