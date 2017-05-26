package com.example.paul.villefutee_android;

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
    private BufferedReader in;



    @Override
    protected String doInBackground(Formulaire... params) {

        try {

            //if (StaticSocket.getI() == 0) {
                Socket socket;
                socket = new Socket("192.168.1.26", 8050);
                //StaticSocket.setSocket(socket2);
                //socket = StaticSocket.getSocket();
          //  }
           // else socket=StaticSocket.getSocket();
            out = new PrintWriter(socket.getOutputStream());
            System.out.println("j'envoie");
            if(params[0].prenom!=null) { //c'est un commercant qui veut s'inscrire
                //On envoie ça au serveur pour qu'il sache ce qu'on veut
                out.println("Create");
                out.flush(); // les données sont automatiquement envoyées si le buffer est plein (1000 octets de base je crois), sinon il faut flush

                System.out.println("j'envoie2 client");
                out.println(params[0].prenom + " " + params[0].nom + " " + params[0].age + " " + params[0].ville + " " + params[0].identifiant + " " + params[0].mdp);
                out.flush();

                System.out.println("données envoyées envoiform: " + params[0].prenom + " " + params[0].nom + " " + params[0].age + " " + params[0].ville + " " + params[0].identifiant + " " + params[0].mdp);
            }
            else
            {   out.println("CCreate");
                out.flush();
                System.out.println("j'envoie2 commerçant");
                String adresse=String.valueOf(params[0].adresse.length)+" ";
                for(int i=0; i<params[0].adresse.length; i++)
                {
                    adresse+=params[0].adresse[i]+" ";
                }
                String domaines=String.valueOf(params[0].domaines.size())+" ";
                for(int i =0; i<params[0].domaines.size(); i++)
                {
                domaines += params[0].domaines.get(i)+" ";
                }

                out.println(adresse+ params[0].nom+" "+domaines+params[0].ville+" "+params[0].identifiant+" "+params[0].mdp);
                out.flush();

            }




            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            char[] reponse= new char[10];
            int message_length=in.read(reponse);
            String cvtReponse= new String(reponse).trim();



            System.out.println("fin");
            System.out.println(cvtReponse);
            return cvtReponse;



        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
