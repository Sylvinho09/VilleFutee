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

        try {

            socketserver = new ServerSocket(); // ne pas spécifier de port car
            // sinon le bind échoue

            socketserver.bind(new InetSocketAddress(InetAddress.getLocalHost().getHostName(), 8050));

            System.out.println("Le serveur est à l'écoute du port " + socketserver.getLocalPort() + " à l'adresse IP "
                    + socketserver.getInetAddress().getHostAddress());

            while (true) {
                socketduserveur = socketserver.accept();

                System.out.println("Un client s'est connecté");
                PrintWriter out = new PrintWriter(socketduserveur.getOutputStream());

                BufferedReader in = new BufferedReader(new InputStreamReader(socketduserveur.getInputStream()));

                /**
                 * On va maintenant se mettre en réception pour savoir ce que
                 * veut le client
                 **/
                System.out.println("je me mets en réception de la demande");

                char[] recep = new char[6];

                int message_length = in.read(recep);

                System.out.println(message_length);

                String extract = new String(recep);

                System.out.println("Message reçu du client : " + extract);

                if (extract.trim().equals("idMdp")) {
                    System.out.println("dans le equals");
                    char[] buffer = new char[60];

					/*
					 * IMPORTANT : ne pas redéclarer la ligne en dessous, en
					 * effet, si le client envoie le 2 eme message avant que le
					 * serveur ne declare la ligne en dessous, alors le buffer
					 * ci dessous sera vide et il y aura un blockage !"
					 */
                    // in = new BufferedReader(new
                    // InputStreamReader(socketduserveur.getInputStream()));

                    int message_length_idMdp = in.read(buffer); // read(CharBuffer)
                    // est
                    // bloquant:pratique

                    // int x = Character.getNumericValue(buffer.get()); dans le
                    // cas où on doit recevoir un entier
                    String id = "";
                    String mdp = "";
                    int chx = 0;
                    String valueCo = new String(buffer);

                    String[] idmdp = valueCo.toString().trim().split("\\s");

                    System.out.println("affichage id et mdp: " + idmdp[0] + " " + idmdp[1]);

                    id = idmdp[0];
                    mdp = idmdp[1];

                    System.out.println(" Identifiant " + id + " Mdp " + mdp);

                    System.out.println("2nd step"); /** Fonctionne **/

                    /**
                     * on vérifie dans la bdd la correspondance identifiant-mdp
                     **/

                    /** On renvoie maintenant si le MDP est correct ou non **/
                    if (id.trim().equals("Sylvinho09") && mdp.trim().equals("blabla"))
                        out.println("Yes");
                    else
                        out.println("No"); // Yes or no
                    out.flush();
                    System.out.println("3rd step");

                }

                /** Le client veut s'inscrire **/

                else if (extract.trim().equals("Create")) {
                    char[] formbuffer = new char[180];

                    System.out.println("Le client veut créer un compte !");
                    Formulaire form = new Formulaire();

                    /**
                     * do while obligatoire sinon si on veut refaire une 2eme
                     * inscription d'affilée, le read renvoie 1 je ne sais pas
                     * pourquoi
                     */
                    int message_lengthCreate = 0;
                    do {
                        message_lengthCreate = in.read(formbuffer, 0, 180);

                    } while (message_lengthCreate == 1);
                    System.out.println("valeur read " + message_lengthCreate);
                    String valueForm = new String(formbuffer);

                    System.out.println("données recues: " + valueForm.trim());

                    // Obtient un tableau avec à chaque case une donnée saisie
                    String[] datas = valueForm.toString().trim().split("\\s+");

                    try {
                        System.out.println("dans le try");
                        form.prenom = datas[0].trim();
                        form.nom = datas[1].trim();
                        form.age = datas[2].trim();
                        form.ville = datas[3].trim();
                        form.identifiant = datas[4].trim();
                        form.mdp = datas[5].trim();
                        System.out.println("apres");

                        // formbuffer.clear();
                        System.out.println("données reçues :" + form.toString());

                        out.println("Ok");
                        out.flush();

                        // On ajoute maintenant ces données dans la base de
                        // données
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("dans le catch");
                        out.println("Error");
                        out.flush();
                    }

                }
            }

        } catch (IOException e) {

            e.printStackTrace();

        }
		/*
		 * socketduserveur.close(); socketserver.close();
		 */
    }
}
