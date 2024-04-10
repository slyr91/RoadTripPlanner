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

public class PlanEditorController {

    @FXML
    private Button addStopButton;

    @FXML
    private Button deleteButton;

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
    private TextField planName;

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

        ResultSet rs = stmt.executeQuery("SELECT a.*, b.* FROM plan AS a JOIN destinations ON a.id = b.plan_id " +
                "WHERE a.id = " + planId + " ORDER BY b.id");

        rs.next();

        String planName = rs.getString("a.name");
        Date departureDate = rs.getDate("a.start_date");
        String startingAddress = rs.getString("a.start_address");
        String destination1Address = rs.getString("b.address");
    }

}
