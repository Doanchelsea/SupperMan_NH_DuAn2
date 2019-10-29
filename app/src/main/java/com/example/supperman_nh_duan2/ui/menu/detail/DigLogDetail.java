package com.example.supperman_nh_duan2.ui.menu.detail;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.supperman_nh_duan2.R;
import com.example.supperman_nh_duan2.base.BaseDiglog;
import com.example.supperman_nh_duan2.untils.SoUtils;
import com.jakewharton.rxbinding3.view.RxView;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;


public class DigLogDetail extends BaseDiglog {
    @BindView(R.id.ed_ban_an)
    EditText ed_ban_an;
    @BindView(R.id.btnCancel_ban_an)
    Button btnCancel;
    @BindView(R.id.btnAccept_ban_an)
    Button btnAccept;
    private ListennerDetail listener;

    public static DigLogDetail newInstance(int soban) {
        Bundle args = new Bundle();
        args.putInt("SOBAN",soban);
        DigLogDetail fragment = new DigLogDetail();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (ListennerDetail) activity;
    }

    @Override
    public void onDestroyView() {
        listener = null;
        super.onDestroyView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_add_manage;
    }

    @Override
    protected void initDialog() {
        Window window = getDialog().getWindow();
        setTransparentDialog(window);
    }

    @Override
    protected void addEvents() {
      int soban = getArguments().getInt("SOBAN",0);
        addDisposable(RxView.clicks(btnCancel).subscribe(unit -> {
            hideDialog();
        }));
        addDisposable(RxView.clicks(btnAccept).subscribe(unit -> {
            String banan = ed_ban_an.getText().toString().trim();
            if (!SoUtils.isVaidSo(banan)){
                Toasty.warning(activity,"Đây không phải là số").show();
            }else if (Integer.valueOf(banan) > soban){
                Toasty.warning(activity,"Bàn ăn không tồn tại").show();
            }else {
                hideDialog();
                listener.success(banan);
            }
        }));
    }

    @Override
    protected void configViews() {

    }

    @Override
    protected void initDatas() {

    }
}
