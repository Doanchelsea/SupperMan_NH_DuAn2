package com.example.supperman_nh_duan2.ui.thanhtoan.dialog;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.supperman_nh_duan2.R;
import com.example.supperman_nh_duan2.base.BaseDiglog;
import com.example.supperman_nh_duan2.lisenner.ConfimLisenner;
import com.example.supperman_nh_duan2.lisenner.ThanhToanLisenner;
import com.example.supperman_nh_duan2.untils.FormatUtils;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class DiglogThanhToan extends BaseDiglog {

    @BindView(R.id.tv_text_thanh_toan)
    TextView tv_text_thanh_toan;
    @BindView(R.id.btn_cancel_xoa_thanh_toan)
    Button buttonCancel;
    @BindView(R.id.btn_xac_nhan_thanh_toan)
    Button buttonXacnhan;
    private ThanhToanLisenner listener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (ThanhToanLisenner) activity;
    }
    @Override
    public void onDestroyView() {
        listener = null;
        super.onDestroyView();
    }
    public static DiglogThanhToan getInstance(double price,int iduser,int ban){
        Bundle args = new Bundle();
        args.putDouble("PRICE",price);
        args.putInt("ID",iduser);
        args.putInt("BAN",ban);
        DiglogThanhToan fragment = new DiglogThanhToan();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.diglog_thanh_toan;
    }

    @Override
    protected void initDialog() {
        Window window = getDialog().getWindow();
        setTransparentDialog(window);
    }

    @Override
    protected void addEvents() {
        double pirce = getArguments().getDouble("PRICE",0);
        int iduser = getArguments().getInt("ID",0);
        int ban = getArguments().getInt("BAN",0);
        tv_text_thanh_toan.setText("Số tiền thanh toán là : " + FormatUtils.convertEstimatedPrice(pirce)+ " VNĐ");
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
            listener.onClick(iduser,pirce,ban);
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
