package com.jonathan.battleshipgame.model;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class Ship {

    private static int carrierCount = 1;
    private static int submarineCount = 1;
    private static int destroyerCount = 1;
    private static int frigateCount = 1;

    // Constructor
    public Ship() {
        resetCounters(); // Reiniciar contadores cada vez que se crea una nueva instancia
    }

    // Método estático para reiniciar contadores
    public static void resetCounters() {
        carrierCount = 1;
        submarineCount = 1;
        destroyerCount = 1;
        frigateCount = 1;
    }

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
        carrier.getProperties().put("shipType", "carrier" + carrierCount++);
        return carrier;
    }

    public  Group createSubmarine() {
        Group submarine = new Group();
        // Submarine main figure (base rectangle)
        Rectangle bodySubmarine = new Rectangle(0, 0, 90, 30);
        bodySubmarine.setArcWidth(20);
        bodySubmarine.setArcHeight(20);
        bodySubmarine.setFill(Color.TRANSPARENT); // Make the base rectangle transparent
        bodySubmarine.setStroke(Color.TRANSPARENT); // Make the border of the base rectangle transparent
        submarine.getChildren().add(bodySubmarine);

        // Submarine body
        Rectangle mainBody = new Rectangle(0, 10, 90, 10);
        mainBody.setArcWidth(10);
        mainBody.setArcHeight(10);
        mainBody.setFill(Color.GRAY);
        submarine.getChildren().add(mainBody);

        // Submarine conning tower
        Rectangle tower = new Rectangle(55, 5, 10, 10);
        tower.setArcWidth(5);
        tower.setArcHeight(5);
        tower.setFill(Color.DARKGRAY);
        submarine.getChildren().add(tower);

        // Submarine periscope
        Rectangle periscope = new Rectangle(60, -5, 3, 5);
        periscope.setFill(Color.DARKGRAY);
        submarine.getChildren().add(periscope);

        // Portholes
        for (int i = 0; i < 3; i++) {
            Rectangle porthole = new Rectangle(20 + i * 20, 12, 5, 5);
            porthole.setFill(Color.BLACK);
            submarine.getChildren().add(porthole);
        }

        submarine.getProperties().put("shipType", "submarine" + submarineCount++);

        return submarine;
    }
    public Group createDestroyer() {
        Group destroyer = new Group();
        // Destroyer main figure
        Rectangle bodyDestroyer = new Rectangle(0, 0, 60, 30);
        bodyDestroyer.setArcWidth(20);
        bodyDestroyer.setArcHeight(20);
        bodyDestroyer.setFill(Color.TRANSPARENT);
        bodyDestroyer.setStroke(Color.TRANSPARENT);

        // Adding the destroyer design
        Rectangle body = new Rectangle(5, 10, 50, 10);
        body.setFill(Color.GRAY);

        Rectangle tower = new Rectangle(20, 5, 10, 10);
        tower.setFill(Color.DARKGRAY);

        Circle frontGun = new Circle(5, 15, 2);
        frontGun.setFill(Color.DARKGRAY);

        Circle backGun = new Circle(55, 15, 2);
        backGun.setFill(Color.DARKGRAY);

        destroyer.getChildren().addAll(bodyDestroyer, body, tower, frontGun, backGun);
        destroyer.getProperties().put("shipType", "destroyer" + destroyerCount++);

        return destroyer;
    }
    public Group createFrigate() {
        Group frigate = new Group();
        // Frigate main figure
        Rectangle bodyFrigate = new Rectangle(0, 0, 30, 30);
        bodyFrigate.setArcWidth(20);
        bodyFrigate.setArcHeight(20);
        bodyFrigate.setFill(Color.TRANSPARENT);
        bodyFrigate.setStroke(Color.TRANSPARENT);

        // Adding the frigate design
        Rectangle body = new Rectangle(5, 10, 20, 10);
        body.setFill(Color.GRAY);

        Rectangle tower = new Rectangle(10, 5, 10, 10);
        tower.setFill(Color.DARKGRAY);

        Circle frontGun = new Circle(5, 15, 2);
        frontGun.setFill(Color.DARKGRAY);

        Circle backGun = new Circle(25, 15, 2);
        backGun.setFill(Color.DARKGRAY);

        // Adding the sail
        Polygon sail = new Polygon();
        sail.getPoints().addAll(15.0, 0.0, 25.0, 10.0, 15.0, 20.0);
        sail.setFill(Color.WHITE);

        frigate.getChildren().addAll(bodyFrigate, body, tower, frontGun, backGun, sail);
        frigate.getProperties().put("shipType", "frigate" + frigateCount++);

        return frigate;
    }

}
