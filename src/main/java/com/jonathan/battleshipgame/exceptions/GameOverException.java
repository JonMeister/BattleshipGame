package com.jonathan.battleshipgame.exceptions;

/**
 * This exception is thrown when the game is over, typically when all ships have been sunk.
 */
public class GameOverException extends Exception {

    /**
     * Constructs a new GameOverException with the specified detail message.
     *
     * @param message the detail message
     */
    public GameOverException(String message) {
        super(message);
    }
}
