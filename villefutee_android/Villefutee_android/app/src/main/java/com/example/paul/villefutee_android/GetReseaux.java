package com.example.paul.villefutee_android;



import android.os.AsyncTask;



import com.example.paul.villefutee_android.villefutee_server.ClientInformations;

import com.example.paul.villefutee_android.villefutee_server.ReseauInfos;

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



public class GetReseaux extends AsyncTask<String, Void, Vector<String>> {

    Socket socket;

    BufferedReader in;

    PrintWriter out;

    ObjectInputStream is;







    @Override

    protected Vector<String> doInBackground(String... params) {



        try {

            socket = new Socket("172.24.12.189", 8050);



            out = new PrintWriter(socket.getOutputStream());



            System.out.println("je suis dans le GetReseaux");

            out.println("getReseaux".trim());

            out.flush();



            Vector<String> res;

            is = new ObjectInputStream(socket.getInputStream());

            res= (Vector<String>)is.readObject();

            return res;



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