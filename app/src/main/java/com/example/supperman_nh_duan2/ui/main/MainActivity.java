package com.example.supperman_nh_duan2.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.supperman_nh_duan2.R;
import com.example.supperman_nh_duan2.api.eventbus.NewEvent;
import com.example.supperman_nh_duan2.base.BaseActivity;
import com.example.supperman_nh_duan2.ui.home.HomeFragment;
import com.example.supperman_nh_duan2.ui.menu.MenuFragmnet;
import com.example.supperman_nh_duan2.ui.ratting.RattingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.novoda.merlin.Bindable;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.NetworkStatus;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class MainActivity extends BaseActivity implements Connectable, Disconnectable, Bindable {

    public static String ID;
    public static String NAME;
    public static String IMAGE;
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottomNavigationView;
    private Fragment activeFragment;
    private HomeFragment homeFragment = HomeFragment.newInstance();
    private MenuFragmnet historyFragment = MenuFragmnet.newInstance();
    private RattingFragment accountFragment = RattingFragment.newInstance();

    @Override
    protected void onResume() {
        super.onResume();
        registerConnectable(this);
        registerDisconnectable(this);
        registerBindable(this);
    }

    public static void startActivity(Activity context, int id, String name,String images){
         context.startActivity(new Intent(context,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        .putExtra("ID",id).putExtra("NAME",name).putExtra("IMAGE",images));
         context.finish();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
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
        Intent intent = getIntent();
        ID = String.valueOf(intent.getIntExtra("ID",0));
        NAME = intent.getStringExtra("NAME");
        IMAGE = intent.getStringExtra("IMAGE");
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void addEvents() {
        activeFragment = homeFragment;
        loadAllFragment();
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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


    private BottomNavigationView.OnNavigationItemSelectedListener
            mOnNavigationItemSelectedListener = menuItem -> {
        switch (menuItem.getItemId()) {
            case R.id.menu_navigation_home:
                loadFragment(activeFragment, homeFragment);
                activeFragment = homeFragment;
                return true;
            case R.id.menu_navigation_menu:
                loadFragment(activeFragment, historyFragment);
                activeFragment = historyFragment;
                return true;
            case R.id.menu_navigation_ratting:
                loadFragment(activeFragment, accountFragment);
                activeFragment = accountFragment;
                return true;
        }
        return false;
    };

    private void loadFragment(Fragment activeFragment, Fragment showFragment) {
        getSupportFragmentManager().beginTransaction().hide(activeFragment).show(showFragment).commit();
    }

    private void loadAllFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container, accountFragment, "3").hide(accountFragment).commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container, historyFragment, "2").hide(historyFragment).commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container, homeFragment, "1").commit();
    }

}
