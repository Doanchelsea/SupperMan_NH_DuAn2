package com.example.supperman_nh_duan2.ui.home.diglog;

import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.supperman_nh_duan2.R;
import com.example.supperman_nh_duan2.base.BaseDiglog;
import com.example.supperman_nh_duan2.lisenner.AddOnlineLisenner;
import com.example.supperman_nh_duan2.lisenner.ConfimLisenner;
import com.example.supperman_nh_duan2.model.manage.Manage;
import com.example.supperman_nh_duan2.untils.SoUtils;
import com.example.supperman_nh_duan2.untils.StringUtils;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class OnlineDiglog extends BaseDiglog {

    @BindView(R.id.ed_ban_an)
    EditText ed_ban_an;
    @BindView(R.id.btnCancel_ban_an)
    Button btnCancel_ban_an;
    @BindView(R.id.btnAccept_ban_an)
    Button btnAccept_ban_an;
    private AddOnlineLisenner listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (AddOnlineLisenner) activity;
    }
    @Override
    public void onDestroyView() {
        listener = null;
        super.onDestroyView();
    }
    public static OnlineDiglog newInstance(Manage manage, int ban) {
        Bundle args = new Bundle();
        args.putParcelable("MANAGE",manage);
        args.putInt("BAN",ban);
        OnlineDiglog fragment = new OnlineDiglog();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.dialog_add_manage;
    }

    @Override
    protected void initDialog() {

    }

    @Override
    protected void addEvents() {
        addDisposable(RxView.clicks(btnCancel_ban_an)
                .throttleFirst(2, TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(unit -> {
            hideDialog();
        }));
        addDisposable(RxView.clicks(btnAccept_ban_an)
                .throttleFirst(2,TimeUnit.SECONDS)
                .compose(bindToLifecycle())
                .subscribe(unit -> {
            String banan = ed_ban_an.getText().toString().trim();
            if (!SoUtils.isVaidSo(banan)){
                Toasty.warning(activity,"Đây không phải số").show();
            }else if (Integer.valueOf(banan) <= 0){
                Toasty.warning(activity,"Bàn ăn phải lớn hơn 0").show();
            } else {
                int ban = getArguments().getInt("BAN",0);
                if (Integer.valueOf(banan) > ban){
                    Toasty.warning(activity,"Bàn ăn không tồn tại").show();
                }else {
                    Manage manage = getArguments().getParcelable("MANAGE");
                    listener.onclick(manage,Integer.valueOf(banan));
                    hideDialog();
                }
            }
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
