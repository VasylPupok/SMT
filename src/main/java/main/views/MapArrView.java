package main.views;


import main.views.Cell.*;
import main.views.MapView;

import java.awt.*;
import java.util.Random;

public class MapArrView {

    private static MapArrView mapArrView;
    private int rowsNumber;
    private int columnsNumber;
    private Cell[][] map;
    private final int CELL_WIDTH;
    private MapView mapView;

    private MapArrView(int CELL_WIDTH, int rowsNumber, int columnsNumber, MapView mapView) {
        this.CELL_WIDTH = CELL_WIDTH;
        this.rowsNumber = rowsNumber;
        this.columnsNumber = columnsNumber;
        map = new Cell[columnsNumber][rowsNumber];
        this.mapView = mapView;
        initFirstLevelMap();
    }

    public static MapArrView getMapArr(int CELL_WIDTH, int rowsNumber, int columnsNumber, MapView mapView) {
        mapArrView = new MapArrView(CELL_WIDTH, rowsNumber, columnsNumber, mapView);
        return mapArrView;
    }

    public static MapArrView getMapArrView() {
        return mapArrView;
    }

    private void initMap() {
        for (int i = 0; i < columnsNumber; i++) {
            for (int j = 0; j < rowsNumber; j++) {
                if (map[i][j] == null) {
                    map[i][j] = generateCellFill(i, j);
                }
            }
        }
    }

    public void initFirstLevelMap() {
        Random rand = new Random();
        for (int i = 0; i < columnsNumber; i++) {
            for (int j = 0; j < rowsNumber; j++) {
                switch (rand.nextInt(3)) {
                    case 0 -> map[i][j] = new MountainCell(CELL_WIDTH, i, j);
                    case 1 -> map[i][j] = new ForestCell(CELL_WIDTH, i, j);
                    case 2 -> map[i][j] = new GrassCell(CELL_WIDTH, i, j);
                }
            }
        }
    }

    private Cell generateCellFill(int i, int j) {
        Random random = new Random();
        return switch (random.nextInt(3)) {
            case 0 -> new MountainCell(CELL_WIDTH, i, j);
            case 1 -> new ForestCell(CELL_WIDTH, i, j);
            case 2 -> new GrassCell(CELL_WIDTH, i, j);
            default -> throw new IllegalStateException("Unexpected value: " + random.nextInt(4));
        };
    }

    public void changeCell(int i, int j, Cell prevCell) {
        Random random = new Random();
        int type = random.nextInt(4);
        switch (type) {
            case 0 -> map[i][j] = new MineralCell(CELL_WIDTH, i, j);
            case 1 -> map[i][j] = new FieldCell(CELL_WIDTH, i, j);
            case 2 -> map[i][j] = new GoldmineCell(CELL_WIDTH, i, j);
            case 3 -> {
                return;
            }
        }
        map[i][j].setDefaultFill();
    }

    public void changeCellOnGrass(int i, int j) {
        map[i][j] = new GrassCell(CELL_WIDTH, i, j);
    }

    public void changeCellOnForest(int i, int j) {
        map[i][j] = new ForestCell(CELL_WIDTH, i, j);
    }

    public void changeCellOnMountain(int i, int j) {
        map[i][j] = new MountainCell(CELL_WIDTH, i, j);
    }

    public void changeCellOnMineral(int i, int j) {
        map[i][j] = new MineralCell(CELL_WIDTH, i, j);
    }

    public void changeCellOnGoldmine(int i, int j) {
        map[i][j] = new GoldmineCell(CELL_WIDTH, i, j);
    }

    public void changeCellOnField(int i, int j) {
        map[i][j] = new FieldCell(CELL_WIDTH, i, j);
    }


    public Cell[][] getMap() {
        return map;
    }

    public int getRowsNumber() {
        return rowsNumber;
    }

    public int getColumnsNumber() {
        return columnsNumber;
    }

    public MapView getMapView() {
        return mapView;
    }
}
