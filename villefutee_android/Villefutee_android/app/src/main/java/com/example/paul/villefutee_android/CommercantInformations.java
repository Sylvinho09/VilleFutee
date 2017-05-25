package com.example.paul.villefutee_android;

/**
 * Created by sylvinho on 25/05/2017.
 */

public class CommercantInformations extends UserInformations{

    private String nom_magasin;
    private String adresse;
    private String infos_supp;



    public CommercantInformations(){super();}



    public void setNom_magasin(String nom_magasin) {
        this.nom_magasin = nom_magasin;
    }



    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }



    public void setInfos_supp(String infos_supp) {
        this.infos_supp = infos_supp;
    }

}
