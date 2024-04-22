package org.example.roadtripplanner;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.roadtripplanner.components.SavedPlanComponent;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StartPageController {

    @FXML
    private Button newPlanButton;

    @FXML
    private VBox previousPlanList;

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

    private void loadSavedPlans() throws SQLException {
        ObservableList<Node> previousPlansList = previousPlanList.getChildren();

        //        VBox prevPlans = controller.getPreviousPlanList();

        Statement stmt = HelloApplication.conn.createStatement();

        ResultSet rs = stmt.executeQuery("WITH b AS (" +
                "SELECT * FROM destinations WHERE stop_order IN (" +
                "   SELECT MAX(stop_order) FROM destinations GROUP BY plan_id" +
                ")" +
                ") SELECT a.*, b.address FROM plan AS a JOIN b " +
                "ON a.id = b.plan_id");

        while (rs.next()) {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("components/SavedPlanComponent.fxml"));
            try {
                Node savedPlanNode = fxmlLoader.load();

                SavedPlanComponent savedPlanController = fxmlLoader.getController();

                savedPlanController.setPlan(rs.getInt("id"), rs.getString("name"),
                        rs.getString("start_address"), rs.getString("address"),
                        rs.getDate("start_date").toLocalDate());

                previousPlansList.add(savedPlanNode);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(rs.getString("address"));;
        }

        // TODO create savedPlanComponent for each saved plan.

        // TODO make it so when the savedPlanComponent (Any part of it) is clicked it opens that plans editor.

    }

    public void initialize() throws SQLException {
        loadSavedPlans();
    }
}
