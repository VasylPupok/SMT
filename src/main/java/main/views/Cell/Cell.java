package main.views.Cell;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import main.ToolPanel;
import main.views.City;
import main.views.MapArrView;
import main.views.MapView;
import main.views.PlayersHandler;

import java.io.*;

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
        setClick();
        setCellImage();
        this.setStroke(Paint.valueOf("BLACK"));
    }

    public Cell(int length) {
        this.x = 0;
        this.y = 0;
        this.setWidth(length);
        this.setHeight(length);
        setMousePointed();
        setClick();
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

    private void setClick() {
        this.setOnMouseClicked(mouseEvent -> {
            try {
                clickResponse();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
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

    public void setCityWhereBuild(City cityWhereBuild) {
        this.cityWhereBuild = cityWhereBuild;
    }

    public City getCityWhereBuild() {
        return cityWhereBuild;
    }

    public boolean isChosen() {
        return isChosen;
    }

    public void setChosen(boolean chosen) {
        isChosen = chosen;
    }

    public ArmyCell getArmyCell() {
        return armyCellView;
    }

    public void setArmyCellView(ArmyCell armyCellView) {
        this.armyCellView = armyCellView;
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

    public boolean isReadyToGotAttack() {
        return readyToGotAttack;
    }

    public void setReadyToGotAttack(boolean readyToGotAttack) {
        this.readyToGotAttack = readyToGotAttack;
    }

    // methods

    protected boolean cellIsChosen() {
        Cell[][] cell = MapArrView.getMapArrView().getMap();
        for (int i = 0; i < MapArrView.getMapArrView().getColumnsNumber(); i++) {
            for (int j = 0; j < MapArrView.getMapArrView().getRowsNumber(); j++) {
                if (cell[i][j].isChosen()) {
                    return true;
                }
            }
        }
        return false;
    }

    protected void checkIfCanGotAttack() {
        Cell[][] cell = MapArrView.getMapArrView().getMap();
        for (int i = Math.max(getArmyCell().takeX() - 1, 0); i <= Math.min(getArmyCell().takeX() + 1, MapArrView.getMapArrView().getColumnsNumber() - 1); i++) {
            for (int j = Math.max(getArmyCell().takeY() - 1, 0); j <= Math.min(getArmyCell().takeY() + 1, MapArrView.getMapArrView().getRowsNumber() - 1); j++) {
                if (cell[i][j] instanceof ArmyCell) {
                    if (PlayersHandler.getPlayersHandler().getPlayer(1).hasArmy(((ArmyCell) cell[i][j]).getArmy())) {
                        cell[i][j].setArmyCellView(getArmyCell());
                        getArmyCell().setChosen(true);
                        ((ArmyCell) cell[i][j]).gotAttack();
                    }
                }
            }
        }
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

    /***/
    protected void clickResponse() throws IOException, InterruptedException {


        if (ToolPanel.getInstance().getActionsPanel().isReadyToDelete()) {
            if (this.getClass().equals(GoldmineCell.class)) {
                MapView.getMapView().changeOnGrass(x, y);
                getCityWhereBuild().getCityCell().changeTerritoryHighlight();
            } else if (this.getClass().equals(MineralCell.class)) {
                MapView.getMapView().changeOnMountain(x, y);
                getCityWhereBuild().getCityCell().changeTerritoryHighlight();
            } else if (this.getClass().equals(FieldCell.class)) {
                MapView.getMapView().changeOnForest(x, y);
                getCityWhereBuild().getCityCell().changeTerritoryHighlight();
            }
        }

        if (ToolPanel.getInstance().getActionsPanel().cityIsActivated()) {
            return;
        }

        ToolPanel.getInstance().refresh(this);
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