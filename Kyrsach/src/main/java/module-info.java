module com.example.kyrsach {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens com.example.kyrsach to javafx.fxml;
    exports com.example.kyrsach;
}