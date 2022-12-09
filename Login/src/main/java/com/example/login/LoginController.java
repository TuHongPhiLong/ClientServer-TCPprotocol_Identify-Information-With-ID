package com.example.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {
    @FXML
    TextField Tf_usename;
    @FXML
    PasswordField Pf_password;
    @FXML
    Button btn_Login;

    @FXML
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

    public void setBtn_Login(ActionEvent event) throws IOException {
        if (Tf_usename.getText().equals("") || Pf_password.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Warning");
            alert.setContentText("Nhập tên đăng nhập và mật khẩu!");

            alert.showAndWait();
        } else {
            try {

                Connection conn = ConnectDb();
                PreparedStatement ps = conn.prepareStatement("Select Password, access_level from account where ID=? and Password=? ");
                ps.setString(1, Tf_usename.getText());
                ps.setString(2, Pf_password.getText());

                ResultSet rs = ps.executeQuery();

                if (!rs.isBeforeFirst()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Hãy nhập đúng");
                    alert.show();
                } else {
                    while (rs.next()) {
                        String retrievedAccess_level = rs.getString("access_level");

                        if (retrievedAccess_level.equals("Server")) {
                            System.out.println("Login Server");
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Server-view.fxml"));
                            Scene scene = new Scene(fxmlLoader.load(), 850, 550);

                            Stage stage = (Stage) btn_Login.getScene().getWindow();
                            stage.setTitle("Server");
                            stage.setScene(scene);
                        } else if (retrievedAccess_level.equals("Client")) {
                            System.out.println("Login Client");
                            FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("Client-view.fxml"));
                            Scene scene = new Scene(fxmlLoader1.load(), 850, 550);

                            Stage stage = (Stage) btn_Login.getScene().getWindow();
                            stage.setTitle("Client");
                            stage.setScene(scene);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}