package com.example.supperman_nh_duan2.ui.thanhtoan.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.supperman_nh_duan2.R;
import com.example.supperman_nh_duan2.base.BaseDiglog;
import com.example.supperman_nh_duan2.lisenner.LisennerDelete;
import com.example.supperman_nh_duan2.lisenner.LisennerDeleteThanhToan;
import com.example.supperman_nh_duan2.lisenner.ThanhToanLisenner;
import com.example.supperman_nh_duan2.untils.FormatUtils;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class DiglogDelete extends BaseDiglog {

    @BindView(R.id.btn_cancel_xoa_thanh_toan)
    Button buttonCancel;
    @BindView(R.id.btn_xac_nhan_xoa_thanh_toan)
    Button buttonXacnhan;
    private LisennerDeleteThanhToan listener;
    @Override
    protected int getLayoutId() {
        return R.layout.diglog_xoa_thanh_toan;
    }

    public static DiglogDelete getInstance(int id){
        Bundle args = new Bundle();
        args.putInt("ID",id);
        DiglogDelete fragment = new DiglogDelete();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (LisennerDeleteThanhToan) activity;
    }
    @Override
    public void onDestroyView() {
        listener = null;
        super.onDestroyView();
    }
    @Override
    protected void initDialog() {
        Window window = getDialog().getWindow();
        setTransparentDialog(window);
    }

    @Override
    protected void addEvents() {
        int iduser = getArguments().getInt("ID",0);
        addDisposable(RxView.clicks(buttonCancel)
                .throttleFirst(2, TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(unit -> {
                    hideDialog();
                }));
        addDisposable(RxView.clicks(buttonXacnhan)
                .throttleFirst(2,TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(unit -> {
                    listener.onClick(iduser);
                    hideDialog();
                }));
    }

    @Override
    protected void configViews() {

    }

    @Override
    protected void initDatas() {

    }
}
