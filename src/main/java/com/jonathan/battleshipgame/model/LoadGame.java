package com.jonathan.battleshipgame.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Utility class for loading a saved game from a file.
 */
public class LoadGame {

    /**
     * Loads a saved game from the specified file.
     *
     * @param fileName the name of the file to load the game from
     * @return the loaded {@link Board} object, or {@code null} if an error occurred
     */
    public static Board load(String fileName) {
        Board board = null;
        try (FileInputStream fileIn = new FileInputStream(fileName);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            board = (Board) in.readObject();
        } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
        }
        return board;
    }
}
