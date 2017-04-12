package com.example.sylvinho.villefutee;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.net.InetAddress;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

/**
 * Created by sylvinho on 11/04/2017.
 */

public class ConnexionPage extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion_page);
        String result="";
        try {
            System.out.println("je lance le thread");
            result = new GetMDPSocket().execute("abcde", null, result).get();
            System.out.println("après le thread");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("Dans le main Thread "+result);

    }
}
