package com.fpoly.supperman_nh_duan2.ui.home.onlinedetail;

public interface OnlineDetailContract {
    void showUser(String name, String phone, String image);

    void showDelete();

    void showSoban(int ban);

    void showthanhtoan(int id);

    void ShowError(int error);
}