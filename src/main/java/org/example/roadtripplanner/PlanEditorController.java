package org.example.roadtripplanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.example.roadtripplanner.HelloApplication.client;

public class PlanEditorController {

    @FXML
    private Button addStopButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Label departureDateText;

    @FXML
    private Label destination2AddressLabel;

    @FXML
    private Label destination2AddressText;

    @FXML
    private Label destination3AddressLabel;

    @FXML
    private Label destination3AddressText;

    @FXML
    private Label destination4AddressLabel;

    @FXML
    private Label destination4AddressText;

    @FXML
    private Label destinationAddressText;

    @FXML
    private VBox listOfStopsVbox;

    @FXML
    private WebView mapArea;

    @FXML
    private TextField planNameTextField;

    @FXML
    private Button printItineraryButton;

    @FXML
    private Button removeStopButton;

    @FXML
    private Button saveButton;

    @FXML
    private Label startingAddressText;

    @FXML
    void addStopButtonClicked(ActionEvent event) {

    }

    @FXML
    void deleteButtonClicked(ActionEvent event) {

    }

    @FXML
    void printItineraryButtonClicked(ActionEvent event) {

    }

    @FXML
    void removeStopButtonClicked(ActionEvent event) {

    }

    @FXML
    void saveButtonClicked(ActionEvent event) {

    }

    public void setPlan(int planId) throws SQLException, IOException {
        Statement stmt = HelloApplication.conn.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT a.*, b.address FROM plan AS a JOIN destinations AS b ON a.id = b.plan_id " +
                "WHERE a.id = " + planId + " ORDER BY b.id");

        rs.next();

        String planName = rs.getString("name");
        Date departureDate = rs.getDate("start_date");
        String startingAddress = rs.getString("start_address");

        planNameTextField.setText(planName);
        departureDateText.setText(departureDate.toString());
        startingAddressText.setText(startingAddress);

        ArrayList<Label> destinationLabels = new ArrayList<>();
        destinationLabels.add(destinationAddressText);
        destinationLabels.add(destination2AddressText);
        destinationLabels.add(destination3AddressText);
        destinationLabels.add(destination4AddressText);

        int index = 0;
        do {
            destinationLabels.get(index).setText(rs.getString("address"));
            destinationLabels.get(index).setVisible(true);
        } while(rs.next());

        // Setup the static map in the WebView container
        MediaType JSON = MediaType.get("application/json");

        String json = "{\n" +
                "  \"origin\":{\n" +
                "    \"location\":{\n" +
                "      \"latLng\":{\n" +
                "        \"latitude\": 37.419734,\n" +
                "        \"longitude\": -122.0827784\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"destination\":{\n" +
                "    \"location\":{\n" +
                "      \"latLng\":{\n" +
                "        \"latitude\": 37.417670,\n" +
                "        \"longitude\": -122.079595\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"travelMode\": \"DRIVE\",\n" +
                "  \"routingPreference\": \"TRAFFIC_AWARE\",\n" +
                "  \"departureTime\": \"2024-10-15T15:01:23.045123456Z\",\n" +
                "  \"computeAlternativeRoutes\": false,\n" +
                "  \"routeModifiers\": {\n" +
                "    \"avoidTolls\": false,\n" +
                "    \"avoidHighways\": false,\n" +
                "    \"avoidFerries\": false\n" +
                "  },\n" +
                "  \"languageCode\": \"en-US\",\n" +
                "  \"units\": \"IMPERIAL\"\n" +
                "}";

        // TODO Use Maps API to get the route overview and place it in the webview window.

        RequestBody body = RequestBody.create(json, JSON);

        Request request = new Request.Builder()
                .url("https://routes.googleapis.com/directions/v2:computeRoutes")
                .post(body)
                .addHeader("X-Goog-Api-Key", HelloApplication.MapsAPIKey)
                .addHeader("X-Goog-FieldMask", "routes.duration,routes.distanceMeters,routes.polyline.encodedPolyline")
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            System.out.println(response.body().string());
            /*
            Outputs the below JSON

            {
                "routes": [
                    {
                        "distanceMeters": 773,
                        "duration": "162s",
                        "polyline": {
                            "encodedPolyline": "ipkcFjichVzQ@d@gU{E?"
                        }
                    }
                ]
            }
             */
        }

        // TODO add entries for stops if they exist to the listOfStopsVbox container

    }

    public void initialize() {
        destination2AddressLabel.visibleProperty().bind(destination2AddressText.visibleProperty());
        destination3AddressLabel.visibleProperty().bind(destination3AddressText.visibleProperty());
        destination4AddressLabel.visibleProperty().bind(destination4AddressText.visibleProperty());
    }

}
