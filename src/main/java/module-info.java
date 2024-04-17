module org.example.roadtripplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires okhttp3;
    requires kotlin.stdlib;
    requires com.google.gson;


    opens org.example.roadtripplanner to javafx.fxml;
    opens org.example.roadtripplanner.jsons to com.google.gson;
    exports org.example.roadtripplanner;
}