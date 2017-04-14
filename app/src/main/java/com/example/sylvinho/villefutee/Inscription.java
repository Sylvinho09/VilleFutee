package com.example.sylvinho.villefutee;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

/**
 * Created by sylvinho on 14/04/2017.
 */

public class Inscription extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription);

        spinner = (Spinner)findViewById(R.id.spinner);
        String[] villes= getResources().getStringArray(R.array.spinner);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(Inscription.this,
                android.R.layout.simple_spinner_item, villes);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        //spinner.getSelectedItem().toString();

        Button valider= (Button) findViewById(R.id.buttonValidation);
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText eprenom= (EditText)findViewById(R.id.editTextPrenom);
                String prenom = eprenom.getText().toString();

                EditText enom= (EditText)findViewById(R.id.editTextNom);
                String nom = enom.getText().toString();

                EditText eage = (EditText) findViewById(R.id.editTextAge);
                String age = eage.getText().toString();

                String ville= spinner.getSelectedItem().toString();

                EditText eidentifiant= (EditText)findViewById(R.id.editTextId);
                String identifiant = eidentifiant.getText().toString();

                EditText emdp= (EditText)findViewById(R.id.editTextMdp);
                String mdp= emdp.getText().toString();

                Formulaire datas= new Formulaire(prenom, nom, age, ville, identifiant, mdp);
                System.out.println("Formulaire va lancer le thread");
                String result="";
                try {
                    result = new EnvoiForm().execute(datas).get();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getApplicationContext(), "Compte créé avec succès !", Toast.LENGTH_LONG).show();
                finish();


            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Toast.makeText(getApplicationContext(), spinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
