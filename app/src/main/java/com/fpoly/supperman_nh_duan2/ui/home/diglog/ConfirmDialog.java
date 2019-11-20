package com.fpoly.supperman_nh_duan2.ui.home.diglog;

import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import com.fpoly.supperman_nh_duan2.R;
import com.fpoly.supperman_nh_duan2.base.BaseDiglog;
import com.fpoly.supperman_nh_duan2.lisenner.ConfimLisenner;
import com.fpoly.supperman_nh_duan2.model.manage.Manage;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class ConfirmDialog extends BaseDiglog {

    @BindView(R.id.dialog_confirm_btn_cancel)
    Button btnCancel;
    @BindView(R.id.dialog_confirm_btn_clear_all_notification)
    Button btnClearAllNotification;
    private ConfimLisenner listener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
            listener = (ConfimLisenner) activity;
    }
    @Override
    public void onDestroyView() {
        listener = null;
        super.onDestroyView();
    }

    public static ConfirmDialog newInstance(Manage manage) {
        Bundle args = new Bundle();
        args.putParcelable("MANAGE",manage);
        ConfirmDialog fragment = new ConfirmDialog();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.dialog_confirm_clear_online;
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void configViews() {

    }

    @Override
    protected void initDialog() {
        Window window = getDialog().getWindow();
        setTransparentDialog(window);
    }

    @Override
    protected void addEvents() {
        // cancel
        addDisposable(RxView.clicks(btnCancel)
                .throttleFirst(2,TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(aVoid -> hideDialog()));

        // clear all dialog
        addDisposable(RxView.clicks(btnClearAllNotification)
                .throttleFirst(2,TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(aVoid -> {
                  Manage manage =   getArguments().getParcelable("MANAGE");
                    listener.onClick(manage);
                    hideDialog();
                }));
    }
}
