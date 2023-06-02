package com.example.entities;

import java.io.Serializable;
import java.sql.Date;

public class error implements Serializable {
    private String TenLoiViPham;
    private String MucDoPhat;
    private Date NgayThangNam;
    private String GhiChu;

    public error(){
    }

    @Override
    public String toString() {
        return "error{" +
                ", TenLoiViPham='" + TenLoiViPham + '\'' +
                ", MucDoPhat='" + MucDoPhat + '\'' +
                ", NgayThangNam=" + NgayThangNam +
                ", GhiChu='" + GhiChu + '\'' +
                '}';
    }

    public error(String tenLoiViPham, String mucDoPhat, Date ngayThangNam, String ghiChu) {
        TenLoiViPham = tenLoiViPham;
        MucDoPhat = mucDoPhat;
        NgayThangNam = ngayThangNam;
        GhiChu = ghiChu;
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
