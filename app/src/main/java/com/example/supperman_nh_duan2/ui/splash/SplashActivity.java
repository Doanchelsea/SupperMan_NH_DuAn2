package com.example.supperman_nh_duan2.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.supperman_nh_duan2.R;
import com.example.supperman_nh_duan2.base.BaseActivity;
import com.example.supperman_nh_duan2.model.local.AppPreferencesHelper;
import com.example.supperman_nh_duan2.model.local.DataManager;
import com.example.supperman_nh_duan2.ui.login.LoginActivity;
import com.example.supperman_nh_duan2.ui.main.MainActivity;
import com.example.supperman_nh_duan2.untils.StringUtils;
import com.novoda.merlin.Bindable;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.NetworkStatus;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends BaseActivity implements Connectable, Disconnectable, Bindable {

    private SharedPreferences mPrefs;
    private AppPreferencesHelper appPreferencesHelper;
    private DataManager dataManager;

    @Override
    protected void onResume() {
        super.onResume();
        registerConnectable(this);
        registerDisconnectable(this);
        registerBindable(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected Merlin initMerlin() {
        return new Merlin.Builder()
                .withConnectableCallbacks()
                .withDisconnectableCallbacks()
                .withBindableCallbacks()
                .build(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void addEvents() {
        mPrefs = context.getSharedPreferences("", Context.MODE_PRIVATE);
        appPreferencesHelper = new AppPreferencesHelper(mPrefs,this);
        dataManager = new DataManager(appPreferencesHelper);

        if (StringUtils.isEmpty(dataManager.getID())){
            addDisposable(Observable.just(0).delay(1000, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aVoid -> {
                        openLoginScreen();
                    }));
        }else {
            addDisposable(Observable.just(0).delay(500, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aVoid -> {
                        openMainScreen();
                    }));
        }

    }


    @Override
    public void onBind(NetworkStatus networkStatus) {

    }

    @Override
    public void onConnect() {

    }

    @Override
    public void onDisconnect() {

    }

    private void openLoginScreen() {
        LoginActivity.startActivity(this);
    }
    private void openMainScreen() {
        MainActivity.startActivity(this);
    }
}
