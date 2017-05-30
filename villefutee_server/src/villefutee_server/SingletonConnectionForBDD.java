package villefutee_server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class SingletonConnectionForBDD {

	private static SingletonConnectionForBDD INSTANCE = null;
	private static Connection connexion = null;

	/******* Méthode de test connexion BDD *******/
	private SingletonConnectionForBDD() {
		/* Chargement du driver JDBC pour MySQL */
		try {
			Class.forName("com.mysql.jdbc.Driver");

		} catch (ClassNotFoundException e) {
			System.out.println("erreur driver ou connextion bdd");
			e.printStackTrace();
			/* G�rer les �ventuelles erreurs ici. */
		}
	}

	public static SingletonConnectionForBDD getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SingletonConnectionForBDD();
		}
		return INSTANCE;
	}

	public static void OuvertureConnexion(String url, String utilisateur, String motDePasse) {
		try {
			connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("erreur sql");
		}
	}

	public static void FermetureConnexion() {
		if (connexion != null)
			try {
				/* Fermeture de la connexion */
				connexion.close();
			} catch (SQLException ignore) {
				System.out.println("erreur close");
				/*
				 * Si une erreur survient lors de la fermeture, il suffit de
				 * l'ignorer.
				 */
			}

	}

	private String FormatToday() {
		Date date = new Date(); // your date
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		month++; // les mois commencent à 0
		return year + "-" + month + "-" + day;

	}

	public void initializeCategories()
	{
		try {
			Statement statement = connexion.createStatement();
			statement.executeUpdate("Insert into categorie (name) VALUES ('Multimédia'),('Pharmacie'),('Supermarché'),('Automobile');");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
	/******* Validation des coordonnées rentrées pas l'utilisateur *******/
	/**
	 * 
	 * @param identifiant
	 * @param mdp
	 * @return 1 si les identifiant sont bons, 0 si c'est faux ,-1 si la
	 *         connexion n'a pas �t� faite, -2 si il y a une erreur sql
	 */

	public int LoginPasswordValidation(int clientouco, String identifiant, String mdp) {
		int retour = 0;
		if (connexion == null) {
			return -1;
		}
		try {
			/* Cr�ation de l'objet g�rant les requ�tes */
			Statement statement = connexion.createStatement();
			/* Ex�cution d'une requ�te de lecture */
			if(clientouco==0)
			{
			ResultSet resultat = statement
					.executeQuery("SELECT *  FROM Utilisateur, Personne where Utilisateur.identifiant=Personne.identifiant AND Utilisateur.identifiant='" + identifiant + "' ;");

			/* R�cup�ration des donn�es du r�sultat de la requ�te de lecture */
			while (resultat.next()) {
				String idUtilisateur = resultat.getString("identifiant");
				String mdp2 = resultat.getString("mdp");
				System.out.println("Utilisateur " + idUtilisateur + " " + mdp);
				if (idUtilisateur.trim().equals(identifiant.trim()) && mdp2.trim().equals(mdp.trim())) {
					return 1;
				}
			}
			return 0;
			}
			else
			{
				ResultSet resultat = statement
						.executeQuery("SELECT *  FROM Utilisateur, Commercant where Utilisateur.identifiant=Commercant.identifiant AND Utilisateur.identifiant='" + identifiant + "' ;");

				/* R�cup�ration des donn�es du r�sultat de la requ�te de lecture */
				while (resultat.next()) {
					String idUtilisateur = resultat.getString("identifiant");
					String mdp2 = resultat.getString("mdp");
					System.out.println("Utilisateur " + idUtilisateur + " " + mdp);
					if (idUtilisateur.trim().equals(identifiant.trim()) && mdp2.trim().equals(mdp.trim())) {
						return 1;
					}
				}
				return -1;
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("erreur sql");
			return -2;
			/* G�rer les �ventuelles erreurs ici */
		}
	}

	/******* Incription d'un client *******/
	/**
	 * 
	 * @param prenom
	 * @param nom
	 * @param age
	 * @param ville
	 * @param identifiant
	 * @param mdp
	 * @return -1 s'il y a une erreur
	 */
	public int ajoutClient(String prenom, String nom, String age, String ville, String identifiant, String mdp) {
		try {
			/* Cr�ation de l'objet g�rant les requ�tes */
			Statement statement = connexion.createStatement();
			/* Ex�cution d'une requ�te de lecture */
			int resultat;
			System.out.println("avant le 1er do while");

			/*
			 * Date date= new Date(); String stringDate=
			 * date.getYear()+"-"+date.getMonth()+"-"+date.getDay();
			 */

			String stringDate = FormatToday();
			if ((resultat = statement.executeUpdate("INSERT INTO Utilisateur (identifiant, mdp, Ville, DateCompte)"
					+ "VALUES ('" + identifiant + "','" + mdp + "','" + ville + "','" + stringDate + "');")) == 0) {
				return resultat;
			}

			// "SELECT * FROM Utilisateur where identifiant='"+identifiant+"';"
			// );
			System.out.println("Utilisateur ajouté avec succès.");
			if ((resultat = statement
					.executeUpdate("INSERT INTO Personne(Age, identifiant, nom, prenom, Politique_notifs)" + "VALUES ('"
							+ Integer.parseInt(age) + "','" + identifiant + "','" + nom + "','" + prenom
							+ "','position');")) == 0)// on choisit ville de base
			{
				return resultat;
			}
			
			if ((resultat = statement
					.executeUpdate("INSERT INTO Personne_has_categorie(Personne_identifiant, categorie_name) VALUES ('"
							+ identifiant+"','Automobile'),('"
							+ identifiant+"','Multimédia'),('"
							+ identifiant+"','Pharmacie'),('"
							+ identifiant+"','Supermarché');")) == 0)// on choisit ville de base
			{
				return resultat;
			}
			
			System.out.println("Personne ajoutée avec succès.");
			return 1;

		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println("Identifiant utilisateur existe déjà.");
			e.printStackTrace();

			return 2;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("erreur sql");
			return 3;
			/* G�rer les �ventuelles erreurs ici */
		}
	}

	public int ajoutCommercant(Vector<String> adresse, String nom, Vector<String> domaines, String ville,
			String identifiant, String mdp, double latitude, double longitude) {
		try {
			/* Cr�ation de l'objet g�rant les requ�tes */
			Statement statement = connexion.createStatement();
			/* Ex�cution d'une requ�te de lecture */

			System.out.println("avant le 1er do while");

			/*
			 * Date date= new Date(); String stringDate=
			 * date.getYear()+"-"+date.getMonth()+"-"+date.getDay();
			 */

			String stringDate = FormatToday();

			if ((statement.executeUpdate("INSERT INTO Utilisateur (identifiant, mdp, Ville, DateCompte)" + "VALUES ('"
					+ identifiant + "','" + mdp + "','" + ville + "','" + stringDate + "');")) == 0) {
				return 0;
			}

			String stringAdresse = "";
			for (String el : adresse) {
				stringAdresse += " " + el;
			}
			if ((statement.executeUpdate("INSERT INTO Commercant(identifiant, nom_Magasin, Pays, Adresse, latitude, longitude)" + "VALUES ('"
					+ identifiant + "','" + nom + "','France','" + stringAdresse.trim() + "','"+latitude+"','"+longitude+"');")) == 0)// on
			// choisit
			// ville
			// de
			// base
			{
				return 0;
			}
			for (String dom : domaines) {
				String nomCate = "";
				ResultSet result = statement.executeQuery("Select name from categorie WHERE name='" + dom + "';");
				while (result.next()) {
					nom = result.getString("name");
				}
				if ((statement
						.executeUpdate("INSERT INTO Commercant_has_categorie(Commercant_identifiant, categorie_name)"
								+ "VALUES ('" + identifiant + "','" + nom + "');")) == 0) {
					return 0;
				}
			}

			System.out.println("Personne ajoutée avec succès.");
		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println("Identifiant déjà existant");
			e.printStackTrace();
			return 2;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("erreur sql");
			return 3;
			/* G�rer les �ventuelles erreurs ici */
		}
		return 1;
	}

	

	
	/*******
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * Retourner les infos relatives au client ou commercant quand il se
	 * connecte
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 *******/

	public UserInformations getInfos(int ClientOuCommercant, String identifiant) {

		String retour = "No";
		try {

			Statement statement = connexion.createStatement();
			Statement statement2 = connexion.createStatement();

			if (ClientOuCommercant == 0) // C'est une client qui veut ses infos
			{
				ClientInformations clientInf = new ClientInformations();
				System.out.println("je suis dans la fonction");
				/* Cr�ation de l'objet g�rant les requ�tes */

				/* Ex�cution d'une requ�te de lecture */
				ResultSet resultat = statement.executeQuery(
						"SELECT ville, DateCompte  FROM Utilisateur where identifiant='" + identifiant + "';");

				/*
				 * R�cup�ration des donn�es du r�sultat de la requ�te de lecture
				 */
				while (resultat.next()) {

					clientInf.setVille(resultat.getString("ville"));
					clientInf.setDateCompte(resultat.getDate("DateCompte").toString());

				}

				resultat = statement
						.executeQuery("SELECT Age, nom, prenom, Politique_notifs  FROM Personne where identifiant='"
								+ identifiant + "';");

				/*
				 * R�cup�ration des donn�es du r�sultat de la requ�te de lecture
				 */
				while (resultat.next()) {

					clientInf.setAge(resultat.getString("Age"));
					clientInf.setNom(resultat.getString("nom"));
					clientInf.setPrenom(resultat.getString("prenom"));
					clientInf.setPolitique_notif(resultat.getString("Politique_notifs"));

				}

				Vector<String> categ = new Vector<String>();
				resultat = statement
						.executeQuery("SELECT categorie_name  FROM Personne_has_categorie where Personne_identifiant='"
								+ identifiant + "';");

				/*
				 * R�cup�ration des donn�es du r�sultat de la requ�te de lecture
				 */
				while (resultat.next()) {
					/*
					 * ResultSet resultat2 = statement.
					 * executeQuery("Select name FROM categorie where categorie_id='"
					 * +resultat.getInt("categorie_categorie_id")+"';");
					 * categ.add(resultat2.getString("name"));
					 */
					categ.add(resultat.getString("categorie_name"));

				}
				clientInf.setCategories(categ);

				/*Hashtable<Integer, Vector<String>> reseaux_list = new Hashtable<Integer, Vector<String>>();
				// Vector<Vector<String>> reseaux = new
				// Vector<Vector<String>>();

				resultat = statement.executeQuery(
						"SELECT Reseau_idReseau FROM Utilisateur_has_Reseau where Utilisateur_identifiant='"
								+ identifiant + "';");*/

				/*
				 * R�cup�ration des donn�es du r�sultat de la requ�te de lecture
				 */
				/*while (resultat.next()) {
					Vector<String> reseau = new Vector<String>();
					ResultSet resultat2 = statement2.executeQuery(
							"SELECT idReseau, Nom_Reseaux, Description, Ville, Politique_join FROM Reseau WHERE idReseau='"
									+ resultat.getInt("Reseau_idReseau") + "';");
					int idReseau = resultat2.getInt("idReseau");
					String nom_reseau = resultat2.getString("Nom_Reseaux");
					String description = resultat2.getString("Description");
					String ville = resultat2.getString("Ville");
					String politique_join = resultat2.getString("Politique_join");
					reseau.addElement(description);
					reseau.addElement(ville);
					reseau.addElement(politique_join);

					reseaux_list.put(idReseau, reseau);
				}

				clientInf.setListe_reseaux(reseaux_list);*/

				Hashtable<String, Vector<Vector<String>>> notif_by_categ = new Hashtable<String, Vector<Vector<String>>>();
				Vector<Vector<String>> oneCategNotif = new Vector<Vector<String>>();

				resultat = statement.executeQuery(
						"SELECT Commercant_identifiant, Date, Texte, multimedia, Reseau_idReseau, categorie_name from Personne_has_notification,"
								+ "Notification WHERE Personne_identifiant ='" + identifiant
								+ "' and Notification_id_notif=id_notif");

				while (resultat.next()) {
					Vector<String> notif = new Vector<String>();

					ResultSet resultat2 = statement2.executeQuery(
							"SELECT nom_magasin, Adresse from Commercant, Notification WHERE identifiant='"
									+ resultat.getString("Commercant_identifiant") + "';");
					
					while(resultat2.next())
					{
					notif.add(resultat2.getString("nom_magasin"));
					notif.addElement(resultat2.getString("Adresse"));
					}

					notif.add(resultat.getDate("Date").toString());

					notif.add(resultat.getString("Texte"));

					notif.add(resultat.getString("multimedia"));

					resultat2 = statement2
							.executeQuery("SELECT Nom_Reseaux, Description, Ville from Reseau WHERE idReseau='"
									+ resultat.getInt("Reseau_idReseau") + "';");
					while(resultat2.next())
					{
					notif.add(resultat2.getString("Nom_Reseaux"));
					notif.add(resultat2.getString("Description"));
					notif.add(resultat2.getString("Ville"));
					}

					String cat = resultat.getString("categorie_name");
					if (notif_by_categ.get(cat) == null) {
						oneCategNotif.add(notif);
						notif_by_categ.put(cat, oneCategNotif);
					}

					else
						notif_by_categ.get(cat).add(notif);

				}

				clientInf.setNotif_by_categ(notif_by_categ);
				return clientInf;

			} else {   /** si c'est un commercant **/

				System.out.println("je suis dans get infos commercant");
				CommercantInformations comInf = new CommercantInformations();
				ResultSet resultat = statement.executeQuery(
						"SELECT ville, DateCompte  FROM Utilisateur where identifiant='" + identifiant + "';");

				/*
				 * R�cup�ration des donn�es du r�sultat de la requ�te de lecture
				 */
				while (resultat.next()) {

					comInf.setVille(resultat.getString("ville"));
					comInf.setDateCompte(resultat.getDate("DateCompte").toString());

				}
				
				/**Récupérations infos dans table commercant **/

				resultat = statement
						.executeQuery("SELECT nom_Magasin, Adresse  FROM Commercant where identifiant='"
								+ identifiant + "';");
				
				while(resultat.next())
				{
				comInf.setNom_magasin(resultat.getString("nom_Magasin"));
				comInf.setAdresse(resultat.getString("Adresse"));
				}
				
				
				Vector<String> categ = new Vector<String>();
				resultat = statement
						.executeQuery("SELECT categorie_name  FROM Commercant_has_categorie where Commercant_identifiant='"
								+ identifiant + "';");

				/*
				 * R�cup�ration des donn�es du r�sultat de la requ�te de lecture
				 */
				while (resultat.next()) {
					
					
					categ.add(resultat.getString("categorie_name"));
					comInf.setCategories(categ);

				}
				
				/*Hashtable<Integer, Vector<String>> reseaux_list = new Hashtable<Integer, Vector<String>>();
				// Vector<Vector<String>> reseaux = new
				// Vector<Vector<String>>();

				resultat = statement.executeQuery(
						"SELECT Reseau_idReseau FROM Utilisateur_has_Reseau where Utilisateur_identifiant='"
								+ identifiant + "';");
*/
				/*
				 * R�cup�ration des donn�es du r�sultat de la requ�te de lecture
				 */
				/*while (resultat.next()) {
					Vector<String> reseau = new Vector<String>();
					ResultSet resultat2 = statement2.executeQuery(
							"SELECT idReseau, Nom_Reseaux, Description, Ville, Politique_join FROM Reseau WHERE idReseau='"
									+ resultat.getInt("Reseau_idReseau") + "';");
					int idReseau = resultat2.getInt("idReseau");
					String nom_reseau = resultat2.getString("Nom_Reseaux");
					String description = resultat2.getString("Description");
					String ville = resultat2.getString("Ville");
					String politique_join = resultat2.getString("Politique_join");
					reseau.addElement(description);
					reseau.addElement(ville);
					reseau.addElement(politique_join);

					reseaux_list.put(idReseau, reseau);
				}
				comInf.setListe_reseaux(reseaux_list);*/
				
				/******* On récupère maintenant les notifications envoyées par le commercant ********/
				
				Hashtable<String, Vector<Vector<String>>> notif_by_categ = new Hashtable<String, Vector<Vector<String>>>();
				Vector<Vector<String>> oneCategNotif = new Vector<Vector<String>>();

				
				resultat = statement.executeQuery(
						"Select id_notif, Date, Texte, multimedia, DestinataireNotifs, Reseau_idReseau, categorie_name"
						+ " FROM  Notification WHERE Commercant_identifiant='"+identifiant+"';");
				

				while (resultat.next()) {
					Vector<String> notif = new Vector<String>();

					

					notif.add(resultat.getDate("Date").toString());

					notif.add(resultat.getString("Texte"));

					notif.add(resultat.getString("multimedia"));

					ResultSet resultat2 = statement2
							.executeQuery("SELECT Nom_Reseaux, Description, Ville from Reseau WHERE idReseau='"
									+ resultat.getInt("Reseau_idReseau") + "';");
					while(resultat2.next())
					{
					notif.add(resultat2.getString("Nom_Reseaux"));
					notif.add(resultat2.getString("Description"));
					notif.add(resultat2.getString("Ville"));
					}

					String cat = resultat.getString("categorie_name");
					if (notif_by_categ.get(cat) == null) {
						oneCategNotif.add(notif);
						notif_by_categ.put(cat, oneCategNotif);
					}

					else
						notif_by_categ.get(cat).add(notif);

				}

				comInf.setNotif_by_categ(notif_by_categ);
				return comInf;
				
				
				
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("erreur sql");
			return null;

			/* G�rer les �ventuelles erreurs ici */
		} /*finally {
			if (connexion != null)
				try {*/
					/* Fermeture de la connexion */
					/*connexion.close();
				} catch (SQLException ignore) {
					System.out.println("erreur close");
					/*
					 * Si une erreur survient lors de la fermeture, il suffit de
					 * l'ignorer.
					 */
				//}

		//}

	}

	public ArrayList<String> getNearestComs(double latitude, double longitude) {
		try {
			ArrayList<String> coms= new ArrayList<String>();
			double latMin= latitude-0.5;
			double latMax= latitude+0.5;
			double longMin= longitude-0.5;
			double longMax= longitude+0.5;

			Statement statement = connexion.createStatement();
			Statement statement2 = connexion.createStatement();
			
			ResultSet resultat= statement.executeQuery("SELECT identifiant, nom_magasin, Adresse, latitude, longitude FROM Commercant"
					+ " WHERE (latitude BETWEEN "+latMin+ "AND +" +latMax+") AND (longitude BETWEEN "+longMin+ " AND "+longMax+");");
			while(resultat.next())
			{
				coms.add(resultat.getString("nom_magasin"));
				coms.add(resultat.getString("Adresse"));
				String lat= String.valueOf(resultat.getDouble("latitude"));
				String lng = String.valueOf(resultat.getDouble("longitude"));
				ResultSet resultat2= statement2.executeQuery("SELECT categorie_name from Commercant_has_categorie WHERE Commercant_identifiant='"+resultat.getString("identifiant")+"';");
			String categs="";
			while(resultat2.next())
			{
				categs+=resultat2.getString("categorie_name")+" ";
			}
			coms.add(categs);
			coms.add(lat.trim());
			coms.add(lng.trim());

			}
			
			return coms;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public int sendNotifs(String idCom, String choix, String categorie, String texte) {
		
		try {
			Statement statement = connexion.createStatement();
			

			if(choix.equals("position".trim()))
			{
		System.out.println("Ajout de notifications dans la base de données pour les gens proches");
	
		
		String date= FormatToday();
			System.out.println("Insert into Notification(Commercant_identifiant, Date, Texte, DestinataireNotifs, categorie_name)"
				+ " VALUES ('"+idCom+"','"+date+"','"+texte+"','"+choix+"','"+categorie);
		if((statement.executeUpdate("Insert into Notification(Commercant_identifiant, Date, Texte, DestinataireNotifs, categorie_name)"
				+ " VALUES ('"+idCom+"','"+date+"','"+texte+"','"+choix+"','"+categorie+"');"))==0)
				{
			System.out.println("Rien n'a été ajouté");
			return 0; 
			
				}
		System.out.println("Notification ajoutée !");
		return 1;
		}
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		return 0;
	}

	public Vector<String> getNotifs(String id, double latitude, double longitude) {
		Vector<String> notifs = new Vector<String>();
		double latMin= latitude-0.5;
		double latMax= latitude+0.5;
		double longMin= longitude-0.5;
		double longMax= longitude+0.5;
		
		try {
			Statement statement = connexion.createStatement();
			Statement statement2 = connexion.createStatement();

			
			/** On va sélectionner toutes les notifications que le client a déja vues **/
			
			System.out.println("Select * FROM Notification, Commercant, Personne_has_Notification "
					+ "WHERE Personne_identifiant='"+id+"' AND Commercant_identifiant=identifiant;");
			
			ResultSet resultat = statement.executeQuery("Select * FROM Notification, Commercant, Personne_has_Notification "
					+ "WHERE Personne_identifiant='"+id+"' AND id_notif=Notification_id_notif AND Commercant_identifiant=identifiant;");
			
			while(resultat.next())
			{
				System.out.println("dans le next");
				String str=" ";
				str+=resultat.getString("nom_Magasin")+" ("+resultat.getString("Adresse")+") vous a envoyé une notification le "+resultat.getDate("Date").toString()+" !\n";
				str+=resultat.getString("Texte");
				notifs.add(str);
			}
			
			
			/** Sélectionner toutes les notifs que le client n'a pas encore vues**/
			//resultat = statement.executeQuery("Select * from Commercant");
		
			
				
				/** On sélectionne les notifications des commerces proches et pas encore dans Personne Has Notification et qui correspond aux catégpries préférées des clients**/
				 ResultSet resultat2= statement.executeQuery("Select nom_Magasin, Adresse, Date, Texte, id_notif FROM Commercant, Notification WHERE "
						+ "Commercant_identifiant=identifiant AND categorie_name IN (Select categorie_name FROM Personne_has_categorie "
								+ "WHERE Personne_identifiant='"+id+"') AND id_notif NOT IN (Select Notification_id_notif from Personne_has_Notification);");
				 while(resultat2.next())
				 {
					 String str="Nouvelle notification !\n";
					 str+=resultat2.getString("nom_Magasin")+" ("+resultat2.getString("Adresse")+") vous a envoyé une notification le "+resultat2.getDate("Date").toString()+" !\n";
						str+=resultat2.getString("Texte");
						notifs.add(str);
						
					statement2.executeUpdate("INSERT INTO Personne_has_Notification (Personne_identifiant, Notification_id_notif, Envoye) VALUES "
							+ "('"+id+"','"+resultat2.getString("id_notif")+"',1);");
					

				 }
				 
				 return notifs;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		return null;
	}
	
	public int ajoutReseau(String nomReseau, String categorie, String typeReseau, String ville,String idUser) {

        try {

            Statement statement = connexion.createStatement();

            if ((statement

                    .executeUpdate(

                            "INSERT INTO Reseau (Nom_reseaux, Description, Ville, Politique_join,categorie_name)"

                                    + "VALUES ('" + nomReseau + "','" + "Sans description" + "','" + ville + "','"

                                    + typeReseau + "','"+categorie+"');",

                            Statement.RETURN_GENERATED_KEYS)) == 0) {

                return 0;

            }

            System.out.println("Le reseazu a été ajouté a la bdd");

            ResultSet Rstest = statement.getGeneratedKeys();

            int idReseau = 0;

            

            while (Rstest.next()) {

                idReseau = Rstest.getInt("GENERATED_KEY");

                System.out.println("IdReseau " + idReseau);

            }

            System.out.println("IdUser "+idUser);

             if(AjoutUtilisateurReseau( idUser, idReseau)!= 1) {

                 return 0;

             }

             return 1;

        } catch (MySQLIntegrityConstraintViolationException e) {

            System.out.println("je suis ici");

            e.printStackTrace();

            return -2;

        } catch (SQLException e) {

            e.printStackTrace();

            System.out.println("erreur sql " + e);

            return -3;

        }

    } 

    public int AjoutUtilisateurReseau(String idUtilisateur, int idReseau) {

        try {

            Statement statement = connexion.createStatement();

            /* Ex�cution d'une requ�te de lecture */

            if ((statement.executeUpdate("INSERT INTO Utilisateur_has_Reseau (Utilisateur_identifiant, Reseau_IdReseau)"

                    + "VALUES ('" + idUtilisateur + "','" + idReseau + "');")) == 0) {

                return 0;

            }

            return 1;

        } catch (MySQLIntegrityConstraintViolationException e) {

            System.out.println("je suis ici");

            e.printStackTrace();

            return -2;

        } catch (SQLException e) {

            e.printStackTrace();

            System.out.println("erreur sql " + e);

            return -3;

            /* G�rer les �ventuelles erreurs ici */

        }

    }

    

    public Vector<String> getReseaux(String idUtilisateur) {

        try {

            Statement statement = connexion.createStatement();

            /* Ex�cution d'une requ�te de lecture */

            ResultSet resultat = statement.executeQuery("Select idReseau,Nom_Reseaux,Description,Politique_join FROM Reseau, utilisateur_has_reseau "

                    + "WHERE idReseau= Reseau_idReseau AND Utilisateur_identifiant='"+idUtilisateur+"';");

            Vector<String> vec= new Vector<String>();

            while(resultat.next())

            {

                String str=" ";

                str+=resultat.getInt("idReseau") +" "+resultat.getString("Nom_Reseaux")+" "+resultat.getString("Description")+" "+resultat.getString("Politique_join");

                System.out.println(str);

                vec.add(str);

            }

            return vec;

            

        } catch (MySQLIntegrityConstraintViolationException e) {

            System.out.println("je suis ici");

            e.printStackTrace();

            return null;

        } catch (SQLException e) {

            e.printStackTrace();

            System.out.println("erreur sql " + e);

            return null;

            /* G�rer les �ventuelles erreurs ici */

        }

    }

}