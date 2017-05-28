package com.example.paul.villefutee_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.concurrent.ExecutionException;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by sylvinho on 11/04/2017.
 */

public class ConnexionPage extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion_page);

        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        int logged = sharedPref.getInt("Log".trim(), -1); //retournera -1 si non trouvé
        int loggedCom = sharedPref.getInt("LogCom".trim(),-1);//retournera -1 si non trouvé
        if(logged==1)
        {


            Toast.makeText(getApplicationContext(),"Vous avez déjà une connexion client en cours, ouverture de votre page d'accueil.",Toast.LENGTH_LONG).show();
            Intent myintent = new Intent(getApplicationContext(), MainAccount.class);
            startActivity(myintent);
            finish();




        }
        else if(loggedCom==1)
        {
            Toast.makeText(getApplicationContext(),"Vous avez déjà une connexion commerçant en cours, ouverture de votre page d'accueil.",Toast.LENGTH_LONG).show();
            Intent myintent = new Intent(getApplicationContext(), MainAccountCommercant.class);
            startActivity(myintent);
            finish();
        }
        else {
            Toast.makeText(getApplicationContext(), "Vous n'avez pas de session en cours. Veuillez vous connecter ou créer un compte.", Toast.LENGTH_LONG).show();
            final CheckBox client = (CheckBox) findViewById(R.id.checkBoxCoClient);
            client.setChecked(true);
            final CheckBox commercant = (CheckBox) findViewById(R.id.checkBoxCoCom);

            client.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


            @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                        if ((client.isChecked() && commercant.isChecked()) || client.isChecked()) {

                            commercant.setChecked(false);


                        } else if (!commercant.isChecked() && !client.isChecked()) {
                            commercant.setChecked(true);
                        }
                    }

            });

            commercant.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if ((commercant.isChecked() && client.isChecked()) || commercant.isChecked()) {
                        client.setChecked(false);

                        // spinner2.setVisibility(View.VISIBLE);


                    } else if (!commercant.isChecked() && !client.isChecked()) {
                        client.setChecked(true);
                    }
                }
                });

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
                                                       if(client.isChecked()) {
                                                           IdMdpClass idmdp = new IdMdpClass("0".trim(),getId, getMdp);
                                                           System.out.println("je lance le thread");
                                                           result = new GetMDPSocket().execute(idmdp).get();
                                                       }


                                                       else if(commercant.isChecked())
                                                       {
                                                           IdMdpClass idmdp = new IdMdpClass("1".trim(),getId, getMdp);
                                                           System.out.println("je lance le thread");
                                                           result = new GetMDPSocket().execute(idmdp).get();
                                                       }

                                                       System.out.println("après le thread");
                                                   } catch (InterruptedException e) {
                                                       e.printStackTrace();
                                                   } catch (ExecutionException e) {
                                                       e.printStackTrace();
                                                   }

                                                   System.out.println("Dans le main Thread " + result); /** Résultat bien reçu ! **/
                                                   if (result == 1 && client.isChecked()) {
                                                       Intent myintent = new Intent(getApplicationContext(), MainAccount.class);

                                                       Toast.makeText(getApplicationContext(), "Ajout de log ok dans sharedpreferences.", Toast.LENGTH_LONG).show();
                                                       SharedPreferences.Editor editor = sharedPref.edit();
                                                       editor.putString("id".trim(), getId.trim());
                                                       editor.putInt("Log".trim(), 1);
                                                       editor.commit();
                                                       startActivity(myintent);
                                                   }

                                                   /** ici: activité commercant **/
                                                   else if(result==1 && commercant.isChecked()) {
                                                       Toast.makeText(getApplicationContext(), "Ajout de log ok dans Shared Preferences."+result, Toast.LENGTH_LONG).show();
                                                      Intent myIntent = new Intent(getApplicationContext(), MainAccountCommercant.class);
                                                       SharedPreferences.Editor editor = sharedPref.edit();
                                                       editor.putString("id".trim(), getId.trim());
                                                       editor.putInt("LogCom".trim(), 1);
                                                       editor.commit();
                                                       startActivity(myIntent);

                                                   } else if(result==0){

                                                       Toast.makeText(getApplicationContext(), "Aucun compte client n'est associé à ces logs."+result, Toast.LENGTH_LONG).show();


                                                   }
                                                   else if(result==-1)
                                                   {
                                                       Toast.makeText(getApplicationContext(), "Aucun compte commercant n'est associé à ces logs."+result, Toast.LENGTH_LONG).show();

                                                   }
                                                   else
                                                   {
                                                       Toast.makeText(getApplicationContext(), "Il y a eu une erreur. Réessayez."+result, Toast.LENGTH_LONG).show();

                                                   }
                                               }

                                           }
                                       }


            );
        }
    }
}