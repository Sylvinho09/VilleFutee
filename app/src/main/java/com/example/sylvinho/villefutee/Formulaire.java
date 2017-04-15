package com.example.sylvinho.villefutee;

import java.text.Normalizer;
import java.util.Date;
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


    public Formulaire(){}
    public Formulaire(String prenom, String nom, String age, String ville, String identifiant, String mdp)
    {
        this.prenom=prenom;
        this.nom=nom;
        this.age=age;
        this.ville=ville;
        this.identifiant=identifiant;
        this.mdp=mdp;
    }

    public String toString()
    {
        return prenom+ " "+nom+" "+age+" "+ville+ " "+identifiant+" "+mdp;

    }
}
