package org.example.roadtripplanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.sql.*;
import java.util.Optional;
import java.util.Properties;

public class HelloApplication extends Application {

    public static Stage mainStage;
    private static Connection conn;

    private static String MapsAPIKey;

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        boolean propFilePresent = true;

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

        Properties prop = new Properties();
        String filename = "app.config";

        try (FileInputStream fis = new FileInputStream(filename)) {
            prop.load(fis);
        } catch (FileNotFoundException e) {
            propFilePresent = false;
        }

        MapsAPIKey = Optional.ofNullable(System.getenv("MapsAPIKey")).orElse("");

        if(propFilePresent && prop.getProperty("MapsAPIKey") != null) {
            MapsAPIKey = prop.getProperty("MapsAPIKey");
        }

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