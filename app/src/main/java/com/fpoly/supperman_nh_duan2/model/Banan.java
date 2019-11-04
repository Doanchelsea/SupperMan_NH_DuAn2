package com.fpoly.supperman_nh_duan2.model;

public class Banan {
    int id;
    int soban;
    int idnhahang;
    String trangthai;

    public Banan(int id, int soban, int idnhahang, String trangthai) {
        this.id = id;
        this.soban = soban;
        this.idnhahang = idnhahang;
        this.trangthai = trangthai;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSoban() {
        return soban;
    }

    public void setSoban(int soban) {
        this.soban = soban;
    }

    public int getIdnhahang() {
        return idnhahang;
    }

    public void setIdnhahang(int idnhahang) {
        this.idnhahang = idnhahang;
    }
}
