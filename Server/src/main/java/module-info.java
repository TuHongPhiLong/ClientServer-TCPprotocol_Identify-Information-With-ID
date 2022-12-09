module com.example.server {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.example.library;
    requires java.desktop;
    requires java.sql;
    requires com.example.entities;
    requires mysql.connector.java;
    requires javafx.swing;


    opens com.example.server to javafx.fxml;
    exports com.example.server;
}