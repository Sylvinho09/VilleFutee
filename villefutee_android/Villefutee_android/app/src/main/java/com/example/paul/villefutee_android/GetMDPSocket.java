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
import java.io.ObjectInputStream;

/**
 * Created by sylvinho on 12/04/2017.
 */

public class GetMDPSocket extends AsyncTask<IdMdpClass, Void, Integer> {
    String id;
    String mdp;



    @Override
    protected Integer doInBackground(IdMdpClass... params) {
        Socket socket;
        //BufferedReader in;
        PrintWriter out;
        ObjectInputStream is;

        try {
            //System.out.println("ici : "+InetAddress.getLocalHost() + "ici "+InetAddress.getLocalHost().getHostAddress());
            System.out.println("valeur: "+params[0].id+ " "+params[0].mdp);

                socket = new Socket("172.20.10.2", 8050);
            is = new ObjectInputStream(socket.getInputStream());
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
            out.println(params[0].clientZCommercantU +" "+params[0].id +" "+ params[0].mdp);

            out.flush();
            System.out.println("apres les 2 out");

            //in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //CharBuffer buffer= CharBuffer.allocate(3); // Yes or No -> taille max : 3
            int buffer =-1;
            //int message_length = in.read(buffer); //read(CharBuffer) est bloquant:pratique
            buffer=(int) is.readObject();

            /*buffer.position(0);
            String result="";
            for(int i=0; i<message_length; i++)
            {
                char c= buffer.get();
                result+=c;
            }*/

            socket.close();
            System.out.println("a la fin");

            return buffer;

        } catch (UnknownHostException e) {

            System.err.println("Hôte inconnu");
            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
