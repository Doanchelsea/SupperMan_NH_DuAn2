package com.fpoly.supperman_nh_duan2.ui.history;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;

import com.fpoly.supperman_nh_duan2.R;
import com.fpoly.supperman_nh_duan2.adapter.HistoryAdapter;
import com.fpoly.supperman_nh_duan2.base.BaseActivity;
import com.fpoly.supperman_nh_duan2.lisenner.HistoryLisenner;
import com.fpoly.supperman_nh_duan2.model.History;
import com.fpoly.supperman_nh_duan2.model.loadmore.EndlessRecyclerViewScrollListener;
import com.fpoly.supperman_nh_duan2.ui.history.detail.HistoryDetailActivity;
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

public class HistoryActivity extends BaseActivity implements Connectable, Disconnectable, Bindable, HistoryContract, HistoryLisenner {

    public static void startActivity(Activity context){
        context.startActivity(new Intent(context, HistoryActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    int page = 1;
    int space = 10;
    private List<History> histories = new ArrayList<>();
    private LinearLayoutManager manager = new LinearLayoutManager(this);
    private HistoryAdapter historyAdapter;
    @BindView(R.id.activity_register_iv_back_history)
    ImageView imgBack;
    @BindView(R.id.re_history_ok)
    RecyclerView recyclerView;
    private HistoryPresenter presenter;

    @Override
    protected void onResume() {
        super.onResume();
        registerBindable(this);
        registerConnectable(this);
        registerDisconnectable(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_history;
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
        presenter = new HistoryPresenter(this,this);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void addEvents() {
        initAdapter();
        presenter.getData(histories,page,space);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                presenter.getData(histories,page+1,space);
            }
        });

        addDisposable(RxView.clicks(imgBack).throttleFirst(2,TimeUnit.SECONDS)
        .compose(bindToLifecycle())
        .subscribe(unit -> {
            onBackPressed();
            finish();
        }));

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
    public void showList(List<History> histories, boolean show) {
        historyAdapter.notifyDataSetChanged();
        
        if (show == false){
            historyAdapter.setOnLoadMore(false);
        }
    }

    @Override
    public void showError(int error) {
        Toasty.error(this,error).show();
    }

    @Override
    public void onClick(int iduser,String idtrangthai) {
        HistoryDetailActivity.startActivity(this,iduser,idtrangthai);
    }

    private void initAdapter(){
        historyAdapter = new HistoryAdapter(this,histories,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(historyAdapter);
    }
}
