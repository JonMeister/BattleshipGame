package com.jonathan.battleshipgame.model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Utility class for saving a game to a file.
 */
public class SaveGame {

    /**
     * Saves the given {@link Board} object to the specified file.
     *
     * @param board the {@link Board} object to save
     * @param fileName the name of the file to save the game to
     */
    public static void save(Board board, String fileName) {
        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(board);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
