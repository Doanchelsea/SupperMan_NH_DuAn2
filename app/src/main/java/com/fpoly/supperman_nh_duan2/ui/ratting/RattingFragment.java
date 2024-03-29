package com.fpoly.supperman_nh_duan2.ui.ratting;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.supperman_nh_duan2.R;
import com.fpoly.supperman_nh_duan2.adapter.BananAdapter;
import com.fpoly.supperman_nh_duan2.api.eventbus.NewEvent;
import com.fpoly.supperman_nh_duan2.api.eventbus.RattingEvent;
import com.fpoly.supperman_nh_duan2.base.BaseFragment;
import com.fpoly.supperman_nh_duan2.lisenner.BanLisenner;
import com.fpoly.supperman_nh_duan2.lisenner.LisennerDelete;
import com.fpoly.supperman_nh_duan2.lisenner.Listener;
import com.fpoly.supperman_nh_duan2.model.Banan;
import com.fpoly.supperman_nh_duan2.ui.thanhtoan.ThanhToanActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jakewharton.rxbinding3.view.RxView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class RattingFragment extends BaseFragment implements RattingContract, Listener, LisennerDelete, BanLisenner {
    @BindView(R.id.re_Ban_an)
    RecyclerView reBanan;
    @BindView(R.id.imgAdd_ban_an)
    ImageView imgAddbanan;
    @BindView(R.id.float_ting_delete_ban_an)
    FloatingActionButton floatingActionButton;
    List<Banan> list = new ArrayList<>();
    LinearLayoutManager manager = new GridLayoutManager(activity,2);
    BananAdapter bananAdapter;
    RattingPresenter presenter;
    boolean isload = false;

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        if (isload == true){
            presenter.getData(list);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
        isload = true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RattingEvent rattingEvent){
        presenter.getData(list);
    }

    public static RattingFragment newInstance() {
        Bundle args = new Bundle();
        RattingFragment fragment = new RattingFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected void addEvents() {
        bananAdapter = new BananAdapter(list,activity,this);
        reBanan.setLayoutManager(manager);
        reBanan.setAdapter(bananAdapter);

        presenter.getData(list);
        addDisposable(RxView.clicks(imgAddbanan)
                .throttleFirst(2, TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(unit -> {
            DiglogAdd dialog = DiglogAdd.newInstance();
            dialog.show(getChildFragmentManager(), dialog.getTag());
        }));

        addDisposable(RxView.clicks(floatingActionButton)
                .throttleFirst(2,TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(unit -> {
            presenter.soban();
        }));
    }

    @Override
    protected void configViews() {

    }

    @Override
    protected void initDatas() {
        presenter = new RattingPresenter(this,activity);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.ratting_menu;
    }


    @Override
    public void show(List<Banan> banans) {
        reBanan.setVisibility(View.VISIBLE);
        bananAdapter.notifyDataSetChanged();
    }

    @Override
    public void ShowError() {
        reBanan.setVisibility(View.GONE);
    }

    @Override
    public void ShowErorr(int error) {
        Toasty.error(activity,error).show();
    }

    @Override
    public void ShowAdd() {
        presenter.getData(list);
    }

    @Override
    public void soban(int ban) {
        if (ban > 0){
            DiglogDelete dialog = DiglogDelete.newInstance(ban);
            dialog.show(getChildFragmentManager(), dialog.getTag());
        }
    }

    @Override
    public void showxoaban() {
        presenter.getData(list);
        EventBus.getDefault().post(new NewEvent());
    }


    @Override
    public void add() {
        presenter.addData();
    }

    @Override
    public void onClick(int ban) {
        presenter.xoaban(ban)   ;
    }

    @Override
    public void onBan(int ban) {
        ThanhToanActivity.startActivity(activity,ban);
    }
}
