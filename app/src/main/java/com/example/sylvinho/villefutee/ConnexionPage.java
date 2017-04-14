package com.example.sylvinho.villefutee;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.concurrent.ExecutionException;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by sylvinho on 11/04/2017.
 */

public class ConnexionPage extends AppCompatActivity {


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion_page);
        Button valider = (Button) findViewById(R.id.button);

        TextView inscription = (TextView) findViewById(R.id.textView3);
        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(getApplicationContext(), Inscription.class);
                startActivity(myintent);
            }
        });

        valider.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {

                                           EditText id = (EditText) findViewById(R.id.editText2);
                                           String getId = id.getText().toString();
                                           EditText mdp = (EditText) findViewById(R.id.editText3);
                                           String getMdp = mdp.getText().toString();
                                           System.out.println("affichage des données saisies: " + getId + " " + getMdp);

                                           String[] spaceId = getId.trim().split("\\s");
                                           String[] spaceMdp = getMdp.trim().split("\\s");
                                           if(!spaceId[0].equals(getId) || !spaceMdp[0].equals(getMdp))
                                           {

                                                         Toast.makeText(getApplicationContext(), "Veuillez ne pas mettre d'espace.", Toast.LENGTH_LONG).show();
                                           }



                                           else {
                                               String result = "";

                                               try {
                                                   IdMdpClass idmdp = new IdMdpClass(getId, getMdp);
                                                   System.out.println("je lance le thread");
                                                   result = new GetMDPSocket().execute(idmdp).get();

                                                   System.out.println("après le thread");
                                               } catch (InterruptedException e) {
                                                   e.printStackTrace();
                                               } catch (ExecutionException e) {
                                                   e.printStackTrace();
                                               }

                                               System.out.println("Dans le main Thread " + result); /** Résultat bien reçu ! **/
                                               if (result.trim().equals("Yes")) {
                                                   Intent myintent = new Intent(getApplicationContext(), MainAccount.class);
                                                   startActivity(myintent);
                                               } else {

                                                   Toast.makeText(getApplicationContext(), "Il y a une erreur dans la saisie de votre identifiant ou mot de passe.", Toast.LENGTH_LONG).show();


                                               }
                                           }

                                       }
                                   }

        );
    }
}