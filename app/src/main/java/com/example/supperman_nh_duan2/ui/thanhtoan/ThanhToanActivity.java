package com.example.supperman_nh_duan2.ui.thanhtoan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.supperman_nh_duan2.R;
import com.example.supperman_nh_duan2.adapter.ThanhToanAdapter;
import com.example.supperman_nh_duan2.base.BaseActivity;
import com.example.supperman_nh_duan2.model.Menu;
import com.example.supperman_nh_duan2.model.ThanhToan;
import com.example.supperman_nh_duan2.ui.menu.detail.MenuDetailActivity;
import com.example.supperman_nh_duan2.untils.FormatUtils;
import com.jakewharton.rxbinding3.view.RxView;
import com.novoda.merlin.Bindable;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.NetworkStatus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ThanhToanActivity extends BaseActivity implements Connectable, Disconnectable, Bindable,ThanhToanContract {
    int ban;
    @BindView(R.id.tv_ban_thanh_toan)
    TextView tv_ban_thanh_toan;
    @BindView(R.id.activity_register_iv_back_thanh_toan)
    ImageView img_thanh_toan;
    @BindView(R.id.recy_thanh_toan)
    RecyclerView recy_thanh_toan;
    @BindView(R.id.tv_price_thanh_toan)
    TextView tv_price_thanh_toan;
    @BindView(R.id.tv_thanh_toan)
    TextView tv_thanh_toan;
    private ThanhToanPresenter presenter;
    List<ThanhToan> thanhToans1 = new ArrayList<>();
    LinearLayoutManager manager = new LinearLayoutManager(this);
    ThanhToanAdapter adapter;


    public static void startActivity(Activity context, int ban){
        context.startActivity(new Intent(context, ThanhToanActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .putExtra("BAN", ban));
    }

    @Override
    protected void onResume() {
        registerConnectable(this);
        registerDisconnectable(this);
        registerBindable(this);
        super.onResume();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_thanh_toan;
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
        presenter = new ThanhToanPresenter(this,this);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void addEvents() {
        ban = getIntent().getIntExtra("BAN",0);
        tv_ban_thanh_toan.setText("Bàn "+ban);
        addDisposable(RxView.clicks(img_thanh_toan).subscribe(unit -> {
            onBackPressed();
            finish();
        }));
        presenter.getData(thanhToans1,ban);


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
    public void ShowList(List<ThanhToan> thanhToans) {
        adapter = new ThanhToanAdapter(this,thanhToans);
        recy_thanh_toan.setHasFixedSize(true);
        recy_thanh_toan.setLayoutManager(manager);
        recy_thanh_toan.setAdapter(adapter);
    }

    @Override
    public void ShowPrice(double tong,double dir, int idser) {
        tv_price_thanh_toan.setText(FormatUtils.convertEstimatedPrice(tong - tong*(dir/100))+ " VNĐ");

    }


}
