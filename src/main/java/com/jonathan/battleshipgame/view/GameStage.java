package com.jonathan.battleshipgame.view;

import com.jonathan.battleshipgame.controller.GameController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class representing the main stage of the Battleship app.
 * Extends Stage class from JavaFX and implements the Singleton pattern to ensure only one instance exists.
 * @author Jonathan Aristizabal
 * @version 1.0
 */
public class GameStage extends Stage {
    /**
     * Reference to the `GameController` instance associated with this stage.
     */
    private GameController gameController;

    /**
     * Private constructor to prevent direct instantiation (enforced by the Singleton pattern).
     * @throws IOException If there's an issue loading the FXML file for the game view.
     */
    public GameStage() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jonathan/battleshipgame/battleship-view.fxml"));
        Parent root = loader.load();
        gameController=loader.getController();
        setTitle("Battleship");
        Scene scene = new Scene(root);
        getIcons().add(new Image(
                String.valueOf(getClass().getResource("/com/jonathan/battleshipgame/images/battleship_icon.png"))));
        setScene(scene);
        setResizable(false);
        show();
//        Platform.runLater(gameController::initialize);
    }

//    public GameController getGameController() {return gameController;}

    /**
     * This static inner class is used to implement the Singleton pattern for GameStage.
     */
    private static class GameStageHolder{
        private static GameStage INSTANCE;
    }
    /**
     * This static method provides a way to access the single instance of the GameStage. If no instance exists, it creates one using the private constructor.
     * @return The single instance of GameStage.
     * @throws IOException If there's an issue loading the FXML file for the game view.
     */
    public static GameStage getInstance() throws IOException{
        return GameStageHolder.INSTANCE= new GameStage();
    }
}
