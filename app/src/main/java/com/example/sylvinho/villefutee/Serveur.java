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

            socketduserveur = socketserver.accept();

            System.out.println("Un zéro s'est connecté");

            /** On va maintenant se mettre en réception pour savoir ce que veut le client **/

            out = new PrintWriter(socketduserveur.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socketduserveur.getInputStream()));

            while(true)
            {
                CharBuffer recep = CharBuffer.allocate(6); /** Get, Create, idMdp --> taille max 6 **/

                System.out.println("je me mets en réception de la demande.");
                int message_length = in.read(recep);
                recep.rewind();

                //recep.position(0); //sinon la pos est la taille du message recu -1
                String extract="";
                extract=recep.toString();

			/*for(int i=0; i<message_length; i++)
			{
				extract += recep.get();
			}*/

                System.out.println("Message reçu du client : "+extract);

                if(extract.trim().equals("idMdp"))
                {
                    System.out.println("dans le equals");
                    /**
                     * on va maintenant recevoir l'identifiant et renvoyer le mot de
                     * passe associé
                     **/

				/* IMPORTANT : ne pas redéclarer la ligne en dessous,
				 * en effet, si le client envoie le 2 eme message avant que le serveur ne declare la ligne en dessous,
				 * alors le buffer ci dessous sera vide et il y aura un blockage !"
				 */
                    //in = new BufferedReader(new InputStreamReader(socketduserveur.getInputStream()));


                    CharBuffer buffer= CharBuffer.allocate(100); // on fixe à 100 la taille de l'identifiant+mdp reçus

                    System.out.println("ici avant");
                    int message_length_idMdp = in.read(buffer); //read(CharBuffer) est bloquant:pratique
                    System.out.println("ici apres");
                    buffer.rewind(); //sinon la position par défaut est celle du nombre de char -1 dans le buffer
                    //int x = Character.getNumericValue(buffer.get()); dans le cas où on doit recevoir un entier
                    //System.out.println("valeur obtenue dans le buffer : "+ x);
                    String id="";
                    String mdp="";
                    int chx =0;
                    String[] idmdp= buffer.toString().split("\\s");

                    System.out.println("affichage id et mdp: "+idmdp[0]+ " "+idmdp[1]);

                    id=idmdp[0];
                    mdp=idmdp[1];

				/*for(int i=0; i<message_length_idMdp; i++)
				{
					char c= buffer.get();
					if(c==' ')
					{
						chx=1;
					}
					else if(chx==0) id+=c;
					else mdp+=c;
				}*/
                    System.out.println(" Identifiant "+id+" Mdp "+mdp);

                    System.out.println("2nd step"); /** Fonctionne **/

                    /** on vérifie dans la bdd la correspondance identifiant-mdp **/

                    /** On renvoie maintenant si le MDP est correct ou non  **/
                    //out = new PrintWriter(socketduserveur.getOutputStream());
                    if(id.trim().equals("Sylvinho09") && mdp.trim().equals("blabla"))
                        out.println("Yes");
                    else out.println("No"); // Yes or no
                    out.flush();
                    System.out.println("3rd step");

                    socketduserveur.close();

                    socketserver.close();
                }

                /** Le client veut s'inscrire **/

                else if(extract.trim().equals("Create"))
                {
                    System.out.println("Le client veut créer un compte !");
                    Formulaire form= new Formulaire();
                    CharBuffer formbuffer = CharBuffer.allocate(180); //on limite chaque élément à 30 caractères
                    int message_lengthCreate = in.read(formbuffer);
                    formbuffer.rewind();

                    System.out.println("données recues: "+formbuffer.toString().trim());
                    //Obtient un tableau avec à chaque case une donnée saisie
                    String[] datas= formbuffer.toString().trim().split("\\s+");
                    form.prenom=datas[0].trim();
                    form.nom=datas[1].trim();
                    form.age=datas[2].trim();
                    form.ville=datas[3].trim();
                    form.identifiant=datas[4].trim();
                    form.mdp=datas[5].trim();

                    System.out.println("données reçues :"+ form.toString());

                    //On ajoute maintenant ces données dans la base de données

                }
            }

        } catch (IOException e) {

            e.printStackTrace();

        }

    }
}
