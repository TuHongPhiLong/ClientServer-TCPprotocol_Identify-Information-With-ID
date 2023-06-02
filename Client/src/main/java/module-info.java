module com.example.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;
    requires com.example.entities;
    requires java.sql;


    opens com.example.client to javafx.fxml;
    exports com.example.client;
}