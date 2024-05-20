package com.if2210.app;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/if2210/app/fxml/GUI.fxml"));

        Scene scene = new Scene(root, 1228, 768);

        stage.setTitle("MoliNana");
        stage.setScene(scene);
        stage.show();
    }
}
