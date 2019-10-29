package com.example.supperman_nh_duan2.ui.home.onlinedetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.supperman_nh_duan2.R;
import com.example.supperman_nh_duan2.api.Server;
import com.example.supperman_nh_duan2.base.BaseActivity;
import com.example.supperman_nh_duan2.lisenner.AddOnlineLisenner;
import com.example.supperman_nh_duan2.lisenner.ConfimLisenner;
import com.example.supperman_nh_duan2.model.Menu;
import com.example.supperman_nh_duan2.model.manage.Manage;
import com.example.supperman_nh_duan2.ui.home.diglog.ConfirmDialog;
import com.example.supperman_nh_duan2.ui.home.diglog.HotlineDialog;
import com.example.supperman_nh_duan2.ui.home.diglog.OnlineDiglog;
import com.example.supperman_nh_duan2.ui.menu.detail.MenuDetailActivity;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.jakewharton.rxbinding3.view.RxView;
import com.novoda.merlin.Bindable;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.NetworkStatus;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class OnlineDetailActivity extends BaseActivity implements Connectable, Disconnectable, Bindable,OnlineDetailContract, ConfimLisenner, AddOnlineLisenner {

    @BindView(R.id.img_user)
    CircleImageView img_user;
    @BindView(R.id.tv_name_user)
    TextView tv_name_user;
    @BindView(R.id.tv_sdt_user)
    TextView tv_sdt_user;
    private OnlineDetailPresenter detailPresenter;
    @BindView(R.id.shimmer_view_container_user)
    ShimmerFrameLayout mshimmerFrameLayout;
    @BindView(R.id.constraintLayout_user)
    ConstraintLayout constraintLayout;
    @BindView(R.id.btn_huy_dat)
    Button btn_huy_dat;
    @BindView(R.id.btn_xac_nhan_user)
    Button btn_xac_nhan_user;
    Manage manage;
    @BindView(R.id.activity_register_iv_back_online)
    ImageView imageView;

    public static void startActivity(Activity context, Manage manage){
        context.startActivity(new Intent(context, OnlineDetailActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .putExtra("MANAGE", manage));
    }
    @Override
    protected void onResume() {
        super.onResume();
        registerBindable(this);
        registerConnectable(this);
        registerDisconnectable(this);
        mshimmerFrameLayout.startShimmer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mshimmerFrameLayout.stopShimmer();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_online_detail;
    }

    @Override
    protected Merlin initMerlin() {
        return new Merlin.Builder()
                .withConnectableCallbacks()
                .withBindableCallbacks()
                .withDisconnectableCallbacks()
                .build(this);
    }

    @Override
    protected void initData() {
        detailPresenter = new OnlineDetailPresenter(this,this);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void addEvents() {
         manage = getIntent().getParcelableExtra("MANAGE");
        detailPresenter.getUser(manage.getIduser());
        addDisposable(RxView.clicks(btn_huy_dat).subscribe(unit -> {
            ConfirmDialog dialog = ConfirmDialog.newInstance(manage.getId());
            dialog.show(getSupportFragmentManager(), dialog.getTag());
        }));
        addDisposable(RxView.clicks(btn_xac_nhan_user).subscribe(unit -> {
            detailPresenter.soban();
        }));
        addDisposable(RxView.clicks(imageView).subscribe(unit -> {
            onBackPressed();
            finish();
        }));
    }

    @Override
    public void onBind(NetworkStatus networkStatus) {
        if (!networkStatus.isAvailable()){
            onDisconnect();
        }
    }

    @Override
    public void onConnect() {

    }

    @Override
    public void onDisconnect() {
        showToastDisconnect();
    }

    @Override
    public void showUser(String name, String phone, String image) {
        tv_name_user.setText(name);
        tv_sdt_user.setText(phone);
        loadAvatar(Server.duongdananh+image,img_user);
        mshimmerFrameLayout.stopShimmer();
        mshimmerFrameLayout.setVisibility(View.GONE);
        visible(constraintLayout);
        addDisposable(RxView.clicks(tv_sdt_user).subscribe(unit -> {
            HotlineDialog dialog = HotlineDialog.newInstance(phone);
            dialog.show(getSupportFragmentManager(), dialog.getTag());
        }));
    }

    @Override
    public void showDelete() {
        finish();
    }

    @Override
    public void showSoban(int ban) {
        OnlineDiglog dialog = OnlineDiglog.newInstance(manage,ban);
        dialog.show(getSupportFragmentManager(), dialog.getTag());
    }

    @Override
    public void showthanhtoan(int id) {
        detailPresenter.delete(id);
        Toasty.success(this,"Món ăn đã hoàn thành").show();
    }


    @Override
    public void onClick(int id) {
        detailPresenter.delete(id);
    }


    @Override
    public void onclick(Manage manage, int ban) {
        detailPresenter.thanhtoan(manage,ban);
    }
}
