package com.example.paul.villefutee_android;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

/**
 * Created by sylvinho on 28/05/2017.
 */

class GetNotifs extends AsyncTask<LatitudeLongitude, Void, Vector<String>> {
    Socket socket;
    PrintWriter out;
    ObjectInputStream is;

    @Override
    protected Vector<String> doInBackground(LatitudeLongitude... params) {
        Vector<String> notifs = new Vector<String>();
        try {
            socket = new Socket("172.20.10.2", 8050);
            out = new PrintWriter(socket.getOutputStream());

            is = new ObjectInputStream(socket.getInputStream());

            out.println("GetNotifs");
            out.flush();
            out.println(params[0].getId()+ " "+params[0].getLatitude()+ " "+params[0].getLongitude());
            out.flush();

            notifs= (Vector<String>)is.readObject();
            return notifs;

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }
}
