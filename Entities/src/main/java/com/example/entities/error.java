package com.example.entities;

import java.util.Date;

public class error {
    private int ID;
    private String TenLoiViPham;
    private String MucDoPhat;
    private Date NgayThangNam;
    private String GhiChu;

    public error(){
    }

    public error( String tenLoiViPham, String mucDoPhat, Date ngayThangNam, String ghiChu) {
        TenLoiViPham = tenLoiViPham;
        MucDoPhat = mucDoPhat;
        NgayThangNam = ngayThangNam;
        GhiChu = ghiChu;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTenLoiViPham() {
        return TenLoiViPham;
    }

    public void setTenLoiViPham(String tenLoiViPham) {
        TenLoiViPham = tenLoiViPham;
    }

    public String getMucDoPhat() {
        return MucDoPhat;
    }

    public void setMucDoPhat(String mucDoPhat) {
        MucDoPhat = mucDoPhat;
    }

    public Date getNgayThangNam() {
        return NgayThangNam;
    }

    public void setNgayThangNam(Date ngayThangNam) {
        NgayThangNam = ngayThangNam;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }
}
