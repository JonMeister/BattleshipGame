package com.jonathan.battleshipgame.exceptions;

public class CellAlreadyAttackedException extends Exception {
    public CellAlreadyAttackedException(String message) {
        super(message);
    }
}
