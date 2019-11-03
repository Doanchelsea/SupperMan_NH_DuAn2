package com.fpoly.supperman_nh_duan2.ui.menu.detail;

public interface DetailContract {
    void showSpiner(String spiner,String time);
    void showSoban(int ban);
    void showBanerror(int error);
    void ShowSuccess();
    void showXoaSuccess();
}
