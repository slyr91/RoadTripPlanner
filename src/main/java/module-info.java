module org.example.roadtripplanner {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.roadtripplanner to javafx.fxml;
    exports org.example.roadtripplanner;
}