package com.example.sylvinho.villefutee;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.CharBuffer;

/**
 * Created by sylvinho on 14/04/2017.
 */

public class EnvoiForm extends AsyncTask<Formulaire, Void, String>{
    private Socket socket;
    private PrintWriter out;



    @Override
    protected String doInBackground(Formulaire... params) {

        try {

            if (StaticSocket.getI() == 0) {
                Socket socket2;
                socket2 = new Socket("172.20.10.4", 8050);
                StaticSocket.setSocket(socket2);
                socket = StaticSocket.getSocket();
            }
            else socket=StaticSocket.getSocket();
            out = new PrintWriter(socket.getOutputStream());
            out.println("Create"); //On envoie ça au serveur pour qu'il sache ce qu'on veut
            out.flush(); // les données sont automatiquement envoyées si le buffer est plein (1000 octets de base je crois), sinon il faut flush

            System.out.println("données envoyées envoiform: "+params[0].prenom+" "+params[0].nom+" "+params[0].age+" "+params[0].ville+" "+params[0].identifiant+" "+params[0].mdp);

            out.println(params[0].prenom+" "+params[0].nom+" "+params[0].age+" "+params[0].ville+" "+params[0].identifiant+" "+params[0].mdp);
            out.flush();

            return "ok";



        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
