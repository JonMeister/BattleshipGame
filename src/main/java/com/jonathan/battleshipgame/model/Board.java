package com.jonathan.battleshipgame.model;

import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private boolean[][] myShipsPositions = new boolean[10][10];
    private boolean [][] enemyShipsPositions = new boolean[10][10];
    List <boolean[][]> enemyGames= new ArrayList<boolean[][]>();

    private GridPane myBoard;
    private GridPane enemyBoard;

    private GridPane shipsBoard;
    private boolean isRotated = false;
    private boolean isVertical;

    public Board(GridPane myBoard, GridPane shipsBoard,GridPane enemyBoard) {
        this.myBoard = myBoard;
        this.shipsBoard = shipsBoard;
        this.enemyBoard= enemyBoard;
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
    public void markBoardOccupied(int row, int col, int length, boolean isVertical) {
        if(isVertical){
            for(int i=0;i<length;i++){
                if(row+i<10){
                    myShipsPositions[row+i][col]=true;//Marcarcon2siesvertical
                }
            }
        }else{
            for(int i=0;i<length;i++){
                if(col+i<10){
                    myShipsPositions[row][col+i]=true;//Marcarcon1sieshorizontal
                }
            }
        }

    }

    public boolean[][] enemyShipsPositions(int position){
        boolean[][] matrix1 = {
                {false, false, false, false, false, false, false, false, false, false},
                {false, true,  true,  true,  true,  false, false, false, false, false},
                {false, false, false, false, false, false, false, false, false, false},
                {false, false, false, true,  true,  true,  false, false, false, false},
                {false, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, true,  true,  false, false},
                {false, false, false, false, false, false, false, false, false, false},
                {false, true,  false, false, false, false, false, false, false, false},
                {false, false, false, false, true,  false, false, false, false, false}
        };
        boolean[][] matrix2 = {
                {false, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, true,  true,  false, false, false, false},
                {false, true,  true,  true,  false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, false, false},
                {true,  true,  true,  true,  false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, false, false},
                {false, false, true,  true,  false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, false, false},
                {false, true,  false, false, false, true,  false, false, false, false}
        };
        boolean[][] matrix3 = {
                {false, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, true,  false},
                {false, true,  true,  true,  true,  false, false, false, false, false},
                {false, false, false, false, false, false, false, false, false, false},
                {true,  true,  true,  false, false, false, false, false, false, false},
                {false, false, false, false, false, true,  true,  false, false, false},
                {false, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, true,  true,  false, false, false, false},
                {false, false, false, false, false, false, true,  false, false, false}
        };
        enemyGames.add(matrix1);
        enemyGames.add(matrix2);
        enemyGames.add(matrix3);
        return enemyGames.get(position);
    }

    public void attack

    public void printBoard() {
        for (boolean[] myShipsPosition : myShipsPositions) {
            for (boolean b : myShipsPosition) {
                System.out.print(b ? "1" : "0");
            }
            System.out.println();
        }
    }

}
