package villefutee_server;



import java.util.Vector;



/**

 * Created by Paul on 27/05/2017.

 */



public class FormulaireReseau {

    String NomReseau;

    String Categorie;

    String typeReseau;

    String ville;

    String idUser;



    public FormulaireReseau(){

    }

    public FormulaireReseau(String NomReseau, String Categorie, String typeReseau, String ville,String idUser) {

        this.NomReseau=NomReseau;

        this. Categorie=Categorie;

        this. typeReseau=typeReseau;

        this.ville = ville;

        this.idUser = idUser;

    }





    public String toString(){



    return NomReseau+ " "+Categorie+" "+typeReseau+ " "+ville+ " "+idUser;

    }

    

}