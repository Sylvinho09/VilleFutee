package com.example.paul.villefutee_android;

import java.text.Normalizer;
import java.util.Date;
import java.util.Vector;
/**
 * Created by sylvinho on 14/04/2017.
 */

public class Formulaire {

    String prenom;
    String nom;
    String age;
    String ville;
    String identifiant;
    String mdp;
    Vector<String> domaines;
    String[] adresse;
    Vector<String> adresseServeur;


    public Formulaire(){
        domaines=new Vector<String>();
        adresseServeur=new Vector<String>();
    }
    public Formulaire(String prenom, String nom, String age, String ville, String identifiant, String mdp) {
        this.prenom = prenom;
        this.nom = nom;
        this.age = age;
        this.ville = ville;
        this.identifiant = identifiant;
        this.mdp = mdp;
    }

    public Formulaire(String[] adresse, String nom, Vector<String> domaines, String ville, String identifiant, String mdp)
    {
        this.prenom=null;
        this.adresse=adresse;
        this.nom=nom;
        this.domaines=domaines;
        this.ville=ville;
        this.identifiant=identifiant;
        this.mdp=mdp;
    }

    public String toString()
    {
        return prenom+ " "+nom+" "+age+" "+ville+ " "+identifiant+" "+mdp;

    }
}
