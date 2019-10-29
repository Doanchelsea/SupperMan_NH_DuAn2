package com.example.supperman_nh_duan2.ui.home.diglog;

import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import com.example.supperman_nh_duan2.R;
import com.example.supperman_nh_duan2.base.BaseDiglog;
import com.example.supperman_nh_duan2.lisenner.ManageLisenner;
import com.example.supperman_nh_duan2.model.manage.Manage;
import com.jakewharton.rxbinding3.view.RxView;

import butterknife.BindView;

public class DiglogManage extends BaseDiglog {

    @BindView(R.id.btn_cance)
    Button btn_cancel;
    @BindView(R.id.btn_huy_mon)
    Button btn_huy_mon;
    @BindView(R.id.btn_xac_nhan)
    Button btn_xac_nhan;
    private ManageLisenner listener;
    public static DiglogManage newInstance(Manage manage) {
        Bundle args = new Bundle();
        args.putInt("ID",manage.getId());
        args.putParcelable("MANAGE",manage);
        DiglogManage fragment = new DiglogManage();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ManageLisenner) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("fragment must implement Listener");
        }
    }
    @Override
    public void onDestroyView() {
        listener = null;
        super.onDestroyView();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.diglog_manage;
    }

    @Override
    protected void initDialog() {

    }

    @Override
    protected void addEvents() {
        addDisposable(RxView.clicks(btn_cancel).subscribe(unit -> {
            hideDialog();
        }));
        addDisposable(RxView.clicks(btn_huy_mon).subscribe(unit -> {
            int id = getArguments().getInt("ID",0);
            hideDialog();
            listener.onClick(id);
        }));
        addDisposable(RxView.clicks(btn_xac_nhan).subscribe(unit -> {
            Manage manage = getArguments().getParcelable("MANAGE");
            hideDialog();
            listener.onAdd(manage);
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
