package com.example.paul.villefutee_android;

import android.os.AsyncTask;

import com.example.paul.villefutee_android.villefutee_server.ClientInformations;
import com.example.paul.villefutee_android.villefutee_server.CommercantInformations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Created by sylvinho on 28/05/2017.
 */

public class GetInfosCommercant extends AsyncTask<String, Void, CommercantInformations> {
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    ObjectInputStream is;

    @Override
    protected CommercantInformations doInBackground(String... params) {
        PrintWriter out;

        try {
            socket = new Socket("172.20.10.2", 8050);

            out = new PrintWriter(socket.getOutputStream());

            System.out.println("je suis dans le getUserInfo");

            System.out.println("je suis dans le getUserInfo");
            out.println("getInfos".trim());
            out.flush();
            out.println("1 " + params[0].trim());
            out.flush();

            CommercantInformations ci = new CommercantInformations();
            //in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            is = new ObjectInputStream(socket.getInputStream());

            ci.setNom_magasin((String)is.readObject());
            ci.setAdresse((String)is.readObject());
            ci.setDateCompte((String)is.readObject());
            ci.setVille((String)is.readObject());
            ci.setCategories((Vector<String>)is.readObject());

            ci.setListe_reseaux((Hashtable<Integer, Vector<String>>) is.readObject());
            ci.setNotif_by_categ((Hashtable<String, Vector<Vector<String>>>) is.readObject());

            System.out.println("affichage commercant : " + ci.toString());
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
