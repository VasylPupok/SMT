package main.views;

import main.LevelScene;
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
        mapRB = new Point(21,12);
        this.levelScene = levelScene;
        CELL_WIDTH = Math.min(width / (mapRB.x - mapLU.x), height / (mapRB.y - mapLU.y));
        initGridPane();
        mapArrView = MapArrView.getMapArr(CELL_WIDTH,y,x,this);
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

    public void clearMap(){
        mapView = null;
    }
}