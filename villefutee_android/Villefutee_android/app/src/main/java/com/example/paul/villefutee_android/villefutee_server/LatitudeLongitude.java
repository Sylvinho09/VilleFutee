package com.example.paul.villefutee_android.villefutee_server;

/**
 * Created by sylvinho on 27/05/2017.
 */

public class LatitudeLongitude {
    double latitude;
    double longitude;

    public LatitudeLongitude(double latitude, double longitude){
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
}
