package main.views;


import main.views.Cell.*;

import java.util.Random;

public class MapArrView {

    private static MapArrView mapArrView;
    private int rowsNumber;
    private int columnsNumber;
    private Cell[][] map;
    private final int CELL_WIDTH;
    private MapView mapView;

    private MapArrView(int CELL_WIDTH, int rowsNumber, int columnsNumber, MapView mapView) {
        this(CELL_WIDTH, rowsNumber, columnsNumber, mapView, 0);
    }

    private MapArrView(int CELL_WIDTH, int rowsNumber, int columnsNumber, MapView mapView, int level) {
        this.CELL_WIDTH = CELL_WIDTH;
        this.rowsNumber = rowsNumber;
        this.columnsNumber = columnsNumber;
        map = new Cell[columnsNumber][rowsNumber];
        this.mapView = mapView;
        switch (level) {
            case 1 -> initFirstLevelMap();
            case 2 -> initSecondLevelMap();
            case 3 -> initThirdLevelMap();
            case 4 -> initFourthLevelMap();
            case 5 -> initFifthLevelMap();
            default -> initMap();
        }
    }

    public static MapArrView getMapArr(int CELL_WIDTH, int rowsNumber, int columnsNumber, MapView mapView) {
        mapArrView = new MapArrView(CELL_WIDTH, rowsNumber, columnsNumber, mapView);
        return mapArrView;
    }

    public static MapArrView getMapArr(int CELL_WIDTH, int rowsNumber, int columnsNumber, MapView mapView, int level) {
        mapArrView = new MapArrView(CELL_WIDTH, rowsNumber, columnsNumber, mapView, level);
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
        for (int i = 0; i < 4; i++) {
            switch (rand.nextInt(3)) {
                case 0 -> map[i][5] = new MountainCell(CELL_WIDTH, i, 5);
                case 1 -> map[i][5] = new ForestCell(CELL_WIDTH, i, 5);
                case 2 -> map[i][5] = new GrassCell(CELL_WIDTH, i, 5);
            }
        }
        CityCell cityCell = new CityCell(CELL_WIDTH, 4, 5, this);
        map[4][5] = cityCell;
        PlayersHandler.getPlayersHandler().getPlayer(0).addCityCell(cityCell);
        PlayersHandler.getPlayersHandler().getPlayer(0).setLevel(1);
        cityCell.setDefaultFill();
        for (int i = 5; i < 16; i++) {
            switch (rand.nextInt(3)) {
                case 0 -> map[i][5] = new MountainCell(CELL_WIDTH, i, 5);
                case 1 -> map[i][5] = new ForestCell(CELL_WIDTH, i, 5);
                case 2 -> map[i][5] = new GrassCell(CELL_WIDTH, i, 5);
            }
        }
        CityCell enemyCityCell = new CityCell(CELL_WIDTH, 16, 5, this);
        map[16][5] = enemyCityCell;
        PlayersHandler.getPlayersHandler().getPlayer(1).addCityCell(enemyCityCell);
        PlayersHandler.getPlayersHandler().getPlayer(1).setLevel(1);
        cityCell.setDefaultFill();
        for (int i = 17; i < columnsNumber; i++) {
            switch (rand.nextInt(3)) {
                case 0 -> map[i][5] = new MountainCell(CELL_WIDTH, i, 5);
                case 1 -> map[i][5] = new ForestCell(CELL_WIDTH, i, 5);
                case 2 -> map[i][5] = new GrassCell(CELL_WIDTH, i, 5);
            }
        }
        for (int i = 0; i < columnsNumber; i++) {
            for (int j = 6; j < rowsNumber; j++) {
                switch (rand.nextInt(3)) {
                    case 0 -> map[i][j] = new MountainCell(CELL_WIDTH, i, j);
                    case 1 -> map[i][j] = new ForestCell(CELL_WIDTH, i, j);
                    case 2 -> map[i][j] = new GrassCell(CELL_WIDTH, i, j);
                }
            }
        }
        generateBuildings(16, 5, enemyCityCell.getCity());
    }

    public void initSecondLevelMap() {
        Random rand = new Random();
        for(int i = 0; i<columnsNumber; i++){
            for(int j = 0; j<3 ; j++){
                switch (rand.nextInt(3)) {
                    case 0 -> map[i][j] = new MountainCell(CELL_WIDTH, i, j);
                    case 1 -> map[i][j] = new ForestCell(CELL_WIDTH, i, j);
                    case 2 -> map[i][j] = new GrassCell(CELL_WIDTH, i, j);
                }
            }
        }
        for(int i = 0; i<18; i++){
            switch (rand.nextInt(3)) {
                case 0 -> map[i][3] = new MountainCell(CELL_WIDTH, i, 3);
                case 1 -> map[i][3] = new ForestCell(CELL_WIDTH, i, 3);
                case 2 -> map[i][3] = new GrassCell(CELL_WIDTH, i, 3);
            }
        }
        CityCell firstEnemyCityCell = new CityCell(CELL_WIDTH,18,3,this);
        map[18][3] = firstEnemyCityCell;
        PlayersHandler.getPlayersHandler().getPlayer(1).addCityCell(firstEnemyCityCell);
        PlayersHandler.getPlayersHandler().getPlayer(1).setLevel(2);
        firstEnemyCityCell.setDefaultFill();
        for(int i = 19; i<columnsNumber; i++){
            switch (rand.nextInt(3)) {
                case 0 -> map[i][3] = new MountainCell(CELL_WIDTH, i, 3);
                case 1 -> map[i][3] = new ForestCell(CELL_WIDTH, i, 3);
                case 2 -> map[i][3] = new GrassCell(CELL_WIDTH, i, 3);
            }
        }
        for(int i = 0; i<columnsNumber; i++){
            for(int j = 4; j<6 ; j++){
                switch (rand.nextInt(3)) {
                    case 0 -> map[i][j] = new MountainCell(CELL_WIDTH, i, j);
                    case 1 -> map[i][j] = new ForestCell(CELL_WIDTH, i, j);
                    case 2 -> map[i][j] = new GrassCell(CELL_WIDTH, i, j);
                }
            }
        }
        for(int i = 0; i<5; i++){
            switch (rand.nextInt(3)) {
                case 0 -> map[i][6] = new MountainCell(CELL_WIDTH, i, 6);
                case 1 -> map[i][6] = new ForestCell(CELL_WIDTH, i, 6);
                case 2 -> map[i][6] = new GrassCell(CELL_WIDTH, i, 6);
            }
        }
        CityCell cityCell = new CityCell(CELL_WIDTH,5,6,this);
        map[5][6] = cityCell;
        PlayersHandler.getPlayersHandler().getPlayer(0).addCityCell(cityCell);
        PlayersHandler.getPlayersHandler().getPlayer(0).setLevel(2);
        cityCell.setDefaultFill();
        for(int i=6; i<columnsNumber; i++){
            switch (rand.nextInt(3)) {
                case 0 -> map[i][6] = new MountainCell(CELL_WIDTH, i, 6);
                case 1 -> map[i][6] = new ForestCell(CELL_WIDTH, i, 6);
                case 2 -> map[i][6] = new GrassCell(CELL_WIDTH, i, 6);
            }
        }
        for(int i=0; i<columnsNumber; i++){
            for(int j=7; j<13; j++){
                switch (rand.nextInt(3)) {
                    case 0 -> map[i][j] = new MountainCell(CELL_WIDTH, i, j);
                    case 1 -> map[i][j] = new ForestCell(CELL_WIDTH, i, j);
                    case 2 -> map[i][j] = new GrassCell(CELL_WIDTH, i, j);
                }
            }
        }
        for(int i=0; i<15; i++){
            switch (rand.nextInt(3)) {
                case 0 -> map[i][13] = new MountainCell(CELL_WIDTH, i, 13);
                case 1 -> map[i][13] = new ForestCell(CELL_WIDTH, i, 13);
                case 2 -> map[i][13] = new GrassCell(CELL_WIDTH, i, 13);
            }
        }
        CityCell secondEnemyCityCell = new CityCell(CELL_WIDTH,15,13,this);
        map[15][13] = secondEnemyCityCell;
        PlayersHandler.getPlayersHandler().getPlayer(1).addCityCell(secondEnemyCityCell);
        PlayersHandler.getPlayersHandler().getPlayer(1).setLevel(2);
        secondEnemyCityCell.setDefaultFill();
        for(int i=16; i<columnsNumber; i++){
            switch (rand.nextInt(3)) {
                case 0 -> map[i][13] = new MountainCell(CELL_WIDTH, i, 13);
                case 1 -> map[i][13] = new ForestCell(CELL_WIDTH, i, 13);
                case 2 -> map[i][13] = new GrassCell(CELL_WIDTH, i, 13);
            }
        }
        for(int i=0; i<columnsNumber; i++){
            for(int j=14; j<rowsNumber; j++){
                switch (rand.nextInt(3)) {
                    case 0 -> map[i][j] = new MountainCell(CELL_WIDTH, i, j);
                    case 1 -> map[i][j] = new ForestCell(CELL_WIDTH, i, j);
                    case 2 -> map[i][j] = new GrassCell(CELL_WIDTH, i, j);
                }
            }
        }
        generateBuildings(18,3,firstEnemyCityCell.getCity());
        generateBuildings(15,13,secondEnemyCityCell.getCity());
    }

    public void initThirdLevelMap(){
        Random rand = new Random();
        for(int i=0; i<columnsNumber; i++){
            for(int j=0; j<3; j++){
                switch (rand.nextInt(3)) {
                    case 0 -> map[i][j] = new MountainCell(CELL_WIDTH, i, j);
                    case 1 -> map[i][j] = new ForestCell(CELL_WIDTH, i, j);
                    case 2 -> map[i][j] = new GrassCell(CELL_WIDTH, i, j);
                }
            }
        }
        for(int i=0; i<15; i++){
            switch (rand.nextInt(3)) {
                case 0 -> map[i][3] = new MountainCell(CELL_WIDTH, i, 3);
                case 1 -> map[i][3] = new ForestCell(CELL_WIDTH, i, 3);
                case 2 -> map[i][3] = new GrassCell(CELL_WIDTH, i, 3);
            }
        }
        CityCell cityCell = new CityCell(CELL_WIDTH,15,3,this);
        map[15][3] = cityCell;
        PlayersHandler.getPlayersHandler().getPlayer(0).addCityCell(cityCell);
        PlayersHandler.getPlayersHandler().getPlayer(0).setLevel(3);
        cityCell.setDefaultFill();
        for(int i=16; i<columnsNumber; i++){
            switch (rand.nextInt(3)) {
                case 0 -> map[i][3] = new MountainCell(CELL_WIDTH, i, 3);
                case 1 -> map[i][3] = new ForestCell(CELL_WIDTH, i, 3);
                case 2 -> map[i][3] = new GrassCell(CELL_WIDTH, i, 3);
            }
        }
        for(int i=0; i<columnsNumber; i++){
            for(int j=4; j<7; j++){
                switch (rand.nextInt(3)) {
                    case 0 -> map[i][j] = new MountainCell(CELL_WIDTH, i, j);
                    case 1 -> map[i][j] = new ForestCell(CELL_WIDTH, i, j);
                    case 2 -> map[i][j] = new GrassCell(CELL_WIDTH, i, j);
                }
            }
        }
        for(int i=0; i<4; i++){
            switch (rand.nextInt(3)) {
                case 0 -> map[i][7] = new MountainCell(CELL_WIDTH, i, 7);
                case 1 -> map[i][7] = new ForestCell(CELL_WIDTH, i, 7);
                case 2 -> map[i][7] = new GrassCell(CELL_WIDTH, i, 7);
            }
        }
        CityCell firstEnemyCityCell = new CityCell(CELL_WIDTH,4,7,this);
        map[4][7] = firstEnemyCityCell;
        PlayersHandler.getPlayersHandler().getPlayer(1).addCityCell(firstEnemyCityCell);
        PlayersHandler.getPlayersHandler().getPlayer(1).setLevel(3);
        firstEnemyCityCell.setDefaultFill();
        for(int i=5; i<columnsNumber; i++){
            switch (rand.nextInt(3)) {
                case 0 -> map[i][7] = new MountainCell(CELL_WIDTH, i, 7);
                case 1 -> map[i][7] = new ForestCell(CELL_WIDTH, i, 7);
                case 2 -> map[i][7] = new GrassCell(CELL_WIDTH, i, 7);
            }
        }
        for(int i=0; i<columnsNumber; i++){
            for(int j=8; j<11; j++){
                switch (rand.nextInt(3)) {
                    case 0 -> map[i][j] = new MountainCell(CELL_WIDTH, i, j);
                    case 1 -> map[i][j] = new ForestCell(CELL_WIDTH, i, j);
                    case 2 -> map[i][j] = new GrassCell(CELL_WIDTH, i, j);
                }
            }
        }
        for(int i=0; i<13; i++){
            switch (rand.nextInt(3)) {
                case 0 -> map[i][11] = new MountainCell(CELL_WIDTH, i, 11);
                case 1 -> map[i][11] = new ForestCell(CELL_WIDTH, i, 11);
                case 2 -> map[i][11] = new GrassCell(CELL_WIDTH, i, 11);
            }
        }
        CityCell secondEnemyCityCell = new CityCell(CELL_WIDTH,13,11,this);
        map[13][11] = secondEnemyCityCell;
        PlayersHandler.getPlayersHandler().getPlayer(1).addCityCell(secondEnemyCityCell);
        PlayersHandler.getPlayersHandler().getPlayer(1).setLevel(3);
        secondEnemyCityCell.setDefaultFill();
        for(int i=14; i<columnsNumber; i++){
            switch (rand.nextInt(3)) {
                case 0 -> map[i][11] = new MountainCell(CELL_WIDTH, i, 11);
                case 1 -> map[i][11] = new ForestCell(CELL_WIDTH, i, 11);
                case 2 -> map[i][11] = new GrassCell(CELL_WIDTH, i, 11);
            }
        }
        for(int i=0; i<columnsNumber; i++){
            for(int j=12; j<15; j++){
                switch (rand.nextInt(3)) {
                    case 0 -> map[i][j] = new MountainCell(CELL_WIDTH, i, j);
                    case 1 -> map[i][j] = new ForestCell(CELL_WIDTH, i, j);
                    case 2 -> map[i][j] = new GrassCell(CELL_WIDTH, i, j);
                }
            }
        }
        for(int i=0; i<6; i++){
            switch (rand.nextInt(3)) {
                case 0 -> map[i][15] = new MountainCell(CELL_WIDTH, i, 15);
                case 1 -> map[i][15] = new ForestCell(CELL_WIDTH, i, 15);
                case 2 -> map[i][15] = new GrassCell(CELL_WIDTH, i, 15);
            }
        }
        CityCell thirdEnemyCityCell = new CityCell(CELL_WIDTH,6,15,this);
        map[6][15] = thirdEnemyCityCell;
        PlayersHandler.getPlayersHandler().getPlayer(1).addCityCell(thirdEnemyCityCell);
        PlayersHandler.getPlayersHandler().getPlayer(1).setLevel(3);
        thirdEnemyCityCell.setDefaultFill();
        for(int i=7; i<columnsNumber; i++){
            switch (rand.nextInt(3)) {
                case 0 -> map[i][15] = new MountainCell(CELL_WIDTH, i, 15);
                case 1 -> map[i][15] = new ForestCell(CELL_WIDTH, i, 15);
                case 2 -> map[i][15] = new GrassCell(CELL_WIDTH, i, 15);
            }
        }
        for(int i=0; i<columnsNumber; i++){
            for(int j=16; j<rowsNumber; j++){
                switch (rand.nextInt(3)) {
                    case 0 -> map[i][j] = new MountainCell(CELL_WIDTH, i, j);
                    case 1 -> map[i][j] = new ForestCell(CELL_WIDTH, i, j);
                    case 2 -> map[i][j] = new GrassCell(CELL_WIDTH, i, j);
                }
            }
        }
        generateBuildings(4,7,firstEnemyCityCell.getCity());
        generateBuildings(13,11,secondEnemyCityCell.getCity());
        generateBuildings(6,15,thirdEnemyCityCell.getCity());
    }

    public void initFourthLevelMap(){
        Random rand = new Random();
        for(int i=0; i<columnsNumber; i++){
            for(int j=0; j<5; j++){
                switch (rand.nextInt(3)) {
                    case 0 -> map[i][j] = new MountainCell(CELL_WIDTH, i, j);
                    case 1 -> map[i][j] = new ForestCell(CELL_WIDTH, i, j);
                    case 2 -> map[i][j] = new GrassCell(CELL_WIDTH, i, j);
                }
            }
        }
        for(int i=0; i<6; i++){
            switch (rand.nextInt(3)) {
                case 0 -> map[i][5] = new MountainCell(CELL_WIDTH, i, 5);
                case 1 -> map[i][5] = new ForestCell(CELL_WIDTH, i, 5);
                case 2 -> map[i][5] = new GrassCell(CELL_WIDTH, i, 5);
            }
        }
        CityCell cityCell = new CityCell(CELL_WIDTH,6,5,this);
        map[6][5] = cityCell;
        PlayersHandler.getPlayersHandler().getPlayer(0).addCityCell(cityCell);
        PlayersHandler.getPlayersHandler().getPlayer(0).setLevel(4);
        cityCell.setDefaultFill();
        for(int i=7; i<16; i++){
            switch (rand.nextInt(3)) {
                case 0 -> map[i][5] = new MountainCell(CELL_WIDTH, i, 5);
                case 1 -> map[i][5] = new ForestCell(CELL_WIDTH, i, 5);
                case 2 -> map[i][5] = new GrassCell(CELL_WIDTH, i, 5);
            }
        }
        CityCell firstEnemyCityCell = new CityCell(CELL_WIDTH,16,5,this);
        map[16][5] = firstEnemyCityCell;
        PlayersHandler.getPlayersHandler().getPlayer(1).addCityCell(firstEnemyCityCell);
        PlayersHandler.getPlayersHandler().getPlayer(1).setLevel(4);
        firstEnemyCityCell.setDefaultFill();
        for (int i=17; i<columnsNumber; i++){
            switch (rand.nextInt(3)) {
                case 0 -> map[i][5] = new MountainCell(CELL_WIDTH, i, 5);
                case 1 -> map[i][5] = new ForestCell(CELL_WIDTH, i, 5);
                case 2 -> map[i][5] = new GrassCell(CELL_WIDTH, i, 5);
            }
        }
        for(int i=0; i<columnsNumber; i++){
            for(int j=6; j<12; j++){
                switch (rand.nextInt(3)) {
                    case 0 -> map[i][j] = new MountainCell(CELL_WIDTH, i, j);
                    case 1 -> map[i][j] = new ForestCell(CELL_WIDTH, i, j);
                    case 2 -> map[i][j] = new GrassCell(CELL_WIDTH, i, j);
                }
            }
        }
        for(int i=0; i<12; i++){
            switch (rand.nextInt(3)) {
                case 0 -> map[i][12] = new MountainCell(CELL_WIDTH, i, 12);
                case 1 -> map[i][12] = new ForestCell(CELL_WIDTH, i, 12);
                case 2 -> map[i][12] = new GrassCell(CELL_WIDTH, i, 12);
            }
        }
        CityCell secondEnemyCityCell = new CityCell(CELL_WIDTH,12,12,this);
        map[12][12] = secondEnemyCityCell;
        PlayersHandler.getPlayersHandler().getPlayer(1).addCityCell(secondEnemyCityCell);
        PlayersHandler.getPlayersHandler().getPlayer(1).setLevel(4);
        secondEnemyCityCell.setDefaultFill();
        for(int i=13; i<columnsNumber; i++){
            switch (rand.nextInt(3)) {
                case 0 -> map[i][12] = new MountainCell(CELL_WIDTH, i, 12);
                case 1 -> map[i][12] = new ForestCell(CELL_WIDTH, i, 12);
                case 2 -> map[i][12] = new GrassCell(CELL_WIDTH, i, 12);
            }
        }
        for(int i=0; i<columnsNumber; i++){
            for(int j=13; j<17; j++){
                switch (rand.nextInt(3)) {
                    case 0 -> map[i][j] = new MountainCell(CELL_WIDTH, i, j);
                    case 1 -> map[i][j] = new ForestCell(CELL_WIDTH, i, j);
                    case 2 -> map[i][j] = new GrassCell(CELL_WIDTH, i, j);
                }
            }
        }
        for(int i=0; i<6; i++){
            switch (rand.nextInt(3)) {
                case 0 -> map[i][17] = new MountainCell(CELL_WIDTH, i, 17);
                case 1 -> map[i][17] = new ForestCell(CELL_WIDTH, i, 17);
                case 2 -> map[i][17] = new GrassCell(CELL_WIDTH, i, 17);
            }
        }
        CityCell thirdEnemyCityCell = new CityCell(CELL_WIDTH,6,17,this);
        map[6][17] = thirdEnemyCityCell;
        PlayersHandler.getPlayersHandler().getPlayer(1).addCityCell(thirdEnemyCityCell);
        PlayersHandler.getPlayersHandler().getPlayer(1).setLevel(4);
        thirdEnemyCityCell.setDefaultFill();
        for(int i=7; i<16; i++){
            switch (rand.nextInt(3)) {
                case 0 -> map[i][17] = new MountainCell(CELL_WIDTH, i, 17);
                case 1 -> map[i][17] = new ForestCell(CELL_WIDTH, i, 17);
                case 2 -> map[i][17] = new GrassCell(CELL_WIDTH, i, 17);
            }
        }
        CityCell fourthEnemyCityCell = new CityCell(CELL_WIDTH,16,17,this);
        map[16][17] = fourthEnemyCityCell;
        PlayersHandler.getPlayersHandler().getPlayer(1).addCityCell(fourthEnemyCityCell);
        PlayersHandler.getPlayersHandler().getPlayer(1).setLevel(4);
        fourthEnemyCityCell.setDefaultFill();
        for(int i=17; i<columnsNumber; i++){
            switch (rand.nextInt(3)) {
                case 0 -> map[i][17] = new MountainCell(CELL_WIDTH, i, 17);
                case 1 -> map[i][17] = new ForestCell(CELL_WIDTH, i, 17);
                case 2 -> map[i][17] = new GrassCell(CELL_WIDTH, i, 17);
            }
        }
        for(int i=0; i<columnsNumber; i++){
            for(int j=18; j<rowsNumber; j++){
                switch (rand.nextInt(3)) {
                    case 0 -> map[i][j] = new MountainCell(CELL_WIDTH, i, j);
                    case 1 -> map[i][j] = new ForestCell(CELL_WIDTH, i, j);
                    case 2 -> map[i][j] = new GrassCell(CELL_WIDTH, i, j);
                }
            }
        }
        generateBuildings(16,5,firstEnemyCityCell.getCity());
        generateBuildings(12,12, secondEnemyCityCell.getCity());
        generateBuildings(6,17,thirdEnemyCityCell.getCity());
        generateBuildings(16,17,fourthEnemyCityCell.getCity());
    }

    public void initFifthLevelMap(){
        Random rand = new Random();
        for(int i=0; i<columnsNumber; i++){
            for(int j=0; j<5; j++){
                switch (rand.nextInt(3)) {
                    case 0 -> map[i][j] = new MountainCell(CELL_WIDTH, i, j);
                    case 1 -> map[i][j] = new ForestCell(CELL_WIDTH, i, j);
                    case 2 -> map[i][j] = new GrassCell(CELL_WIDTH, i, j);
                }
            }
        }
        for(int i=0; i<6; i++){
            switch (rand.nextInt(3)) {
                case 0 -> map[i][5] = new MountainCell(CELL_WIDTH, i, 5);
                case 1 -> map[i][5] = new ForestCell(CELL_WIDTH, i, 5);
                case 2 -> map[i][5] = new GrassCell(CELL_WIDTH, i, 5);
            }
        }
        CityCell cityCell = new CityCell(CELL_WIDTH,6,5,this);
        map[6][5] = cityCell;
        PlayersHandler.getPlayersHandler().getPlayer(0).addCityCell(cityCell);
        PlayersHandler.getPlayersHandler().getPlayer(0).setLevel(5);
        cityCell.setDefaultFill();
        for(int i=7; i<19; i++){
            switch (rand.nextInt(3)) {
                case 0 -> map[i][5] = new MountainCell(CELL_WIDTH, i, 5);
                case 1 -> map[i][5] = new ForestCell(CELL_WIDTH, i, 5);
                case 2 -> map[i][5] = new GrassCell(CELL_WIDTH, i, 5);
            }
        }
        CityCell firstEnemyCityCell = new CityCell(CELL_WIDTH,19,5,this);
        map[19][5] = firstEnemyCityCell;
        PlayersHandler.getPlayersHandler().getPlayer(1).addCityCell(firstEnemyCityCell);
        PlayersHandler.getPlayersHandler().getPlayer(1).setLevel(5);
        firstEnemyCityCell.setDefaultFill();
        for(int i=20; i<columnsNumber; i++){
            switch (rand.nextInt(3)) {
                case 0 -> map[i][5] = new MountainCell(CELL_WIDTH, i, 5);
                case 1 -> map[i][5] = new ForestCell(CELL_WIDTH, i, 5);
                case 2 -> map[i][5] = new GrassCell(CELL_WIDTH, i, 5);
            }
        }
        for(int i=0; i<columnsNumber; i++){
            for(int j=6; j<12; j++){
                switch (rand.nextInt(3)) {
                    case 0 -> map[i][j] = new MountainCell(CELL_WIDTH, i, j);
                    case 1 -> map[i][j] = new ForestCell(CELL_WIDTH, i, j);
                    case 2 -> map[i][j] = new GrassCell(CELL_WIDTH, i, j);
                }
            }
        }
        for(int i=0; i<6; i++){
            switch (rand.nextInt(3)) {
                case 0 -> map[i][12] = new MountainCell(CELL_WIDTH, i, 12);
                case 1 -> map[i][12] = new ForestCell(CELL_WIDTH, i, 12);
                case 2 -> map[i][12] = new GrassCell(CELL_WIDTH, i, 12);
            }
        }
        CityCell secondEnemyCityCell = new CityCell(CELL_WIDTH,6,12,this);
        map[6][12] = secondEnemyCityCell;
        PlayersHandler.getPlayersHandler().getPlayer(1).addCityCell(secondEnemyCityCell);
        PlayersHandler.getPlayersHandler().getPlayer(1).setLevel(5);
        secondEnemyCityCell.setDefaultFill();
        for(int i=7; i<19; i++){
            switch (rand.nextInt(3)) {
                case 0 -> map[i][12] = new MountainCell(CELL_WIDTH, i, 12);
                case 1 -> map[i][12] = new ForestCell(CELL_WIDTH, i, 12);
                case 2 -> map[i][12] = new GrassCell(CELL_WIDTH, i, 12);
            }
        }
        CityCell thirdEnemyCityCell = new CityCell(CELL_WIDTH,19,12,this);
        map[19][12] = thirdEnemyCityCell;
        PlayersHandler.getPlayersHandler().getPlayer(1).addCityCell(thirdEnemyCityCell);
        PlayersHandler.getPlayersHandler().getPlayer(1).setLevel(5);
        thirdEnemyCityCell.setDefaultFill();
        for(int i=20; i<columnsNumber; i++){
            switch (rand.nextInt(3)) {
                case 0 -> map[i][12] = new MountainCell(CELL_WIDTH, i, 12);
                case 1 -> map[i][12] = new ForestCell(CELL_WIDTH, i, 12);
                case 2 -> map[i][12] = new GrassCell(CELL_WIDTH, i, 12);
            }
        }
        for(int i=0; i<columnsNumber; i++){
            for(int j=13; j<19; j++){
                switch (rand.nextInt(3)) {
                    case 0 -> map[i][j] = new MountainCell(CELL_WIDTH, i, j);
                    case 1 -> map[i][j] = new ForestCell(CELL_WIDTH, i, j);
                    case 2 -> map[i][j] = new GrassCell(CELL_WIDTH, i, j);
                }
            }
        }
        for(int i=0; i<6; i++){
            switch (rand.nextInt(3)) {
                case 0 -> map[i][19] = new MountainCell(CELL_WIDTH, i, 19);
                case 1 -> map[i][19] = new ForestCell(CELL_WIDTH, i, 19);
                case 2 -> map[i][19] = new GrassCell(CELL_WIDTH, i, 19);
            }
        }
        CityCell fourthEnemyCityCell = new CityCell(CELL_WIDTH,6,19,this);
        map[6][19] = fourthEnemyCityCell;
        PlayersHandler.getPlayersHandler().getPlayer(1).addCityCell(fourthEnemyCityCell);
        PlayersHandler.getPlayersHandler().getPlayer(1).setLevel(5);
        fourthEnemyCityCell.setDefaultFill();
        for(int i=7; i<19; i++){
            switch (rand.nextInt(3)) {
                case 0 -> map[i][19] = new MountainCell(CELL_WIDTH, i, 19);
                case 1 -> map[i][19] = new ForestCell(CELL_WIDTH, i, 19);
                case 2 -> map[i][19] = new GrassCell(CELL_WIDTH, i, 19);
            }
        }
        CityCell fifthEnemyCityCell = new CityCell(CELL_WIDTH,19,19,this);
        map[19][19] = fifthEnemyCityCell;
        PlayersHandler.getPlayersHandler().getPlayer(1).addCityCell(fifthEnemyCityCell);
        PlayersHandler.getPlayersHandler().getPlayer(1).setLevel(5);
        fifthEnemyCityCell.setDefaultFill();
        for(int i=20; i<columnsNumber; i++){
            switch (rand.nextInt(3)) {
                case 0 -> map[i][19] = new MountainCell(CELL_WIDTH, i, 19);
                case 1 -> map[i][19] = new ForestCell(CELL_WIDTH, i, 19);
                case 2 -> map[i][19] = new GrassCell(CELL_WIDTH, i, 19);
            }
        }
        for(int i=0; i<columnsNumber; i++){
            for(int j=20; j<rowsNumber; j++){
                switch (rand.nextInt(3)) {
                    case 0 -> map[i][j] = new MountainCell(CELL_WIDTH, i, j);
                    case 1 -> map[i][j] = new ForestCell(CELL_WIDTH, i, j);
                    case 2 -> map[i][j] = new GrassCell(CELL_WIDTH, i, j);
                }
            }
        }
        generateBuildings(19,5, firstEnemyCityCell.getCity());
        generateBuildings(6,12, secondEnemyCityCell.getCity());
        generateBuildings(19,12, thirdEnemyCityCell.getCity());
        generateBuildings(6,19, fourthEnemyCityCell.getCity());
        generateBuildings(19,19, fifthEnemyCityCell.getCity());
    }

    private void generateBuildings(int x, int y, City city) {
        for (int i = Math.max(x - 2, 0); i <= Math.min(x + 2, columnsNumber - 1); i++) {
            for (int j = Math.max(y - 2, 0); j <= Math.min(y + 2, rowsNumber - 1); j++) {
                if ((map[i][j] == null || map[i][j].isEmpty()) && i != x && j != y) {
                    Random random = new Random();
                    if (random.nextInt(3) >= 1 && city.getBuildings().size() < 8) {
                        changeCell(i, j, city, new GrassCell(CELL_WIDTH, i, j));
                    }
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

    public void changeCell(int i, int j, City cityWhereBuild, Cell prevCell) {
        Random random = new Random();
        int type = random.nextInt(4);
        if (cityWhereBuild.getBuildings().size() == 7 && cityWhereBuild.getArmies().size() == 0)
            type = 3;
        switch (type) {
            case 0 -> map[i][j] = new MineralCell(CELL_WIDTH, i, j);
            case 1 -> map[i][j] = new FieldCell(CELL_WIDTH, i, j);
            case 2 -> map[i][j] = new GoldmineCell(CELL_WIDTH, i, j);
            case 3 -> {
                if (cityWhereBuild.getArmies().size() < 3) {
                    map[i][j] = new ArmyCell(CELL_WIDTH, i, j);
                    ((ArmyCell) map[i][j]).setPrevCell(prevCell);
                } else
                    return;
            }
        }
//        System.out.println(cityWhereBuild);
//        System.out.println(i + " " + j + " " + map[i][j].getClass());
        System.gc();
        map[i][j].setCityWhereBuild(cityWhereBuild);
        if (map[i][j] instanceof BuildingCell) {
            cityWhereBuild.addBuilding((BuildingCell) map[i][j]);
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
