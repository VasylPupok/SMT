package main;

import main.views.Cell.*;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import main.views.MapArrView;
import main.views.MapView;
import main.views.Player;
import main.views.PlayersHandler;

import java.awt.*;

public class MiniMap {

    private static MiniMap miniMap;

    private MapArrView mapArrView = MapArrView.getMapArrView();
    private static GridPane gridPane;
    private int miniMapCellWidth;
    private int miniMapCellHeight;
    private int gridWidth;
    private int gridHeight;
    private int gapX;
    private int gapY;
    private static Canvas canvas;

    private MiniMap(GridPane gridPane, int width, int height) {
        mapArrView = MapArrView.getMapArrView();
        MiniMap.gridPane = gridPane;
        gridWidth = width;
        gridHeight = height;
        canvas = new Canvas(width, height);
        miniMapCellWidth = Math.min(gridWidth, gridHeight) / Math.max(mapArrView.getColumnsNumber(), mapArrView.getRowsNumber());
        miniMapCellHeight = miniMapCellWidth;
        gapX = (width - miniMapCellWidth * mapArrView.getColumnsNumber()) / 2;
        gapY = (height - miniMapCellHeight * mapArrView.getRowsNumber()) / 2;
        drawMiniMap();
        gridPane.add(canvas, 0, 0);
    }

    public static MiniMap getMiniMap(GridPane gridPane, int width, int height) {
        miniMap = new MiniMap(gridPane, width, height);
        return miniMap;
    }

    public static MiniMap getMiniMap() {
        return miniMap;
    }

    public void drawMiniMap() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, gridWidth, gridHeight);
        Point mapLU = MapView.getMapView().getMapLU();
        Point mapRB = MapView.getMapView().getMapRB();
        Cell[][] map = MapArrView.getMapArrView().getMap();
        Player player = PlayersHandler.getPlayersHandler().getPlayer(0);
        for (int i = 0; i < mapArrView.getColumnsNumber(); i++) {
            for (int j = 0; j < mapArrView.getRowsNumber(); j++) {
                gc.setStroke(Paint.valueOf("BLACK"));
                if (map[i][j] instanceof CityCell) {
                    if (player.hasCity(((CityCell) map[i][j]).getCity())) {
                        drawFillRect(i, j, gc, "BLUE");
                    } else {
                        drawFillRect(i, j, gc, "RED");
                    }
                } else if (map[i][j] instanceof ArmyCell) {
                    if (player.hasArmy(((ArmyCell) map[i][j]).getArmy())) {
                        drawFillRect(i, j, gc, "BLUE");
                    } else {
                        drawFillRect(i, j, gc, "RED");
                    }

                } else if (map[i][j] instanceof MountainCell) {
                    drawFillRect(i, j, gc, "GRAY");
                } else if (map[i][j] instanceof ForestCell) {
                    drawFillRect(i, j, gc, "DARKGREEN");
                } else {
                    drawFillRect(i, j, gc, "GREEN");
                }
                gc.strokeRect(gapX + i * miniMapCellWidth, j * miniMapCellHeight, miniMapCellWidth, miniMapCellHeight);
            }
        }
        gc.setStroke(Paint.valueOf("YELLOW"));
        gc.strokeRect(gapX + mapLU.x * miniMapCellWidth, mapLU.y * miniMapCellHeight, (mapRB.x - mapLU.x) * miniMapCellWidth, (mapRB.y - mapLU.y) * miniMapCellHeight);
    }


    public void drawFillRect(int i, int j, GraphicsContext gc, String color) {
        gc.setFill(Paint.valueOf(color));
        gc.fillRect(gapX + i * miniMapCellWidth, j * miniMapCellHeight, miniMapCellWidth, miniMapCellHeight);
    }
}



