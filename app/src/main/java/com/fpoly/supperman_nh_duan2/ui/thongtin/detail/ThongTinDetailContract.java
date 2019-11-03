package com.fpoly.supperman_nh_duan2.ui.thongtin.detail;

public interface ThongTinDetailContract {
    void ShowSucces();
    void ShowError(int error);
    void ShowLoading(boolean show);
    void ShowUpdate(String name,String image);
}
