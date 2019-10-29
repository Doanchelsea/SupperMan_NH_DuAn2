package com.example.supperman_nh_duan2.ui.ratting;

import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.supperman_nh_duan2.R;
import com.example.supperman_nh_duan2.base.BaseDiglog;
import com.example.supperman_nh_duan2.ui.home.diglog.ConfirmDialog;
import com.jakewharton.rxbinding3.view.RxView;

import butterknife.BindView;

public class DiglogDelete extends BaseDiglog {

    @BindView(R.id.dialog_confirm_tv_message_xoa_ban)
    TextView tv_Messing;
    @BindView(R.id.dialog_confirm_btn_cancel_xoa_ban)
    Button btnCancel;
    @BindView(R.id.dialog_confirm_btn_clear_all_notification_xoa_ban)
    Button btnClearAllNotification;
    private LisennerDelete lisenner;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            lisenner = (LisennerDelete) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("fragment must implement Listener");
        }
    }
    @Override
    public void onDestroyView() {
        lisenner = null;
        super.onDestroyView();
    }
    public static DiglogDelete newInstance(int ban) {
        Bundle args = new Bundle();
        args.putInt("BAN",ban);
        DiglogDelete fragment = new DiglogDelete();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.diglog_xoa_ban;
    }

    @Override
    protected void initDialog() {

    }

    @Override
    protected void addEvents() {
        int ban = getArguments().getInt("BAN",0);

        tv_Messing.setText("Bạn có muốn xóa bàn "+ban+" không ?");
        addDisposable(RxView.clicks(btnCancel)
                .subscribe(aVoid -> hideDialog()));

        // clear all dialog
        addDisposable(RxView.clicks(btnClearAllNotification)
                .subscribe(aVoid -> {
                    lisenner.onClick(ban);
                    hideDialog();
                }));
    }

    @Override
    protected void configViews() {

    }

    @Override
    protected void initDatas() {
        Window window = getDialog().getWindow();
        setTransparentDialog(window);
    }
}
