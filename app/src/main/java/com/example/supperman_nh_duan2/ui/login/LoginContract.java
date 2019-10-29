package com.example.supperman_nh_duan2.ui.login;

public interface LoginContract {
    void showLoading(boolean show);
    void ShowSuccer(int id,String name,String images);
    void ShowError(int error);
}
