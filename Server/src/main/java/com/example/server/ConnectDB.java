package com.example.server;

import com.example.entities.error;
import com.example.entities.subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import javax.swing.*;
import java.sql.*;

public class ConnectDB {
    private Connection conn = null;

    public static Connection ConnectDb() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/identifyinformationwithpictures_clientserverdata", "root", "");
            return conn;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
    public static ObservableList<subject> getDatasubject() {
        Connection conn = ConnectDb();
        ObservableList<subject> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from subject");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new subject(Integer.parseInt(rs.getString("ID_subject")), rs.getString("HovaTen"), rs.getDate("NgayThangNamSinh"), rs.getString("GioiTinh"), rs.getString("QuocTich"), rs.getString("QueQuan"), rs.getString("NoiThuongTru")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {

            } catch (Exception e) {

            }
        }

        return list;
    }
    public static ObservableList<error> getDataerror(Label lbl) {
        Connection conn = ConnectDb();
        ObservableList<error> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from error where ID_subject ='"+lbl.getText()+"' ");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new error(rs.getString("TenLoiViPham"), rs.getString("MucDoPhat"), rs.getDate("NgayThangNam"), rs.getString("GhiChu")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {

            } catch (Exception e) {

            }
        }

        return list;
    }
}





