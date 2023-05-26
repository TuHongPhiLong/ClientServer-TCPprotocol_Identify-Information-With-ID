module com.example.server {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.desktop;
    requires java.sql;

    requires mysql.connector.java;
    requires javafx.swing;


    opens com.example.server to javafx.fxml;
    exports com.example.server;
}