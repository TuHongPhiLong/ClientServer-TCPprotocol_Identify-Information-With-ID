module com.example.login {
    requires javafx.controls;
    requires javafx.fxml;
    //requires com.example.server;
    requires java.sql;
    requires java.desktop;
    requires mysql.connector.java;

    opens com.example.login to javafx.fxml;
    exports com.example.login;
}