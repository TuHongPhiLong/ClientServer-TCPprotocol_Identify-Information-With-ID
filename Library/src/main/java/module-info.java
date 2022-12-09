module com.example.library {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.example.entities;
    requires java.sql;



    opens com.example.library to javafx.fxml;
    exports com.example.library;
}