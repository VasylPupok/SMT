package main.views;

import main.views.Cell.CityCell;

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


    public int getLevel() {
        return level;
    }

    public void setLevel(int level){
        this.level = level;
    }
}