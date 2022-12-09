module com.example.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.example.library;
    requires java.desktop;
    requires com.example.entities;
    requires javafx.swing;


    opens com.example.client to javafx.fxml;
    exports com.example.client;
}