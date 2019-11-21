package com.fpoly.supperman_nh_duan2.ui.adddish;

import androidx.annotation.Nullable;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.fpoly.supperman_nh_duan2.R;
import com.fpoly.supperman_nh_duan2.base.BaseActivity;

import com.fpoly.supperman_nh_duan2.model.LoadingDialog;
import com.fpoly.supperman_nh_duan2.ui.main.MainActivity;
import com.fpoly.supperman_nh_duan2.untils.StringUtils;
import com.fpoly.supperman_nh_duan2.untils.ValidateUtils;
import com.jakewharton.rxbinding3.view.RxView;
import com.makeramen.roundedimageview.RoundedImageView;
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
import es.dmoral.toasty.Toasty;

public class AddDishActivity extends BaseActivity implements Connectable, Disconnectable, Bindable,AddDishContract {

    @BindView(R.id.imgThemAnhMonAn)
    ImageView imgThemAnhMonAn;
    @BindView(R.id.imgAnhMonAn)
    RoundedImageView imgAnhMonAn;
    @BindView(R.id.btn_add_dish)
    Button btn_add_dish;
    @BindView(R.id.activity_register_iv_back)
    ImageView activityRegisterIvBack;
    @BindView(R.id.radioBuaTrua)
    RadioButton radioBuaTrua;
    @BindView(R.id.radioBuatoi)
    RadioButton radioBuatoi;
    @BindView(R.id.radioCaNgay)
    RadioButton radioCaNgay;
    @BindView(R.id.radio_lau)
    RadioButton radioLau;
    @BindView(R.id.radio_do_an)
    RadioButton radioDoAn;
    @BindView(R.id.radio_nuoc)
    RadioButton radioNuoc;
    @BindView(R.id.edNameAddDish)
    EditText edNameAddDish;
    @BindView(R.id.edPriceAddDish)
    EditText edPriceAddDish;
    @BindView(R.id.edDescriptionAdd)
    EditText edDescriptionAdd;

    private Bitmap bitmap,bitmap1;
    private AddDishPresenter presenter;


    @Override
    protected void onResume() {
        super.onResume();
        registerConnectable(this);
        registerDisconnectable(this);
        registerBindable(this);
    }

    public static void startActivity(Activity context) {
        context.startActivity(new Intent(context, AddDishActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_dish;
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
        presenter = new AddDishPresenter(this,this);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void addEvents() {

        addDisposable(RxView.clicks(imgThemAnhMonAn)
                .throttleFirst(2, TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(
                unit -> {
                    showImage();
                }
        ));

        addDisposable(RxView.clicks(btn_add_dish)
                .throttleFirst(2,TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(
                btn -> {
                    String name = edNameAddDish.getText().toString().trim();
                    String price = edPriceAddDish.getText().toString().trim();
                    String description = edDescriptionAdd.getText().toString().trim();
                    String idmonan;
                    String dates;

                    if (bitmap == null){
                        Toasty.warning(this,R.string.error_null_image).show();
                    }else if (StringUtils.isEmpty(name)){
                        Toasty.warning(this,R.string.error_null).show();
                    }else if (!ValidateUtils.isVaidFullName(name)){
                        Toasty.warning(this,R.string.error_validate).show();
                    }else if (StringUtils.isEmpty(price)){
                        Toasty.warning(this,R.string.error_null).show();
                    }else if (!ValidateUtils.isVaidFullName(name)){
                        Toasty.warning(this,R.string.error_validate).show();
                    } else if (StringUtils.isEmpty(description)){
                        Toasty.warning(this,R.string.error_null).show();
                    } else {
                        if (radioBuaTrua.isChecked()){
                            dates = "lunch";
                        }else if (radioBuatoi.isChecked()){
                            dates = "dinner";
                        }else {
                            dates = "day";
                        }

                        if (radioLau.isChecked()){
                            idmonan = "1";
                        }else if (radioDoAn.isChecked()){
                            idmonan = "2";
                        }else {
                            idmonan = "3";
                        }
                        showProgress(true);
                        String imageData = imageToString(bitmap);
                        presenter.postMonAn(name,price,description,idmonan,imageData,dates);
                    }
                }
        ));
        addDisposable(RxView.clicks(activityRegisterIvBack)
                .throttleFirst(2,TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(
                back -> {
                    onBackPressed();
                    finish();
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
        showProgress(false);
        showDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
           Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imgAnhMonAn.setImageBitmap(bitmap);
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

    @Override
    public void showSuccer() {
        showProgress(false);
        radioBuaTrua.setChecked(true);
        radioLau.setChecked(true);
        edNameAddDish.setText("");
        edDescriptionAdd.setText("");
        edPriceAddDish.setText("");
        imgAnhMonAn.setImageBitmap(bitmap1);
        Alerter.create(this)
                .setTitle(MainActivity.NAME)
                .setText("Thêm món ăn thành công")
                .setDuration(1000)
                .setBackgroundColorRes(R.color.bg_color_alert_dialog)
                .show();
    }

    @Override
    public void showProgress(boolean show) {
        if (show){
            LoadingDialog.getInstance().showLoading(this);
        }else {
            LoadingDialog.getInstance().hideLoading();
        }
    }

    @Override
    public void ShowError(int error) {
        showProgress(false);
        Toasty.error(this,error).show();
    }
}
