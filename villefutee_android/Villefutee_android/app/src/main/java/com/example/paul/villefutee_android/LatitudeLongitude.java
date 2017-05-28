package com.example.paul.villefutee_android;

/**
 * Created by sylvinho on 27/05/2017.
 */

public class LatitudeLongitude {
    String id;
    double latitude;
    double longitude;

    public LatitudeLongitude(double latitude, double longitude){
        this.latitude=latitude;
        this.longitude=longitude;
    }
    public LatitudeLongitude(String id, double latitude, double longitude){
        this.id=id;
        this.latitude=latitude;
        this.longitude=longitude;
    }


    public double getLatitude()
    {
        return latitude;
    }
    public double getLongitude()
    {
        return longitude;
    }
    public String getId()
    {
        return id;
    }
}
