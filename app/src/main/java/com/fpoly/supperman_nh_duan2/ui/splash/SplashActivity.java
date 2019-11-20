package com.fpoly.supperman_nh_duan2.ui.splash;

import android.content.Context;
import android.content.SharedPreferences;

import com.fpoly.supperman_nh_duan2.R;
import com.fpoly.supperman_nh_duan2.base.BaseActivity;
import com.fpoly.supperman_nh_duan2.model.local.AppPreferencesHelper;
import com.fpoly.supperman_nh_duan2.model.local.DataManager;
import com.fpoly.supperman_nh_duan2.ui.login.LoginActivity;
import com.fpoly.supperman_nh_duan2.ui.main.MainActivity;
import com.fpoly.supperman_nh_duan2.untils.StringUtils;
import com.google.firebase.iid.FirebaseInstanceId;
import com.novoda.merlin.Bindable;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.NetworkStatus;

import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends BaseActivity implements Connectable, Disconnectable, Bindable, SplashContract {

    private SharedPreferences mPrefs;
    private AppPreferencesHelper appPreferencesHelper;
    private DataManager dataManager;
    private SplashPresenter presenter;
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
        presenter = new SplashPresenter(this,this);
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
            presenter.getData(dataManager.getID());
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

    @Override
    public void showerror(int error) {
        Toasty.error(this,error).show();
        openLoginScreen();
    }

    @Override
    public void showSucces(String token) {

        if (!token.equals(dataManager.token())){
            openLoginScreen();
            dataManager.clearAllUserInfo();
            Toasty.error(this,R.string.error_android).show();
            return;
        }

        addDisposable(Observable.just(0).delay(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> {
                    openMainScreen();
                }));
    }
}
