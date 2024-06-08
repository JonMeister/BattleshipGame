package com.jonathan.battleshipgame.model;

public interface IBoard {
    void onCellAttacked(int row, int col, boolean hit, boolean sunk);
    void onGameOver();
}
