package com.example.paul.villefutee_android;

import android.os.AsyncTask;

import com.example.paul.villefutee_android.villefutee_server.ClientInformations;
import com.example.paul.villefutee_android.villefutee_server.UserInformations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Created by sylvinho on 25/05/2017.
 */

public class GetInfosUser extends AsyncTask<String, Void, UserInformations> {
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    ObjectInputStream is;


    @Override
    protected UserInformations doInBackground(String... params) {

        PrintWriter out;

        try {
            socket = new Socket("192.168.1.26", 8050);

            out = new PrintWriter(socket.getOutputStream());

            System.out.println("je suis dans le getUserInfo");

            System.out.println("je suis dans le getUserInfo");
            out.println("getInfos".trim());
            out.flush();
            out.println("0 " + params[0].trim());
            out.flush();

            ClientInformations ci = new ClientInformations();
            //in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            is = new ObjectInputStream(socket.getInputStream());

            ci.setPrenom((String)is.readObject());
            ci.setNom((String)is.readObject());
            ci.setAge((String)is.readObject());
            ci.setDateCompte((String)is.readObject());
            ci.setVille((String)is.readObject());
            ci.setPolitique_notif((String)is.readObject());


            ci.setCategories((Vector<String>)is.readObject());
            ci.setListe_reseaux((Hashtable<Integer, Vector<String>>) is.readObject());
            ci.setNotif_by_categ((Hashtable<String, Vector<Vector<String>>>) is.readObject());

            System.out.println("affichage user: " + ci.toString());
            //ClientInformations ci= (ClientInformations) is.readObject();


            return ci;

        } catch (IOException e) {
            System.out.println("Erreur socket getinfosUser");
            e.printStackTrace();
            return null;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}

