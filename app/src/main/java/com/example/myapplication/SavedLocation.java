package com.example.myapplication;

import java.io.Serializable;


//Uses Serializable Interface
public class SavedLocation implements Serializable {

    private String id;
    private double longitude;
    private double latitude;

    public SavedLocation(String id, double longitude, double latitude){
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public String toString() {
        return id;
    }
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getId() {
        return id;
    }
}
