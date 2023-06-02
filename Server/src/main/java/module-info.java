module module.com.example.server {
    requires javafx.controls;
    requires java.desktop;
    requires java.sql;
    requires mysql.connector.java;
    requires javafx.swing;
    requires javafx.fxml;
    requires com.example.entities;

    opens com.example.server to javafx.fxml;
    exports com.example.server;
    //exports com.example.server.model;

}