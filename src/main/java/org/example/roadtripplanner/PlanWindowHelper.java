package org.example.roadtripplanner;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class PlanWindowHelper {

    public static void openPlanEditorWindow(Stage stage, int plan_id) throws IOException, SQLException {
        if(stage == null) {
            stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(PlanWindowHelper.class.getResource("PlanEditorSimplified.fxml"));
            Scene scene = new Scene(fxmlLoader.load(),800, 280);
            stage.setTitle("Plan Editor");
            stage.setScene(scene);

            PlanEditorSimplifiedController controller = fxmlLoader.getController();
            controller.setPlan(plan_id);

            stage.show();
        }
    }

    public static void openPlanEditorWindow(int plan_id) throws IOException, SQLException {
        openPlanEditorWindow(null, plan_id);
    }
}
