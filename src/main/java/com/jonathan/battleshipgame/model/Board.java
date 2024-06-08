package com.jonathan.battleshipgame.model;

import com.jonathan.battleshipgame.exceptions.CellAlreadyAttackedException;
import com.jonathan.battleshipgame.exceptions.GameOverException;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.Serializable;
import java.util.*;

/**
 * Represents the game board for the Battleship game.
 */
public class Board implements Serializable {
    private static final long serialVersionUID = 1L;

    private int[][] myShipsPositions = new int[10][10];
    private int[][] enemyShipsPositions = new int[10][10];
    private int[][] hitsOnEnemyShips = new int[10][10];
    private int[][] hitsOnMyShips = new int[10][10];
    private transient IBoard boardListener;

    private transient Image fail;
    private transient Image hit;
    private transient Image sunk;
    private Set<String> attackedCells = new HashSet<>();
    private transient Random random = new Random();

    private List<int[][]> enemyGames = new ArrayList<>();
    private int onFire;

    private transient GridPane myBoard;
    private transient GridPane enemyBoard;
    private transient GridPane shipsBoard;

    /**
     * Constructs a new Board.
     *
     * @param myBoard    the player's board
     * @param shipsBoard the board containing the ships
     * @param enemyBoard the enemy's board
     */
    public Board(GridPane myBoard, GridPane shipsBoard, GridPane enemyBoard) {
        this.myBoard = myBoard;
        this.shipsBoard = shipsBoard;
        this.enemyBoard = enemyBoard;
        resetBoard();
        initializeTransientFields();
    }

    /**
     * Sets the board listener.
     *
     * @param boardListener the board listener
     */
    public void setBoardListener(IBoard boardListener) {
        this.boardListener = boardListener;
    }

    /**
     * Resets the board by clearing all positions and hits.
     */
    private void resetBoard() {
        myShipsPositions = new int[10][10];
        enemyShipsPositions = new int[10][10];
        hitsOnEnemyShips = new int[10][10];
        hitsOnMyShips = new int[10][10];
        attackedCells.clear();
    }

    /**
     * Initializes the transient fields.
     */
    private void initializeTransientFields() {
        fail = new Image(String.valueOf(getClass().getResource("/com/jonathan/battleshipgame/images/x.png")));
        hit = new Image(String.valueOf(getClass().getResource("/com/jonathan/battleshipgame/images/bomb.png")));
        sunk = new Image(String.valueOf(getClass().getResource("/com/jonathan/battleshipgame/images/fire.png")));
        random = new Random();
    }

    /**
     * Initializes the transient fields with new GridPane objects.
     *
     * @param myBoard    the player's board
     * @param shipsBoard the board containing the ships
     * @param enemyBoard the enemy's board
     */
    public void initializeTransientFields(GridPane myBoard, GridPane shipsBoard, GridPane enemyBoard) {
        this.myBoard = myBoard;
        this.shipsBoard = shipsBoard;
        this.enemyBoard = enemyBoard;
        initializeTransientFields();
    }

    /**
     * Populates the grid with empty cells.
     */
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

    /**
     * Creates a TextField with specified style.
     *
     * @param style the style to apply
     * @return the created TextField
     */
    private TextField createTextField(String style) {
        TextField textField = new TextField();
        textField.setPrefHeight(30);
        textField.setPrefWidth(30);
        textField.setAlignment(Pos.CENTER);
        textField.setStyle(style);
        textField.setMouseTransparent(true);
        return textField;
    }

    /**
     * Marks the board as occupied by a ship.
     *
     * @param row       the starting row
     * @param col       the starting column
     * @param length    the length of the ship
     * @param isVertical whether the ship is placed vertically
     * @param num       the ship identifier
     */
    public void markBoardOccupied(int row, int col, int length, boolean isVertical, int num) {
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

    /**
     * Selects a predefined enemy game configuration.
     *
     * @param position the position of the predefined game
     */
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

    /**
     * Attacks the enemy board at the specified position.
     *
     * @param row the row index
     * @param col the column index
     * @return an ImageView representing the attack result
     * @throws CellAlreadyAttackedException if the cell was already attacked
     * @throws GameOverException            if the game is over
     */
    public ImageView attackEnemyBoard(int row, int col) throws CellAlreadyAttackedException, GameOverException {
        ImageView imageView = null;
        int damaged;
        if (hitsOnEnemyShips[row][col] == -1 || hitsOnEnemyShips[row][col] == -2) {
            throw new CellAlreadyAttackedException("This cell has already been attacked");
        }
        hitsOnEnemyShips[row][col] = -1;
        if (enemyShipsPositions[row][col] != 0) {
            damaged = enemyShipsPositions[row][col];
            hitsOnEnemyShips[row][col] = -2;

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
                    throw new GameOverException("YOU WON. All enemy ships have been sunk!");
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

    /**
     * Checks if there is a ship on fire.
     *
     * @return the ship identifier if on fire, otherwise 0
     */
    public int isOnFire() {
        return onFire;
    }

    /**
     * Gets the positions of the enemy ships.
     *
     * @return a 2D array of enemy ship positions
     */
    public int[][] getEnemyShipsPositions() {
        return enemyShipsPositions;
    }

    /**
     * Gets the positions of the player's ships.
     *
     * @return a 2D array of player ship positions
     */
    public int[][] getMyShipsPositions() {
        return myShipsPositions;
    }

    /**
     * Returns an ImageView representing a fire image.
     *
     * @return an ImageView of a fire image
     */
    public ImageView fireImage() {
        ImageView imageView = new ImageView(sunk);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        return imageView;
    }

    /**
     * Prints the player's board to the console.
     */
    public void printBoard() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(hitsOnMyShips[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Checks if the game is over by verifying all enemy ships are sunk.
     *
     * @return true if the game is over, otherwise false
     */
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

    /**
     * Attacks the player's board randomly.
     *
     * @return an ImageView representing the attack result
     * @throws GameOverException if the game is over
     */
    public ImageView attackMyBoard() throws GameOverException {
        int row, col;
        String cell;

        // Search for a cell that has not been attacked
        do {
            row = random.nextInt(10);
            col = random.nextInt(10);
            cell = row + "," + col;
        } while (attackedCells.contains(cell));

        // Mark the cell as attacked
        attackedCells.add(cell);
        boolean hit = myShipsPositions[row][col] != 0;
        boolean sunk = false;

        // Determine the attack result
        if (hit) {
            hitsOnMyShips[row][col] = -2; // Mark as hit
            int damaged = myShipsPositions[row][col];
            boolean isSunk = true;

            // Verify if the ship is sunk
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (myShipsPositions[i][j] == damaged && hitsOnMyShips[i][j] != -2) {
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
                            if (boardListener != null) {
                                boardListener.onCellAttacked(i, j, true, true);
                            }
                        }
                    }
                }
                // Verify if all ships are sunk
                if (checkMyBoardGameOver()) {
                    if (boardListener != null) {
                        boardListener.onGameOver();
                    }
                    throw new GameOverException("YOU LOSE. All your ships have been sunk!");
                }
            }
        } else {
            hitsOnMyShips[row][col] = -1; // Mark as missed
        }

        // Notify the listener about the attack result
        if (boardListener != null) {
            boardListener.onCellAttacked(row, col, hit, sunk);
        }

        return new ImageView(); // Return an appropriate image based on the view logic
    }

    /**
     * Checks if all player's ships are sunk.
     *
     * @return true if all ships are sunk, otherwise false
     */
    private boolean checkMyBoardGameOver() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                // Check if there is any ship cell that is not marked as hit (-2) or sunk (-1)
                if (myShipsPositions[i][j] != 0 && hitsOnMyShips[i][j] != -2 && hitsOnMyShips[i][j] != -1) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Sets the hits on the player's ships.
     */
    public void setHitsOnMyShips() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                this.hitsOnMyShips[i][j] = myShipsPositions[i][j];
            }
        }
    }

    /**
     * Gets the hits on the enemy ships.
     *
     * @return a 2D array of hits on enemy ships
     */
    public int[][] getHitsOnEnemyShips() {
        return hitsOnEnemyShips;
    }

    /**
     * Gets the hits on the player's ships.
     *
     * @return a 2D array of hits on player's ships
     */
    public int[][] getHitsOnMyShips() {
        return hitsOnMyShips;
    }
}
