package com.fpoly.supperman_nh_duan2.ui.dish;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;

import com.fpoly.supperman_nh_duan2.R;
import com.fpoly.supperman_nh_duan2.adapter.MenuAdapter;
import com.fpoly.supperman_nh_duan2.base.BaseActivity;
import com.fpoly.supperman_nh_duan2.model.Menu;
import com.novoda.merlin.Bindable;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.NetworkStatus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DishActivity extends BaseActivity implements Connectable, Disconnectable, Bindable {

    @BindView(R.id.recyMenu)
    RecyclerView recyclerView;
    List<Menu> lauList = new ArrayList<>();
    LinearLayoutManager manager = new LinearLayoutManager(this);
    MenuAdapter lauAdapter;

    public static void startActivity(Activity context){
        context.startActivity(new Intent(context, DishActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        context.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerConnectable(this);
        registerDisconnectable(this);
        registerBindable(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dish;
    }

    @Override
    protected Merlin initMerlin() {
        return new Merlin.Builder()
                .withConnectableCallbacks()
                .withDisconnectableCallbacks()
                .withBindableCallbacks()
                .build(this);
    }

    @Override
    protected void initData() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void addEvents() {



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
}
