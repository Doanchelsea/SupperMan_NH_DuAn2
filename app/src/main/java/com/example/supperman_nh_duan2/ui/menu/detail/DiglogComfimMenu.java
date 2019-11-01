package com.example.supperman_nh_duan2.ui.menu.detail;

import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.supperman_nh_duan2.R;
import com.example.supperman_nh_duan2.base.BaseDiglog;
import com.example.supperman_nh_duan2.lisenner.DeleteMenuLisenner;
import com.example.supperman_nh_duan2.model.Menu;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class DiglogComfimMenu extends BaseDiglog {

    @BindView(R.id.btn_cancel_xoa_menu)
    Button btn_cancel_xoa_menu;
    @BindView(R.id.btn_xac_nhan_xoa_menu)
    Button btn_xac_nhan_xoa_menu;
    @BindView(R.id.tv_text_xoa_menu)
    TextView tv_text_xoa_menu;
    Menu menu;
    private DeleteMenuLisenner listener;

    public static DiglogComfimMenu newInstance(Menu menu) {
        Bundle args = new Bundle();
        args.putParcelable("MENU",menu);
        DiglogComfimMenu fragment = new DiglogComfimMenu();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (DeleteMenuLisenner) activity;
    }

    @Override
    public void onDestroyView() {
        listener = null;
        super.onDestroyView();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.diglog_xoa_menu;
    }

    @Override
    protected void initDialog() {
    }

    @Override
    protected void addEvents() {
        menu = getArguments().getParcelable("MENU");
        tv_text_xoa_menu.setText("Bạn có muốn xóa món "+menu.getNames()+" không ?");
        addDisposable(RxView.clicks(btn_cancel_xoa_menu)
                .throttleFirst(2, TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(unit -> {
            hideDialog();
        }));
        addDisposable(RxView.clicks(btn_xac_nhan_xoa_menu)
                .throttleFirst(2,TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(unit -> {
            listener.onClick(menu.getId());
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
