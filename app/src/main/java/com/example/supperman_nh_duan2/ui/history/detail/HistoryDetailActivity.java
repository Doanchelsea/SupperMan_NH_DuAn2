package com.example.supperman_nh_duan2.ui.history.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.supperman_nh_duan2.R;
import com.example.supperman_nh_duan2.adapter.ThanhToanAdapter;
import com.example.supperman_nh_duan2.api.Server;
import com.example.supperman_nh_duan2.base.BaseActivity;
import com.example.supperman_nh_duan2.model.ThanhToan;
import com.example.supperman_nh_duan2.ui.history.HistoryActivity;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.jakewharton.rxbinding3.view.RxView;
import com.novoda.merlin.Bindable;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.NetworkStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class HistoryDetailActivity extends BaseActivity implements Connectable, Disconnectable, Bindable,HistoryDetailContract {

    @BindView(R.id.relativeLayout_history)
    RelativeLayout relativeLayout;
    @BindView(R.id.img_user_history)
    CircleImageView img_user_history;
    @BindView(R.id.tv_name_user_history)
    TextView tv_name_user_history;
    @BindView(R.id.re_history_detail)
    RecyclerView re_history_detail;
    @BindView(R.id.activity_register_iv_back_history_detail)
    ImageView imgBack;
    private List<ThanhToan> thanhToans = new ArrayList<>();
    private ThanhToanAdapter thanhToanAdapter;
    private LinearLayoutManager manager = new LinearLayoutManager(this);

    public static void startActivity(Activity context,int iduser,String idtrangthai){
        context.startActivity(new Intent(context, HistoryDetailActivity.class)
                .putExtra("IDUSER",iduser)
                .putExtra("IDTRANGTHAI",idtrangthai)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
    private HistoryDetailPresenter presenter;
    @Override
    protected void onResume() {
        super.onResume();
        registerBindable(this);
        registerConnectable(this);
        registerDisconnectable(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_history_detail;
    }

    @Override
    protected Merlin initMerlin() {
        return new Merlin.Builder().withConnectableCallbacks()
                .withBindableCallbacks()
                .withDisconnectableCallbacks()
                .build(this);
    }

    @Override
    protected void initData() {
        presenter = new HistoryDetailPresenter(this,this);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void addEvents() {
        addDisposable(RxView.clicks(imgBack).throttleFirst(2, TimeUnit.SECONDS)
        .compose(bindToLifecycle())
        .subscribe(unit -> {
            onBackPressed();
            finish();
        }));
        Intent intent = getIntent();
        int iduser = intent.getIntExtra("IDUSER",0);
        String idtrangthai = intent.getStringExtra("IDTRANGTHAI");
        presenter.getData(thanhToans,idtrangthai);
        if (iduser == 0){
            gone(tv_name_user_history);
            gone(relativeLayout);
        }else {
            presenter.getUser(iduser);
        }


    }

    @Override
    public void onBind(NetworkStatus networkStatus) {
        if (!networkStatus.isAvailable()){
            onDisconnect();
        }
    }

    @Override
    public void onConnect() {

    }

    @Override
    public void onDisconnect() {
        showToastDisconnect();
    }

    @Override
    public void ShowUser(String name, String images) {
        loadAvatar(Server.duongdananh+images,img_user_history);
        loadFullName(name,tv_name_user_history);
    }

    @Override
    public void ShowList(List<ThanhToan> thanhToans) {
        thanhToanAdapter = new ThanhToanAdapter(this,thanhToans);
        re_history_detail.setLayoutManager(manager);
        re_history_detail.setAdapter(thanhToanAdapter);
    }

    @Override
    public void showError(int error) {
        Toasty.error(this,error).show();
    }
}