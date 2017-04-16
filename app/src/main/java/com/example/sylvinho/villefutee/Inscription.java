package com.example.sylvinho.villefutee;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Text;

import java.util.concurrent.ExecutionException;
import com.example.sylvinho.villefutee.multispinner.multispinnerListener;
/**
 * Created by sylvinho on 14/04/2017.
 */

public class Inscription extends AppCompatActivity implements AdapterView.OnItemSelectedListener, multispinnerListener{

    private CheckBox client;
    private CheckBox commercant;
    private Spinner spinner;
    private Spinner spinner2;
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

       /* spinner2 = (Spinner)findViewById(R.id.spinner2);
        String[] domaine= getResources().getStringArray(R.array.domaine);
        ArrayAdapter<String>adapter2 = new ArrayAdapter<String>(Inscription.this,
                android.R.layout.simple_spinner_item, domaine);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);
        spinner2.setVisibility(View.INVISIBLE);*/

        multispinner ms = (multispinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<String>();
        String[] domaine= getResources().getStringArray(R.array.domaine);
        for(int i=0; i<domaine.length; i++)
        {
            list.add(domaine[i]);
        }
        ms.setItems(list, "Domaine", this);

         client = (CheckBox) findViewById(R.id.checkBox);
        commercant = (CheckBox) findViewById(R.id.checkBox2);

        client.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               // client.setChecked(true);
                if(client.isChecked() && commercant.isChecked()) {

                    commercant.setChecked(false);
                    client.setChecked(true);
                    spinner2.setVisibility(View.INVISIBLE);
                    TextView tprenom = (TextView) findViewById(R.id.textViewPrenom);
                    tprenom.setText("Prénom");
                    TextView tage= (TextView)findViewById(R.id.textViewAge);
                    tage.setText("Age");
                    EditText eage = (EditText) findViewById(R.id.editTextAge);
                    eage.setVisibility(View.VISIBLE);





                }
            }
        });

        commercant.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(commercant.isChecked() && client.isChecked()) {
                    client.setChecked(false);
                    commercant.setChecked(true);
                    spinner2.setVisibility(View.VISIBLE);



                    TextView tprenom = (TextView) findViewById(R.id.textViewPrenom);

                    tprenom.setText("Adresse");
                    //EditText eprenom = (EditText) findViewById(R.id.editTextPrenom);

                    TextView tage= (TextView)findViewById(R.id.textViewAge);
                    tage.setText("Domaine");
                    EditText eage = (EditText) findViewById(R.id.editTextAge);
                    eage.setVisibility(View.INVISIBLE);

                }

            }
        }
        );


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

                String[] spacePrenom = prenom.trim().split("\\s");
                String[] spaceNom = nom.trim().split("\\s");
                String[] spaceAge = age.trim().split("\\s");
                String[] spaceIdentifiant = identifiant.trim().split("\\s");
                String[] spaceMdp = mdp.trim().split("\\s");


                if(mdp.trim().length()==0 || identifiant.trim().length()==0 ||
                age.trim().length()==0 || nom.trim().length()==0 || prenom.trim().length()==0)
                {
                    Toast.makeText(getApplicationContext(), "Merci de remplir toutes les informations.", Toast.LENGTH_LONG).show();
                }


                else if(!spacePrenom[0].trim().equals(prenom.trim()) || !spaceNom[0].trim().equals(nom.trim()) || !spaceAge[0].trim().equals(age.trim())
                        || !spaceIdentifiant[0].trim().equals(identifiant.trim()) || !spaceMdp[0].trim().equals(mdp.trim()))
                {
                    Toast.makeText(getApplicationContext(), "Merci de ne pas mettre d'espace.", Toast.LENGTH_LONG).show();
                }

                else if(mdp.trim().length()>30 || identifiant.trim().length()>=30 || age.trim().length()>30
                        || nom.trim().length()>30 || prenom.trim().length()>30)
                {
                    Toast.makeText(getApplicationContext(), "Chaque saisie doit contenir au maximum 30 caractères.", Toast.LENGTH_LONG).show();
                }
                else if(mdp.trim().length()<6 || identifiant.trim().length()<4)
                {
                    Toast.makeText(getApplicationContext(), "L'identifiant doit contenir au moins 4 caractères et le mot de passe 6", Toast.LENGTH_LONG).show();

                }
                else {

                    try
                    {

                        int intage = Integer.parseInt(age);
                        if(14 <=intage && intage <=120) {
                            Formulaire datas = new Formulaire(prenom, nom, age, ville, identifiant, mdp);
                            System.out.println("données passées au thread: " + datas.toString());
                            System.out.println("Formulaire va lancer le thread");
                            String result = "";

                            result = new EnvoiForm().execute(datas).get();
                            if(result.trim().equals("Ok")) {
                                Toast.makeText(getApplicationContext(), "Compte créé avec succès !", Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else if(result.trim().equals("Error"))
                            {
                                Toast.makeText(getApplicationContext(), "Il y a eu une erreur. Réessayez.", Toast.LENGTH_LONG).show();

                            }
                        }
                        else Toast.makeText(getApplicationContext(), "Vous devez avoir entre 14 et 120 ans!", Toast.LENGTH_LONG).show();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch(NumberFormatException e)
                    {
                        Toast.makeText(getApplicationContext(),"Veuillez rentrer un âge correct.", Toast.LENGTH_LONG).show();
                    }


                }

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

    @Override
    public void onItemschecked(boolean[] checked) {

    }
}
