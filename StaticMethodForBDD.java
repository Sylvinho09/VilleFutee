package SocketAndroid;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class StaticMethodForBDD {

	/******* Méthode de test connexion BDD *******/

	public static void getPersonne(String identifiant) {
		/* Chargement du driver JDBC pour MySQL */
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("erreur driver");
			/* G�rer les �ventuelles erreurs ici. */
		}

		/* Connexion � la base de donn�es */
		String url = "jdbc:mysql://localhost:3306/mydb?useSSL=false";
		String utilisateur = "root";
		String motDePasse = "ffry6by6";
		Connection connexion = null;
		try {
			connexion = DriverManager.getConnection(url, utilisateur, motDePasse);

			/* Cr�ation de l'objet g�rant les requ�tes */
			Statement statement = connexion.createStatement();
			/* Ex�cution d'une requ�te de lecture */
			ResultSet resultat = statement
					.executeQuery("SELECT *  FROM Utilisateur where identifiant='" + identifiant + "';");

			/* R�cup�ration des donn�es du r�sultat de la requ�te de lecture */
			while (resultat.next()) {

				String idUtilisateur = resultat.getString("identifiant");
				String mdp = resultat.getString("mdp");
				String nom = resultat.getString("nom");
				String prenom = resultat.getString("prenom");
				String date = resultat.getString("DateCompte");

				System.out.println(
						"Utilisateur " + idUtilisateur + " " + mdp + " " + nom + " " + prenom + " " + date + " ");
			}

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

	}

	/******* Validation des coordonnées rentrées pas l'utilisateur *******/

	public static String LoginPasswordValidation(String identifiant, String mdp) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("erreur driver");
			/* G�rer les �ventuelles erreurs ici. */
		}

		/* Connexion � la base de donn�es */
		String url = "jdbc:mysql://localhost:3306/mydb?useSSL=false";
		String utilisateur = "root";
		String motDePasse = "ffry6by6";
		Connection connexion = null;
		String retour = "No";
		try {
			connexion = DriverManager.getConnection(url, utilisateur, motDePasse);

			/* Cr�ation de l'objet g�rant les requ�tes */
			Statement statement = connexion.createStatement();
			/* Ex�cution d'une requ�te de lecture */
			ResultSet resultat = statement
					.executeQuery("SELECT *  FROM Utilisateur where identifiant='" + identifiant + "';");

			/* R�cup�ration des donn�es du r�sultat de la requ�te de lecture */
			while (resultat.next()) {

				String idUtilisateur = resultat.getString("identifiant");
				String mdp2 = resultat.getString("mdp");
				if (idUtilisateur.trim().equals(identifiant) && mdp2.trim().equals(mdp)) {
					retour = "Yes";
				}

				System.out.println("Utilisateur " + idUtilisateur + " " + mdp);
			}
			// return retour;

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
		return retour.trim();

	}

	/******* Incription d'un client *******/

	public static String ajoutClient(String prenom, String nom, String age, String ville, String identifiant,
			String mdp) {
		String retour = "Error";
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("erreur driver");
			/* G�rer les �ventuelles erreurs ici. */
		}

		/* Connexion � la base de donn�es */
		String url = "jdbc:mysql://localhost:3306/mydb?useSSL=false";
		String utilisateur = "root";
		String motDePasse = "ffry6by6";
		Connection connexion = null;
		String retour1 = "Error";
		try {
			connexion = DriverManager.getConnection(url, utilisateur, motDePasse);

			/* Cr�ation de l'objet g�rant les requ�tes */
			Statement statement = connexion.createStatement();
			/* Ex�cution d'une requ�te de lecture */
			int resultat;
			System.out.println("avant le 1er do while");

			/*
			 * Date date= new Date(); String stringDate=
			 * date.getYear()+"-"+date.getMonth()+"-"+date.getDay();
			 */
			Date date = new Date(); // your date
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			int day = cal.get(Calendar.DAY_OF_MONTH);
			month++;// les mois commencent à 0
			String stringDate = year + "-" + month + "-" + day;

			if ((resultat = statement.executeUpdate("INSERT INTO Utilisateur (identifiant, mdp, Ville, DateCompte)"
					+ "VALUES ('" + identifiant + "','" + mdp + "','" + ville + "','" + stringDate + "');")) == 0) {
				return retour;
			}

			// "SELECT * FROM Utilisateur where identifiant='"+identifiant+"';"
			// );
			System.out.println("Utilisateur ajouté avec succès.");

			if ((resultat = statement
					.executeUpdate("INSERT INTO Personne(Age, identifiant, nom, prenom, Politique_notifs)" + "VALUES ('"
							+ Integer.parseInt(age) + "','" + identifiant + "','" + nom + "','" + prenom
							+ "','ville');")) == 0)// on choisit ville de base
			{
				return retour;
			}

			System.out.println("Personne ajoutée avec succès.");

			retour1 = "Ok"; // si le code arrive jusqu'ici, il n'y aura pas eu
							// d'exception

		} catch (MySQLIntegrityConstraintViolationException e) {
			retour1 = "ErrorName";
			e.printStackTrace();
		} catch (SQLException e) {
			retour1 = "Error";
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
		return retour1; // return "Ok" ou "Error"

	}

	public static String ajoutCommercant(Vector<String> adresse, String nom, Vector<String> domaines, String ville,
			String identifiant, String mdp) {
		String retour = "Error";
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("erreur driver");
			/* G�rer les �ventuelles erreurs ici. */
		}

		/* Connexion � la base de donn�es */
		String url = "jdbc:mysql://localhost:3306/mydb?useSSL=false";
		String utilisateur = "root";
		String motDePasse = "ffry6by6";
		Connection connexion = null;
		String retour1 = "Error";
		try {
			connexion = DriverManager.getConnection(url, utilisateur, motDePasse);

			/* Cr�ation de l'objet g�rant les requ�tes */
			Statement statement = connexion.createStatement();
			/* Ex�cution d'une requ�te de lecture */
			int resultat;
			System.out.println("avant le 1er do while");

			/*
			 * Date date= new Date(); String stringDate=
			 * date.getYear()+"-"+date.getMonth()+"-"+date.getDay();
			 */
			Date date = new Date(); // your date
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			int day = cal.get(Calendar.DAY_OF_MONTH);
			month++; // les mois commencent à 0
			String stringDate = year + "-" + month + "-" + day;

			if ((resultat = statement.executeUpdate("INSERT INTO Utilisateur (identifiant, mdp, Ville, DateCompte)"
					+ "VALUES ('" + identifiant + "','" + mdp + "','" + ville + "','" + stringDate + "');")) == 0) {
				return retour;
			}

			String stringAdresse = "";
			for (String el : adresse) {
				stringAdresse += " " + el;
			}
			if ((resultat = statement.executeUpdate("INSERT INTO Commercant(identifiant, nom_Magasin, Pays, Adresse)"
					+ "VALUES ('" + identifiant + "','" + nom + "','France','" + stringAdresse.trim() + "');")) == 0)// on
																														// choisit
																														// ville
																														// de
																														// base
			{
				return retour;
			}

			for (String dom : domaines) {
				int index = 0;
				ResultSet result = statement
						.executeQuery("Select categorie_id from categorie WHERE name='" + dom + "';");
				while (result.next()) {
					index = result.getInt("categorie_id");
				}
				if ((resultat = statement.executeUpdate(
						"INSERT INTO Commercant_has_categorie(Commercant_identifiant, categorie_categorie_id)"
								+ "VALUES ('" + identifiant + "','" + index + "');")) == 0) {
					return retour;
				}
			}

			System.out.println("Personne ajoutée avec succès.");
		} catch (MySQLIntegrityConstraintViolationException e) {
			System.out.println("je suis ici");
			e.printStackTrace();
			return "ErrorName";
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("erreur sql");
			return "Error";

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

		return "Ok";
	}
}
