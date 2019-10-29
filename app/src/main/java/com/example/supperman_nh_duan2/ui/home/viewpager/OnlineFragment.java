package com.example.supperman_nh_duan2.ui.home.viewpager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supperman_nh_duan2.R;
import com.example.supperman_nh_duan2.adapter.ManageAdapter;
import com.example.supperman_nh_duan2.api.eventbus.NewEvent;
import com.example.supperman_nh_duan2.base.BaseFragment;
import com.example.supperman_nh_duan2.lisenner.OnlineLisenner;
import com.example.supperman_nh_duan2.model.manage.Manage;
import com.example.supperman_nh_duan2.ui.home.HomeContract;
import com.example.supperman_nh_duan2.ui.home.HomeFragment;
import com.example.supperman_nh_duan2.ui.home.HomePresenter;
import com.example.supperman_nh_duan2.ui.home.onlinedetail.OnlineDetailActivity;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class OnlineFragment extends BaseFragment implements HomeContract, OnlineLisenner {

    @BindView(R.id.re_online)
    RecyclerView recyclerView;
    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout mshimmerFrameLayout;
    private HomePresenter presenter;
    List<Manage> list = new ArrayList<>();
    ManageAdapter adapter;
    LinearLayoutManager manager = new LinearLayoutManager(activity);

    @Override
    public void onResume() {
        super.onResume();
        presenter.getData(list,"online");
    }


    @Override
    public void onStart() {
        super.onStart();
        mshimmerFrameLayout.startShimmer();
    }

    @Override
    public void onStop() {
        super.onStop();
        mshimmerFrameLayout.stopShimmer();
    }

    public static OnlineFragment newInstance() {
        Bundle args = new Bundle();
        OnlineFragment fragment = new OnlineFragment();
        fragment.setArguments(args);
        return fragment;
    }
        @Override
    protected void addEvents() {
        presenter.getData(list,"online");
    }

    @Override
    protected void configViews() {

    }

    @Override
    protected void initDatas() {
        presenter = new HomePresenter(activity,this);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.online_fragment;
    }

    @Override
    public void loadData(List<Manage> manages) {
        recyclerView.setVisibility(View.VISIBLE);
        adapter = new ManageAdapter(activity,manages,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        mshimmerFrameLayout.stopShimmer();
        mshimmerFrameLayout.setVisibility(View.GONE);


    }

    @Override
    public void ShowError() {
        mshimmerFrameLayout.stopShimmer();
        mshimmerFrameLayout.setVisibility(View.GONE);
    }

    @Override
    public void ShowErorr(int error) {
        Toasty.error(activity,error).show();
        mshimmerFrameLayout.stopShimmer();
        mshimmerFrameLayout.setVisibility(View.GONE);
    }

    @Override
    public void ShowDelete() {

    }

    @Override
    public void addThanhtoan(int id) {

    }

    @Override
    public void Onclick(Manage manage) {
        OnlineDetailActivity.startActivity(activity,manage);
    }
}
