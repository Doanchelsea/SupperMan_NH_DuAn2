package com.fpoly.supperman_nh_duan2.ui.thongtin.detail;

import androidx.annotation.Nullable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fpoly.supperman_nh_duan2.R;
import com.fpoly.supperman_nh_duan2.api.Server;
import com.fpoly.supperman_nh_duan2.base.BaseActivity;
import com.fpoly.supperman_nh_duan2.model.LoadingDialog;
import com.fpoly.supperman_nh_duan2.model.local.AppPreferencesHelper;
import com.fpoly.supperman_nh_duan2.model.local.DataManager;
import com.fpoly.supperman_nh_duan2.ui.main.MainActivity;
import com.fpoly.supperman_nh_duan2.untils.StringUtils;
import com.fpoly.supperman_nh_duan2.untils.ValidateUtils;
import com.jakewharton.rxbinding3.view.RxView;
import com.novoda.merlin.Bindable;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.NetworkStatus;
import com.tapadoo.alerter.Alerter;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ThongTinDetailActivity extends BaseActivity implements Connectable, Disconnectable, Bindable,ThongTinDetailContract {

    private SharedPreferences mPrefs;
    private AppPreferencesHelper appPreferencesHelper;
    private DataManager dataManager;

    public static void startActivity(Activity context){
        context.startActivity(new Intent(context, ThongTinDetailActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    Bitmap bitmap;
    @BindView(R.id.activity_update_info_iv_back)
    ImageView imgBack;
    @BindView(R.id.activity_update_info_tv_save)
    TextView tvSave;
    @BindView(R.id.activity_update_info_edt_full_name)
    EditText edName;
    @BindView(R.id.activity_update_info_iv_avatar_driver)
    CircleImageView imgAvatar;
    private ThongTinDetailPresenter presenter;

    @Override
    protected void onResume() {
        registerConnectable(this);
        registerDisconnectable(this);
        registerBindable(this);
        super.onResume();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_thong_tin_detail;
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
        mPrefs = context.getSharedPreferences("", Context.MODE_PRIVATE);
        appPreferencesHelper = new AppPreferencesHelper(mPrefs,this);
        dataManager = new DataManager(appPreferencesHelper);

        presenter = new ThongTinDetailPresenter(this,this);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void addEvents() {
        addDisposable(RxView.clicks(imgBack).throttleFirst(2, TimeUnit.SECONDS)
        .compose(bindToLifecycle())
        .subscribe(unit -> {
            onBackPressed();
            finish();
        }));
        loadAvatar(Server.duongdananh+ dataManager.getImage(),imgAvatar);
        edName.setText(dataManager.getName());

        addDisposable(RxView.clicks(imgAvatar)
                .throttleFirst(2,TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(unit -> {
                    showImage();
                }));
        addDisposable(RxView.clicks(tvSave)
                .throttleFirst(2,TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(unit -> {
                    String name = edName.getText().toString().trim();
                    if (StringUtils.isEmpty(name)){
                        Toasty.warning(this,R.string.error_null).show();
                    }else if (!ValidateUtils.isVaidFullName(name)){
                        Toasty.warning(this,R.string.error_ky_tu).show();
                    } else {
                        ShowLoading(true);
                        if (bitmap != null){
                            presenter.Save(name,imageToString(bitmap));
                        }else {
                            presenter.Save(name,"");
                        }
                    }
                }));
    }

    @Override
    public void onBind(NetworkStatus networkStatus) {
        if (!networkStatus.isAvailable()){
            onDisconnect();
        }else {
            hideDialog();
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
    public void ShowSucces() {
        ShowLoading(false);
        presenter.UpdateNH();
    }

    @Override
    public void ShowError(int error) {
        Toasty.error(this,error).show();
        ShowLoading(false);
    }

    @Override
    public void ShowLoading(boolean show) {
        if (show){
            LoadingDialog.getInstance().showLoading(this);
        }else {
            LoadingDialog.getInstance().hideLoading();
        }
    }

    @Override
    public void ShowUpdate(String name, String image) {
        dataManager.updateUserInfoSharedPreference(MainActivity.ID,name,image,true);
        Alerter.create(this)
                .setTitle(R.string.app_name)
                .setText("Cập nhật thông tin thành công")
                .setDuration(1500)
                .setBackgroundColorRes(R.color.bg_color_alert_dialog)
                .show();
        addDisposable(Observable.just(0).delay(1600, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> {
                    finish();
                }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imgAvatar.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

    }

    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
        byte[]  imageBytes = outputStream.toByteArray();
        String edcodeImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return  edcodeImage;
    }

}
