package org.example.roadtripplanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class NewPlanController {

    private int departureAddressCount = 0;

    @FXML
    private Button addDestinationButton;

    private ArrayList<AnchorPane> destinationAddresses = new ArrayList<>(4);

    @FXML
    private DatePicker departureDate;

    @FXML
    private TextField destination1Address;

    @FXML
    private AnchorPane destination1AddressComponent;

    @FXML
    private TextField destination2Address;

    @FXML
    private AnchorPane destination2AddressComponent;

    @FXML
    private TextField destination3Address;

    @FXML
    private AnchorPane destination3AddressComponent;

    @FXML
    private TextField destination4Address;

    @FXML
    private AnchorPane destination4AddressComponent;

    @FXML
    private TextField planName;

    @FXML
    private Button removeDestinationButton;

    @FXML
    private TextField startingAddress;

    @FXML
    private Button submit;

    @FXML
    private AnchorPane warningAnchor;

    @FXML
    private Label warningLabel;

    @FXML
    void addDestinationButton(ActionEvent event) {
        departureAddressCount++;

        if(destinationAddresses.size() > departureAddressCount) {
            removeDestinationButton.setDisable(false);

            AnchorPane destination = destinationAddresses.get(departureAddressCount);

            destination.setVisible(true);

            if(departureAddressCount == 3) {
                addDestinationButton.setDisable(true);
            }
        } else {
            departureAddressCount--;
        }

    }

    @FXML
    void onCancelButtonClick(ActionEvent event) throws IOException {

        Stage stage = HelloApplication.mainStage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Start-Page.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();
        Scene scene = new Scene(anchorPane, 350, 270);
        stage.setTitle("Road Trip Planner");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void removeDestinationButton(ActionEvent event) {
        if(departureAddressCount < 1) {
            removeDestinationButton.setDisable(true);
        } else {
            addDestinationButton.setDisable(false);

            AnchorPane destination = destinationAddresses.get(departureAddressCount);

            destination.setVisible(false);
            TextField textField = (TextField) destination.getChildren().get(1);
            textField.setText("");

            departureAddressCount--;

            if(departureAddressCount == 0) {
                removeDestinationButton.setDisable(true);
            }
        }


    }

    @FXML
    void submitButton(ActionEvent event) {

        // Validate entered data

        if(planName.getText().isEmpty()) {
            warningLabel.setText("Plan name can not be blank.");
            warningLabel.setVisible(true);
            return;
        }

        if(departureDate.getValue() == null) {
            warningLabel.setText("Departure date can not be blank.");
            warningLabel.setVisible(true);
            return;
        }

        if(startingAddress.getText().isEmpty()) {
            warningLabel.setText("Starting address can not be blank.");
            warningLabel.setVisible(true);
            return;
        }

        if(destination1Address.getText().isEmpty()) {
            warningLabel.setText("Destination address can not be blank.");
            warningLabel.setVisible(true);
            return;
        }

        // Add data to the database

        int id = -1;

        try {
            Statement stmt = HelloApplication.conn.createStatement();

            stmt.execute("INSERT INTO plan (name, start_date, start_address) VALUES ('" + planName.getText() + "','" +
                    departureDate.getValue() + "','" + startingAddress.getText() + "')");

            ResultSet rs = stmt.executeQuery("SELECT id FROM plan WHERE name = '" + planName.getText() + "' AND " +
                    "start_date ='" + departureDate.getValue() + "' AND start_address = '" + startingAddress.getText() +
                    "'");

            rs.next();

            id = rs.getInt(1);

            stmt.execute("INSERT INTO destinations (plan_id, address) VALUES (" + id + ",'" +
                    destination1Address.getText() + "')");

            if(!destination2Address.getText().isEmpty()) {
                stmt.execute("INSERT INTO destinations (plan_id, address) VALUES (" + id + ",'" +
                        destination2Address.getText() + "')");
            }

            if(!destination3Address.getText().isEmpty()) {
                stmt.execute("INSERT INTO destinations (plan_id, address) VALUES (" + id + ",'" +
                        destination3Address.getText() + "')");
            }

            if(!destination4Address.getText().isEmpty()) {
                stmt.execute("INSERT INTO destinations (plan_id, address) VALUES (" + id + ",'" +
                        destination4Address.getText() + "')");
            }

        } catch (SQLException e) {
            System.err.println("Fatal error occured adding data to the database. Closing application...");
            System.err.println(e.getMessage());
            System.exit(1);
        }

        // TODO Open plan editor in a new window (alongside opening the main window again)
        // Reopen main stage with new plan inplace. Then open plan editor in new window.

        Stage stage = HelloApplication.mainStage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Start-Page.fxml"));
        AnchorPane anchorPane = null;
        try {
            anchorPane = fxmlLoader.load();
        } catch (IOException e) {
            System.err.println("Fatal error occurred opening the main stage after new plan creation. Closing application...");
            System.err.println(e.getMessage());
            System.exit(1);
        }
        Scene scene = new Scene(anchorPane, 350, 270);
        stage.setTitle("Road Trip Planner");
        stage.setScene(scene);
        stage.show();

        // Open plan editor to current plan in plan editor.

        try {
            PlanWindowHelper.openPlanEditorWindow(id);
        } catch (IOException | SQLException e) {
            System.err.println("Fatal error occurred opening the plan editor after new plan creation. Closing application...");
            System.err.println(e.getMessage());
            System.exit(2);
        }


    }

    @FXML
    public void initialize() {
        destinationAddresses.add(destination1AddressComponent);
        destinationAddresses.add(destination2AddressComponent);
        destinationAddresses.add(destination3AddressComponent);
        destinationAddresses.add(destination4AddressComponent);

        destination1AddressComponent.managedProperty().bind(destination1AddressComponent.visibleProperty());
        destination2AddressComponent.managedProperty().bind(destination2AddressComponent.visibleProperty());
        destination3AddressComponent.managedProperty().bind(destination3AddressComponent.visibleProperty());
        destination4AddressComponent.managedProperty().bind(destination4AddressComponent.visibleProperty());

        warningAnchor.managedProperty().bind(warningLabel.visibleProperty());
    }

}
