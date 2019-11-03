package com.fpoly.supperman_nh_duan2.lisenner;

import com.fpoly.supperman_nh_duan2.model.manage.Manage;

public interface ManageLisenner {
    void onClick(Manage manage);

    void onClick(int id);

    void onAdd(Manage manage);
}
