package com.example.paul.villefutee_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.concurrent.ExecutionException;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by sylvinho on 11/04/2017.
 */

public class ConnexionPage extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        final SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        int logged = sharedPref.getInt("Log", -1); //retournera -1 si non trouvé
        if(logged==1)
        {

            Toast.makeText(getApplicationContext(),"Vous avez déjà une connexion en cours, ouverture de votre page d'accueil.",Toast.LENGTH_LONG).show();
            Intent myintent = new Intent(getApplicationContext(), MainAccount.class);
            startActivity(myintent);



        }
        else {
            Toast.makeText(getApplicationContext(), "Vous n'avez pas de session en cours. Veuillez vous connecter ou créer un compte.", Toast.LENGTH_LONG).show();


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
                                               if (!spaceId[0].trim().equals(getId.trim()) || !spaceMdp[0].trim().equals(getMdp.trim())) {

                                                   Toast.makeText(getApplicationContext(), "Veuillez ne pas mettre d'espace.", Toast.LENGTH_LONG).show();
                                               } else if (getMdp.length() == 0 || getId.length() == 0) {

                                                   Toast.makeText(getApplicationContext(), "Au moins une saisie est vide.", Toast.LENGTH_LONG).show();

                                               } else if (getMdp.length() > 30 || getId.length() > 30) {
                                                   Toast.makeText(getApplicationContext(), "Chaque saisie contient au maximum 30 caractères.", Toast.LENGTH_LONG).show();

                                               } else {
                                                   int result = 0;

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
                                                   if (result == 49) {//car la repré hexadécimale de 1 est 49, il faut ajouter 48 aux chiffres pour obtenir leur valeur hexa
                                                       Intent myintent = new Intent(getApplicationContext(), MainAccount.class);
                                                       startActivity(myintent);
                                                       Toast.makeText(getApplicationContext(), "Ajout de log ok dans sharedpreferences.", Toast.LENGTH_LONG).show();
                                                       SharedPreferences.Editor editor = sharedPref.edit();
                                                       editor.putInt("Log".trim(), 1);
                                                       editor.commit();

                                                   } else {

                                                       Toast.makeText(getApplicationContext(), "Il y a une erreur dans la saisie de votre identifiant ou mot de passe.", Toast.LENGTH_LONG).show();


                                                   }
                                               }

                                           }
                                       }


            );
        }
    }
}