package com.fpoly.supperman_nh_duan2.ui.history.detail;

import com.fpoly.supperman_nh_duan2.model.ThanhToan;

import java.util.List;

public interface HistoryDetailContract {
    void ShowUser(String name,String images);
    void ShowList(List<ThanhToan> thanhToans);
    void showError(int error);
}
