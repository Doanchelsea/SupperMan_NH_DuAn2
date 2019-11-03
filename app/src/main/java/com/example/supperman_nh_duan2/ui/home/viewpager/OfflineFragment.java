package com.example.supperman_nh_duan2.ui.home.viewpager;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supperman_nh_duan2.R;
import com.example.supperman_nh_duan2.adapter.ManageAdapter;
import com.example.supperman_nh_duan2.api.eventbus.NewEvent;
import com.example.supperman_nh_duan2.base.BaseFragment;
import com.example.supperman_nh_duan2.lisenner.ManageLisenner;
import com.example.supperman_nh_duan2.model.manage.Manage;
import com.example.supperman_nh_duan2.ui.home.HomeContract;
import com.example.supperman_nh_duan2.ui.home.HomePresenter;
import com.example.supperman_nh_duan2.ui.home.diglog.DiglogManage;
import com.example.supperman_nh_duan2.ui.main.MainActivity;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.tapadoo.alerter.Alerter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class OfflineFragment extends BaseFragment implements HomeContract, ManageLisenner {
    @BindView(R.id.re_offline)
    RecyclerView recyclerView;
    @BindView(R.id.shimmer_view_containeroff)
    ShimmerFrameLayout mshimmerFrameLayout;
    private HomePresenter presenter;
    List<Manage> list = new ArrayList<>();
    ManageAdapter adapter;
    LinearLayoutManager manager = new LinearLayoutManager(activity);
    boolean isLoad = false;

    public static OfflineFragment newInstance() {
        Bundle args = new Bundle();
        OfflineFragment fragment = new OfflineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        if (isLoad == true){
            presenter.getData(list,"offline");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
        isLoad = true;
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
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(NewEvent newEvent){
        presenter.getData(list,"offline");
    }

    @Override
    protected void addEvents() {
        adapter = new ManageAdapter(activity,list,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        presenter.getData(list,"offline");
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
        return R.layout.offline_fragment;
    }

    @Override
    public void loadData(List<Manage> manages) {
        recyclerView.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();
        mshimmerFrameLayout.stopShimmer();
        mshimmerFrameLayout.setVisibility(View.GONE);
    }

    @Override
    public void ShowError() {
        mshimmerFrameLayout.stopShimmer();
        mshimmerFrameLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void ShowErorr(int error) {
        Toasty.error(activity,error).show();
        mshimmerFrameLayout.stopShimmer();
        mshimmerFrameLayout.setVisibility(View.GONE);
    }

    @Override
    public void ShowDelete() {
        presenter.getData(list,"offline");
    }

    @Override
    public void addThanhtoan(int id) {
        presenter.delete(id);
        Alerter.create(activity)
                .setTitle(MainActivity.NAME)
                .setText("Món ăn đã hoàn thành")
                .setDuration(1000)
                .setBackgroundColorRes(R.color.bg_color_alert_dialog)
                .show();
    }


    @Override
    public void onClick(Manage manage) {
        DiglogManage dialog = DiglogManage.newInstance(manage);
        dialog.show(getChildFragmentManager(), dialog.getTag());
    }

    @Override
    public void onClick(int id) {
        presenter.delete(id);
    }

    @Override
    public void onAdd(Manage manage) {
        presenter.thanhtoan(manage);
    }
}
