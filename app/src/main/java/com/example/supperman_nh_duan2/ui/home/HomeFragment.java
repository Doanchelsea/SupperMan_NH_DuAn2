package com.example.supperman_nh_duan2.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.supperman_nh_duan2.R;
import com.example.supperman_nh_duan2.adapter.ManageAdapter;
import com.example.supperman_nh_duan2.api.Server;
import com.example.supperman_nh_duan2.base.BaseFragment;
import com.example.supperman_nh_duan2.model.manage.Manage;
import com.example.supperman_nh_duan2.ui.adddish.AddDishActivity;
import com.example.supperman_nh_duan2.ui.home.viewpager.ViewPage;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.tabs.TabLayout;
import com.jakewharton.rxbinding3.view.RxView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

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
