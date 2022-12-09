package com.example.entities;

import java.io.Serializable;
import java.util.Date;

public class subject implements Serializable {
    private static final long serialVersionUID = 1L;
    private int ID;
    private String HovaTen;
    private Date NgayThangNamSinh;
    private String GioiTinh;
    private String QuocTich;
    private String QueQuan;
    private String NoiThuongTru;
    private String CacLoiViPham;

    public subject(){
    }
    @Override
    public String toString() {
        return "subject [ID=" + ID + ", HovaTen=" + HovaTen + ", NgayThangNamSinh=" + NgayThangNamSinh + ", GioiTinh=" + GioiTinh + ", QuocTich=" + QuocTich + ", QueQuan=" + QueQuan + ", NoiThuongTru=" + NoiThuongTru + ", CacLoiViPham=" + CacLoiViPham + "]";
    }

    public subject(int ID, String hovaTen, Date ngayThangNamSinh, String gioiTinh, String quocTich, String queQuan, String noiThuongTru, String cacLoiViPham) {
        this.ID = ID;
        HovaTen = hovaTen;
        NgayThangNamSinh = ngayThangNamSinh;
        GioiTinh = gioiTinh;
        QuocTich = quocTich;
        QueQuan = queQuan;
        NoiThuongTru = noiThuongTru;
        CacLoiViPham = cacLoiViPham;
    }
    public String getAll(){
        return getID() + getHovaTen() + getNgayThangNamSinh() + getGioiTinh() + getQuocTich() + getQueQuan() + getNoiThuongTru() + getCacLoiViPham();
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getHovaTen() {
        return HovaTen;
    }

    public void setHovaTen(String hovaTen) {
        HovaTen = hovaTen;
    }

    public Date getNgayThangNamSinh() {
        return NgayThangNamSinh;
    }

    public void setNgayThangNamSinh(Date ngayThangNamSinh) {
        NgayThangNamSinh = ngayThangNamSinh;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        GioiTinh = gioiTinh;
    }

    public String getQuocTich() {
        return QuocTich;
    }

    public void setQuocTich(String quocTich) {
        QuocTich = quocTich;
    }

    public String getQueQuan() {
        return QueQuan;
    }

    public void setQueQuan(String queQuan) {
        QueQuan = queQuan;
    }

    public String getNoiThuongTru() {
        return NoiThuongTru;
    }

    public void setNoiThuongTru(String noiThuongTru) {
        NoiThuongTru = noiThuongTru;
    }

    public String getCacLoiViPham() {
        return CacLoiViPham;
    }

    public void setCacLoiViPham(String cacLoiViPham) {
        CacLoiViPham = cacLoiViPham;
    }
}
