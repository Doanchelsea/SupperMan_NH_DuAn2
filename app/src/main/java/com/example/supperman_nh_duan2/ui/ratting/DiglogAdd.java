package com.example.supperman_nh_duan2.ui.ratting;

import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.example.supperman_nh_duan2.R;
import com.example.supperman_nh_duan2.base.BaseDiglog;
import com.example.supperman_nh_duan2.untils.SoUtils;
import com.example.supperman_nh_duan2.untils.StringUtils;
import com.jakewharton.rxbinding3.view.RxView;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class DiglogAdd extends BaseDiglog {

    @BindView(R.id.txtMessage)
    TextView edSoban;
    @BindView(R.id.btnCancel)
    Button btnCancel;
    @BindView(R.id.btnAccept)
    Button btnAccept;
    private Listener listener;

    public static DiglogAdd newInstance() {
        Bundle args = new Bundle();
        DiglogAdd fragment = new DiglogAdd();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (Listener) getParentFragment();
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
    protected void addEvents() {
        addDisposable(RxView.clicks(btnCancel).subscribe(unit -> {
            hideDialog();
        }));
        addDisposable(RxView.clicks(btnAccept).subscribe(unit -> {
            hideDialog();
            listener.add();
        }));
    }

    @Override
    protected void configViews() {

    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_add_ban_an;
    }

    @Override
    protected void initDialog() {
        Window window = getDialog().getWindow();
        setTransparentDialog(window);
    }
}
