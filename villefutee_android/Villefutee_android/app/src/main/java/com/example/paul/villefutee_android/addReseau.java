package com.example.paul.villefutee_android;



import android.content.Intent;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.view.View;

import android.widget.AdapterView;

import android.widget.ArrayAdapter;

import android.widget.Button;

import android.widget.EditText;

import android.widget.RadioButton;

import android.widget.RadioGroup;

import android.widget.Spinner;

import android.widget.TextView;

import android.widget.Toast;



import java.util.concurrent.ExecutionException;



/**

 * Created by Paul on 27/05/2017.

 */

public class addReseau extends AppCompatActivity implements AdapterView.OnItemSelectedListener{



    private Spinner spinner1;

    private RadioGroup rg;

    private RadioButton radioCatButton;

    private String ville;



    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.addreseaux);

        final Intent intent = getIntent();

        String message = intent.getStringExtra("ville");

        ville=message;

        TextView Texville= (TextView)findViewById(R.id.TextVille);

        Texville.setText("Ville :"+ville);



        spinner1 = (Spinner)findViewById(R.id.spinnercategories);

        String[] Categeories = getResources().getStringArray(R.array.domaine);

        ArrayAdapter<String>adapter = new ArrayAdapter<String>(addReseau.this,

                android.R.layout.simple_spinner_item, Categeories);



        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(adapter);

        spinner1.setOnItemSelectedListener(this);



        Button valider= (Button) findViewById(R.id.buttonvalid);

        valider.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {



                EditText enom= (EditText)findViewById(R.id.editNomreseau);

                String nom = enom.getText().toString().trim();



                String categorie= spinner1.getSelectedItem().toString().trim();

                rg = (RadioGroup)findViewById(R.id.rginvit);

                int radioButtonID = rg.getCheckedRadioButtonId();

                radioCatButton = (RadioButton) findViewById(radioButtonID);

                String selectedtext = radioCatButton.getText().toString().trim();

                FormulaireReseau fr= new FormulaireReseau(nom,categorie, selectedtext, ville,/*idUser*/"0");

                try

                {

                    int result = new EnvoiFormRes().execute(fr).get();



                    if(result==1) {

                        Toast.makeText(getApplicationContext(), "Reseau créé avec succès !", Toast.LENGTH_LONG).show();

                        finish();

                    }

                    else

                    {

                        Toast.makeText(getApplicationContext(), "Il y a eu une erreur. Réessayez.", Toast.LENGTH_LONG).show();



                    }

                } catch (InterruptedException e) {

                    e.printStackTrace();

                } catch (ExecutionException e) {

                    e.printStackTrace();

                }

            }

        });

    }

    @Override

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



        //    Toast.makeText(getApplicationContext(), spinner1.getSelectedItem().toString().toLowerCase(), Toast.LENGTH_LONG).show();

    }



    @Override

    public void onNothingSelected(AdapterView<?> parent) {



    }



    public void onItemschecked(boolean[] checked) {







    }

}