package com.fpoly.supperman_nh_duan2.ui.menu.xemthem;

import com.fpoly.supperman_nh_duan2.model.Menu;

import java.util.List;

public interface XemthemContract {
    void showlist(List<Menu> menus,boolean show);
    void showError(int error);
}
