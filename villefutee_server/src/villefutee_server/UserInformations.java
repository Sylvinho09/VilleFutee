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
	private String dateCompte;
	
	private String ville;
	
	private Hashtable<Integer, Vector<String>> liste_reseaux; //nom réseau en clé, ville et autres infos dans le vecteur
	private Vector<String> categories;

	
	//Clé= catégorie, Vector<String> = toutes les notifs
	private Hashtable <String, Vector<Vector<String>>> notif_by_categ = new Hashtable<String, Vector<Vector<String>>>();

	public UserInformations() {
		
		
		
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	

	public void setDateCompte(String dateCompte) {
		this.dateCompte = dateCompte;
	}

	

	public void setListe_reseaux(Hashtable<Integer, Vector<String>> liste_reseaux) {
		this.liste_reseaux = liste_reseaux;
	}

	public void setCategories_pref(Vector<String> categories_pref) {
		this.categories = categories_pref;
	}

	public void setNotif_by_categ(Hashtable<String, Vector<Vector<String>>> notif_by_categ) {
		this.notif_by_categ = notif_by_categ;
	}
	
	

	}
	
	

	
	
