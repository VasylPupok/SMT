package main.views;


import main.views.Cell.*;

import java.util.ArrayList;
import java.util.Random;
import java.awt.*;

public class City {
    private String name;
    private int resMineral;
    private int resGold;
    private int resField;
    public static final int maxNumberOfBuildings = 8;
    private CityCell cityCell;
    private Player player;
    private int health;
    private int defenceDamage;
    private ArrayList<Army> armies;
    private ArrayList<BuildingCell> buildings;

    public City(String name) {
        this.name = name;
        resMineral = 50;
        resGold = 50;
        resField = 50;
        health = 100;
        defenceDamage = 20;
        buildings = new ArrayList<>();
        armies = new ArrayList<>();
        /*Thread collectorRes = new Thread(() -> {
            Thread currentThread = Thread.currentThread();
            while (!currentThread.isInterrupted()) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                collectResources();
                spendResources();
            }
        });*/
        //collectorRes.start();
    }

    public void addBuilding(BuildingCell buildingCell){
        buildings.add(buildingCell);
        if(buildingCell.getClass().equals(ArmyCell.class)) {
            Army army = new Army();
            ((ArmyCell) buildingCell).setArmy(army);
            army.setArmyCell((ArmyCell) buildingCell);
            army.setCity(this);
            armies.add(army);
        }
    }

    public void deleteBuilding(BuildingCell buildingCell){
        buildings.remove(buildingCell);
    }

    public boolean hasBuilding(BuildingCell buildingCell){
        return buildings.contains(buildingCell);
    }

    public ArrayList<Army> getArmies() {
        return armies;
    }

    public String getName() {
        return name;
    }

    public CityCell getCityCell() {
        return cityCell;
    }

    public void setCityCell(CityCell cityCell) {
        this.cityCell = cityCell;
    }

    public ArrayList<BuildingCell> getBuildings() {
        return buildings;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDefenceDamage() {
        return defenceDamage;
    }

    public void setDefenceDamage() {
        this.defenceDamage = defenceDamage;
    }

    public int getResMineral() {
        return resMineral;
    }

    public void setResMineral(int resMineral) {
        this.resMineral = resMineral;
    }

    public int getResGold() {
        return resGold;
    }

    public void setResGold(int resGold) {
        this.resGold = resGold;
    }

    public int getResFood() {
        return resField;
    }

    public void setResFood(int resField) {
        this.resField = resField;
    }

    public void decrementResMineral() {
        resMineral--;
    }

    public void decrementResGold() {
        resGold--;
    }

    public void decrementResField() {
        resField--;
    }

    public void collectResources(){
        for(BuildingCell buildingCell: buildings){
            if(buildingCell.getClass().equals(FieldCell.class))
                resField += 2;
            else if(buildingCell.getClass().equals(MineralCell.class))
                resMineral += 2;
            else if(buildingCell.getClass().equals(GoldmineCell.class))
                resGold += 2;
        }
    }

    public void spendResources(){
        if(resField > 0)
            resField--;
        if(resGold > 0)
            resGold--;
        if(resMineral > 0)
            resMineral--;
    }

    public void receiveDamage(int damage){
        this.health -= damage;
        if(this.health<=0){
            this.health = 0;
        }
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", resMineral=" + resMineral +
                ", resGold=" + resGold +
                ", resField=" + resField +
                '}';
    }

    public void deleteArmy(ArmyCell armyCell) {
        armies.remove(armyCell.getArmy());
        buildings.remove(armyCell);
    }

    public void generateArmy() {
        ArrayList<Point> emptyCells = new ArrayList<>();
        Cell[][] map = MapArrView.getMapArrView().getMap();
        for(int i = Math.max(0, cityCell.takeX()-2); i <= Math.min(MapArrView.getMapArrView().getColumnsNumber()-1, cityCell.takeX()+2);i++){
            for(int j = Math.max(0, cityCell.takeY()-2); j <= Math.min(MapArrView.getMapArrView().getColumnsNumber()-1, cityCell.takeY()+2); j++){
                if(map[i][j].isEmpty())
                    emptyCells.add(new Point(i,j));
            }
        }
//        Random random = new Random();
//        int randCell = random.nextInt(emptyCells.size());
//        if(random.nextInt(5)<=2) {
//            MapView.getMapView().buildResources(emptyCells.get(randCell).x, emptyCells.get(randCell).y, this, ArmyCell.class);
//        }
    }

}