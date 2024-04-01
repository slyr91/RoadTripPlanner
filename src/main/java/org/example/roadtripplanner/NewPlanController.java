package org.example.roadtripplanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

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
    }

}
