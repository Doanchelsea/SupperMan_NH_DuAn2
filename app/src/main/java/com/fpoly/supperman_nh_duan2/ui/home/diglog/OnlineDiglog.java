package com.fpoly.supperman_nh_duan2.ui.home.diglog;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.supperman_nh_duan2.R;
import com.fpoly.supperman_nh_duan2.base.BaseDiglog;
import com.fpoly.supperman_nh_duan2.lisenner.AddOnlineLisenner;
import com.fpoly.supperman_nh_duan2.lisenner.PeopleLisenner;
import com.fpoly.supperman_nh_duan2.model.People;
import com.fpoly.supperman_nh_duan2.model.manage.Manage;
import com.fpoly.supperman_nh_duan2.ui.home.adapter.PeopleAdapter;
import com.fpoly.supperman_nh_duan2.ui.home.diglog.contract.OnlineDialogContract;
import com.fpoly.supperman_nh_duan2.ui.home.diglog.presenter.OnlineDialogPresenter;
import com.fpoly.supperman_nh_duan2.untils.SoUtils;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class OnlineDiglog extends BaseDiglog implements OnlineDialogContract, PeopleLisenner {

    @BindView(R.id.tv_so_ban_online_detail_dialog)
    TextView tv_so_ban_online_detail_dialog;
    @BindView(R.id.recyclerView_dialog_online_detail)
    RecyclerView recyclerView_dialog_online_detail;
    @BindView(R.id.btn_cancel_online_detail)
    Button btn_cancel_online_detail;
    @BindView(R.id.btn_success_online_detail)
    Button btn_success_online_detail;
    private AddOnlineLisenner listener;
    private List<People> list = new ArrayList<>();
    private PeopleAdapter peopleAdapter;
    private LinearLayoutManager manager;
    private OnlineDialogPresenter presenter;
    int peoples = 0;
    Manage manage;


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
    public static OnlineDiglog newInstance(Manage manage) {
        Bundle args = new Bundle();
        args.putParcelable("MANAGE",manage);
        OnlineDiglog fragment = new OnlineDiglog();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.item_manage_dialog;
    }

    @Override
    protected void initDialog() {
        peopleAdapter = new PeopleAdapter(activity,list,this);
        manager = new LinearLayoutManager(activity);
        recyclerView_dialog_online_detail.setLayoutManager(manager);
        recyclerView_dialog_online_detail.setHasFixedSize(true);
        recyclerView_dialog_online_detail.setAdapter(peopleAdapter);
    }

    @Override
    protected void addEvents() {
        tv_so_ban_online_detail_dialog.setText("Bàn ăn : ");
        presenter = new OnlineDialogPresenter(activity,this);
        manage = getArguments().getParcelable("MANAGE");
        addDisposable(RxView.clicks(btn_cancel_online_detail).throttleFirst(1,TimeUnit.SECONDS)
        .subscribe(unit -> {
            hideDialog();
        }));

        addDisposable(RxView.clicks(btn_success_online_detail).throttleFirst(1,TimeUnit.SECONDS)
        .subscribe(unit -> {
            if (peoples == 0){
                Toasty.warning(activity,R.string.error_null_ban_an).show();
                return;
            }else {
                listener.onclick(manage,peoples);
                hideDialog();
            }
        }));
        presenter.getData(manage.getIdnhahang(),list);
    }

    @Override
    protected void configViews() {

    }

    @Override
    protected void initDatas() {
        Window window = getDialog().getWindow();
        setTransparentDialog(window);
    }

    @Override
    public void showSuccess() {
        peopleAdapter.notifyDataSetChanged();
    }

    @Override
    public void error(int error) {
        Toasty.error(activity,error).show();
    }

    @Override
    public void peoole(int people) {
        peoples = people;
        tv_so_ban_online_detail_dialog.setText("Bàn ăn : "+people);
    }
}
