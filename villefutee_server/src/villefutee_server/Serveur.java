package villefutee_server;

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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Serveur {

	public static void getPersonne(String identifiant){
		/* Chargement du driver JDBC pour MySQL */
		try {
		    Class.forName( "com.mysql.jdbc.Driver" );
		} catch ( ClassNotFoundException e ) {
			System.out.println("erreur driver");
		    /* GÈrer les Èventuelles erreurs ici. */
		}
		
		/* Connexion ‡ la base de donnÈes */
		String url = "jdbc:mysql://localhost:3306/mybd";
		String utilisateur = "root";
		String motDePasse = "w223fgh52ty001jq";
		Connection connexion = null;
		try {
		    connexion = DriverManager.getConnection( url, utilisateur, motDePasse );

		    /* CrÈation de l'objet gÈrant les requÍtes */
		    Statement statement = connexion.createStatement();
		    /* ExÈcution d'une requÍte de lecture */
		    ResultSet resultat = statement.executeQuery( "SELECT *  FROM Utilisateur;" );
		    
		    /* RÈcupÈration des donnÈes du rÈsultat de la requÍte de lecture */
		    while ( resultat.next() ) {
		        int idUtilisateur = resultat.getInt( "id" );
		        String mdp = resultat.getString( "mdp" );
		        String nom = resultat.getString( "nom" );
		        String prenom = resultat.getString( "prenom" );
		        String date = resultat.getString( "DateCompte" );

		        System.out.println("Utilisateur "+idUtilisateur+" "+mdp+" "+nom+" "+prenom+" "+date+" ");
		    }

		} catch ( SQLException e ) {

			System.out.println("erreur sql");
		    /* GÈrer les Èventuelles erreurs ici */
		} finally {
		    if ( connexion != null )
		        try {
		            /* Fermeture de la connexion */
		            connexion.close();
		        } catch ( SQLException ignore ) {
					System.out.println("erreur close");
		            /* Si une erreur survient lors de la fermeture, il suffit de l'ignorer. */
		        }
		}
		
		
	}
	public static void main(String[] zero) {
		Serveur.getPersonne("test");
        ServerSocket socketserver;
        Socket socketduserveur;

        try {

            socketserver = new ServerSocket(); // ne pas sp√©cifier de port car
            // sinon le bind √©choue

            socketserver.bind(new InetSocketAddress(InetAddress.getLocalHost().getHostName(), 8050));

            System.out.println("Le serveur est √† l'√©coute du port " + socketserver.getLocalPort() + " √† l'adresse IP "
                    + socketserver.getInetAddress().getHostAddress());

            while (true) {
                socketduserveur = socketserver.accept();

                System.out.println("Un client s'est connect√©");
                PrintWriter out = new PrintWriter(socketduserveur.getOutputStream());

                BufferedReader in = new BufferedReader(new InputStreamReader(socketduserveur.getInputStream()));

                /**
                 * On va maintenant se mettre en r√©ception pour savoir ce que
                 * veut le client
                 **/
                System.out.println("je me mets en r√©ception de la demande");

                char[] recep = new char[7]; /**idMdp, Create, Get...*/

                int message_length = in.read(recep);

                System.out.println(message_length);

                String extract = new String(recep);

                System.out.println("Message re√ßu du client : " + extract);

                if (extract.trim().equals("idMdp")) {
                    System.out.println("dans le equals");
                    char[] buffer = new char[60];

					/*
					 * IMPORTANT : ne pas red√©clarer la ligne en dessous, en
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
                    // cas o√π on doit recevoir un entier
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
                     * on v√©rifie dans la bdd la correspondance identifiant-mdp
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

                    System.out.println("Le client veut cr√©er un compte !");
                    Formulaire form = new Formulaire();

                    /**
                     * do while obligatoire sinon si on veut refaire une 2eme
                     * inscription d'affil√©e, le read renvoie 1 je ne sais pas
                     * pourquoi
                     */
                    int message_lengthCreate = 0;
                    do {
                        message_lengthCreate = in.read(formbuffer, 0, 180);

                    } while (message_lengthCreate == 1);
                    System.out.println("valeur read " + message_lengthCreate);
                    String valueForm = new String(formbuffer);

                    System.out.println("donn√©es recues: " + valueForm.trim());

                    // Obtient un tableau avec √† chaque case une donn√©e saisie
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
                        System.out.println("donn√©es re√ßues :" + form.toString());

                        out.println("Ok");
                        out.flush();

                        // On ajoute maintenant ces donn√©es dans la base de
                        // donn√©es
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("dans le catch");
                        out.println("Error");
                        out.flush();
                    }

                }
                else if(extract.trim().equals("CCreate"))
                {
                    System.out.println("message re√ßu du commer√ßant qui veut cr√©er un compte");
                    char[] formbufferCommercant = new char[210];

                    Formulaire form = new Formulaire();

                    /**
                     * do while obligatoire sinon si on veut refaire une 2eme
                     * inscription d'affil√©e, le read renvoie 1 je ne sais pas
                     * pourquoi
                     */
                    int message_lengthCreate = 0;
                    do {
                        message_lengthCreate = in.read(formbufferCommercant, 0, 210);
                        System.out.println("ici");

                    } while (message_lengthCreate == 1);
                    System.out.println("valeur read " + message_lengthCreate);
                    String valueForm = new String(formbufferCommercant);

                    System.out.println("donn√©es recues: " + valueForm.trim());

                    // Obtient un tableau avec √† chaque case une donn√©e saisie
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


                        out.println("Ok");
                        out.flush();

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
