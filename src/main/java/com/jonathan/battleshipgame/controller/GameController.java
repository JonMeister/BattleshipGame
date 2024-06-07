package com.jonathan.battleshipgame.controller;

import com.jonathan.battleshipgame.model.Board;
import com.jonathan.battleshipgame.model.Ship;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.Group;
import javafx.geometry.Point2D;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;

public class GameController {
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
    private int whichGame=0;


    @FXML
    private GridPane enemyBoard;

    @FXML
    private GridPane shipsBoard;

    @FXML
    private GridPane myBoard;

    @FXML
    private void initialize() {
        this.gameBoard = new Board(myBoard, shipsBoard,enemyBoard);
        gameBoard.populateGrid();
    }
    @FXML
    void onClickButtonStartBattle(ActionEvent event) {
        setupEnemyBoardClickEvent();
    }
    @FXML
    void onClickButtonPlaceEnemyShips(ActionEvent event) {
        gameBoard.enemyShipsPositions(whichGame);
        whichGame++;
        if (whichGame >2) {whichGame=0;}
    }
    @FXML
    void onClickButtonVerify(ActionEvent event) {
        gameBoard.printBoard();
    }

    @FXML
    void onClickButtonRotate(ActionEvent event) {
        toggleRotation(shipsBoard);
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
                    System.out.println(width);
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
                    System.out.println(numShips2);
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
                gameBoard.markBoardOccupied(row, col, shipLength, isRotated);

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


        });
    }

}
