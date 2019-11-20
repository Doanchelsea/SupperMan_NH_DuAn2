package com.fpoly.supperman_nh_duan2.ui.thongtin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fpoly.supperman_nh_duan2.R;
import com.fpoly.supperman_nh_duan2.api.Server;
import com.fpoly.supperman_nh_duan2.base.BaseFragment;
import com.fpoly.supperman_nh_duan2.model.LoadingDialog;
import com.fpoly.supperman_nh_duan2.model.local.AppPreferencesHelper;
import com.fpoly.supperman_nh_duan2.model.local.DataManager;
import com.fpoly.supperman_nh_duan2.ui.history.HistoryActivity;
import com.fpoly.supperman_nh_duan2.ui.home.diglog.HotlineDialog;
import com.fpoly.supperman_nh_duan2.ui.login.LoginActivity;
import com.fpoly.supperman_nh_duan2.ui.main.MainActivity;
import com.fpoly.supperman_nh_duan2.ui.thongtin.detail.ThongTinDetailActivity;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;


public class ThongTinFragment extends BaseFragment implements ThongtinContract {

    private SharedPreferences mPrefs;
    private AppPreferencesHelper appPreferencesHelper;
    private DataManager dataManager;
    private ThongtinPrsenter prsenter;
    @BindView(R.id.fragment_account_iv_avatar_driver)
    CircleImageView ivAvatar;

    @BindView(R.id.fragment_account_tv_full_name_driver)
    TextView tvFullName;

    @BindView(R.id.account_container_bill)
    ConstraintLayout account_history;
    @BindView(R.id.fragment_account_container_profile)
    ConstraintLayout container_thongtin;
    @BindView(R.id.fragment_account_container_language)
    ConstraintLayout container_logout;
    @BindView(R.id.account_container_contact)
    ConstraintLayout container_lienhe;



    @Override
    public void onResume() {
        super.onResume();
        loadAvatar(Server.duongdananh + dataManager.getImage(),ivAvatar);
        loadFullName(dataManager.getName(),tvFullName);
    }

    public static ThongTinFragment newInstance() {
        Bundle args = new Bundle();
        ThongTinFragment fragment = new ThongTinFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void addEvents() {
        addDisposable(RxView.clicks(account_history)
                .throttleFirst(2, TimeUnit.SECONDS)
        .compose(bindToLifecycle())
        .subscribe(unit -> {
            HistoryActivity.startActivity(activity);
        }));

        addDisposable(RxView.clicks(container_lienhe)
                .throttleFirst(2, TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(unit -> {
                HotlineDialog dialog =   HotlineDialog.newInstance("0961143327");
                dialog.show(getChildFragmentManager(), dialog.getTag());
                }));
        addDisposable(RxView.clicks(container_thongtin)
                .throttleFirst(2, TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(unit -> {
                    ThongTinDetailActivity.startActivity(activity);
                }));
        addDisposable(RxView.clicks(container_logout)
                .throttleFirst(2, TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(unit -> {
                    showLoading(true);
                    prsenter.updateToken("null",MainActivity.ID);
                }));
    }

    @Override
    protected void configViews() {

    }

    @Override
    protected void initDatas() {
        mPrefs = context.getSharedPreferences("", Context.MODE_PRIVATE);
        appPreferencesHelper = new AppPreferencesHelper(mPrefs,activity);
        dataManager = new DataManager(appPreferencesHelper);
        prsenter = new ThongtinPrsenter(activity,this);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.thongtin_fragment;
    }

    @Override
    public void error(int error) {
        showLoading(false);
        Toasty.error(activity,error).show();
    }

    @Override
    public void success() {
        dataManager.clearAllUserInfo();
        LoginActivity.startActivity(activity);
        showLoading(false);
    }

    @Override
    public void showLoading(boolean show) {
        if (show){
            LoadingDialog.getInstance().showLoading(activity);
        }else {
            LoadingDialog.getInstance().hideLoading();
        }
    }
}
