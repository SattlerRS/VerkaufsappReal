module com.example.verkaufsappreal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.verkaufsappreal to javafx.fxml;
    exports com.example.verkaufsappreal;
}