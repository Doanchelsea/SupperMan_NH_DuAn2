package com.fpoly.supperman_nh_duan2.ui.home;

import android.os.Bundle;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.fpoly.supperman_nh_duan2.R;
import com.fpoly.supperman_nh_duan2.base.BaseFragment;
import com.fpoly.supperman_nh_duan2.ui.home.viewpager.ViewPage;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;

public class HomeFragment extends BaseFragment {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void addEvents() {
        PagerAdapter adapter = new ViewPage(getFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText(R.string.text_offline);
        tabLayout.getTabAt(1).setText(R.string.text_online);
    }

    @Override
    protected void configViews() {

    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_fragment;
    }
}
