module com.example.entities {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.entities to javafx.fxml;
    exports com.example.entities;
}