package com.example.paul.villefutee_android;

import android.os.AsyncTask;



import java.io.IOException;

import java.io.ObjectInputStream;

import java.io.PrintWriter;

import java.net.Socket;



/**

 * Created by Paul on 27/05/2017.

 */



public class EnvoiFormRes extends AsyncTask<FormulaireReseau, Void, Integer>{

    private Socket socket;

    private PrintWriter out;

    // private BufferedReader in;

    ObjectInputStream is;







    @Override

    protected Integer doInBackground(FormulaireReseau... params) {

        try {

            //if (StaticSocket.getI() == 0) {

            Socket socket;

            socket = new Socket("172.24.12.189", 8050);

            //StaticSocket.setSocket(socket2);

            //socket = StaticSocket.getSocket();

            //  }

            // else socket=StaticSocket.getSocket();

            out = new PrintWriter(socket.getOutputStream());

            System.out.println("j'envoie");

            out.println("CreateRes".trim());

            out.flush();

            System.out.println("j'envoie2 client");

            out.println(params[0].NomReseau + " " + params[0].Categorie + " " + params[0].typeReseau  + " " + params[0].ville+" " + params[0].idUser);

            out.flush();



            System.out.println("données envoyées envoiform: " +params[0].NomReseau + " " + params[0].Categorie + " " + params[0].ville + " " + params[0].typeReseau+" " + params[0].idUser);



            is = new ObjectInputStream(socket.getInputStream());

            //in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //recuperer int  char[] reponse= new char[10];

            int buffer =(int)is.readObject();









            System.out.println("fin");



            return buffer;





        } catch (IOException e)

        {

            e.printStackTrace();

        } catch (ClassNotFoundException e) {

            e.printStackTrace();

        }catch (Exception e) {

            e.printStackTrace();

        }

        return null;

    }

}