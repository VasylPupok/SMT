package main.views.Cell;

import javafx.scene.layout.*;
import javafx.scene.paint.*;
import main.views.City;
import main.views.MapArrView;
import main.views.Player;
import main.views.PlayersHandler;

import java.io.*;
import java.util.concurrent.*;
public class CityCell extends Cell implements Attackable {

    private static final String imageURL = "file:resources\\images\\city\\City.png";//path to image of city
    private static final String imageEnemyURL = "file:resources\\images\\city\\EnemyCity.png";//path to image of city
    private City city;
    private MapArrView mapArrView;

    public CityCell(int length, int x, int y, MapArrView mapArrView) {
        super(length, x, y, false, false, true);
        this.mapArrView = mapArrView;
    }

    // private methods

    public void changeTerritoryHighlight() {
        //this.setChosen(!this.isChosen());
        Cell[][] cell = MapArrView.getMapArrView().getMap();
        int indX = this.takeX();
        int indY = this.takeY();
        if (isChosen()) {
            System.out.println(city);
        }
        if (this.isChosen()) {
            for (int i = Math.max(indX - 2, 0); i <= Math.min(indX + 2, MapArrView.getMapArrView().getColumnsNumber()-1); i++) {
                for (int j = Math.max(indY - 2, 0); j <= Math.min(indY + 2, MapArrView.getMapArrView().getRowsNumber()-1); j++) {
                    if (cell[i][j].isEmpty()) {
                        cell[i][j].setFill(Paint.valueOf("ORANGE"));
                        cell[i][j].setReadyToBuild(true);
                        cell[i][j].setCityWhereBuild(city);
                    }
                }
            }
        } else {
            for (int i = Math.max(indX - 2, 0); i <= Math.min(indX + 2, MapArrView.getMapArrView().getColumnsNumber()-1); i++) {
                for (int j = Math.max(indY - 2, 0); j <= Math.min(indY + 2, MapArrView.getMapArrView().getRowsNumber()-1); j++) {
                    if (cell[i][j].isEmpty()) {
                        cell[i][j].setDefaultFill();
                        cell[i][j].setReadyToBuild(false);
                        cell[i][j].setCityWhereBuild(null);
                    }
                }
            }
        }
    }

//    public void changeTerritoryHighlight() {
//        //this.setChosen(!this.isChosen());
//        Cell[][] cell = MapArrView.getMapArrView().getMap();
//        int indX = this.takeX();
//        int indY = this.takeY();
//        if (isChosen()) {
//            System.out.println(city);
//        }
//        if (this.isChosen()) {
//            for (int i = Math.max(indX - 2, 0); i <= Math.min(indX + 2, MapArrView.getMapArrView().getColumnsNumber()-1); i++) {
//                for (int j = Math.max(indY - 2, 0); j <= Math.min(indY + 2, MapArrView.getMapArrView().getRowsNumber()-1); j++) {
//                    if (cell[i][j].isEmpty()) {
//                        cell[i][j].setFill(Paint.valueOf("ORANGE"));
//                        cell[i][j].setReadyToBuild(true);
//                        cell[i][j].setCityWhereBuild(city);
//                    }
//                }
//            }
//        } else {
//            for (int i = Math.max(indX - 2, 0); i <= Math.min(indX + 2, MapArrView.getMapArrView().getColumnsNumber()-1); i++) {
//                for (int j = Math.max(indY - 2, 0); j <= Math.min(indY + 2, MapArrView.getMapArrView().getRowsNumber()-1); j++) {
//                    if (cell[i][j].isEmpty()) {
//                        cell[i][j].setDefaultFill();
//                        cell[i][j].setReadyToBuild(false);
//                        cell[i][j].setCityWhereBuild(null);
//                    }
//                }
//            }
//        }
//    }

    @Override
    protected void setCellImage() {
        if (PlayersHandler.getPlayersHandler().getPlayer(0).hasCity(getCity())) {
            fillCell(imageURL);
        } else {
            fillCell(imageEnemyURL);
        }
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}