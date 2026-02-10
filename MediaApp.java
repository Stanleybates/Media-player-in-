package com.mediaplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MediaPlayerApp extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mediaplayer/MediaPlayerUI.fxml"));
        Parent root = loader.load();
        
        // Get the controller
        MediaPlayerController controller = loader.getController();
        
        // Initialize controller
        controller.initialize();
        
        // Setup the scene
        Scene scene = new Scene(root, 900, 700);
        scene.getStylesheets().add(getClass().getResource("/com/mediaplayer/styles.css").toExternalForm());
        
        primaryStage.setTitle("My Media Player");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
