package main.views;

import main.MiniMap;
import main.StartMenuScene;
import main.views.Cell.BuildingCell;
import main.views.Cell.Cell;
import main.views.Cell.CityCell;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Player {

    private ArrayList<City> cities;
    private int level;

    public Player(){
        cities = new ArrayList<>();
    }

    public Player(ArrayList<City> cities){
        this.cities = cities;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public void addCity(City city){
        cities.add(city);
    }

    public void deleteCity(City city){
        cities.remove(city);
    }

    public boolean hasCity(City city){
        return cities.contains(city);
    }


    public void addCityCell(CityCell cityCell){
        City city = new City("SMT");
        cityCell.setCity(city); // set city to cell
        city.setCityCell(cityCell); // set cell to city
        CityHandler.getCityHandler().addCity(city); // add city to all cities handler
        cities.add(city); // add city to player`s cities
        city.setPlayer(this); // set player to city
    }

    public boolean hasArmy(Army army) {
        for(City city: cities){
            if(city.getArmies().contains(army))
                return true;
        }
        return false;
    }
    public void moveArmy() throws IOException {
        Army army = getFirstArmy();
        if(army == null)
            return;
        Player player = PlayersHandler.getPlayersHandler().getPlayer(0);
        City city = player.getCities().get(0);
        Cell[][] map = MapArrView.getMapArrView().getMap();
        CityCell cityCell = canAttack(army);
        if(cityCell != null){
            System.out.println(cityCell.takeX() + " " + cityCell.takeY());
            attackCity(army, cityCell.getCity());
            System.out.println(army.getHealth() + " " + cityCell.getCity().getHealth());
            if(army.getHealth() > 0) {
                System.out.println("Bot won");
                addCity(city);
                player.deleteCity(city);
                for(BuildingCell buildingCell: city.getBuildings()){
                    ((Cell)buildingCell).setDefaultFill();
                }
                cityCell.setDefaultFill();
                cityCell.getCity().setHealth(100);
                MiniMap.getMiniMap().drawMiniMap();
                if(player.getCities().size()==0){
                    MapArrView.getMapArrView().getMapView().getLevelScene().getClip().stop();
                    MapArrView.getMapArrView().getMapView().getLevelScene().getClip().setMicrosecondPosition(0);
//                    LoseScene loseScene = new LoseScene(new GridPane(),
//                            StartMenuScene.takeWidth(), StartMenuScene.takeHeight(),MapArrView.getMapArrView().getMapView().getLevelScene());;
//                    StartMenuScene.getStage().setScene(loseScene);
                }
            } else {
                System.out.println("Bot lose");
                MapView.getMapView().changeOnGrass(army.getArmyCell().takeX(), army.getArmyCell().takeY());
                army.getCity().deleteBuilding(army.getArmyCell());
                army.getCity().getArmies().remove(army);
            }
        }
        if(army.getArmyCell().takeX() > city.getCityCell().takeX()){
            Cell cellToMove = map[army.getArmyCell().takeX()-1][army.getArmyCell().takeY()];
            moveArmyToCell(cellToMove, army);
        }
        if(army.getArmyCell().takeY() < city.getCityCell().takeY()){
            Cell cellToMove = map[army.getArmyCell().takeX()][army.getArmyCell().takeY()+1];
            moveArmyToCell(cellToMove, army);
        }
        if(army.getArmyCell().takeX() < city.getCityCell().takeX()){
            Cell cellToMove = map[army.getArmyCell().takeX()+1][army.getArmyCell().takeY()];
            moveArmyToCell(cellToMove, army);
        }
        if(army.getArmyCell().takeY() > city.getCityCell().takeY()){
            Cell cellToMove = map[army.getArmyCell().takeX()][army.getArmyCell().takeY()-1];
            moveArmyToCell(cellToMove, army);
        }
    }
    private void moveSingleArmy(Army army) throws IOException {
        if(army == null)
            return;
        Player player = PlayersHandler.getPlayersHandler().getPlayer(0);
        City city = player.getCities().get(0);
        Cell[][] map = MapArrView.getMapArrView().getMap();
        CityCell cityCell = canAttack(army);
        if(cityCell != null){
            System.out.println(cityCell.takeX() + " " + cityCell.takeY());
            attackCity(army, cityCell.getCity());
            System.out.println(army.getHealth() + " " + cityCell.getCity().getHealth());
            if(army.getHealth() > 0) {
                System.out.println("Bot won");
                addCity(city);
                player.deleteCity(city);
                for(BuildingCell buildingCell: city.getBuildings()){
                    ((Cell)buildingCell).setDefaultFill();
                }
                cityCell.setDefaultFill();
                cityCell.getCity().setHealth(100);
                MiniMap.getMiniMap().drawMiniMap();
                if(player.getCities().size()==0){
//                    MapArrView.getMapArrView().getMapView().getLevelScene().getClip().stop();
//                    MapArrView.getMapArrView().getMapView().getLevelScene().getClip().setMicrosecondPosition(0);
//                    LoseScene loseScene = new LoseScene(new GridPane(),
//                            StartMenuScene.takeWidth(), StartMenuScene.takeHeight(),MapArrView.getMapArrView().getMapView().getLevelScene());;
//                    StartMenuScene.getStage().setScene(loseScene);
//                    MapView.getMapView().clearMap();
//                    System.gc();
                }
            } else {
                System.out.println("Bot lose");
                MapView.getMapView().changeOnGrass(army.getArmyCell().takeX(), army.getArmyCell().takeY());
                army.getCity().deleteArmy(army.getArmyCell());
                army.getCity().generateArmy();
                return;
            }
        }
        if(army.getArmyCell().takeX() > city.getCityCell().takeX()){
            Cell cellToMove = map[army.getArmyCell().takeX()-1][army.getArmyCell().takeY()];
            moveArmyToCell(cellToMove, army);
        }
        if(army.getArmyCell().takeY() < city.getCityCell().takeY()){
            Cell cellToMove = map[army.getArmyCell().takeX()][army.getArmyCell().takeY()+1];
            moveArmyToCell(cellToMove, army);
        }
        if(army.getArmyCell().takeX() < city.getCityCell().takeX()){
            Cell cellToMove = map[army.getArmyCell().takeX()+1][army.getArmyCell().takeY()];
            moveArmyToCell(cellToMove, army);
        }
        if(army.getArmyCell().takeY() > city.getCityCell().takeY()){
            Cell cellToMove = map[army.getArmyCell().takeX()][army.getArmyCell().takeY()-1];
            moveArmyToCell(cellToMove, army);
        }
        System.gc();
    }

    public void moveAllBotArmies() throws IOException {
        ArrayList<Army> botArmies = new ArrayList<>();
        for (City i: PlayersHandler.getPlayersHandler().getPlayer(1).getCities()){
            for (Army j: i.getArmies()){
                botArmies.add(j);
            }
        }
        for (Army i: botArmies) {
            moveSingleArmy(i);
        }
    }

    public boolean hasBuilding(BuildingCell buildingCell) {
        for(City city: cities){
            if(city.getBuildings().contains(buildingCell))
                return true;
        }
        return false;
    }
    private void moveArmyToCell(Cell cellToMove, Army army) {
        if(!(cellToMove instanceof CityCell)) {
            cellToMove.setArmyCellView(army.getArmyCell());
            MapView.getMapView().moveArmy(cellToMove.takeX(), cellToMove.takeY(), cellToMove.getArmyCell());
            if ((cellToMove instanceof BuildingCell) && !(hasBuilding((BuildingCell) cellToMove))) {
                cellToMove.getCityWhereBuild().deleteBuilding((BuildingCell) cellToMove);
                cellToMove.getArmyCell().setPrevCell(null);
            } else
                cellToMove.getArmyCell().setPrevCell(cellToMove);
        }
    }

    public int getLevel() {
        return level;
    }
    private Army getFirstArmy() {
        for(City city: cities){
            if(city.getArmies().size()> 0)
                return city.getArmies().get(0);
        }
        return null;
    }
    private CityCell canAttack(Army army) {
        Cell[][] map = MapArrView.getMapArrView().getMap();
        for (int i = Math.max(army.getArmyCell().takeX() - 1, 0); i < Math.min(army.getArmyCell().takeX() + 1, MapArrView.getMapArrView().getColumnsNumber()); i++) {
            for (int j = Math.max(army.getArmyCell().takeY() - 1, 0); j < Math.min(army.getArmyCell().takeY() + 1, MapArrView.getMapArrView().getRowsNumber()); j++) {
                if(map[i][j] instanceof CityCell){
                    if(PlayersHandler.getPlayersHandler().getPlayer(0).hasCity(((CityCell) map[i][j]).getCity())){
                        return (CityCell) map[i][j];
                    }
                }
            }
        }
        return null;
    }

    public void attack(Army firstArmy, Army secondArmy){
        Random rand = new Random();
        while(firstArmy.getHealth()>0 && secondArmy.getHealth()>0){
            int firstArmyDamage = rand.nextInt(firstArmy.getAttackDamage()*2);
            int secondArmyDamage = rand.nextInt(secondArmy.getDefenceDamage()*2);
            firstArmy.receiveDamage(secondArmyDamage);
            secondArmy.receiveDamage(firstArmyDamage);
        }
    }

    public void attackCity(Army army, City city){
        while(army.getHealth()>0 && city.getHealth()>0){
            Random rand = new Random();
            int armyDamage = rand.nextInt(army.getAttackDamage()*2);
            int cityDamage = rand.nextInt(city.getDefenceDamage()*2);
            city.receiveDamage(armyDamage);
            if(city.getHealth() <= 0)
                break;
            army.receiveDamage(cityDamage);
        }
    }

    public void setLevel(int level){
        this.level = level;
    }
}