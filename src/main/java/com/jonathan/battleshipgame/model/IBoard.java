package com.jonathan.battleshipgame.model;

/**
 * Interface for handling board events in the Battleship game.
 */
public interface IBoard {
    /**
     * Called when a cell on the board is attacked.
     *
     * @param row  the row index of the attacked cell
     * @param col  the column index of the attacked cell
     * @param hit  whether the attack was a hit
     * @param sunk whether the ship was sunk
     */
    void onCellAttacked(int row, int col, boolean hit, boolean sunk);

    /**
     * Called when the game is over.
     */
    void onGameOver();
}
