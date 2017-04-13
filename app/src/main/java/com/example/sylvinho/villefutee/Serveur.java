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
import java.nio.CharBuffer;

public class Serveur {

    public static void main(String[] zero) {

        ServerSocket socketserver;
        Socket socketduserveur;
        BufferedReader in;
        PrintWriter out;

        try {

            socketserver = new ServerSocket(); // ne pas spécifier de port car
            // sinon le bind échoue

            socketserver.bind(new InetSocketAddress(InetAddress.getLocalHost().getHostName(), 8050));

            System.out.println("Le serveur est à l'écoute du port " + socketserver.getLocalPort() + " à l'adresse IP "
                    + socketserver.getInetAddress().getHostAddress());

			/*CharBuffer cbuf = CharBuffer.allocate(5);
			System.out.println("taille : "+cbuf.length());//donne la taille restante !!



			cbuf.put("allo");
			cbuf.position(0);
			String a = "";
			for (int i = 0; i < cbuf.capacity(); i++) {

				a += cbuf.get();
				System.out.println("ici " + a);

			}

			System.out.println("affichage : " + a);*/

            socketduserveur = socketserver.accept();

            System.out.println("Un zéro s'est connecté");

            /** On envoie la notif au client **/
            out = new PrintWriter(socketduserveur.getOutputStream());
            out.println("Vous êtes connecté zéro !");
            out.flush();
            System.out.println("1st step");

            /**
             * on va maintenant recevoir l'identifiant et renvoyer le mot de
             * passe associé
             **/
            in = new BufferedReader(new InputStreamReader(socketduserveur.getInputStream()));

            CharBuffer buffer= CharBuffer.allocate(100); // on fixe à 100 la taille de l'identifiant+mdp reçus


            int message_length = in.read(buffer); //read(CharBuffer) est bloquant:pratique
            buffer.position(0); //sinon la position par défaut est celle du nombre de char -1 dans le buffer
            //int x = Character.getNumericValue(buffer.get()); dans le cas où on doit recevoir un entier
            //System.out.println("valeur obtenue dans le buffer : "+ x);
            String id="";
            String mdp="";
            int chx =0;
            for(int i=0; i<message_length; i++)
            {
                char c= buffer.get();
                if(c==' ')
                {
                    chx=1;
                }
                else if(chx==0) id+=c;
                else mdp+=c;
            }
            System.out.println(" Identifiant "+id+" Mdp "+mdp);

            System.out.println("2nd step"); /** Fonctionne **/

            /** on vérifie dans la bdd la correspondance identifiant-mdp **/

            /** On renvoie maintenant si le MDP est correct ou non  **/
            out = new PrintWriter(socketduserveur.getOutputStream());
            if(id.trim().equals("Sylvinho09") && mdp.trim().equals("blabla"))
                out.println("Yes");
            else out.println("No"); // Yes or no
            out.flush();
            System.out.println("3rd step");

            socketduserveur.close();

            socketserver.close();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }
}
