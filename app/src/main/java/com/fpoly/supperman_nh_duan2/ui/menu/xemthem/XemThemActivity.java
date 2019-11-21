package com.fpoly.supperman_nh_duan2.ui.menu.xemthem;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.fpoly.supperman_nh_duan2.R;
import com.fpoly.supperman_nh_duan2.adapter.XemthemAdapter;
import com.fpoly.supperman_nh_duan2.base.BaseActivity;
import com.fpoly.supperman_nh_duan2.lisenner.DatMonLisenner;
import com.fpoly.supperman_nh_duan2.model.Menu;
import com.fpoly.supperman_nh_duan2.model.loadmore.EndlessRecyclerViewScrollListener;
import com.fpoly.supperman_nh_duan2.ui.menu.detail.MenuDetailActivity;
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
import es.dmoral.toasty.Toasty;

public class XemThemActivity extends BaseActivity implements Connectable, Disconnectable, Bindable,XemthemContract, DatMonLisenner {
    public static void startActivity(Activity context, int idmon){
        context.startActivity(new Intent(context, XemThemActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .putExtra("IDMON", idmon));
    }
    private List<Menu> menus = new ArrayList<>();
    private LinearLayoutManager manager = new LinearLayoutManager(this);
    private XemthemAdapter xemthemAdapter;
    @BindView(R.id.img_xem_them_back)
    ImageView imgBack;
    @BindView(R.id.tv_name_xem_them)
    TextView tvName;
    @BindView(R.id.re_xem_them)
    RecyclerView recyclerView;
    private XemthemPresenter xemthemPresenter;
    int page = 1;

    @Override
    protected void onResume() {
        registerBindable(this);
        registerConnectable(this);
        registerDisconnectable(this);
        super.onResume();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_xem_them;
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
        xemthemPresenter = new XemthemPresenter(this,this);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void addEvents() {
        int idmon = getIntent().getIntExtra("IDMON",0);
        if (idmon == 1){
            tvName.setText("Lẩu nướng");
        }else if (idmon == 2){
            tvName.setText("Đồ ăn");
        }else if (idmon == 3){
            tvName.setText("Nước uống");
        }
        addDisposable(RxView.clicks(imgBack).throttleFirst(2, TimeUnit.SECONDS)
        .compose(bindToLifecycle())
        .subscribe(unit -> {
            onBackPressed();
            finish();
        }));

        xemthemAdapter = new XemthemAdapter(this,menus,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(xemthemAdapter);

        xemthemPresenter.getData(menus,String.valueOf(idmon),page);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                xemthemPresenter.getData(menus,String.valueOf(idmon),page+1);
            }
        });
    }

    @Override
    public void onBind(NetworkStatus networkStatus) {
        if (!networkStatus.isAvailable()){
            onDisconnect();
        }else {
            hideDialog();
        }
    }

    @Override
    public void onConnect() {
        hideDialog();
    }

    @Override
    public void onDisconnect() {
        showDialog();
    }

    @Override
    public void showlist(List<Menu> menus, boolean show) {
        xemthemAdapter.notifyDataSetChanged();
        if (show == false){
            xemthemAdapter.setOnLoadMore(show);
        }
    }

    @Override
    public void showError(int error) {
        Toasty.error(this,error).show();
    }

    @Override
    public void onclick(Menu menu) {
        MenuDetailActivity.startActivity(this,menu);
        finish();
    }
}
