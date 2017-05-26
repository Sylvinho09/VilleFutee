package com.example.paul.villefutee_android.villefutee_server;

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

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getAge() {
        return age;
    }

    public String getPolitique_notif() {
        return politique_notif;
    }

    public String toString()
    {
        return nom+" "+prenom+" "+age+" "+politique_notif;
    }
}
