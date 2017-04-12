package com.example.sylvinho.villefutee;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by sylvinho on 12/04/2017.
 */

public class GetMDPSocket extends AsyncTask<String, Void, String> {


    @Override
    protected String doInBackground(String... params) {
        Socket socket;
        BufferedReader in;
        PrintWriter out;

        try {
            //System.out.println("ici : "+InetAddress.getLocalHost() + "ici "+InetAddress.getLocalHost().getHostAddress());
            System.out.println("valeur: "+params[0]);
            socket = new Socket("170.20.10.4", 8050);
            System.out.println("Demande de connexion");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message_distant = in.readLine();
            System.out.println(message_distant); //affiche "vous etes connecté";

            out = new PrintWriter(socket.getOutputStream());

            out.println(params[0]); //contiendra normalement l'identifiant rentré par l'user

            out.flush();

            /**
             * On va récupérer maintenant le mot de passe renvoyé par le serveur pour le passer au main thread
             **/
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message_distantMDP = in.readLine();
            System.out.println(message_distant);

            socket.close();
            return message_distant;

        } catch (UnknownHostException e) {

            System.err.println("Hôte inconnu");
            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
        return null;
    }
}
