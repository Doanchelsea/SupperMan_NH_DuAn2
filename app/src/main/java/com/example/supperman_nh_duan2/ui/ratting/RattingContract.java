package com.example.supperman_nh_duan2.ui.ratting;

import com.example.supperman_nh_duan2.model.Banan;

import java.util.List;

public interface RattingContract {
    void show(List<Banan> banans);
    void ShowError();
    void ShowErorr(int error);
    void ShowAdd();
    void soban(int ban);
    void showxoaban();
}
