package com.fpoly.supperman_nh_duan2.ui.home.onlinedetail;

import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fpoly.supperman_nh_duan2.R;
import com.fpoly.supperman_nh_duan2.api.Server;
import com.fpoly.supperman_nh_duan2.api.eventbus.CancelEvent;
import com.fpoly.supperman_nh_duan2.base.BaseActivity;
import com.fpoly.supperman_nh_duan2.lisenner.AddOnlineLisenner;
import com.fpoly.supperman_nh_duan2.lisenner.ConfimLisenner;
import com.fpoly.supperman_nh_duan2.model.LoadingDialog;
import com.fpoly.supperman_nh_duan2.model.manage.Manage;
import com.fpoly.supperman_nh_duan2.ui.home.diglog.ConfirmDialog;
import com.fpoly.supperman_nh_duan2.ui.home.diglog.HotlineDialog;
import com.fpoly.supperman_nh_duan2.ui.home.diglog.OnlineDiglog;
import com.fpoly.supperman_nh_duan2.ui.main.MainActivity;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.jakewharton.rxbinding3.view.RxView;
import com.novoda.merlin.Bindable;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.NetworkStatus;
import com.tapadoo.alerter.Alerter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    boolean isOnClick = true;

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
        isOnClick = true;
        manage = getIntent().getParcelableExtra("MANAGE");
        detailPresenter.getUser(manage);
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        mshimmerFrameLayout.stopShimmer();
        isOnClick = false;
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CancelEvent cancelEvent){
        if (cancelEvent.getId().equals(String.valueOf(manage.getId()))){
            finish();
        }
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
        addDisposable(RxView.clicks(imageView)
                .throttleFirst(2,TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(unit -> {
                    onBackPressed();
                    finish();
                }));

        addDisposable(RxView.clicks(btn_huy_dat)
                .throttleFirst(2, TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(unit -> {
                    if (isOnClick == true){
                        ConfirmDialog dialog = ConfirmDialog.newInstance(manage);
                        dialog.show(getSupportFragmentManager(), dialog.getTag());
                    }
        }));
        addDisposable(RxView.clicks(btn_xac_nhan_user)
                .throttleFirst(2,TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(unit -> {
                    if (isOnClick == true){
                        OnlineDiglog dialog = OnlineDiglog.newInstance(manage);
                        dialog.show(getSupportFragmentManager(), dialog.getTag());
                    }
        }));
    }

    @Override
    public void onBind(NetworkStatus networkStatus) {
        if (!networkStatus.isAvailable()){
            onDisconnect();
        }else {
            onConnect();
        }
    }

    @Override
    public void onConnect() {
        hideDialog();
    }

    @Override
    public void onDisconnect() {
        showDialog();
    }

    @Override
    public void showUser(String name, String phone, String image) {
        tv_name_user.setText(name);
        tv_sdt_user.setText(phone);
        loadAvatar(Server.duongdananh+image,img_user);
        mshimmerFrameLayout.stopShimmer();
        mshimmerFrameLayout.setVisibility(View.GONE);
        visible(constraintLayout);
        addDisposable(RxView.clicks(tv_sdt_user)
                .throttleFirst(2,TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(unit -> {
                    if (isOnClick == true){
                        HotlineDialog dialog = HotlineDialog.newInstance(phone);
                        dialog.show(getSupportFragmentManager(), dialog.getTag());
                    }
        }));
    }

    @Override
    public void showDelete() {
        showLoading(false);
        finish();
    }

    @Override
    public void showSoban(int ban) {
        OnlineDiglog dialog = OnlineDiglog.newInstance(manage);
        dialog.show(getSupportFragmentManager(), dialog.getTag());
    }

    @Override
    public void showthanhtoan(int id) {
        Alerter.create(this)
                .setTitle(MainActivity.NAME)
                .setText("Xác nhận khách hàng thành công")
                .setDuration(1500)
                .setBackgroundColorRes(R.color.bg_color_alert_dialog)
                .show();
        isOnClick = false;
        addDisposable(Observable.just(0).delay(1600, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> {
                    finish();
                }));
    }

    @Override
    public void ShowError(int error) {
        Toasty.error(context,error).show();
        showLoading(false);
    }

    @Override
    public void showLoading(boolean show) {
        if (show){
            LoadingDialog.getInstance().showLoading(this);
        }else {
            LoadingDialog.getInstance().hideLoading();
        }
    }


    @Override
    public void onClick(Manage manage) {
        showLoading(true);
        detailPresenter.delete(manage);
    }


    @Override
    public void onclick(Manage manage,int banan) {
        detailPresenter.thanhtoan(manage,banan);
    }
}
