module org.example.roadtripplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;


    opens org.example.roadtripplanner to javafx.fxml;
    exports org.example.roadtripplanner;
}