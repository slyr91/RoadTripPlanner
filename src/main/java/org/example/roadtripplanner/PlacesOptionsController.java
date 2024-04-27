package org.example.roadtripplanner;

import com.google.maps.model.LatLng;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class PlacesOptionsController {

    private VBox listOfStops;
    private List<LatLng> waypoints;

    public void setPlacesOptions(VBox listOfStops, List<LatLng> waypoints) {
        this.listOfStops = listOfStops;
        this.waypoints = waypoints;
    }
}
