package com.example.sylvinho.villefutee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.concurrent.ExecutionException;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by sylvinho on 11/04/2017.
 */

public class ConnexionPage extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion_page);
        Button valider = (Button) findViewById(R.id.button);

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText id = (EditText) findViewById(R.id.editText2);
                String getId =id.getText().toString();
                EditText mdp =(EditText)findViewById(R.id.editText3);
                String getMdp = mdp.getText().toString();
                System.out.println("affichage des données saisies: "+ getId+" "+getMdp);

                String result="";
                try {
                    IdMdpClass idmdp= new IdMdpClass(getId, getMdp);
                    System.out.println("je lance le thread");
                    result = new GetMDPSocket().execute(idmdp).get();

                    System.out.println("après le thread");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                System.out.println("Dans le main Thread "+result); /** Résultat bien reçu ! **/
                if(result.trim().equals("No"))
                {
                    Toast.makeText(getApplicationContext(), "Il y a une erreur dans la saisie de votre identifiant ou mot de passe.", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent myintent = new Intent(getApplicationContext(), MainAccount.class);
                    startActivity(myintent);


                }

            }
        });




    }
}
