module com.example.entities {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.entities to javafx.fxml;
    exports com.example.entities;
}