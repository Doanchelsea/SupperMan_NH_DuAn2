package com.example.supperman_nh_duan2.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.supperman_nh_duan2.R;
import com.example.supperman_nh_duan2.base.BaseActivity;
import com.example.supperman_nh_duan2.model.LoadingDialog;
import com.example.supperman_nh_duan2.ui.home.HomePresenter;
import com.example.supperman_nh_duan2.ui.main.MainActivity;
import com.example.supperman_nh_duan2.untils.PhoneUtils;
import com.example.supperman_nh_duan2.untils.StringUtils;
import com.jakewharton.rxbinding3.view.RxView;
import com.novoda.merlin.Bindable;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.NetworkStatus;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class LoginActivity extends BaseActivity implements Connectable, Disconnectable, Bindable,LoginContract {
    @BindView(R.id.edNumber)
    EditText edNumber;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    private LoginPresenter presenter;

    public static void startActivity(Activity context){
        context.startActivity(new Intent(context, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        context.finish();
    }
    @Override
    protected void onResume() {
        super.onResume();
        registerBindable(this);
        registerConnectable(this);
        registerDisconnectable(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
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
        presenter = new LoginPresenter(this,this);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void addEvents() {
        addDisposable(RxView.clicks(btnLogin).subscribe(
                unit ->{
                    String phone = edNumber.getText().toString().trim();
                    if (StringUtils.isEmpty(phone)){
                        Toasty.warning(this,R.string.error_null).show();
                        return;
                    }else if (!PhoneUtils.isPhoneNumberNormal(phone) && !PhoneUtils.isPhoneNumberWithCountryCode(phone)){
                        Toasty.warning(this,R.string.error_phone_number).show();
                        return;
                    }else {
                        showLoading(true);
                        presenter.Login(phone);
                    }
                } ));
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
    public void ShowSuccer(int id,String name,String images) {
        showLoading(false);
        MainActivity.startActivity(this,id,name,images);
    }

    @Override
    public void ShowError(int error) {
        showLoading(false);
        Toasty.error(this,error).show();
    }
}
