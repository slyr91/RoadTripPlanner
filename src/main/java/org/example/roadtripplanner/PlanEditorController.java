package org.example.roadtripplanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class PlanEditorController {

    @FXML
    private Button addStopButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Label departureDateText;

    @FXML
    private Label destination2AddressText;

    @FXML
    private Label destination3AddressText;

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

    public void setPlan(int planId) throws SQLException {
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
        } while(rs.next());

        // TODO Remove unneeded destination addresses.

        // TODO add entries for stops if they exist to the listOfStopsVbox container

    }

}
