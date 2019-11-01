package com.example.supperman_nh_duan2.ui.menu;

import com.example.supperman_nh_duan2.model.Menu;

import java.util.List;

public interface MenuContract {
    void showList(List<Menu> menus);
    void showList1(List<Menu> menus);
    void showList2(List<Menu> menus);
    void showError(int erro);
    void showEror(int idmon);
}
