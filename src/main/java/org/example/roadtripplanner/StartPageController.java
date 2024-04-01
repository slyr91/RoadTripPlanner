package org.example.roadtripplanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class StartPageController {

    @FXML
    private Button newPlanButton;

    @FXML
    private ScrollPane previousPlanList;

    @FXML
    void onNewPlanButtonClick(ActionEvent event) throws IOException {
        Stage stage = HelloApplication.mainStage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("NewPlan.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();
        Scene scene = new Scene(anchorPane, 320, 240);
        stage.setTitle("Road Trip Planner - New Plan");
        stage.setScene(scene);
        stage.show();
    }

}
