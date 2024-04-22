module org.example.roadtripplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires okhttp3;
    requires kotlin.stdlib;
    requires com.google.gson;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;


    opens org.example.roadtripplanner to javafx.fxml;
    opens org.example.roadtripplanner.components to javafx.fxml;
    opens org.example.roadtripplanner.jsons to com.google.gson;
    exports org.example.roadtripplanner;
}