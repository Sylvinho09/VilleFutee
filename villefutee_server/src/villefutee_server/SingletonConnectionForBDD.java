package villefutee_server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class SingletonConnectionForBDD {

	private static SingletonConnectionForBDD INSTANCE = null;
	private static Connection connexion=null;

	/******* Méthode de test connexion BDD *******/
	private SingletonConnectionForBDD(){
		/* Chargement du driver JDBC pour MySQL */
		try {
			Class.forName("com.mysql.jdbc.Driver");

		} catch (ClassNotFoundException e) {
			System.out.println("erreur driver ou connextion bdd");
			e.printStackTrace();
			/* G�rer les �ventuelles erreurs ici. */
		}
	}

	public static SingletonConnectionForBDD getInstance(){
		if (INSTANCE == null)
		{ 	INSTANCE = new SingletonConnectionForBDD();		
		}
		return INSTANCE;
	}

	public static void OuvertureConnexion(String url,String utilisateur, String motDePasse){
		try {
			connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
		}catch ( SQLException e ) {
			e.printStackTrace();
			System.out.println("erreur sql");  
		}	
	}

	public static void FermetureConnexion(){
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

	private String FormatToday(){
		Date date = new Date(); // your date
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		month++; // les mois commencent à 0
		return  year + "-" + month + "-" + day;


	}
	/******* Validation des coordonnées rentrées pas l'utilisateur *******/
	/**
	 * 
	 * @param identifiant
	 * @param mdp
	 * @return 1 si les identifiant sont bons, 0 si c'est faux ,-1 si la connexion n'a pas �t� faite, -2 si il y a une erreur sql
	 */

	public int LoginPasswordValidation(String identifiant, String mdp) {
		int retour = 0;
		if (connexion == null)
		{return -1; }
		try {
			/* Cr�ation de l'objet g�rant les requ�tes */
			Statement statement = connexion.createStatement();
			/* Ex�cution d'une requ�te de lecture */
			ResultSet resultat = statement
					.executeQuery("SELECT *  FROM Utilisateur where identifiant='" + identifiant + "';");

			/* R�cup�ration des donn�es du r�sultat de la requ�te de lecture */
			while (resultat.next()) {
				String idUtilisateur = resultat.getString("identifiant");
				String mdp2 = resultat.getString("mdp");
				System.out.println("Utilisateur " + idUtilisateur + " " + mdp);
				if (idUtilisateur.trim().equals(identifiant) && mdp2.trim().equals(mdp)) {
					return 1;
				}
			} 
			return 0;
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
	public  int ajoutClient(String prenom, String nom, String age, String ville, String identifiant,
			String mdp) {
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
							+ "','ville');")) == 0)// on choisit ville de base
			{
				return resultat;
			}
			System.out.println("Personne ajoutée avec succès.");
			return 1;

		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println("Identifiant utilisateur existe déjà.");
			e.printStackTrace();
			
			return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("erreur sql");
			return -2;
			/* G�rer les �ventuelles erreurs ici */
		} 
	}

	public  int ajoutCommercant(Vector<String> adresse, String nom, Vector<String> domaines, String ville,
			String identifiant, String mdp) {
		try {
			/* Cr�ation de l'objet g�rant les requ�tes */
			Statement statement = connexion.createStatement();
			/* Ex�cution d'une requ�te de lecture */

			System.out.println("avant le 1er do while");

			/*
			 * Date date= new Date(); String stringDate=
			 * date.getYear()+"-"+date.getMonth()+"-"+date.getDay();
			 */

			String stringDate =FormatToday();

			if (( statement.executeUpdate("INSERT INTO Utilisateur (identifiant, mdp, Ville, DateCompte)"
					+ "VALUES ('" + identifiant + "','" + mdp + "','" + ville + "','" + stringDate + "');")) == 0) {
				return 0;
			}

			String stringAdresse = "";
			for (String el : adresse) {
				stringAdresse += " " + el;
			}
			if ((statement.executeUpdate("INSERT INTO Commercant(identifiant, nom_Magasin, Pays, Adresse)"
					+ "VALUES ('" + identifiant + "','" + nom + "','France','" + stringAdresse.trim() + "');")) == 0)// on
				// choisit
				// ville
				// de
				// base
			{
				return 0;
			}
			for (String dom : domaines) {
				String nomCate="";
				ResultSet result = statement
						.executeQuery("Select name from categorie WHERE name='" + dom + "';");
				while (result.next()) {
					nom = result.getString("name");
				}
				if ((statement.executeUpdate(
						"INSERT INTO Commercant_has_categorie(Commercant_identifiant, categorie_name)"
								+ "VALUES ('" + identifiant + "','" + nomCate + "');")) == 0) {
					return 0;
				}
			}

			System.out.println("Personne ajoutée avec succès.");
		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println("Identifiant déjà existant");
			e.printStackTrace();
			return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("erreur sql");
			return -2;
			/* G�rer les �ventuelles erreurs ici */
		} 
		return 1;
	}

	/**
	 * 
	 * @param nomReseaux
	 * @param Description
	 * @param Ville
	 * @param PolitiqueJoin
	 * @param idUtilisateurChef
	 * @return 1 si le reseaux � �t� ajouter 0,si une erreur,-1 si erreur Attribut PolitiqueJoin,-2 Si MySQLIntegrityConstraintViolationException,-3 si SQLException
	 */
	public int AddReseaux(String nomReseaux,String Description,String Ville,String PolitiqueJoin,String idUtilisateurChef,Vector<String> Categorie){
		if(PolitiqueJoin.equals("all")||PolitiqueJoin.equals("ask")||PolitiqueJoin.equals("invit_only")){
			try {
				Statement statement = connexion.createStatement();
				/* Ex�cution d'une requ�te de lecture */

				if (( statement.executeUpdate("INSERT INTO Reseau (Nom_reseaux, Description, Ville, Politique_join,Utilisateur_chef)"
						+ "VALUES ('" + nomReseaux + "','" + Description + "','" + Ville + "','" + PolitiqueJoin + "','"+idUtilisateurChef+"');",Statement.RETURN_GENERATED_KEYS)) == 0) {
					return 0;
				}
				ResultSet Rstest= statement.getGeneratedKeys();
				int idReseau =0;
				while (Rstest.next()) {
					idReseau = Rstest.getInt("GENERATED_KEY");
					System.out.println("IdReseau " + idReseau);
				}
				if (( statement.executeUpdate("INSERT INTO Utilisateur_has_Reseau (Utilisateur_identifiant, Reseau_IdReseau)"
						+ "VALUES ('" + idUtilisateurChef + "','"+idReseau+"');")) == 0) {
					return 0;
				}
				
				for(String Cat : Categorie){
					if (( statement.executeUpdate("INSERT INTO Reseau_has_Categorie (Reseau_IdReseau, categorie_name)"
							+ "VALUES ('" + idReseau + "','"+Cat+"');")) == 0) {
						return 0;
					}
				}
			}
			catch (MySQLIntegrityConstraintViolationException e) {
				System.out.println("je suis ici");
				e.printStackTrace();
				return -2;
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("erreur sql "+ e);
				return -3;

				/* G�rer les �ventuelles erreurs ici */
			} 
			return 1;
		}
		else return 0;
	}


	public int AjoutUtilisateurReseau(String idUtilisateur,int idReseau){
		try {
			Statement statement = connexion.createStatement();
			/* Ex�cution d'une requ�te de lecture */

			if (( statement.executeUpdate("INSERT INTO Utilisateur_has_Reseau (Utilisateur_identifiant, Reseau_IdReseau)"
					+ "VALUES ('" + idUtilisateur + "','"+idReseau+"');")) == 0) {
				return 0;
			}
			return 1;
		}
		catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println("je suis ici");
			e.printStackTrace();
			return -2;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("erreur sql "+ e);
			return -3;
			/* G�rer les �ventuelles erreurs ici */
		} 
	}
	
	
	
	
	
	
	
	
/******* Retourner les infos relatives au client ou commercant quand il se connecte *******/
	
	public UserInformations getInfos(int ClientOuCommercant, String identifiant)
	{
		
		UserInformations userInf = new UserInformations();
		
			String retour = "No";
			try {
		

				/* Cr�ation de l'objet g�rant les requ�tes */
				Statement statement = connexion.createStatement();
				/* Ex�cution d'une requ�te de lecture */
				ResultSet resultat = statement
						.executeQuery("SELECT ville, DateCompte  FROM Utilisateur where identifiant='" + identifiant + "';");

				/* R�cup�ration des donn�es du r�sultat de la requ�te de lecture */
				while (resultat.next()) {

					
					userInf.setVille(resultat.getString("ville"));
					userInf.setDateCompte(resultat.getDate("DateCompte").toString());
					

				}
				
				resultat = statement
						.executeQuery("SELECT Age, nom, prenom, Politique_notifs  FROM Personne where identifiant='" + identifiant + "';");

				/* R�cup�ration des donn�es du r�sultat de la requ�te de lecture */
				while (resultat.next()) {

				userInf.setAge(resultat.getString("Age"));
				userInf.setNom(resultat.getString("nom"));
				userInf.setPrenom(resultat.getString("prenom"));
				userInf.setPolitique_notif(resultat.getString("Politique_notifs"));
					

				}
				
				Vector<String> categ= new Vector<String>();
				resultat = statement
						.executeQuery("SELECT categorie_name  FROM Personne_has_categorie where Personne_identifiant='" + identifiant + "';");

				/* R�cup�ration des donn�es du r�sultat de la requ�te de lecture */
				while (resultat.next()) {
					/*ResultSet resultat2 = statement.executeQuery("Select name FROM categorie where categorie_id='"+resultat.getInt("categorie_categorie_id")+"';");
					categ.add(resultat2.getString("name"));*/
					categ.add(resultat.getString("categorie_name"));
					
					
				}
				userInf.setCategories_pref(categ);
				
				Hashtable<Integer, Vector<String>> reseaux_list= new Hashtable<Integer, Vector<String>>();
				//Vector<Vector<String>> reseaux = new Vector<Vector<String>>();
				
				resultat = statement
						.executeQuery("SELECT Reseau_idReseau FROM Utilisateur_has_Reseau where Utilisateur_identifiant='" + identifiant + "';");

				/* R�cup�ration des donn�es du r�sultat de la requ�te de lecture */
				while (resultat.next()) {
					Vector<String> reseau= new Vector<String>();
					ResultSet resultat2 = statement.executeQuery("SELECT idReseau, Nom_Reseaux, Description, Ville, Politique_join FROM Reseau WHERE idReseau='"+resultat.getInt("Reseau_idReseau")+"';");
					int idReseau= resultat2.getInt("idReseau");
					String nom_reseau= resultat2.getString("Nom_Reseaux");
					String description = resultat2.getString("Description");
					String ville= resultat2.getString("Ville");
					String politique_join= resultat2.getString("Politique_join");
					reseau.addElement(description);
					reseau.addElement(ville);
					reseau.addElement(politique_join);
					
					reseaux_list.put(idReseau, reseau);
				}
				
				userInf.setListe_reseaux(reseaux_list);	
				
				
			 Hashtable <String, Vector<Vector<String>>> notif_by_categ = new Hashtable<String, Vector<Vector<String>>>();
			 Vector<Vector<String>> oneCategNotif= new Vector<Vector<String>>();
			 
			 resultat = statement.executeQuery("SELECT Commercant_identifiant, Date, Texte, multimedia, Reseau_idReseau, categorie_name from Personne_has_notification,"
			 		+ "Notification WHERE Personne_identifiant ='"+identifiant+"' and Notification_id_notif=id_notif");
			 
			 while(resultat.next())
			 {
				 Vector<String> notif = new Vector<String>();
				 
				 ResultSet resultat2 = statement.executeQuery("SELECT nom_magasin, Adresse from Commercant, Notification WHERE identifiant='"+resultat.getString("identifiant")+"';");
				 notif.add(resultat2.getString("nom_magasin"));
				 notif.addElement(resultat2.getString("Adresse"));
				 
				 notif.add(resultat.getDate("Date").toString());
				 
				 notif.add(resultat.getString("Texte"));
				 
				 notif.add(resultat.getString("multimedia"));
				 
				 resultat2 = statement.executeQuery("SELECT Nom_Reseaux, Description, Ville from Reseau WHERE idReseau='"+resultat.getInt("Reseau_idReseau")+"';");
				 notif.add(resultat2.getString("Nom_Reseaux"));
				 notif.add(resultat2.getString("Description"));
				 notif.add(resultat2.getString("Ville"));
				 
				 String cat = resultat.getString("categorie_name");
				 if(notif_by_categ.get(cat)==null)
				 {
					 oneCategNotif.add(notif);
					 notif_by_categ.put(cat, oneCategNotif);
				 }
				
				 else notif_by_categ.get(cat).add(notif);
				 
				 
				
			 }

				
				/** ne pas oublier d'ajouter les cancel **/
				/** ajout maintenant de la liste des notifs**/
				
					
					
					
				

			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("erreur sql");

				/* G�rer les �ventuelles erreurs ici */
			} finally {
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
		
		return userInf;
	}
	
	
	
	
	
	
	
	
	
	
	
}