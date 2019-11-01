package com.example.supperman_nh_duan2.ui.thongtin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.supperman_nh_duan2.R;
import com.example.supperman_nh_duan2.api.Server;
import com.example.supperman_nh_duan2.base.BaseFragment;
import com.example.supperman_nh_duan2.model.local.AppPreferencesHelper;
import com.example.supperman_nh_duan2.model.local.DataManager;
import com.example.supperman_nh_duan2.ui.history.HistoryActivity;
import com.example.supperman_nh_duan2.ui.home.diglog.HotlineDialog;
import com.example.supperman_nh_duan2.ui.login.LoginActivity;
import com.example.supperman_nh_duan2.ui.main.MainActivity;
import com.example.supperman_nh_duan2.ui.thongtin.detail.ThongTinDetailActivity;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;


public class ThongTinFragment extends BaseFragment {

    private SharedPreferences mPrefs;
    private AppPreferencesHelper appPreferencesHelper;
    private DataManager dataManager;
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
                    dataManager.clearAllUserInfo();
                    LoginActivity.startActivity(activity);
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
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.thongtin_fragment;
    }

}
