package com.example.sylvinho.villefutee;

/**
 * Created by sylvinho on 12/04/2017.
 * A lancer autre part ***
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.io.PrintWriter;

public class Serveur {

    public static void main(String[] zero) {

        ServerSocket socketserver;
        Socket socketduserveur;
        BufferedReader in;
        PrintWriter out;

        try {

            socketserver = new ServerSocket(); //ne pas spécifier de port car sinon le bind échoue

            socketserver.bind(new InetSocketAddress(InetAddress.getLocalHost().getHostName(), 8050));


            System.out.println("Le serveur est à l'écoute du port " + socketserver.getLocalPort()+
                    " à l'adresse IP "+socketserver.getInetAddress().getHostAddress());


            socketduserveur = socketserver.accept();

            System.out.println("Un zéro s'est connecté");

            /** On envoie la notif au client **/
            out = new PrintWriter(socketduserveur.getOutputStream());
            out.println("Vous êtes connecté zéro !");
            out.flush();
            System.out.println("1st step");


            /** on va maintenant recevoir l'identifiant et renvoyer le mot de passe associé **/
            in = new BufferedReader(new InputStreamReader(socketduserveur.getInputStream()));
            String message_distantMDP = in.readLine();
            System.out.println("2nd step");

            /** on vérifie dans la bdd la correspondance identifiant-mdp**/

            /** On renvoie maintenant le mdp **/
            out = new PrintWriter(socketduserveur.getOutputStream());
            out.println("abcd");
            out.flush();
            System.out.println("3rd step");

            socketduserveur.close();

            socketserver.close();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }
}
