package com.example.paul.villefutee_android.villefutee_server;



/**

 * Created by Paul on 30/05/2017.

 */



public class ReseauInfos {

    private int IDRESEAUX ;

    private String NOM ;

    private String DESCRIPTION ;

    private String POLITIQUEJOIN ;



    public  ReseauInfos(int idR,String nom,String description,String POLITIQUEJOIN){

        setIDRESEAUX(idR);

        setNOM(nom);

        setDESCRIPTION(description);

        this.setPOLITIQUEJOIN(POLITIQUEJOIN);

    }



    public int getIDRESEAUX() {

        return IDRESEAUX;

    }



    public void setIDRESEAUX(int IDRESEAUX) {

        this.IDRESEAUX = IDRESEAUX;

    }



    public String getNOM() {

        return NOM;

    }



    public void setNOM(String NOM) {

        this.NOM = NOM;

    }



    public String getDESCRIPTION() {

        return DESCRIPTION;

    }



    public void setDESCRIPTION(String DESCRIPTION) {

        this.DESCRIPTION = DESCRIPTION;

    }



    public String getPOLITIQUEJOIN() {

        return POLITIQUEJOIN;

    }



    public void setPOLITIQUEJOIN(String POLITIQUEJOIN) {

        this.POLITIQUEJOIN = POLITIQUEJOIN;

    }



}