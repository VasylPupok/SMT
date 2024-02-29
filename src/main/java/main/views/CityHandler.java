package main.views;

import java.util.ArrayList;

public class CityHandler {
    private ArrayList<City> cities;
    private static CityHandler cityHandler;

    private CityHandler() {
        cities = new ArrayList<>();
    }

    public static CityHandler getCityHandler() {
        if(cityHandler == null)
            cityHandler = new CityHandler();
        return cityHandler;
    }

    public void addCity(City city){
        cities.add(city);
    }

    public ArrayList<City> getCities() {
        return cities;
    }
}
