package com.fpoly.supperman_nh_duan2.ui.thanhtoan;

import com.fpoly.supperman_nh_duan2.model.ThanhToan;

import java.util.List;

public interface ThanhToanContract {
    void ShowList(List<ThanhToan> thanhToans);
    void ShowPrice(double tong,double dir,int iduser);
    void ShowHistory();
    void ShowError(int error);
    void showDelete();
    void Error();

}
