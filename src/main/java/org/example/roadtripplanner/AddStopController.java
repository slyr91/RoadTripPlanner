package org.example.roadtripplanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.LatLng;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddStopController {

    @FXML
    private Button dest1ToDest2;

    @FXML
    private Button dest2ToDest3;

    @FXML
    private Button dest3ToDest4;

    @FXML
    private Button startToDest1;

    private VBox listOfStops;
    private ArrayList<Label> addressLabels;

    @FXML
    void startToDest1Clicked(ActionEvent event) {
        String startAddress = addressLabels.get(0).getText();
        String destAddress = addressLabels.get(1).getText();

        showPlacesOptions(startAddress, destAddress);
    }

    @FXML
    void dest1ToDest2Clicked(ActionEvent event) {
        String startAddress = addressLabels.get(1).getText();
        String destAddress = addressLabels.get(2).getText();

        showPlacesOptions(startAddress, destAddress);
    }

    @FXML
    void dest2ToDest3Clicked(ActionEvent event) {
        String startAddress = addressLabels.get(2).getText();
        String destAddress = addressLabels.get(3).getText();

        showPlacesOptions(startAddress, destAddress);
    }

    @FXML
    void dest3ToDest4Clicked(ActionEvent event) {
        String startAddress = addressLabels.get(3).getText();
        String destAddress = addressLabels.get(4).getText();

        showPlacesOptions(startAddress, destAddress);
    }

    public void setOptions(VBox listOfStops, ArrayList<Label> addressLabels) {
        switch (addressLabels.size() - 1) {
            case 4:
                dest3ToDest4.setVisible(true);
            case 3:
                dest2ToDest3.setVisible(true);
            case 2:
                dest1ToDest2.setVisible(true);
                break;
        }

        this.addressLabels = addressLabels;
        this.listOfStops = listOfStops;
    }

    private void showPlacesOptions(String startAddress, String destAddress) {
        try (GeoApiContext context = new GeoApiContext.Builder().apiKey(HelloApplication.MapsAPIKey).build()) {
            DirectionsResult result = DirectionsApi.getDirections(context, startAddress,
                    destAddress).await();

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            List<LatLng> waypoints = result.routes[0].overviewPolyline.decodePath();
//            System.out.println(waypoints.size());
//            System.out.println(gson.toJson(result.routes[0].legs[0]));

        } catch (IOException | InterruptedException | ApiException e) {
            throw new RuntimeException(e);
        }
    }

}

