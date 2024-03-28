package main.views.Cell;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import main.views.City;

import java.io.*;

public abstract class Cell extends Rectangle {

    private int length;
    private int x;
    private int y;
    private boolean isEmpty; //cell don't have any structures
    private boolean readyToBuild;
    private boolean readyToMove;
    private boolean readyToGotAttack;
    private boolean isChosen;
    private boolean armyCanMove;
    private boolean armyCanAttack;

    private City cityWhereBuild;

    private ArmyCell armyCellView;

    public Cell(int length, int x, int y, boolean isEmpty, boolean armyCanMove, boolean armyCanAttack) {
        this.x = x;
        this.y = y;
        this.isEmpty = isEmpty;
        this.armyCanMove = armyCanMove;
        this.armyCanAttack = armyCanAttack;
        this.setWidth(length);
        this.setHeight(length);
        setMousePointed();
        setCellImage();
        this.setStroke(Paint.valueOf("BLACK"));
    }

    public Cell(int length) {
        this.x = 0;
        this.y = 0;
        this.setWidth(length);
        this.setHeight(length);
        setCellImage();
        this.setStroke(Paint.valueOf("BLACK"));
    }

    // private methods

    /**
     * Method highlights the cell when mouse is pointed on it
     *
     * @since 1.1.0
     */
    private void setMousePointed() {

        this.setOnMouseEntered(mouseEvent -> {
            mouseEnteredResponse();
        });

        this.setOnMouseExited(mouseEvent -> {
            mouseExitedResponse();
        });
    }


    // getters & setters

    public int getLength() {
        return length;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public int takeX() {
        return x;
    }

    public int takeY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    public void setCityWhereBuild(City cityWhereBuild) {
        this.cityWhereBuild = cityWhereBuild;
    }

    public boolean isReadyToBuild() {
        return readyToBuild;
    }

    public void setReadyToBuild(boolean readyToBuild) {
        this.readyToBuild = readyToBuild;
    }

    public boolean isReadyToMove() {
        return readyToMove;
    }

    public void setReadyToMove(boolean readyToMove) {
        this.readyToMove = readyToMove;
    }
    public boolean isChosen() {
        return isChosen;
    }

    public void setChosen(boolean chosen) {
        isChosen = chosen;
    }

    public boolean isArmyCanMove() {
        return armyCanMove;
    }

    public void setArmyCanMove(boolean armyCanMove) {
        this.armyCanMove = armyCanMove;
    }

    public boolean isArmyCanAttack() {
        return armyCanAttack;
    }

    public void setArmyCanAttack(boolean armyCanAttack) {
        this.armyCanAttack = armyCanAttack;
    }

    public void setArmyCellView(ArmyCell armyCellView) {
        this.armyCellView = armyCellView;
    }
    public ArmyCell getArmyCell() {
        return armyCellView;
    }

    public boolean isReadyToGotAttack() {
        return readyToGotAttack;
    }

    public void setReadyToGotAttack(boolean readyToGotAttack) {
        this.readyToGotAttack = readyToGotAttack;
    }

    // methods
    public City getCityWhereBuild() {
        return cityWhereBuild;
    }


    protected void fillCell(String url) {
        Image city = new Image(url);
        this.setFill(new ImagePattern(city, super.getX(), super.getY(), super.getWidth(), super.getHeight(), false));
    }

    /**
     * Restores default fill of empty
     *
     * @since 1.1.1
     */
    public void setDefaultFill() {
        setCellImage();
    }

    protected void mouseEnteredResponse() {
        this.setStroke(Paint.valueOf("ORANGE"));
        toFront();
    }

    protected void mouseExitedResponse() {
        this.setStroke(Paint.valueOf("BLACK"));
        toBack();
    }


    //abstract methods

    /**
     * Sets image of particular cell
     *
     * @see MountainCell
     * @since 1.1.0
     */
    protected abstract void setCellImage();
}