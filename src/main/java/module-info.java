//https://javabeginners.de/Grundlagen/Module_verwenden.php

module org.example.applify {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.zipfs;
    requires java.net.http;
    opens model to javafx.base;
    opens org.example.controllerAndMain to javafx.fxml;
    exports org.example.controllerAndMain;
}