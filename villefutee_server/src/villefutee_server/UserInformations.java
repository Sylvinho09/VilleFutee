package villefutee_server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Vector;

public class UserInformations {
	
	//L'identifiant sera gardé lors de la co, pas besoin de l'envoyer 
	private String nom;
	private String prenom;
	private String dateCompte;
	private String age;
	private String ville;
	private String politique_notif;
	private Hashtable<Integer, Vector<String>> liste_reseaux; //nom réseau en clé, ville et autres infos dans le vecteur
	private Vector<String> categories_pref;

	
	//Clé= catégorie, Vector<String> = toutes les notifs
	private Hashtable <String, Vector<String>> notif_by_categ = new Hashtable<String, Vector<String>>();

	public UserInformations() {
		
		this.nom = null;
		this.prenom = null;
		this.dateCompte = null;
		this.age = null;
		this.ville=null;
		this.politique_notif = null;
		this.liste_reseaux = null;
		this.categories_pref = null;
		this.notif_by_categ = null;
		
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public void setDateCompte(String dateCompte) {
		this.dateCompte = dateCompte;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public void setPolitique_notif(String politique_notif) {
		this.politique_notif = politique_notif;
	}

	public void setListe_reseaux(Hashtable<Integer, Vector<String>> liste_reseaux) {
		this.liste_reseaux = liste_reseaux;
	}

	public void setCategories_pref(Vector<String> categories_pref) {
		this.categories_pref = categories_pref;
	}

	public void setNotif_by_categ(Hashtable<String, Vector<String>> notif_by_categ) {
		this.notif_by_categ = notif_by_categ;
	}
	
	

	}
	
	

	
	
