//https://javabeginners.de/Grundlagen/Module_verwenden.php

module org.example.applify {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.zipfs;
    requires java.net.http;
    requires java.sql.rowset;
    requires java.desktop;
    opens model to javafx.base;
    opens org.example.controllerAndMain to javafx.fxml;
    exports org.example.controllerAndMain;
    exports org.example.controllerAndMain.appliedJobs;
    opens org.example.controllerAndMain.appliedJobs to javafx.fxml;
    exports org.example.controllerAndMain.savedJobs;
    opens org.example.controllerAndMain.savedJobs to javafx.fxml;
    exports org.example.controllerAndMain.statistics;
    opens org.example.controllerAndMain.statistics to javafx.fxml;
}