package main.views;

import main.LevelScene;
import main.MiniMap;
import main.views.Cell.ArmyCell;
import main.views.Cell.BuildingCell;
import main.views.Cell.Cell;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

import java.awt.*;

public class MapView {
    public static int CELL_WIDTH;
    private Point mapLU = new Point(0, 0);
    private Point mapRB;
    private MapArrView mapArrView;
    private Cell[][] map;
    private int width;
    private int height;
    private GridPane gridPane;
    private static MapView mapView;
    private LevelScene levelScene;

    private MapView(int width, int height, GridPane gridPane, int x, int y, LevelScene levelScene) {
        this.width = width;
        this.height = height;
        this.gridPane = gridPane;
        mapRB = new Point(21, 12);
        this.levelScene = levelScene;
        CELL_WIDTH = Math.min(width / (mapRB.x - mapLU.x), height / (mapRB.y - mapLU.y));
        initGridPane();
        mapArrView = MapArrView.getMapArr(CELL_WIDTH, y, x, this);
        map = mapArrView.getMap();
        drawMap();
    }

    private MapView(int width, int height, GridPane gridPane, int x, int y, LevelScene levelScene, int level) {
        this.width = width;
        this.height = height;
        this.gridPane = gridPane;
        mapRB = new Point(21, 12);
        this.levelScene = levelScene;
        CELL_WIDTH = Math.min(width / (mapRB.x - mapLU.x), height / (mapRB.y - mapLU.y));
        initGridPane();
        mapArrView = MapArrView.getMapArr(CELL_WIDTH, y, x, this, level);
        map = mapArrView.getMap();
        drawMap();
    }

    public static MapView getMapView(int length, GridPane gridPane, int x, int y, LevelScene levelScene) {
        mapView = new MapView(length, length, gridPane, x, y, levelScene);
        return mapView;
    }

    public static MapView getMapView() {
        return mapView;
    }

    public static MapView getMapView(int frame_width, int frame_height, GridPane gridPane, int x, int y, LevelScene levelScene) {
        mapView = new MapView(frame_width, frame_height, gridPane, x, y, levelScene);
        return mapView;
    }

    public static MapView getMapView(int frame_width, int frame_height, GridPane gridPane, int x, int y, LevelScene levelScene, int level) {
        mapView = new MapView(frame_width, frame_height, gridPane, x, y, levelScene, level);
        return mapView;
    }

    private void initGridPane() {
        gridPane.setMinWidth(width);
        gridPane.setMinHeight(height);
        gridPane.setPadding(new Insets(0, 0, 0, 0));
    }


    public void drawMap() {
        gridPane.getChildren().clear();
        for (int i = mapLU.x; i < mapRB.x; i++) {
            for (int j = mapLU.y; j < mapRB.y; j++) {
                Cell cell = map[i][j];
                try {
                    gridPane.add(cell, (i - mapLU.x), (j - mapLU.y));
                } catch (Exception e) {
                    System.out.println(i);
                    System.out.println(j);
                    System.out.println(cell);
                }
            }
        }
    }

    public void moveArmy(int i, int j, ArmyCell armyCellView) {
        mapArrView.moveArmy(i, j, armyCellView);
        drawMap();
        MiniMap.getMiniMap().drawMiniMap();
    }

    public void changeOnGrass(int i, int j) {
        mapArrView.changeCellOnGrass(i, j);
        drawMap();
        MiniMap.getMiniMap().drawMiniMap();
    }

    public void buildResources(int i, int j, City cityWhereBuild, Class<? extends Cell> type){
        mapArrView.buildResources(i,j,cityWhereBuild,type);
        drawMap();
        MiniMap.getMiniMap().drawMiniMap();
    }

    public void changeOnForest(int i, int j) {
        mapArrView.changeCellOnForest(i, j);
        drawMap();
        MiniMap.getMiniMap().drawMiniMap();
    }

    public void changeOnMountain(int i, int j) {
        mapArrView.changeCellOnMountain(i, j);
        drawMap();
        MiniMap.getMiniMap().drawMiniMap();
    }

    public Point getMapLU() {
        return mapLU;
    }

    public void setMapLU(Point mapLU) {
        this.mapLU = mapLU;
    }

    public Point getMapRB() {
        return mapRB;
    }

    public void setMapRB(Point mapRB) {
        this.mapRB = mapRB;
    }


    public LevelScene getLevelScene() {
        return levelScene;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void clearMap() {
        mapView = null;
    }
}