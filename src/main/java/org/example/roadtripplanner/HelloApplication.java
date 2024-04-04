package org.example.roadtripplanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class HelloApplication extends Application {

    public static Stage mainStage;
    private static Connection conn;

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        conn = DriverManager.getConnection("jdbc:h2:mem:testdb", "", "");

        Statement stmt = conn.createStatement();

        stmt.execute("CREATE TABLE IF NOT EXISTS plan (id INT PRIMARY KEY auto_increment, name VARCHAR(255), " +
                "start_date DATE, start_address VARCHAR(255))");

        stmt.execute("CREATE TABLE IF NOT EXISTS destinations (id INT PRIMARY KEY auto_increment, plan_id INT, " +
                "address VARCHAR(255))");

        mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Start-Page.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();
        Scene scene = new Scene(anchorPane, 350, 270);
        stage.setTitle("Road Trip Planner");
        stage.setScene(scene);
        stage.setOnCloseRequest((event) -> {
            try {
                exitApp();
            } catch (SQLException e) {
                System.err.println("Issue closing the h2 database connection.");
            }
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void exitApp() throws SQLException {
        if(conn != null) {
            conn.close();
        }
    }
}