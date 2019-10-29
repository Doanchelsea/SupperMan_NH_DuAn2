package com.example.supperman_nh_duan2.ui.menu;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supperman_nh_duan2.R;
import com.example.supperman_nh_duan2.adapter.MenuAdapter;
import com.example.supperman_nh_duan2.base.BaseFragment;
import com.example.supperman_nh_duan2.lisenner.DatMonLisenner;
import com.example.supperman_nh_duan2.model.Menu;
import com.example.supperman_nh_duan2.ui.adddish.AddDishActivity;
import com.example.supperman_nh_duan2.ui.menu.detail.MenuDetailActivity;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class MenuFragmnet extends BaseFragment implements MenuContract, DatMonLisenner {
    List<Menu> list = new ArrayList<>();
    List<Menu> list1 = new ArrayList<>();
    List<Menu> list2 = new ArrayList<>();
    LinearLayoutManager manager = new GridLayoutManager(activity,3, LinearLayoutManager.HORIZONTAL,false);
    LinearLayoutManager manager1 = new GridLayoutManager(activity,3, LinearLayoutManager.HORIZONTAL,false);
    LinearLayoutManager manager2 = new GridLayoutManager(activity,3, LinearLayoutManager.HORIZONTAL,false);
    MenuAdapter menuAdapter,menuAdapter1,menuAdapter2;
    private MenuPresenter menuPresenter;
    @BindView(R.id.imgAddDish)
    ImageView imgAddDish;
    @BindView(R.id.re_menu_lau_nuong)
    RecyclerView re_menu_lau_nuong;
    @BindView(R.id.re_menu_do_an)
    RecyclerView re_menu_do_an;
    @BindView(R.id.re_menu_nuoc_uong)
    RecyclerView re_menu_nuoc_uong;

    public static MenuFragmnet newInstance() {
        Bundle args = new Bundle();
        MenuFragmnet fragment = new MenuFragmnet();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
            menuPresenter.getData(list,""+1);
            menuPresenter.getData1(list1,""+2);
            menuPresenter.getData2(list2,""+3);
    }

    @Override
    protected void addEvents() {
        menuPresenter = new MenuPresenter(activity,this);
        menuPresenter.getData(list,""+1);
        menuPresenter.getData1(list1,""+2);
        menuPresenter.getData2(list2,""+3);
        addDisposable(RxView.clicks(imgAddDish).subscribe(
                unit -> {
                    openAddDishScreen();
                }
        ));
    }

    @Override
    protected void configViews() {

    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.menu_fragment;
    }
    private void openAddDishScreen(){
        AddDishActivity.startActivity(activity);
    }

    @Override
    public void showList(List<Menu> menus) {
        menuAdapter = new MenuAdapter(this,activity,menus);
        re_menu_lau_nuong.setHasFixedSize(true);
        re_menu_lau_nuong.setLayoutManager(manager);
        re_menu_lau_nuong.setAdapter(menuAdapter);
    }

    @Override
    public void showList1(List<Menu> menus) {
        menuAdapter1 = new MenuAdapter(this,activity,menus);
        re_menu_do_an.setHasFixedSize(true);
        re_menu_do_an.setLayoutManager(manager1);
        re_menu_do_an.setAdapter(menuAdapter1);
    }

    @Override
    public void showList2(List<Menu> menus) {
        menuAdapter2 = new MenuAdapter(this,activity,menus);
        re_menu_nuoc_uong.setHasFixedSize(true);
        re_menu_nuoc_uong.setLayoutManager(manager2);
        re_menu_nuoc_uong.setAdapter(menuAdapter2);
    }

    @Override
    public void showError(int erro) {
        Toasty.error(activity,erro).show();
    }

    @Override
    public void onclick(Menu menu) {
        MenuDetailActivity.startActivity(activity,menu);
    }
}
