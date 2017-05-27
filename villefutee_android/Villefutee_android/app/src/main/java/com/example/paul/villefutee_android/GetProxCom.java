package com.example.paul.villefutee_android;

import android.os.AsyncTask;

import com.example.paul.villefutee_android.villefutee_server.LatitudeLongitude;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by sylvinho on 27/05/2017.
 */

public class GetProxCom extends AsyncTask<LatitudeLongitude, Void, ArrayList<String>> {
    Socket socket;
    PrintWriter out;
    ObjectInputStream is;


    @Override
    protected ArrayList<String> doInBackground(LatitudeLongitude... params) {

        try {
            socket = new Socket("192.168.1.26", 8050);
            out= new PrintWriter(socket.getOutputStream());

            is=new ObjectInputStream(socket.getInputStream());
            System.out.println("VALEURS LATITUDE LONGITUDE THREAD "+params[0].getLatitude()+" "+params[0].getLongitude());
            System.out.println("VALEURS LATITUDE LONGITUDE THREAD valueof"+String.valueOf(params[0].getLatitude())+" "+params[0].getLongitude());

            out.println("ProxComs".trim());

            out.flush();
            out.println(params[0].getLatitude()+" "+params[0].getLongitude());
            out.flush();



            ArrayList<String> proxComs= (ArrayList<String>)is.readObject();

            return proxComs;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
