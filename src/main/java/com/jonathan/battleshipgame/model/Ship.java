package com.jonathan.battleshipgame.model;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Ship {

    public Ship(){}

    public   Group createCarrier() {
        Group carrier = new Group();
        // Aircraft carrier main figure
        Rectangle bodyCarrier = new Rectangle(0, 0, 120, 30);
        bodyCarrier.setArcWidth(20);
        bodyCarrier.setArcHeight(20);
        bodyCarrier.setFill(Color.GRAY);

        // Darker rectangle towards the inside
        Rectangle innerRectangle = new Rectangle(10, 5, 100, 20);
        innerRectangle.setFill(Color.DARKGRAY);

        // Landing strip (a long, thin rectangle in the middle)
        Rectangle landingStrip = new Rectangle(10, 13, 100, 4);
        landingStrip.setFill(Color.BLACK);

        // Dotted white line
        Line dashedLine = new Line(10, 15, 110, 15);
        dashedLine.setStroke(Color.WHITE);
        dashedLine.getStrokeDashArray().addAll(10d, 10d);  // dash length, gap length
        dashedLine.setStrokeWidth(2);

        carrier.getChildren().addAll(bodyCarrier,innerRectangle,landingStrip,dashedLine);
        return carrier;
    }

    public  Group createSubmarine() {
        Group submarine = new Group();
        // Submarine main figure
        Rectangle bodySubmarine = new Rectangle(0,0 , 90,30);
        bodySubmarine.setArcWidth(20);
        bodySubmarine.setArcHeight(20);
        bodySubmarine.setFill(Color.GRAY);
        submarine.getChildren().add(bodySubmarine);
        return submarine;
    }
    public Group createDestroyer() {
        Group destroyer = new Group();
        // Destroyer main figure
        Rectangle bodyDestroyer = new Rectangle(0,0 , 60,30);
        bodyDestroyer.setArcWidth(20);
        bodyDestroyer.setArcHeight(20);
        bodyDestroyer.setFill(Color.GRAY);
        destroyer.getChildren().add(bodyDestroyer);
        return destroyer;
    }
    public Group createFrigate() {
        Group frigate = new Group();
        // Frigate main figure
        Rectangle bodyFrigate = new Rectangle(0,0 , 30,30);
        bodyFrigate.setArcWidth(20);
        bodyFrigate.setArcHeight(20);
        bodyFrigate.setFill(Color.GRAY);
        frigate.getChildren().add(bodyFrigate);
        return frigate;
    }

}
