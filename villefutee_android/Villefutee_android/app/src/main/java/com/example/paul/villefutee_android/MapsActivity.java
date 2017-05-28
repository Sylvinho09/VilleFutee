package com.example.paul.villefutee_android;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<String> proxCom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        proxCom=new ArrayList<String>();
        proxCom=getIntent().getStringArrayListExtra("Com");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        /** Pour faire des title et snippets personnalisés **/
        /*mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });*/

        /** Magasins en durs **/
        LatLng Magasin1 = new LatLng(43.5, 3.5);

        mMap.addMarker(new MarkerOptions().position(Magasin1).title("MagasinEnDur").snippet("Hello"));


        /**Magasins récupérés du serveur **/
        int i=0;

        while (i<proxCom.size())
        {
            String nomMag= proxCom.get(i);
            String infosMag = proxCom.get(i+1)+", "+proxCom.get(i+2);
            LatLng magasin= new LatLng(Double.parseDouble(proxCom.get(i+3)), Double.parseDouble(proxCom.get(i+4)));
            //On met le focus sur le premier magasin proche
            if(i==0) {

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Magasin1, 8.0f));

            }
            i+=5;
            mMap.addMarker(new MarkerOptions().position(magasin).title(nomMag).snippet(infosMag));
        }

    }
}
