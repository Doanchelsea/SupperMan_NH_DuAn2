package com.fpoly.supperman_nh_duan2.model;

public class History {
    int id;
    int iduser;
    int idnhahang;
    String price;
    String date;
    String namenh;
    String images;
    int tables;
    String idtrangthai;

    public History(int id, int iduser, int idnhahang, String price, String date, String namenh, String images, int tables, String idtrangthai) {
        this.id = id;
        this.iduser = iduser;
        this.idnhahang = idnhahang;
        this.price = price;
        this.date = date;
        this.namenh = namenh;
        this.images = images;
        this.tables = tables;
        this.idtrangthai = idtrangthai;
    }

    public String getIdtrangthai() {
        return idtrangthai;
    }

    public void setIdtrangthai(String idtrangthai) {
        this.idtrangthai = idtrangthai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public int getIdnhahang() {
        return idnhahang;
    }

    public void setIdnhahang(int idnhahang) {
        this.idnhahang = idnhahang;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNamenh() {
        return namenh;
    }

    public void setNamenh(String namenh) {
        this.namenh = namenh;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getTables() {
        return tables;
    }

    public void setTables(int tables) {
        this.tables = tables;
    }

}
