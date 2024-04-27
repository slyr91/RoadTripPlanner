package org.example.roadtripplanner;

import com.google.gson.Gson;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.example.roadtripplanner.jsons.Location;
import org.example.roadtripplanner.jsons.RoutesRequest;
import org.example.roadtripplanner.jsons.RoutesResponseArray;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.example.roadtripplanner.HelloApplication.client;

public class PlanEditorSimplifiedController {

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
    private WebView mapArea;

    @FXML
    private TextField planNameTextField;

    @FXML
    private Button printItineraryButton;

    @FXML
    private Button saveButton;

    @FXML
    private Label startingAddressText;

    private int planId;
    private ArrayList<Label> addressLabels;
    private int numDestinations;

    @FXML
    void saveButtonClicked(ActionEvent event) throws SQLException {
        Statement stmt = HelloApplication.conn.createStatement();

        stmt.execute("UPDATE plan SET name = '" + planNameTextField.getText() + "', start_date = '" +
                departureDateText.getText() + "', start_address = '" + startingAddressText.getText() + "' WHERE " +
                "id = " + planId);

        Stage stage = (Stage) deleteButton.getScene().getWindow();
        stage.close();

//        for(int i = 0; i < addressLabels.size(); i++) {
//            String currentAddress = addressLabels.get(i).getText();
//
//            if(currentAddress.isEmpty()) {
//                stmt.execute("DELETE destinations WHERE plan_id = " + planId + " AND stop_order = " + (i + 1));
//            } else {
//                stmt.execute("UPDATE destinations SET address = '" + currentAddress +
//                        "' WHERE plan_id = " + planId + " AND stop_order = " + (i + 1));
//            }
//        }
    }

    @FXML
    void deleteButtonClicked(ActionEvent event) throws SQLException {
        Statement stmt = HelloApplication.conn.createStatement();

        // TODO add cascade deletion of destinations table.
        stmt.execute("DELETE plan WHERE id = " + planId);
        stmt.execute("DELETE destination WHERE plan_id = " + planId);

        Stage stage = (Stage) deleteButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void printItineraryButtonClicked(ActionEvent event) throws IOException {

        File outputFile = new File(planNameTextField.getText() + ".html");
        try(FileWriter fileWriter = new FileWriter(outputFile)) {
            try (GeoApiContext context = new GeoApiContext.Builder().apiKey(HelloApplication.MapsAPIKey).build()) {
                for(int i = 1; i <= numDestinations; i++) {
                    DirectionsResult result = DirectionsApi.getDirections(context, addressLabels.get(i - 1).getText(),
                                addressLabels.get(i).getText()).await();

                    for(int j = 0; j < result.routes.length; j++) {

                        for(int k = 0; k < result.routes[j].legs.length; k++) {

                            for(int m = 0; m < result.routes[j].legs[k].steps.length; m++) {

                                System.out.println(result.routes[j].legs[k].steps[m].htmlInstructions);
                                fileWriter.write(result.routes[j].legs[k].steps[m].htmlInstructions);
                            }
                        }
                    }
                }

                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(outputFile.toURI());
                }

//                Gson gson = new GsonBuilder().setPrettyPrinting().create();

//                List<LatLng> waypoints = result.routes[0].overviewPolyline.decodePath();
//            System.out.println(waypoints.size());
//            System.out.println(gson.toJson(result.routes[0].legs[0]));

            } catch (IOException | InterruptedException | ApiException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void setPlan(int planId) throws SQLException, IOException {
        this.planId = planId;

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

        addressLabels = new ArrayList<>();
        addressLabels.add(startingAddressText);
        addressLabels.add(destinationAddressText);
        addressLabels.add(destination2AddressText);
        addressLabels.add(destination3AddressText);
        addressLabels.add(destination4AddressText);

        int index = 1;
        do {
            addressLabels.get(index).setText(rs.getString("address"));
            addressLabels.get(index).setVisible(true);
            index++;
        } while(rs.next());

        numDestinations = index - 1;


        // Setup the static map in the WebView container
        MediaType JSON = MediaType.get("application/json");

        Gson gson = new Gson();

        RoutesRequest route = new RoutesRequest();
        Location origin = new Location();
        Location destination = new Location();

        origin.setAddress(startingAddress);

        for(int i = 1; i < index; i++) {
            if(i + 1 == index) {
                destination.setAddress(addressLabels.get(i).getText());
            } else {
                if(route.getIntermediates() == null) {
                    route.setIntermediates(new ArrayList<>());
                }
                Location inter = new Location();
                inter.setAddress(addressLabels.get(i).getText());
                route.getIntermediates().add(inter);
            }
        }

        route.setOrigin(origin);
        route.setDestination(destination);

        RequestBody body = RequestBody.create(gson.toJson(route), JSON);

        Request request = new Request.Builder()
                .url("https://routes.googleapis.com/directions/v2:computeRoutes")
                .post(body)
                .addHeader("X-Goog-Api-Key", HelloApplication.MapsAPIKey)
                .addHeader("X-Goog-FieldMask", "routes.duration,routes.distanceMeters,routes.polyline.encodedPolyline")
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            RoutesResponseArray routesResponseArray = gson.fromJson(response.body().string(), RoutesResponseArray.class);
            System.out.println(routesResponseArray.getRoutes().get(0));

            // Build the static map URL starting from the initial URL and required parameters.
            StringBuilder staticMapURL = new StringBuilder("https://maps.googleapis.com/maps/api/staticmap?" +
                    "size=400x200&maptype=roadmap");

            // Add each address in order
            for(int i = 0; i < index; i++) {
                staticMapURL.append("&markers=color:blue|label:").append(i + 1).append("|")
                        .append(convertToURLString(addressLabels.get(i).getText()));
            }

            // Add overall route polyline.
            staticMapURL.append("&path=enc:")
                    .append(routesResponseArray.getRoutes().get(0).getPolyline().getEncodedPolyline());

            // Add Maps API Key
            staticMapURL.append("&key=").append(HelloApplication.MapsAPIKey);

            mapArea.getEngine().load(staticMapURL.toString());
        }

        // TODO add entries for stops if they exist to the listOfStopsVbox container

    }

    private String convertToURLString(String address) {
        String[] addressParts = address.split(",", 2);

        if(addressParts.length < 2) {
            return address.replaceAll(" ", "+");
        } else {
            return addressParts[0].replaceAll(" ", "+") + "," +
                    addressParts[1].replaceAll(" ", "");
        }
    }

    public void initialize() {
        destination2AddressLabel.visibleProperty().bind(destination2AddressText.visibleProperty());
        destination3AddressLabel.visibleProperty().bind(destination3AddressText.visibleProperty());
        destination4AddressLabel.visibleProperty().bind(destination4AddressText.visibleProperty());
    }

}
