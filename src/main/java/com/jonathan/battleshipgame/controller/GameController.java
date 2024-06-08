package com.jonathan.battleshipgame.controller;

import com.jonathan.battleshipgame.exceptions.CellAlreadyAttackedException;
import com.jonathan.battleshipgame.exceptions.GameOverException;
import com.jonathan.battleshipgame.model.Board;
import com.jonathan.battleshipgame.model.IBoard;
import com.jonathan.battleshipgame.model.Ship;
import com.jonathan.battleshipgame.view.GameStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.geometry.Point2D;
import javafx.scene.layout.GridPane;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class GameController implements IBoard {
    private Board gameBoard;
    private int rotation = 0;
    private Group draggedShip;
    private Group rotatedShip;
    private Group carrier;
    private Group submarine;
    private Group submarine2;
    private Group destroyer;
    private Group destroyer2;
    private Group destroyer3;
    private Group frigate;
    private Group frigate2;
    private Group frigate3;
    private Group frigate4;
    private Ship shipGenerator;
    private int[][] myBoardOccupied = new int[10][10];
    private boolean isRotated = false;
    private boolean isVertical;
    private int whichGame = 0;
    private int shipNum=0;

    @FXML
    private Button buttonPlaceEnemyShips;

    @FXML
    private Button buttonPlay;

    @FXML
    private Button buttonRestart;

    @FXML
    private Button buttonStartBattle;

    @FXML
    private Button buttonVerify;

    @FXML
    private GridPane enemyBoard;

    @FXML
    private GridPane shipsBoard;

    @FXML
    private GridPane myBoard;

    @FXML
    private void initialize() {
        this.gameBoard = new Board(myBoard, shipsBoard, enemyBoard);
        gameBoard.setBoardListener(this);
        gameBoard.populateGrid();
    }

    @FXML
    void onClickButtonRestart(ActionEvent event) throws IOException {
        gameBoard=null;
        GameStage.deleteInstance();
        GameStage.getInstance();
    }

    @FXML
    void onClickButtonStartBattle(ActionEvent event) {
        setupEnemyBoardClickEvent();
        gameBoard.setHitsOnMyShips();
    }

    @FXML
    void onClickButtonPlaceEnemyShips(ActionEvent event) {
        gameBoard.selectGame(whichGame);
        whichGame++;
        if (whichGame > 2) {
            whichGame = 0;
        }
        buttonStartBattle.setDisable(false);
    }

    @FXML
    void onClickButtonVerify(ActionEvent event) throws IOException {
        if (isBoardValid(gameBoard.getMyShipsPositions())) {
            buttonPlaceEnemyShips.setDisable(false);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Board");
            alert.setHeaderText(null);
            alert.setContentText("You must place exactly 20 cells worth of ships on your board.");
            alert.showAndWait();
        }
    }

    @FXML
    void onClickButtonRotate(ActionEvent event) {
        toggleRotation(shipsBoard);
    }

    @FXML
    void onMousePressedShow(MouseEvent event) {
        showEnemyShips();
    }

    @FXML
    void onMouseReleasedHide(MouseEvent event) {
        hideEnemyShips();
    }

    private void showEnemyShips() {
        int[][] enemyShipsPositions = gameBoard.getEnemyShipsPositions();
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (enemyShipsPositions[row][col] != 0) {
                    Rectangle shipRectangle = new Rectangle(30, 30);
                    shipRectangle.setFill(Color.GRAY);
                    enemyBoard.add(shipRectangle, col, row);
                }
            }
        }
    }

    public boolean isBoardValid(int[][] board) {
        int occupiedCount = 0;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (board[i][j] != 0) {
                    occupiedCount++;
                }
            }
        }

        return occupiedCount == 20;
    }

    private void hideEnemyShips() {
        enemyBoard.getChildren().removeIf(node -> node instanceof Rectangle);
    }

    private void toggleRotation(GridPane shipsBoard) {
        isRotated = !isRotated;
        int rotationAngle = isRotated ? 90 : 0;
        rotateShips(rotationAngle, shipsBoard);
    }

    private void rotateShips(int rotation, GridPane shipsBoard) {
        switch (rotation) {
            case 90:
                int numShips = shipsBoard.getChildren().size();
                for (int i = 0; i < numShips; i++) {
                    double width = shipsBoard.getChildren().get(0).getBoundsInLocal().getWidth();
                    rotatedShip = (Group) shipsBoard.getChildren().get(0);
                    rotatedShip.setRotate(rotation);
                    rotatedShip.setTranslateY(width / 2 - 15);
                    rotatedShip.setTranslateX(-width / 2 + 15);
                    shipsBoard.getChildren().remove(0);
                    shipsBoard.add(rotatedShip, i, 0);
                }
                break;

            case 0:
                int numShips2 = shipsBoard.getChildren().size();
                for (int i = 0; i < numShips2; i++) {
                    double height = shipsBoard.getChildren().get(0).getBoundsInLocal().getHeight();
                    rotatedShip = (Group) shipsBoard.getChildren().get(0);
                    rotatedShip.setRotate(rotation);
                    rotatedShip.setTranslateY(-height / 2 - 15 + 30);
                    rotatedShip.setTranslateX(height / 2 + 15 - 30);
                    shipsBoard.getChildren().remove(0);
                    if (i < 5) {
                        shipsBoard.add(rotatedShip, 0, i);
                    } else {
                        shipsBoard.add(rotatedShip, 7, i - 5);
                    }
                }
                break;
        }
    }

    @FXML
    void onClickButtonPlay(ActionEvent event) {
        shipGenerator = new Ship();
        carrier = shipGenerator.createCarrier();
        submarine = shipGenerator.createSubmarine();
        submarine2 = shipGenerator.createSubmarine();
        destroyer = shipGenerator.createDestroyer();
        destroyer2 = shipGenerator.createDestroyer();
        destroyer3 = shipGenerator.createDestroyer();
        frigate = shipGenerator.createFrigate();
        frigate2 = shipGenerator.createFrigate();
        frigate3 = shipGenerator.createFrigate();
        frigate4 = shipGenerator.createFrigate();

        // Add the group to the GridPane
        shipsBoard.add(carrier, 0, 0);
        shipsBoard.add(submarine, 0, 1);
        shipsBoard.add(submarine2, 0, 2);
        shipsBoard.add(destroyer, 0, 3);
        shipsBoard.add(destroyer2, 0, 4);
        shipsBoard.add(destroyer3, 7, 0);
        shipsBoard.add(frigate, 7, 1);
        shipsBoard.add(frigate2, 7, 2);
        shipsBoard.add(frigate3, 7, 3);
        shipsBoard.add(frigate4, 7, 4);

        enableDrag(carrier);
        enableDrag(submarine);
        enableDrag(submarine2);
        enableDrag(destroyer);
        enableDrag(destroyer2);
        enableDrag(destroyer3);
        enableDrag(frigate);
        enableDrag(frigate2);
        enableDrag(frigate3);
        enableDrag(frigate4);

        myBoard.setOnDragOver(this::handleDragOver);
        myBoard.setOnDragDropped(this::handleDragDropped);
    }

    private void handleDragDropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;

        if (db.hasString() && "ship".equals(db.getString())) {
            Point2D localCoords = myBoard.sceneToLocal(event.getSceneX(), event.getSceneY());

            // Get the local coordinates
            double localX = localCoords.getX();
            double localY = localCoords.getY();

            // Convert local coordinates to row and column indices of the GridPane
            int row = (int) (localY / 30); // Height of each cell is 30
            int col = (int) (localX / 30); // Width of each cell is 30

            // Check if the coordinates are within the range of the GridPane
            if (row >= 0 && row < 10 && col >= 0 && col < 10) {
                // Remove the ship from shipsBoard to avoid duplicates
                shipsBoard.getChildren().remove(draggedShip);

                // Check if the node is already in myBoard and remove it if necessary
                myBoard.getChildren().remove(draggedShip);

                // Determine the size of the ship (number of cells it occupies)
                int shipLength = getWidth(draggedShip);

                // Insert the ship in the corresponding cell of myBoard
                myBoard.add(draggedShip, col, row);

                // Mark the cells in the myBoardOccupied array
                gameBoard.markBoardOccupied(row, col, shipLength, isRotated, shipNum);

                // Disable dragging for the ship
                draggedShip.setOnDragDetected(null);
                draggedShip.setOnMouseDragged(null);
                draggedShip.setOnDragDone(null);

                // Show confirmation message
                System.out.println("Ship inserted at: (" + row + "," + col + ")");
                success = true;
            } else {
                System.out.println("Coordinates out of range: (" + row + "," + col + ")");
            }
        }
        event.setDropCompleted(success);
        event.consume();
    }

    private int getWidth(Group draggedShip) {
        return (int) (draggedShip.getChildren().get(0).getBoundsInLocal().getWidth() / 30);
    }

    private void handleDragOver(DragEvent event) {
        if (event.getGestureSource() != myBoard && event.getDragboard().hasString()) {
            Point2D localCoords = myBoard.sceneToLocal(event.getSceneX(), event.getSceneY());

            // Get the local coordinates
            double localX = localCoords.getX();
            double localY = localCoords.getY();

            // Convert local coordinates to row and column indices of the GridPane
            int row = (int) (localY / 30); // Height of each cell is 30
            int col = (int) (localX / 30); // Width of each cell is 30

            // Determine the width of the ship (number of cells it occupies)
            int shipLength = getWidth(draggedShip);

            // Check if the ship fits completely in the grid
            if (row >= 0 && row + (isRotated ? shipLength : 1) <= 10 && col >= 0 && col + (isRotated ? 1 : shipLength) <= 10) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
        }
        event.consume();
    }

    private void enableDrag(Group ship) {
        ship.setOnDragDetected(event -> {
            String shipType = (String) ship.getProperties().get("shipType");

            switch (shipType) {
                case "carrier1":
                    shipNum = 1;
                    break;
                case "submarine1":
                    shipNum = 2;
                    break;
                case "submarine2":
                    shipNum = 3;
                    break;
                case "destroyer1":
                    shipNum = 4;
                    break;
                case "destroyer2":
                    shipNum = 5;
                    break;
                case "destroyer3":
                    shipNum = 6;
                    break;
                case "frigate1":
                    shipNum = 7;
                    break;
                case "frigate2":
                    shipNum = 8;
                    break;
                case "frigate3":
                    shipNum = 9;
                    break;
                case "frigate4":
                    shipNum = 10;
                    break;
            }

            Dragboard db = ship.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString("ship");
            db.setContent(content);
            draggedShip = ship;
            System.out.println("Drag started");

            Point2D localCoords = myBoard.sceneToLocal(event.getSceneX(), event.getSceneY());

            // Get the local coordinates
            double localX = localCoords.getX();
            double localY = localCoords.getY();

            // Convert local coordinates to row and column indices of the GridPane
            int row = (int) (localY / 30); // Height of each cell is 30
            int col = (int) (localX / 30); // Width of each cell is 30

            // Print the local coordinates
            System.out.println("Starting coordinates: (" + row + "," + col + ")");
            event.consume();
        });
    }

    private void setupEnemyBoardClickEvent() {
        enemyBoard.setOnMouseClicked(event -> {
            Point2D localCoords = enemyBoard.sceneToLocal(event.getSceneX(), event.getSceneY());

            // Get the local coordinates
            double localX = localCoords.getX();
            double localY = localCoords.getY();

            // Convert local coordinates to row and column indices of the GridPane
            int row = (int) (localY / 30); // Height of each cell is 30
            int col = (int) (localX / 30); // Width of each cell is 30

            // Print the click coordinates
            System.out.println("Clicked coordinates: (" + row + "," + col + ")");
            try {
                enemyBoard.add(gameBoard.attackEnemyBoard(row, col), col, row);
                gameBoard.attackMyBoard();
            } catch (CellAlreadyAttackedException e) {
                System.out.println(e.getMessage());
            } catch (GameOverException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Game Over");
                alert.setHeaderText(null);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }

            int isOnFire = gameBoard.isOnFire();
            int[][] enemyPositions = gameBoard.getEnemyShipsPositions();
            if (isOnFire != 0) {
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        if (enemyPositions[i][j] == isOnFire) {
                            enemyBoard.add(gameBoard.fireImage(), j, i);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onCellAttacked(int row, int col, boolean hit, boolean sunk) {
        ImageView imageView;
        if (sunk) {
            imageView = new ImageView(new Image(getClass().getResource("/com/jonathan/battleshipgame/images/fire.png").toExternalForm()));
        } else if (hit) {
            imageView = new ImageView(new Image(getClass().getResource("/com/jonathan/battleshipgame/images/bomb.png").toExternalForm()));
        } else {
            imageView = new ImageView(new Image(getClass().getResource("/com/jonathan/battleshipgame/images/x.png").toExternalForm()));
        }

        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        myBoard.add(imageView, col, row);
    }

    @Override
    public void onGameOver() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("All your ships have been sunk!");
        alert.showAndWait();
    }
}
