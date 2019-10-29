package com.example.supperman_nh_duan2.ui.home;

import com.example.supperman_nh_duan2.model.manage.Manage;

import java.util.List;

public interface HomeContract {

    void loadData(List<Manage> manages);
    void ShowError();
    void ShowErorr(int error);

    void ShowDelete();
    void addThanhtoan(int id);
}
