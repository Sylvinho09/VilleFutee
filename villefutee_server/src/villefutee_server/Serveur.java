package villefutee_server;

/* Created by sylvinho on 12/04/2017.
 * A lancer autre part ***
 */
import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.io.PrintWriter;
import java.nio.CharBuffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Random;

public class Serveur {

	
	public static void main(String[] zero) {
		
        ServerSocket socketserver;
        Socket socketduserveur;
        SingletonConnectionForBDD ConnectionBDD=SingletonConnectionForBDD.getInstance();
       
         String url = "jdbc:mysql://localhost:3306/dbVilleFutee?useSSL=false";
        
    	String utilisateur = "root";
    	String motDePasse = "ffry6by6";/**/
    	/*String utilisateur = "Indianapaul";
    	String motDePasse = "w223fgh52ty001jq";/**/
    	Vector<String> Cat=new Vector<String>();
    	Cat.addElement("Automobile");Cat.addElement("Supermarch�");
        ConnectionBDD.OuvertureConnexion(url, utilisateur, motDePasse);
        //ConnectionBDD.ajoutClient("Paul", "dfs", "18", "m", "indianapaul", "mdp");
        ConnectionBDD.ajoutClient("Sylvain", "Utzel", "22", "Montpellier", "Sylvinho09", "ffry6by6");
        ConnectionBDD.LoginPasswordValidation(0, "Sylvinho09", "ffry6yb6");
        
        Vector<String> adresse = new Vector<String>();
        adresse.add("4C");adresse.add("avenue");adresse.add("de");adresse.add("la");adresse.add("Halte");
        Vector<String> adresse2 = new Vector<String>();
        adresse2.add("20");adresse2.add("Rue");adresse2.add("de");adresse2.add("Bône");
        Vector<String> adresse3 = new Vector<String>();
        adresse3.add("8C");adresse3.add("Impasse");adresse3.add("des");adresse3.add("Cerisiers");
        
        Vector<String> domaines = new Vector<String>();
        domaines.add("Supermarché"); domaines.addElement("Pharmacie");
        //mettre entre 42.8 et 43.2 et entre 1.4 et 1.8
        ConnectionBDD.ajoutCommercant(adresse, "Carrefour", domaines, "Montpellier", "Carrouf34", "ffry6by6", 43.8d, 3.9d);
        ConnectionBDD.ajoutCommercant(adresse2, "Carrefour", domaines, "Pamiers", "Carrouf09", "ffry6by6", 42.867d, 1.756d);
        ConnectionBDD.ajoutCommercant(adresse3, "Intermarche", domaines, "Verniolle", "Inter09", "ffry6by6", 43.127d, 1.486d);


        
       // ConnectionBDD.AddReseaux("testReseau", "Description", "Ville", "all", "indianapaul",Cat);
        try {
            socketserver = new ServerSocket(); // ne pas spécifier de port car
            // sinon le bind échoue

            socketserver.bind(new InetSocketAddress(InetAddress.getLocalHost().getHostName(), 8050));

            System.out.println("Le serveur est à l'écoute du port oui " + socketserver.getLocalPort() + " à l'adresse IP "
                    + socketserver.getInetAddress().getHostAddress());

            while (true) {
                socketduserveur = socketserver.accept();

                System.out.println("Un client s'est connecté");
               // PrintWriter out = new PrintWriter(socketduserveur.getOutputStream());
                ObjectOutputStream os = new ObjectOutputStream(socketduserveur.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(socketduserveur.getInputStream()));
               
               


                /**
                 * On va maintenant se mettre en réception pour savoir ce que
                 * veut le client
                 **/
                System.out.println("je me mets en réception de la demande");

                char[] recep = new char[8]; /**idMdp, Create, Get...*/

                int message_length = in.read(recep);

                System.out.println(message_length);

                String extract = new String(recep);

                System.out.println("Message reçu du client : " + extract);

                if (extract.trim().equals("idMdp")) {
                    System.out.println("dans le equals");
                    char[] buffer = new char[61];

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
                    int clientouco=-1;
                    String id = "";
                    String mdp = "";
                    
                    int chx = 0;
                    String valueCo = new String(buffer);

                    String[] idmdp = valueCo.toString().trim().split("\\s");

                    System.out.println("affichage clientouco et id et mdp: " + idmdp[0] + " " + idmdp[1]+" "+idmdp[2]);

                    clientouco= Integer.parseInt(idmdp[0]);
                    id = idmdp[1];
                    mdp = idmdp[2]; 

                    System.out.println(" Identifiant " + id + " Mdp " + mdp);

                    System.out.println("2nd step"); /** Fonctionne **/

                    /**
                     * on vérifie dans la bdd la correspondance identifiant-mdp
                     **/
                    
                    int reponse=ConnectionBDD.LoginPasswordValidation(clientouco, id, mdp);
                    os.writeObject(reponse);
                    os.flush();   
                    
                  

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

                        int resultat=ConnectionBDD.ajoutClient(form.prenom, form.nom, form.age, form.ville, form.identifiant, form.mdp);
                        os.writeObject(resultat); //ajoutClient retourne Ok ou Error;
                        os.flush();

                        // On ajoute maintenant ces données dans la base de
                        // données
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("dans le catch");
                        os.writeObject(-3);
                        os.flush();
                    }

                }
                else if(extract.trim().equals("CCreate"))
                {
                    System.out.println("message reçu du commerçant qui veut créer un compte");
                    char[] formbufferCommercant = new char[210];

                    Formulaire form = new Formulaire();

                    /**
                     * do while obligatoire sinon si on veut refaire une 2eme
                     * inscription d'affilée, le read renvoie 1 je ne sais pas
                     * pourquoi
                     */
                    
                    int message_lengthCreate = 0;
                    do {
                        message_lengthCreate = in.read(formbufferCommercant, 0, 210);
                        System.out.println("ici");

                    } while (message_lengthCreate == 1);
                    System.out.println("valeur read " + message_lengthCreate);
                    String valueForm = new String(formbufferCommercant);

                    System.out.println("données recues: " + valueForm.trim());

                    // Obtient un tableau avec à chaque case une donnée saisie
                    String[] datas = valueForm.toString().trim().split("\\s+");
                    System.out.println("avant le try");

					/*System.out.println("affichage commercant ");
					for(int i=0; i< datas.length; i++)
					{
						System.out.println( datas[i]+ " ");
					}*/
                    try
                    {
                        int tailleAdresse= Integer.parseInt(datas[0].trim());
                        int i;
                        for(i=1; i< 1+ tailleAdresse; i++)
                        {
                            form.adresseServeur.addElement(datas[i].trim());
                        }
                        System.out.println("affichage adresse: "+form.adresseServeur.toString());
                        //i++;
                        form.nom=datas[i].trim();
                        System.out.println("affichage nom: "+form.nom);

                        i++;
                        int tailleDomaine= Integer.parseInt(datas[i].trim());
                        System.out.println("affichage taille domaine: "+tailleDomaine);

                        i++;
                        int j;
                        System.out.println("au milieu");
                        for(j=i; j<i+tailleDomaine; j++)
                        {
                            form.domaines.addElement(datas[j].trim());
                        }
                        System.out.println("affichage domaines: "+form.domaines.toString());


                        form.ville=datas[j].trim();
                        System.out.println("affichage ville: "+form.ville);

                        form.identifiant=datas[j+1].trim();
                        System.out.println("affichage id: "+form.identifiant);

                        form.mdp=datas[j+2].trim();
                        System.out.println("affichage mdp: "+form.mdp);

                        Random fRandom = new Random();
                        double minLat=43.5d;
                        double maxLat=44d;
                        double minLong=3.5d;
                        double maxLong=4d;
                        double longitude;
                        double latitude;
                        Random rand = new Random();
                        
                        longitude = rand.nextDouble() * (maxLong - minLong) + minLong;
                        latitude = rand.nextDouble() * (maxLat - minLat) + minLat;
                        
                      System.out.println("valeurs latitude et longitude: "+latitude + " "+longitude);

                        int result = ConnectionBDD.ajoutCommercant(form.adresseServeur, form.nom, form.domaines, form.ville, form.identifiant, form.mdp, latitude, longitude);
                        System.out.println("Valeur de result :"+result);
                        os.writeObject(result);
                        os.flush();

                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("dans le catch");
                        os.writeObject(-3);
                        os.flush();
                    }



                }
                
                else if(extract.trim().equals("getInfos"))
                {
                	System.out.println("message reçu d'un utilisateur qui veut obtenir ses infos");
                	/*** commercant ou client en 1er, identifiant ensuite ***/
                    char[] userAsk = new char[35];
                    
                    int message_lengthCreate = 0;
                    do {
                        message_lengthCreate = in.read(userAsk, 0, 35);
                        System.out.println("ici");

                    } while (message_lengthCreate == 1);
                    System.out.println("valeur read " + message_lengthCreate);
                    String valueAsk = new String(userAsk);

                    System.out.println("données recues: " + valueAsk.trim());
                    String[] datas = valueAsk.toString().trim().split("\\s+");
                    ClientInformations infosClient;
                    CommercantInformations infosCom;
                    if(datas[0].trim().equals("0"))
                    {
                    	System.out.println("je suis dans le 0");
                   if((infosClient =(ClientInformations) ConnectionBDD.getInfos(0, datas[1].trim()))==null)
                   {
                	   os.writeObject("Error");
                   }
                   else
                   {
                	   
                	   
                	   
                	   	   os.writeObject(infosClient.getPrenom());
                	   	   os.flush();
                	   	   os.writeObject(infosClient.getNom());
                	   	   os.flush();
                	   	   os.writeObject(infosClient.getAge());
                	   	   os.flush();
                	   	
                    	   os.writeObject(infosClient.getDateCompte());
                    	   os.flush();
                    	   os.writeObject(infosClient.getVille());
                    	   os.flush();
                    	   os.writeObject(infosClient.getPolitique_notif());
                    	   os.flush();
                    	   
                    	   
                     	   /*ObjectOutputStream os = new ObjectOutputStream(socketduserveur.getOutputStream()); */
                     	   
                    	   
                    	   os.writeObject(infosClient.getCategories());
                    	   os.flush();
                    	   
                    	   os.writeObject(infosClient.getListe_reseaux());
                    	   os.flush();
                    	   os.writeObject(infosClient.getNotif_by_categ());
                    	   os.flush();
                    	   
                    	 
                    	   
                           
                   }
                    } 
                   else if((infosCom =(CommercantInformations)ConnectionBDD.getInfos(1, datas[1].trim()))==null)
                   {
                	   os.writeObject("Error");
                   }
                   
                  

                }
                else if(extract.trim().equals("ProxComs".trim()))
                {
                	char[] coordonnees= new char[50];

                    
                    int message_lengthCreate = 0;
                    do {
                        message_lengthCreate = in.read(coordonnees, 0, 50);
                        System.out.println("ici coordonnees");

                    } while (message_lengthCreate == 1);
                    System.out.println("valeur read " + message_lengthCreate);
                    String valueCo = new String(coordonnees);

                    System.out.println("données recues: " + valueCo.trim());
                    String[] datas = valueCo.toString().trim().split("\\s+");
                	
                    double latitude = Double.parseDouble(datas[0].trim());
                    double longitude = Double.parseDouble(datas[1].trim());
                    
                    //le premier read ne lit rien, le second lit les 2 valeurs???
                	/*in.read(latitude);
                	String slatitude= new String(latitude);
                	System.out.println("double recu: "+slatitude);
                	in.read(latitude);
                	slatitude= new String(latitude);
                	System.out.println("double recu: "+slatitude);
                	
                	double dlatitude = Double.parseDouble(slatitude);
                	
                	char[] longitude= new char[35];
                	
                	in.read(longitude);
                	String slongitude= new String(longitude);
                	double dlongitude = Double.parseDouble(slongitude);*/
                	
                	/** Utilisation de ArrayList car c'est ce qu'on passera dans le intent cote client**/
                	ArrayList<String> proxComs= ConnectionBDD.getNearestComs(latitude, longitude);
                	os.writeObject(proxComs);
                	
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