module org.example.applify {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.controllerAndMain to javafx.fxml;
    exports org.example.controllerAndMain;
}