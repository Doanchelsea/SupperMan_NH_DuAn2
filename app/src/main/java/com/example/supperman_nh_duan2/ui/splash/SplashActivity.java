package com.example.supperman_nh_duan2.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.supperman_nh_duan2.R;
import com.example.supperman_nh_duan2.base.BaseActivity;
import com.example.supperman_nh_duan2.ui.login.LoginActivity;
import com.example.supperman_nh_duan2.ui.main.MainActivity;
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
        addDisposable(Observable.just(0).delay(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> {
                    openMainScreen();
                }));
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void addEvents() {

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

    private void openMainScreen() {
        LoginActivity.startActivity(this);
    }
}
