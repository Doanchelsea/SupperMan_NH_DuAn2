package com.fpoly.supperman_nh_duan2.api.eventbus;

public class CancelEvent {
    String id;

    public CancelEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
