package com.fpoly.supperman_nh_duan2.ui.history;

import com.fpoly.supperman_nh_duan2.model.History;

import java.util.List;

public interface HistoryContract {
    void showList(List<History> histories,boolean show);
    void showError(int error);
}
