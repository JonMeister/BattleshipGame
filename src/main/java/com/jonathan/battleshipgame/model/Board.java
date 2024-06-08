package com.jonathan.battleshipgame.model;

import com.jonathan.battleshipgame.exceptions.CellAlreadyAttackedException;
import com.jonathan.battleshipgame.exceptions.GameOverException;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.*;

public class Board {

    private int[][] myShipsPositions = new int[10][10];
    private int[][] enemyShipsPositions = new int[10][10];
    private int[][] hitsOnEnemyShips = new int[10][10];
    private int[][] hitsOnMyShips = new int[10][10];
    private IBoard boardListener;

    public void setBoardListener(IBoard boardListener) {
        this.boardListener = boardListener;
    }

    private Image fail = new Image(String.valueOf(getClass().getResource("/com/jonathan/battleshipgame/images/x.png")));
    private Image hit = new Image(String.valueOf(getClass().getResource("/com/jonathan/battleshipgame/images/bomb.png")));
    private Image sunk = new Image(String.valueOf(getClass().getResource("/com/jonathan/battleshipgame/images/fire.png")));
    private Set<String> attackedCells = new HashSet<>();
    private Random random = new Random();


    List<int[][]> enemyGames = new ArrayList<>();
    private int onFire;

    private GridPane myBoard;
    private GridPane enemyBoard;
    private GridPane shipsBoard;

    public Board(GridPane myBoard, GridPane shipsBoard, GridPane enemyBoard) {
        this.myBoard = myBoard;
        this.shipsBoard = shipsBoard;
        this.enemyBoard = enemyBoard;
        resetBoard();
    }
    private void resetBoard() {
        // Reinicializar las matrices
        myShipsPositions = new int[10][10];
        enemyShipsPositions = new int[10][10];
        hitsOnEnemyShips = new int[10][10];
        hitsOnMyShips = new int[10][10];

        attackedCells.clear(); // Limpiar las celdas atacadas
    }

    public void populateGrid() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                String style = "-fx-background-color: transparent;" +
                        " -fx-border-width: 1;" +
                        " -fx-border-color: black;";

                TextField myBoardTextField = createTextField(style);
                TextField enemyBoardTextField = createTextField(style);

                myBoard.add(myBoardTextField, j, i);
                enemyBoard.add(enemyBoardTextField, j, i);
            }
        }
    }

    private TextField createTextField(String style) {
        TextField textField = new TextField();
        textField.setPrefHeight(30);
        textField.setPrefWidth(30);
        textField.setAlignment(Pos.CENTER);
        textField.setStyle(style);
        textField.setMouseTransparent(true);
        return textField;
    }

    public void markBoardOccupied(int row, int col, int length, boolean isVertical,int num) {
        if (isVertical) {
            for (int i = 0; i < length; i++) {
                if (row + i < 10) {
                    myShipsPositions[row + i][col] = num;
                }
            }
        } else {
            for (int i = 0; i < length; i++) {
                if (col + i < 10) {
                    myShipsPositions[row][col + i] = num;
                }
            }
        }
    }

    public void selectGame(int position) {
        int[][] matrix1 = {
                {0, 0, 0, 0, 0, 0, 0, 3, 3, 3},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 10, 0, 0, 4, 4},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 2, 2, 2, 0, 0, 9, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 5, 0, 0, 7, 0, 0, 6, 0, 0},
                {0, 5, 0, 0, 0, 0, 0, 6, 0, 0},
                {0, 0, 0, 8, 0, 0, 0, 0, 0, 0}
        };
        int[][] matrix2 = {
                {0, 0, 0, 0, 0, 10, 0, 0, 0, 4},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 4},
                {6, 6, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 3, 0, 0, 7, 0, 0},
                {0, 0, 0, 0, 3, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 3, 0, 0, 0, 0, 0},
                {0, 8, 0, 9, 0, 0, 5, 5, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 2, 2, 2, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 1, 1, 1}
        };
        int[][] matrix3 = {
                {3, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {3, 0, 0, 7, 0, 0, 6, 6, 0, 0},
                {3, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 9, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 8, 0, 0, 1, 0, 0, 0, 4, 4},
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 5, 5, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 2, 2, 2, 0},
                {0, 0, 10, 0, 0, 0, 0, 0, 0, 0}
        };
        enemyGames.add(matrix1);
        enemyGames.add(matrix2);
        enemyGames.add(matrix3);
        enemyShipsPositions = enemyGames.get(position);
        hitsOnEnemyShips = new int[10][10];
        for (int i = 0; i < 10; i++) {
            System.arraycopy(enemyShipsPositions[i], 0, hitsOnEnemyShips[i], 0, 10);
        }
    }

    public ImageView attackEnemyBoard(int row, int col) throws CellAlreadyAttackedException, GameOverException {
        ImageView imageView = null;
        int damaged;
        if (hitsOnEnemyShips[row][col] == -1) {
            throw new CellAlreadyAttackedException("Esta celda ya ha sido atacada");
        }
        hitsOnEnemyShips[row][col] = -1;
        if (enemyShipsPositions[row][col] != 0) {
            damaged = enemyShipsPositions[row][col];

            boolean isSunk = true;
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (hitsOnEnemyShips[i][j] == damaged) {
                        isSunk = false;
                        break;
                    }
                }
                if (!isSunk) break;
            }

            if (isSunk) {
                imageView = new ImageView(sunk);
                this.onFire = damaged;
                if (checkGameOver()) {
                    throw new GameOverException("All enemy ships have been sunk!");
                }
            } else {
                imageView = new ImageView(hit);
                this.onFire = 0;
            }
        } else {
            imageView = new ImageView(fail);
            this.onFire = 0;
        }

        // Ensure the image fits within the cell size (optional)
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);

        // Add the image to the enemyBoard GridPane
        return imageView;
    }

    public int isOnFire() {
        return onFire;
    }

    public int[][] getEnemyShipsPositions() {
        return enemyShipsPositions;
    }
    public int[][] getMyShipsPositions() {
        return myShipsPositions;
    }

    public ImageView fireImage() {
        ImageView imageView = new ImageView(sunk);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        return imageView;
    }

    public void printBoard() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(hitsOnMyShips[i][j] + " ");
            }
            System.out.println();
        }
    }

    private boolean checkGameOver() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (hitsOnEnemyShips[i][j] > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public ImageView attackMyBoard() throws GameOverException {
        int row, col;
        String cell;

        // Buscar una celda no atacada
        do {
            row = random.nextInt(10);
            col = random.nextInt(10);
            cell = row + "," + col;
        } while (attackedCells.contains(cell));

        // Marcar la celda como atacada
        attackedCells.add(cell);
        hitsOnMyShips[row][col] = -1;
        boolean hit = myShipsPositions[row][col] != 0;
        boolean sunk = false;

        // Determinar el resultado del ataque
        if (hit) {
            int damaged = myShipsPositions[row][col];
            boolean isSunk = true;

            // Verificar si el barco está hundido
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (myShipsPositions[i][j] == damaged && hitsOnMyShips[i][j] != -1) {
                        isSunk = false;
                        break;
                    }
                }
                if (!isSunk) break;
            }

            if (isSunk) {
                sunk = true;
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        if (myShipsPositions[i][j] == damaged) {
                            hitsOnMyShips[i][j] = -1; // Asegurar que se marque como atacada
                            if (boardListener != null) {
                                boardListener.onCellAttacked(i, j, true, true);
                            }
                        }
                    }
                }
                // Verificar si todos los barcos han sido hundidos
                if (checkMyBoardGameOver()) {
                    if (boardListener != null) {
                        boardListener.onGameOver();
                    }
                    throw new GameOverException("All your ships have been sunk!");
                }
            }
        }

        // Notificar al oyente sobre el resultado del ataque
        if (boardListener != null) {
            boardListener.onCellAttacked(row, col, hit, sunk);
        }

        return new ImageView(); // Retorna una imagen adecuada según la lógica de la vista
    }

    private boolean checkMyBoardGameOver() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (myShipsPositions[i][j] != 0 && hitsOnMyShips[i][j] != -1) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setHitsOnMyShips(){
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                this.hitsOnMyShips[i][j] = myShipsPositions[i][j];
            }
        }
    }


}
