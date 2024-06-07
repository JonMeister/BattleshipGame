package com.jonathan.battleshipgame;

import com.jonathan.battleshipgame.view.GameStage;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The class is the main application class for the Battleship game.
 * It extends the Application class from JavaFX and serves as the entry point for the application.
 * @author Jonathan Aristizabal
 * @version 1.0
 */
public class Main extends Application  {
    /**
     * The main method launches the JavaFX application.
     * @param args Command-line arguments passed to the application (if any).
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This method is called by the JavaFX runtime to initialize and start the application.
     * @param primaryStage The primary stage (window) of the application provided by JavaFX.
     * @throws IOException If there's an issue loading the FXML file for the game view.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        GameStage.getInstance();
    }
}
