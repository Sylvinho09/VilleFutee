package com.example.paul.villefutee_android;



import android.content.ContentValues;

import android.content.Context;

import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;



import com.example.paul.villefutee_android.villefutee_server.ReseauInfos;



import java.util.Vector;



/**

 * Created by Paul on 30/05/2017.

 */



public class ReseauBDD {

    private SQLiteDatabase bdd;

    private BaseInfoClient maBaseSQLite;



    private static final String TABLE_RESEAUX = "table_reseaux";

    private static final String COL_IDRESEAUX = "IDRESEAUX";

    private static final String COL_NOM = "NOM";

    private static final String COL_DESCRIPTION= "DESCRIPTION";

    private static final String COL_POLITIQUEJOIN = "POLITIQUEJOIN";



    public ReseauBDD(Context context) {

        //On crée la BDD et sa table

        maBaseSQLite = new BaseInfoClient(context, "reseaux.db", null, 1);

    }

    public void open(){

        //on ouvre la BDD en écriture

        bdd = maBaseSQLite.getWritableDatabase();

    }

    public void close(){

        //on ferme l'accès à la BDD

        bdd.close();

    }

    public SQLiteDatabase getBDD(){

        return bdd;

    }



    public long insertReseaux(Vector<ReseauInfos> liste_reseaux){

        for (ReseauInfos r: liste_reseaux) {

            ContentValues values = new ContentValues();

            //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)

            values.put(COL_IDRESEAUX, r.getIDRESEAUX());

            values.put(COL_NOM, r.getNOM());

            values.put(COL_DESCRIPTION, r.getDESCRIPTION());

            values.put(COL_POLITIQUEJOIN, r.getPOLITIQUEJOIN());

            //on insère l'objet dans la BDD via le ContentValues

            bdd.insert(TABLE_RESEAUX, null, values);

        }

        return 0;

    }



    public int updateReseau(int id, ReseauInfos r){

        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion

        //il faut simplement préciser quel livre on doit mettre à jour grâce à l'ID

        ContentValues values = new ContentValues();

        values.put(COL_IDRESEAUX, r.getIDRESEAUX());

        values.put(COL_NOM, r.getNOM());

        values.put(COL_DESCRIPTION, r.getDESCRIPTION());

        values.put(COL_POLITIQUEJOIN, r.getPOLITIQUEJOIN());

        return bdd.update(TABLE_RESEAUX, values, COL_IDRESEAUX + " = " +id, null);

    }



    public int removeReseauWithID(int id){

        //Suppression d'un livre de la BDD grâce à l'ID

        return bdd.delete(TABLE_RESEAUX, COL_IDRESEAUX + " = " +id, null);

    }



    public Cursor getAllReseau(){

        //Récupère dans un Cursor les valeurs correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)

        Cursor c = bdd.query(TABLE_RESEAUX, new String[] {COL_IDRESEAUX, COL_NOM, COL_DESCRIPTION,COL_POLITIQUEJOIN},  null, null, null, null, null);

        return c;

    }



    public ReseauInfos getReseauWithid(int id){

        //Récupère dans un Cursor les valeurs correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)

        Cursor c = bdd.query(TABLE_RESEAUX, new String[] {COL_IDRESEAUX, COL_NOM, COL_DESCRIPTION,COL_POLITIQUEJOIN}, COL_IDRESEAUX + " LIKE \"" + id +"\"", null, null, null, null);

        return cursorToReseau(c);

    }

    public ReseauInfos cursorToReseau(Cursor c){

        if (c.getCount() == 0)

            return null;



        //Sinon on se place sur le premier élément

        c.moveToFirst();

        //On créé un livre

        ReseauInfos res = new ReseauInfos(

                c.getInt(0),/*NUM col Reseau id*/

                c.getString(1),/*NUM col Reseau nom*/

                c.getString(2),/*NUM col Reseau description*/

                c.getString(3)/*NUM col Reseau Politiquejoin*/

        );

        c.close();



        //On retourne le livre

        return res;

    }

}