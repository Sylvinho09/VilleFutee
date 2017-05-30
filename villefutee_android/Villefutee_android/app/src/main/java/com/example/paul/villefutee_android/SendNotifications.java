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

class SendNotifications extends AsyncTask<Vector<String>, Void, Integer> {
    @Override
    protected Integer doInBackground(Vector<String>... params) {

        Socket socket;

        PrintWriter out;
        ObjectInputStream is;

        try {

            socket = new Socket("172.24.12.189", 8050);
            is = new ObjectInputStream(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());
            out.println(new String("SendNotifs").trim()); //On envoie ça au serveur pour qu'il sache ce qu'on veut
            out.flush();

            String id=params[0].get(0).trim();
            String choix=params[0].get(1).trim();
            String categorie=params[0].get(2).trim();
            String value= params[0].get(3).trim();

            out.println(id+" "+choix+ " "+categorie+ " "+value+ " 0");
            out.flush();

            int reponse= (int)is.readObject();
            return reponse;

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
