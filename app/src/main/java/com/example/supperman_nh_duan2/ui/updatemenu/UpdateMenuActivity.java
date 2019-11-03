package com.example.supperman_nh_duan2.ui.updatemenu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.supperman_nh_duan2.R;
import com.example.supperman_nh_duan2.api.Server;
import com.example.supperman_nh_duan2.base.BaseActivity;
import com.example.supperman_nh_duan2.model.LoadingDialog;
import com.example.supperman_nh_duan2.model.Menu;
import com.example.supperman_nh_duan2.untils.StringUtils;
import com.example.supperman_nh_duan2.untils.ValidateUtils;
import com.jakewharton.rxbinding3.view.RxView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.novoda.merlin.Bindable;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.NetworkStatus;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class UpdateMenuActivity extends BaseActivity implements Connectable, Disconnectable, Bindable,UpdateContract {

    public static void startActivity(Activity context, Menu menu){
        context.startActivity(new Intent(context,UpdateMenuActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .putExtra("MENU",menu));
        context.finish();
    }
    private UpdatePresenter presenter;

    @BindView(R.id.activity_register_iv_back_update)
    ImageView img_back;
    @BindView(R.id.img_update)
    RoundedImageView imgUpdate;
    @BindView(R.id.img_add_update)
    ImageView img_add_update;
    @BindView(R.id.radioBuaTrua_update)
    RadioButton radioBuaTrua_update;
    @BindView(R.id.radioBuatoi_update)
    RadioButton radioBuatoi_update;
    @BindView(R.id.radioCaNgay_update)
    RadioButton radioCaNgay_update;
    @BindView(R.id.radio_lau_update)
    RadioButton radio_lau_update;
    @BindView(R.id.radio_do_an_update)
    RadioButton radio_do_an_update;
    @BindView(R.id.radio_nuoc_update)
    RadioButton radio_nuoc_update;
    @BindView(R.id.edName_update)
    EditText edName_update;
    @BindView(R.id.edPrice_update)
    EditText edPrice_update;
    @BindView(R.id.edDescription_update)
    EditText edDescription_update;
    @BindView(R.id.btn_update_dish)
    Button btn_update_dish;
    Bitmap bitmap;

    Menu menu;
    @Override
    protected void onResume() {
        registerBindable(this);
        registerConnectable(this);
        registerDisconnectable(this);
        super.onResume();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_menu;
    }

    @Override
    protected Merlin initMerlin() {
        return new  Merlin.Builder().withConnectableCallbacks()
                .withDisconnectableCallbacks()
                .withBindableCallbacks()
                .build(this);
    }

    @Override
    protected void initData() {
        presenter = new UpdatePresenter(this,this);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void addEvents() {
        menu = getIntent().getParcelableExtra("MENU");
        loadAvatar(Server.duongdananh+menu.getImages(),imgUpdate);
        if (menu.getIdmonan() == 1){
            radio_lau_update.setChecked(true);
        }else if (menu.getIdmonan() == 2){
            radio_do_an_update.setChecked(true);
        }else if (menu.getIdmonan() == 3){
            radio_nuoc_update.setChecked(true);
        }

        if (menu.getDates().equals("day")){
            radioCaNgay_update.setChecked(true);
        }else if (menu.getDates().equals("lunch")){
            radioBuaTrua_update.setChecked(true);
        }else if (menu.getDates().equals("dinner")){
            radioBuatoi_update.setChecked(true);
        }
        edDescription_update.setText(menu.getDescriptions());
        edName_update.setText(menu.getNames());
        edPrice_update.setText(""+menu.getPrices());

        addDisposable(RxView.clicks(btn_update_dish)
                .throttleFirst(2, TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(unit -> {
            String name = edName_update.getText().toString().trim();
            String price = edPrice_update.getText().toString().trim();
            String description = edDescription_update.getText().toString().trim();
            String idmonan = null;
            String dates = null;
            String imageData = null;
            if (StringUtils.isEmpty(name)){
                Toasty.warning(this,R.string.error_null).show();
            }else if (!ValidateUtils.isVaidFullName(name)){
                Toasty.warning(this,R.string.error_validate).show();
            }else if (StringUtils.isEmpty(price)){
                Toasty.warning(this,R.string.error_null).show();
            }else if (!ValidateUtils.isVaidFullName(price)){
                Toasty.warning(this,R.string.error_validate).show();
            }else if (StringUtils.isEmpty(description)){
                Toasty.warning(this,R.string.error_null).show();
            }else if (!ValidateUtils.isVaidFullName(description)){
                Toasty.warning(this,R.string.error_validate).show();
            }else {

                if (radioBuaTrua_update.isChecked()) {
                    dates = "lunch";
                } else if (radioBuatoi_update.isChecked()) {
                    dates = "dinner";
                } else {
                    dates = "day";
                }

                if (radio_lau_update.isChecked()) {
                    idmonan = "1";
                } else if (radio_do_an_update.isChecked()) {
                    idmonan = "2";
                } else {
                    idmonan = "3";
                }

                showLoading(true);
                if (bitmap != null){
                    imageData = imageToString(bitmap);
                }else {
                    imageData = "";
                }
                presenter.postMonAn(name,price,description,idmonan,imageData,dates,menu.getId());

            }
        }));
        addDisposable(RxView.clicks(img_back)
                .throttleFirst(2,TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(unit -> {
            onBackPressed();
            finish();
        }));
        addDisposable(RxView.clicks(img_add_update)
                .throttleFirst(2,TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(unit -> {
            showImage();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imgUpdate.setImageBitmap(bitmap);
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
    public void showLoading(boolean show) {
        if (show){
            LoadingDialog.getInstance().showLoading(this);
        }else {
            LoadingDialog.getInstance().hideLoading();
        }
    }

    @Override
    public void ShowError(int error) {
        showLoading(false);
        Toasty.error(this,error).show();
    }

    @Override
    public void ShowSuccer() {
        showLoading(false);
        finish();
    }
}
