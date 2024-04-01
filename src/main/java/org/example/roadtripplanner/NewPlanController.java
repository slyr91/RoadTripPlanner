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

    }

    @FXML
    void submitButton(ActionEvent event) {

    }

}
