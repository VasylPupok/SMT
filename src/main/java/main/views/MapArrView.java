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
        for(int i = 0; i<4; i++){
            switch (rand.nextInt(3)) {
                case 0 -> map[i][5] = new MountainCell(CELL_WIDTH, i, 5);
                case 1 -> map[i][5] = new ForestCell(CELL_WIDTH, i, 5);
                case 2 -> map[i][5] = new GrassCell(CELL_WIDTH, i, 5);
            }
        }
        CityCell cityCell = new CityCell(CELL_WIDTH,4,5,this);
        map[4][5] = cityCell;
        PlayersHandler.getPlayersHandler().getPlayer(0).addCityCell(cityCell);
        PlayersHandler.getPlayersHandler().getPlayer(0).setLevel(1);
        cityCell.setDefaultFill();
        for(int i = 5; i<16; i++){
            switch (rand.nextInt(3)) {
                case 0 -> map[i][5] = new MountainCell(CELL_WIDTH, i, 5);
                case 1 -> map[i][5] = new ForestCell(CELL_WIDTH, i, 5);
                case 2 -> map[i][5] = new GrassCell(CELL_WIDTH, i, 5);
            }
        }
        CityCell enemyCityCell = new CityCell(CELL_WIDTH,16,5,this);
        map[16][5] = enemyCityCell;
        PlayersHandler.getPlayersHandler().getPlayer(1).addCityCell(enemyCityCell);
        PlayersHandler.getPlayersHandler().getPlayer(1).setLevel(1);
        cityCell.setDefaultFill();
        for(int i = 17; i<columnsNumber; i++){
            switch (rand.nextInt(3)) {
                case 0 -> map[i][5] = new MountainCell(CELL_WIDTH, i, 5);
                case 1 -> map[i][5] = new ForestCell(CELL_WIDTH, i, 5);
                case 2 -> map[i][5] = new GrassCell(CELL_WIDTH, i, 5);
            }
        }
        for(int i = 0 ; i<columnsNumber; i++){
            for(int j=6; j<rowsNumber; j++){
                switch (rand.nextInt(3)) {
                    case 0 -> map[i][j] = new MountainCell(CELL_WIDTH, i, j);
                    case 1 -> map[i][j] = new ForestCell(CELL_WIDTH, i, j);
                    case 2 -> map[i][j] = new GrassCell(CELL_WIDTH, i, j);
                }
            }
        }
        generateBuildings(16,5,enemyCityCell.getCity());
    }

    private void generateBuildings(int x, int y, City city) {
        for (int i = Math.max(x - 2, 0); i <= Math.min(x + 2, columnsNumber-1); i++) {
            for (int j = Math.max(y - 2, 0); j <= Math.min(y + 2, rowsNumber-1); j++) {
                if ((map[i][j] == null||map[i][j].isEmpty()) && i!=x&&j!=y) {
                    Random random = new Random();
                    if(random.nextInt(3) >=1 && city.getBuildings().size() < 8){
                        changeCell(i,j,city, new GrassCell(CELL_WIDTH, i, j));
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
        if(cityWhereBuild.getBuildings().size() == 7&&cityWhereBuild.getArmies().size()==0)
            type=3;
        switch (type) {
            case 0 -> map[i][j] = new MineralCell(CELL_WIDTH, i,j);
            case 1 -> map[i][j] = new FieldCell(CELL_WIDTH, i ,j);
            case 2 -> map[i][j] = new GoldmineCell(CELL_WIDTH, i, j);
            case 3 -> {
                if(cityWhereBuild.getArmies().size() < 3) {
                    map[i][j] = new ArmyCell(CELL_WIDTH, i, j);
                    ((ArmyCell)map[i][j]).setPrevCell(prevCell);
                }
                else
                    return;
            }
        }
//        System.out.println(cityWhereBuild);
//        System.out.println(i + " " + j + " " + map[i][j].getClass());
        System.gc();
        map[i][j].setCityWhereBuild(cityWhereBuild);
        if(map[i][j] instanceof BuildingCell) {
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
