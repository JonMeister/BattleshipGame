package com.jonathan.battleshipgame.exceptions;

/**
 * This exception is thrown when an attempt is made to attack a cell that has already been attacked.
 */
public class CellAlreadyAttackedException extends Exception {

    /**
     * Constructs a new CellAlreadyAttackedException with the specified detail message.
     *
     * @param message the detail message
     */
    public CellAlreadyAttackedException(String message) {
        super(message);
    }
}
