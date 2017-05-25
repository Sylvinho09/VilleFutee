package com.example.paul.villefutee_android;

/**
 * Created by sylvinho on 25/05/2017.
 */

public class ClientInformations extends UserInformations
{
    private String nom;
    private String prenom;
    private String age;
    private String politique_notif;

    public ClientInformations(){ super();}

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setPolitique_notif(String politique_notif) {
        this.politique_notif = politique_notif;
    }

}