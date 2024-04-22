package org.example.roadtripplanner.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.roadtripplanner.HelloApplication;
import org.example.roadtripplanner.PlanWindowHelper;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SavedPlanComponent {

    private int planId;

    @FXML
    private FontIcon arrowFontIcon;

    @FXML
    private Label departureDateLabel;

    @FXML
    private Label endCityAndStateLabel;

    @FXML
    private Label planNameLabel;

    @FXML
    private Label startCityAndStateLabel;

    public void planClicked(MouseEvent mouseEvent) throws IOException, SQLException {
        PlanWindowHelper.openPlanEditorWindow(planId);
    }

    public void setPlan(int planId, String planName, String startingAddress, String destinationAddress,
                        LocalDate departureDate) {

        this.planId = planId;
        planNameLabel.setText(planName);
        startCityAndStateLabel.setText(startingAddress);
        endCityAndStateLabel.setText(destinationAddress);
        departureDateLabel.setText(departureDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));

    }
}
