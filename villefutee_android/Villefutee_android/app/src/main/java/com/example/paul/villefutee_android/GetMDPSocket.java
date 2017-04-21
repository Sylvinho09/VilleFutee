package com.example.paul.villefutee_android;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.CharBuffer;

/**
 * Created by sylvinho on 12/04/2017.
 */

public class GetMDPSocket extends AsyncTask<IdMdpClass, Void, String> {
    String id;
    String mdp;

    GetMDPSocket()
    {
        this.id=id;
        this.mdp=mdp;
    }

    @Override
    protected String doInBackground(IdMdpClass... params) {
        Socket socket;
        BufferedReader in;
        PrintWriter out;

        try {
            //System.out.println("ici : "+InetAddress.getLocalHost() + "ici "+InetAddress.getLocalHost().getHostAddress());
            System.out.println("valeur: "+params[0].id+ " "+params[0].mdp);

                socket = new Socket("172.20.10.4", 8050);
                //StaticSocket.setSocket(socket2);
                //socket = StaticSocket.getSocket();



            /*System.out.println("Demande de connexion");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message_distant = in.readLine();
            System.out.println(message_distant); //affiche "vous etes connecté";
            System.out.println("type de socket: "+ socket.getClass()+ " "+ socket);*/

            out = new PrintWriter(socket.getOutputStream());
            out.println(new String("idMdp").trim()); //On envoie ça au serveur pour qu'il sache ce qu'on veut
            out.flush();
            System.out.println("entre les 2 out");
            out = new PrintWriter(socket.getOutputStream());
            /** params[0].id contient l'id et params[0].mdp le mdp **/
            out.println(params[0].id + " "+ params[0].mdp);

            out.flush();
            System.out.println("apres les 2 out");

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            CharBuffer buffer= CharBuffer.allocate(3); // Yes or No -> taille max : 3

            int message_length = in.read(buffer); //read(CharBuffer) est bloquant:pratique
            buffer.position(0);
            String result="";
            for(int i=0; i<message_length; i++)
            {
                char c= buffer.get();
                result+=c;
            }

            socket.close();
            System.out.println("a la fin");

            return result;

        } catch (UnknownHostException e) {

            System.err.println("Hôte inconnu");
            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
        return null;
    }
}
