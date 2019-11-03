package com.fpoly.supperman_nh_duan2.ui.home.viewpager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class ViewPage extends FragmentPagerAdapter {
    String IDNH,IMGNH;
    Bundle bundle;

    public ViewPage(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int i) {

        switch (i){
            case 0:
                OfflineFragment offlineFragment = OfflineFragment.newInstance();
                return offlineFragment;
            case 1:
                OnlineFragment onlineFragment = OnlineFragment.newInstance();
                return onlineFragment;

                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
