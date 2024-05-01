package com.eneskoseoglu.seestanbul.Model;

public class Park {

    private String name;
    private String district;
    private String coordinate;

    public Park(String name, String district, String coordinate) {

        this.name = name;
        this.district = district;
        this.coordinate = coordinate;

    }

    public String getName() {
        return name;
    }


    public String getDistrict() {
        return district;
    }

    public String getCoordinate() {
        return coordinate;
    }

}
