package com.example.paul.villefutee_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Dialog;
import java.util.Vector;

import android.widget.Spinner;
import com.example.paul.villefutee_android.villefutee_server.CommercantInformations;
import com.example.paul.villefutee_android.villefutee_server.PersonalizedDialog;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import android.location.LocationListener;

public class MainAccountCommercant extends AppCompatActivity implements LocationListener, multispinner.multispinnerListener {
    private String[] mesItems;
    private String[] mesCommerces;
    private ListView reseauList;
    private ListView ComListRight;

    static final int PRODUCT_CHOICE = 1;
    /** identifiant de l'intent généré **/
    CommercantInformations ci;
    ImageView targetImage;
    LocationManager locationManager;
    private  Criteria criteria;
    private String bestProvider;
    LatitudeLongitude ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_account_commercant);

        locationManager = (LocationManager)  this.getSystemService(Context.LOCATION_SERVICE);
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String id = sharedPref.getString("id".trim(), "NotFound"); //NotFound est la valeur par défaut si rien n'est trouvé


        try {

            // String id =getIntent().getStringExtra("id"); // fait plutot avec SharedPreference car si l'user se connecte directement il ne rentre pas ses logs

            System.out.println("valeur identifiant donnée: " + id);
            ci =  new GetInfosCommercant().execute(id).get();


           // Toast.makeText(getApplicationContext(), "Données du client bien recues " + ci.getNom_magasin() + " " + ci.getAdresse() + " " + ci.getDateCompte(), Toast.LENGTH_LONG).show();
           /* for(String categ : ci.getCategories())
            {
                Toast.makeText(getApplicationContext(), categ, Toast.LENGTH_LONG).show();

            }*/
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layoutC);
        final FrameLayout fm = (FrameLayout) findViewById(R.id.nav_viewC);
        final FrameLayout fm2 = (FrameLayout) findViewById(R.id.nav_viewrightC);

        //Button buttonEditProfile = (Button) findViewById(R.id.button2C);

        // targetImage = (ImageView)findViewById(R.id.imageView);


        //ImageButton navLauncher = (ImageButton) findViewById(R.id.imageButton3);
        // Button buttonright = (Button) findViewById(R.id.buttonnav);
        mesItems = getResources().getStringArray(R.array.items);
        mesCommerces = getResources().getStringArray(R.array.commerce_commercant);
        reseauList = (ListView) findViewById(R.id.left_drawerC);
        reseauList.setAdapter(new ArrayAdapter<String>(this, R.layout.navline, mesItems));

        reseauList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    Intent myintent = new Intent(getApplicationContext(), addReseau.class);
                    myintent.putExtra("ville", ci.getVille());
                    startActivity(myintent);
                }
                if(position==1){
                    Intent myintent = new Intent(getApplicationContext(), MesReseaux.class);
                    startActivity(myintent);
                    String idUtilisateur =sharedPref.getString("id".trim(), "NotFound");
                    myintent.putExtra("idutilisateur", idUtilisateur);
                }
            }        });
        ComListRight = (ListView) findViewById(R.id.left_drawer2C);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.navline, mesCommerces);
        ComListRight.setAdapter(adapter);
        ComListRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {

               // Toast.makeText(getApplicationContext(), view.getId() + " " + position + " " + id, Toast.LENGTH_LONG).show();
                if (position == 0) {


                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        System.out.println("DANS LE CHECK1");

                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(MainAccountCommercant.this,
                                android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                            System.out.println("DANS LE CHECK2");


                            // Show an explanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.

                        }else {

                            //ActivityCompat.requestPermissions(MainAccount.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                            // No explanation needed, we can request the permission.
                            System.out.println("DANS LE CHECK3");
                            /*ActivityCompat.requestPermissions(MainAccount.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    1);*/ //la constante est utilisée pour attacher un requestPermission particulier
                            //à un onRequestPermissionResult (resultCode)

                            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                            // app-defined int constant. The callback method gets the
                            // result of the request.
                        }
                        System.out.println("DANS LE CHECK4");

                        return;
                    }

                    System.out.println("DANS LE CHECK5");


                    String locationProvider = LocationManager.GPS_PROVIDER;

                    Location location = locationManager.getLastKnownLocation(locationProvider);

                    //Ci-dessous très important, sinon la carte ne fonctionne qu'une fois car le requestLocationUpdates ne s'effectue plus, surement car c'est a cause de l'emulateur
                    if(location!=null)
                    {
                        Double latitude = location.getLatitude();
                        Double longitude = location.getLongitude();
                        ll= new LatitudeLongitude(latitude, longitude);
                        ArrayList<String> proxCom= null;
                        try {
                            proxCom = new GetProxCom().execute(ll).get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        double[] posClient= new double[2];
                        posClient[0]= latitude;
                        posClient[1]= longitude;
                        Intent map = new Intent(getApplicationContext(), MapsActivity.class);
                        map.putStringArrayListExtra("Com", proxCom);
                        map.putExtra("Cli", posClient);
                        startActivity(map);

                    }
                    else {

                        criteria = new Criteria();
                        bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();
                       // Toast.makeText(getApplicationContext(), "Lancement location updates", Toast.LENGTH_LONG);
                        locationManager.requestLocationUpdates(bestProvider, 1000, 0, MainAccountCommercant.this);
                    }



                }
                else if(position==1)
                {
                    final PersonalizedDialog dialog = new PersonalizedDialog(MainAccountCommercant.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.send_notifs);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setCancelable(true);

                    //TextView textView = (TextView) dialog.findViewById(R.id.txtTitle);
                    //ListView listView = (ListView) dialog.findViewById(R.id.listView);
                    Button btnBtmLeft = (Button) dialog.findViewById(R.id.btnBtmLeft);
                    Button btnBtmRight = (Button) dialog.findViewById(R.id.btnBtmRight);

                    /** Pour afficher les options dans un multispinner, ne fonctionnait pas au début mais fonctionne maintenant
                     multispinner ms = (multispinner) dialog.findViewById(R.id.spinnerNotifs);
                    ArrayList<String> list = new ArrayList<String>();
                    String[] domaine= getResources().getStringArray(R.array.notifs_categ);
                    for(int i=0; i<domaine.length; i++)
                    {
                        Toast.makeText(getApplicationContext(), domaine[i], Toast.LENGTH_LONG).show();
                        list.add(domaine[i]);
                    }

                    ms.setItems(list, "A qui l'envoyer ?", dialog);**//**dialog ou MainAccountCommercant fonctionnent **/

                    final Spinner ms = (Spinner) dialog.findViewById(R.id.spinnerNotifs);
                    ArrayList<String> list = new ArrayList<String>();
                    String[] domaine= getResources().getStringArray(R.array.notifs_categ);
                    for(int i=0; i<domaine.length; i++)
                    {
                        //Toast.makeText(getApplicationContext(), domaine[i], Toast.LENGTH_LONG).show();
                        list.add(domaine[i]);
                    }
                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainAccountCommercant.this,
                            android.R.layout.simple_spinner_item, domaine);
                    ms.setAdapter(adapter);

                   // ms.setItems(list, "Catégorie de la notification ", dialog);

                    btnBtmLeft.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    btnBtmRight.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            /** dialog.find... important de mettre dialog sinon regarde dans le layout du MainAccount **/
                            CheckBox proche = (CheckBox) dialog.findViewById(R.id.checkBoxProche);
                            CheckBox reseau = (CheckBox) dialog.findViewById(R.id.checkBoxReseaux);


                            if(reseau.isChecked())
                            {
                                Toast.makeText(getApplicationContext(), "La fonctionnalité \"Envoyer à un réseau\" n'est pas encore implémentée.", Toast.LENGTH_LONG).show();
                            }

                            else if(proche.isChecked())
                            {
                                EditText text = (EditText)dialog.findViewById(R.id.editTextMaNotif);
                                String value= text.getText().toString().trim();
                                if(value.length()==0)
                                {

                                    Toast.makeText(getApplicationContext(),"Veuillez rentrer un message avant de valider.", Toast.LENGTH_LONG).show();
                                }
                                else if(value.length()>140)
                                {
                                    Toast.makeText(getApplicationContext(), "Texte trop long, limité à 140 caractères.", Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    if(ms.getSelectedItemPosition()==0)
                                    {
                                        Toast.makeText(getApplicationContext(), "Veuillez sélectionner une catégorie.", Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        Vector<String> idnp = new Vector<String>();
                                        String id = sharedPref.getString("id".trim(), "NotFound").trim(); //NotFound est la valeur par défaut si rien n'est trouvé

                                        idnp.add(id);
                                        idnp.add("position".trim());
                                        idnp.add(ms.getSelectedItem().toString().trim());
                                        idnp.add(value);


                                        try {
                                            int result = new SendNotifications().execute(idnp).get();

                                            if (result == 1) {
                                                Toast.makeText(getApplicationContext(), "Les utilisateurs proches seront avertis de votre notification.", Toast.LENGTH_LONG).show();
                                                dialog.cancel();

                                            } else
                                                Toast.makeText(getApplicationContext(), "Il y a eu une erreur lors de l'envoi de la notification.", Toast.LENGTH_LONG).show();


                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        } catch (ExecutionException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                }
                            }
                            else  Toast.makeText(getApplicationContext(), "Veuillez sélectionner une option.", Toast.LENGTH_LONG).show();



                        }
                    });

                    /**
                     * if you want the dialog to be specific size, do the following
                     * this will cover 85% of the screen (85% width and 85% height)
                     */
                    DisplayMetrics displayMetrics = MainAccountCommercant.this.getResources().getDisplayMetrics();
                    int dialogWidth = (int)(displayMetrics.widthPixels * 0.85);
                    int dialogHeight = (int)(displayMetrics.heightPixels * 0.35);
                    dialog.getWindow().setLayout(dialogWidth, dialogHeight);

                    dialog.show();
                }
            }
        });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE); // the results will be higher than using the activity context object or the getWindowManager() shortcut
        wm.getDefaultDisplay().getMetrics(displayMetrics);

        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        System.out.println("valeur de screen: "+screenWidth+ " "+ screenHeight);

        /** Ajustement du bouton en bas a gauche **/
        ImageButton buttonleft = (ImageButton) findViewById(R.id.buttonnavC);
        buttonleft.setMinimumWidth(screenWidth/3);
        buttonleft.setMinimumHeight(screenHeight/7);
        buttonleft.setScaleY((float) 1.50);
        buttonleft.setScaleX((float)1.1);

/** Ajustement du bouton du milieu **/
        ImageButton buttonmiddle = (ImageButton) findViewById(R.id.button3C);
        buttonmiddle.setMinimumWidth(screenWidth/3);
        buttonmiddle.setMinimumHeight(screenHeight/7);
        buttonmiddle.setScaleY((float) 1.63);
        buttonmiddle.setScaleX((float)1.15);

        /** Ajustement du bouton en bas a droite **/
        ImageButton buttonright = (ImageButton) findViewById(R.id.button4C);
        buttonright.setMinimumWidth(screenWidth/3);
        buttonright.setMinimumHeight(screenHeight/7);
        buttonright.setScaleY((float) 1.5);
        buttonright.setScaleX((float)1.1);

        /** Ajuqtement de la photo de profil **/
        ImageView picture= (ImageView) findViewById(R.id.imageViewProfileC);
        picture.setMinimumWidth((int) (screenWidth/(2)));
        picture.setMinimumHeight((int)(screenHeight /(3)));
         picture.setScaleX(2);
        picture.setScaleY(2);

        TextView description= (TextView)findViewById(R.id.textViewDescriptionC);
        description.setText(ci.getNom_magasin()+" situé à "+ci.getAdresse()+", membre de VilleFutee depuis "+ci.getDateCompte()+",  "+ci.getVille());

        TextView interest= (TextView) findViewById(R.id.textViewInterestC);
        String str= "Type de commerce ";

        for(String i : ci.getCategories())
        {
            str+=i+" ";
        }
        interest.setText(str);
        Button buttonDeco = (Button) findViewById(R.id.buttonDeconnexionC);

        buttonDeco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedPref.edit();

                editor.putInt("LogCom".trim(), -1);
                editor.commit();
                Intent myIntent= new Intent(getApplicationContext(), ConnexionPage.class);
                startActivity(myIntent);
                finish();
            }
        });

        /** Onclick du bouton gauche **/
        //mDrawerLayout.closeDrawer(mDrawerLayout);
        buttonleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDrawerLayout.openDrawer(fm);
            }
        });


        /** Onclick du bouton droit **/
        buttonright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDrawerLayout.openDrawer(fm2);
            }
        });

        buttonmiddle.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {

                Toast.makeText(getApplicationContext(), "Cette fonctionnalité n'est pas encore développée.", Toast.LENGTH_LONG).show();


            }});

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == PRODUCT_CHOICE)
        {
           // Toast.makeText(this, "dans le onActivityResult", Toast.LENGTH_LONG);
        }


        /*** NE FONCTIONNE PAS, Permet de changer la photo de profil de l'utilisateur ***/
       /* if (resultCode == RESULT_OK){
            System.out.println("Je suis dans le onActivityResult");
            Uri targetUri = data.getData();

            Bitmap bitmap;
            Toast.makeText(getApplicationContext(), "dans le onActResult", Toast.LENGTH_LONG).show();//OK
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inSampleSize = 1;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri), null, options);
                //targetImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }*/
    }

    @Override
    public void onLocationChanged(Location location) {

        //locationManager.removeUpdates(this);


        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        //System.out.println("mes coordonnées aaaaaaaaaaaa: "+latitude+ " "+longitude);
        ll= new LatitudeLongitude(latitude, longitude);
        ArrayList<String> proxCom= null;
        try {
            proxCom = new GetProxCom().execute(ll).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        double[] posClient= new double[2];
        posClient[0]= latitude;
        posClient[1]= longitude;
        Intent map = new Intent(getApplicationContext(), MapsActivity.class);
        map.putStringArrayListExtra("Com", proxCom);
        map.putExtra("Cli", posClient);
        startActivity(map);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    @Override
    public void onItemschecked(boolean[] checked) {

    }
}



