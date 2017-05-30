package com.example.paul.villefutee_android;



import android.app.ListActivity;

import android.content.Intent;

import android.database.Cursor;

import android.os.Bundle;

import android.view.View;

import android.widget.ArrayAdapter;

import android.widget.ListView;

import android.widget.Toast;



import com.example.paul.villefutee_android.villefutee_server.ReseauInfos;



import java.util.Hashtable;

import java.util.Map;

import java.util.Vector;

import java.util.concurrent.ExecutionException;



/**

 * Created by Paul on 28/05/2017.

 */



public class MesReseaux extends ListActivity {





    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.listviewproducts);


        final Intent intent = getIntent();

        String message = intent.getStringExtra("idutilisateur");

        Vector<ReseauInfos> VectorReseau = new Vector<>();

        String[] Reseaux = new String[0];
        try {



            Vector<String> reseaux = new GetReseaux().execute(message).get();
            Reseaux = new String[reseaux.size()];


            int i = 0;

            if (reseaux.size() != 0) {

                for (String s : reseaux) {

                    String[] datas = s.trim().split("\\s+");

                    String id = datas[0].trim();

                    String nom = datas[1].trim();

                    String description = datas[2].trim();

                    String POLITIQUEJOIN = datas[4].trim();



                    Reseaux[i] = nom + " " + POLITIQUEJOIN;
                    i++;

                    VectorReseau.add(new ReseauInfos(Integer.parseInt(id), nom, description, POLITIQUEJOIN));

                }


            }
        } catch (InterruptedException e) {

            e.printStackTrace();

        } catch (ExecutionException e) {

            e.printStackTrace();

        }

        // ListView listview = getListView();



       /* int i=0;

        for(ReseauInfos r: VectorReseau ){

           Reseaux[i]=r.getNOM() +" "+ r.getDESCRIPTION() +" "+ r.getPOLITIQUEJOIN();

            i++;

            Toast.makeText(getApplicationContext(), Reseaux[i], Toast.LENGTH_LONG).show();

        }*/



        /*ReseauBDD rb=new ReseauBDD(this);

        rb.open();

        rb.insertReseaux(VectorReseau);

        Cursor c=rb.getAllReseau();

        if (c.getCount() != 0){

           int i=0;



        Sinon on se place sur le premier élément

        c.moveToFirst();

        while(c.moveToNext()) {

            Reseaux[i]=c.getString(1);

            i++;

        }

        c.close();

        }*/


        setListAdapter(new ArrayAdapter<String>(this, R.layout.reseaux, Reseaux));

    }





    public void onListItemClick(ListView parent, View v, int position, long id) {

        //CheckedTextView item = (CheckedTextView) v;



        /*LANCER FRAGMENT CORRESPONDANT AU RESEAU*/

    }



}