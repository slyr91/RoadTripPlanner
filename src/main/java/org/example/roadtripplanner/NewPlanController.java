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

        // TODO run checks on required fields (Show warning label if fields are missing data or are wrong)

        // TODO Add data to database

        // TODO Open plan editor in a new window (alongside opening the main window again)

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
