package com.fpoly.supperman_nh_duan2.ui.main;

import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.fpoly.supperman_nh_duan2.R;
import com.fpoly.supperman_nh_duan2.base.BaseActivity;
import com.fpoly.supperman_nh_duan2.model.local.AppPreferencesHelper;
import com.fpoly.supperman_nh_duan2.model.local.DataManager;
import com.fpoly.supperman_nh_duan2.ui.home.HomeFragment;
import com.fpoly.supperman_nh_duan2.ui.menu.MenuFragmnet;
import com.fpoly.supperman_nh_duan2.ui.ratting.RattingFragment;
import com.fpoly.supperman_nh_duan2.ui.thongtin.ThongTinFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.novoda.merlin.Bindable;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.NetworkStatus;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements Connectable, Disconnectable, Bindable {

    public static String ID;
    public static String NAME;
    public static String IMAGE;

    private SharedPreferences mPrefs;
    private AppPreferencesHelper appPreferencesHelper;
    private DataManager dataManager;

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottomNavigationView;
    private Fragment activeFragment;
    private HomeFragment homeFragment = HomeFragment.newInstance();
    private MenuFragmnet menuFragmnet = MenuFragmnet.newInstance();
    private RattingFragment accountFragment = RattingFragment.newInstance();
//    private HistoryFragment historyFragment = HistoryFragment.newInstance();
    private ThongTinFragment thongTinFragment = ThongTinFragment.newInstance();

    @Override
    protected void onResume() {
        super.onResume();
        registerConnectable(this);
        registerDisconnectable(this);
        registerBindable(this);
        ID = dataManager.getID();
        NAME = dataManager.getName();
        IMAGE = dataManager.getImage();
    }

    public static void startActivity(Activity context){
         context.startActivity(new Intent(context,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
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
        mPrefs = context.getSharedPreferences("", Context.MODE_PRIVATE);
        appPreferencesHelper = new AppPreferencesHelper(mPrefs,this);
        dataManager = new DataManager(appPreferencesHelper);

        ID = dataManager.getID();
        NAME = dataManager.getName();
        IMAGE = dataManager.getImage();

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
                loadFragment(activeFragment, menuFragmnet);
                activeFragment = menuFragmnet;
                return true;
            case R.id.menu_navigation_ratting:
                loadFragment(activeFragment, accountFragment);
                activeFragment = accountFragment;
                return true;
            case R.id.menu_navigation_thong_tin:
                loadFragment(activeFragment, thongTinFragment);
                activeFragment = thongTinFragment;
                return true;
        }
        return false;
    };

    private void loadFragment(Fragment activeFragment, Fragment showFragment) {
        getSupportFragmentManager().beginTransaction().hide(activeFragment).show(showFragment).commit();
    }
    private void loadAllFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container, thongTinFragment, "4").hide(thongTinFragment).commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container, accountFragment, "3").hide(accountFragment).commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container, menuFragmnet, "2").hide(menuFragmnet).commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container, homeFragment, "1").commit();
    }

}
