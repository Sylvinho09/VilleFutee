package com.example.paul.villefutee_android;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Button;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.content.Context;
import android.widget.Toast;
import android.widget.ImageView;
import java.util.Vector;

import com.example.paul.villefutee_android.villefutee_server.ClientInformations;
import com.example.paul.villefutee_android.villefutee_server.PersonalizedDialog;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.widget.TextView;
import android.location.LocationManager;


public class MainAccount extends AppCompatActivity implements LocationListener{
    private String[] mesItems;
    private String[] mesCommerces;
    private ListView reseauList;
    private ListView reseauListRight;
    static final int PRODUCT_CHOICE = 1;
    /** identifiant de l'intent généré **/
    ClientInformations ci;
    ImageView targetImage;
    LocationManager locationManager;
    private  Criteria criteria;
    private String bestProvider;
    LatitudeLongitude ll;
    int mapOrNotif=0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_account);
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        locationManager = (LocationManager)  this.getSystemService(Context.LOCATION_SERVICE);
        try {

            // String id =getIntent().getStringExtra("id"); // fait plutot avec SharedPreference car si l'user se connecte directement il ne rentre pas ses logs

            String id = sharedPref.getString("id".trim(), "NotFound"); //NotFound est la valeur par défaut si rien n'est trouvé
            System.out.println("valeur identifiant donnée: " + id);
            ci = new GetInfosUser().execute(id).get();


            Toast.makeText(getApplicationContext(), "Données du client bien recues " + ci.getAge() + " " + ci.getNom() + " " + ci.getPrenom(), Toast.LENGTH_LONG).show();
           /* for(String categ : ci.getCategories())
            {
                Toast.makeText(getApplicationContext(), categ, Toast.LENGTH_LONG).show();

            }*/
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        final FrameLayout fm = (FrameLayout) findViewById(R.id.nav_view);
        final FrameLayout fm2 = (FrameLayout) findViewById(R.id.nav_viewright);

        Button buttonEditProfile = (Button) findViewById(R.id.button2);

        // targetImage = (ImageView)findViewById(R.id.imageView);


        //ImageButton navLauncher = (ImageButton) findViewById(R.id.imageButton3);
        // Button buttonright = (Button) findViewById(R.id.buttonnav);
        mesItems = getResources().getStringArray(R.array.items);
        mesCommerces = getResources().getStringArray(R.array.commerces);
        reseauList = (ListView) findViewById(R.id.left_drawer);
        reseauList.setAdapter(new ArrayAdapter<String>(this, R.layout.navline, mesItems));
        reseauListRight = (ListView) findViewById(R.id.left_drawer2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.navline, mesCommerces);
        reseauListRight.setAdapter(adapter);
        reseauListRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getApplicationContext(), view.getId() + " " + position + " " + id, Toast.LENGTH_LONG).show();
                if (position == 0) {


                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(MainAccount.this,
                                Manifest.permission.ACCESS_FINE_LOCATION)) {


                            // Show an explanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.

                        }else {

                            //ActivityCompat.requestPermissions(MainAccount.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                            // No explanation needed, we can request the permission.
                            /*ActivityCompat.requestPermissions(MainAccount.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    1);*/ //la constante est utilisée pour attacher un requestPermission particulier
                            //à un onRequestPermissionResult (resultCode)

                            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                            // app-defined int constant. The callback method gets the
                            // result of the request.
                        }

                        return;
                    }



                    String locationProvider = LocationManager.GPS_PROVIDER;

                    Location location = locationManager.getLastKnownLocation(locationProvider);
                    mapOrNotif=0; // indication pour le onrequestLocation callback
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
                            Intent map= new Intent(getApplicationContext(), MapsActivity.class);
                            map.putStringArrayListExtra("Com", proxCom);
                            startActivity(map);

                        }
                    else {

                            criteria = new Criteria();
                            bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();
                            Toast.makeText(getApplicationContext(), "Lancement location updates", Toast.LENGTH_LONG);
                            locationManager.requestLocationUpdates(bestProvider, 1000, 0, MainAccount.this);
                        }



                }
                else if(position==1) {
                    mapOrNotif=1; //indication pour le onRequestLocation callback
                    String ident = sharedPref.getString("id".trim(), "NotFound"); //NotFound est la valeur par défaut si rien n'est trouvé

                    Vector<String> listNotifs = new Vector<String>();
                    String locationProvider = LocationManager.GPS_PROVIDER;

                    Location location = locationManager.getLastKnownLocation(locationProvider);

                    //Ci-dessous très important, sinon la carte ne fonctionne qu'une fois car le requestLocationUpdates ne s'effectue plus, surement car c'est a cause de l'emulateur
                    if (location != null) {
                        Double latitude = location.getLatitude();
                        Double longitude = location.getLongitude();
                        ll = new LatitudeLongitude(ident, latitude, longitude);


                        try {
                            listNotifs = new GetNotifs().execute(ll).get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                        final PersonalizedDialog dialog = new PersonalizedDialog(MainAccount.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.see_notifs);
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.setCancelable(true);

                        ListView listnotifs = (ListView) dialog.findViewById(R.id.listViewSeeNotifs);
                        ArrayList<String> list = new ArrayList<String>();
                        for(String notif : listNotifs)
                        {
                            list.add(notif);
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
                        listnotifs.setAdapter(adapter);


                        Button btnBtmLeft = (Button) dialog.findViewById(R.id.btnBtmLeft);
                        btnBtmLeft.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                            }
                        });
                        DisplayMetrics displayMetrics = MainAccount.this.getResources().getDisplayMetrics();
                        int dialogWidth = (int)(displayMetrics.widthPixels * 0.85);
                        int dialogHeight = (int)(displayMetrics.heightPixels * 0.35);
                        dialog.getWindow().setLayout(dialogWidth, dialogHeight);

                        dialog.show();
                    }
                    else
                    {
                        criteria = new Criteria();
                        bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();
                        Toast.makeText(getApplicationContext(), "Lancement location updates", Toast.LENGTH_LONG);
                        locationManager.requestLocationUpdates(bestProvider, 1000, 0, MainAccount.this);
                    }
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
        ImageButton buttonleft = (ImageButton) findViewById(R.id.buttonnav);
       buttonleft.setMinimumWidth(screenWidth/3);
        buttonleft.setMinimumHeight(screenHeight/7);
        buttonleft.setScaleY((float) 1.50);
        buttonleft.setScaleX((float)1.1);


        /** Ajustement du bouton du milieu **/
        ImageButton buttonmiddle = (ImageButton) findViewById(R.id.button3);
        buttonmiddle.setMinimumWidth(screenWidth/3);
        buttonmiddle.setMinimumHeight(screenHeight/7);
        buttonmiddle.setScaleY((float) 1.5);
        buttonmiddle.setScaleX((float)1.1);

        /** Ajustement du bouton en bas a droite **/
        ImageButton buttonright = (ImageButton) findViewById(R.id.button4);
        buttonright.setMinimumWidth(screenWidth/3);
        buttonright.setMinimumHeight(screenHeight/7);
        buttonright.setScaleY((float) 1.5);
        buttonright.setScaleX((float)1.1);

        /** Ajuqtement de la photo de profil **/
        //ImageView picture= (ImageView) findViewById(R.id.imageView2);
        //picture.setMinimumWidth((int) (screenWidth/(2)));
        //picture.setMinimumHeight((int)(screenHeight /(3)));
       // picture.setScaleX(4);
        //picture.setScaleY(4);

        TextView description= (TextView)findViewById(R.id.textViewDescription);
        description.setText(ci.getPrenom()+" "+ci.getNom()+" "+ci.getAge()+" ans, de "+ci.getVille());

        TextView interest= (TextView) findViewById(R.id.textViewInterest);
        String str= "Intéressé par ";

        for(String i : ci.getCategories())
        {
        str+=i+" ";
        }
        interest.setText(str);
        Button buttonDeco = (Button) findViewById(R.id.buttonDeconnexion);

        buttonDeco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedPref.edit();

                 editor.putInt("Log".trim(), -1);
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

        /** onClick du bouton du milieu**/

        buttonmiddle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productList = new Intent(MainAccount.this, ProductListActivity.class);
                startActivityForResult(productList, PRODUCT_CHOICE);


            }
        });
        /** Onclick du bouton droit **/
        buttonright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDrawerLayout.openDrawer(fm2);
            }
        });

        buttonEditProfile.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                System.out.println("Je suis dans le intent");

              /*  // TODO Auto-generated method stub
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
                System.out.println("intent lancé");
                Toast.makeText(getApplicationContext(), "cliqué ", Toast.LENGTH_LONG).show();*/

            }});

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == PRODUCT_CHOICE)
        {
        Toast.makeText(this, "dans le onActivityResult", Toast.LENGTH_LONG);
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

        if(mapOrNotif==0) {
            ll= new LatitudeLongitude(latitude, longitude);

            ArrayList<String> proxCom = null;
            try {
                proxCom = new GetProxCom().execute(ll).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            Intent map = new Intent(getApplicationContext(), MapsActivity.class);
            map.putStringArrayListExtra("Com", proxCom);
            startActivity(map);
        }
        else
        {
            final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

            String ident = sharedPref.getString("id".trim(), "NotFound"); //NotFound est la valeur par défaut si rien n'est trouvé
            ll= new LatitudeLongitude(ident, latitude, longitude);
            Vector<String> listNotifs = new Vector<String>();
            try {
                listNotifs = new GetNotifs().execute(ll).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            final PersonalizedDialog dialog = new PersonalizedDialog(MainAccount.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.see_notifs);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(true);

            ListView listnotifs = (ListView) dialog.findViewById(R.id.listViewSeeNotifs);
            ArrayList<String> list = new ArrayList<String>();
            for(String notif : listNotifs)
            {
                list.add(notif);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
            listnotifs.setAdapter(adapter);


            Button btnBtmLeft = (Button) dialog.findViewById(R.id.btnBtmLeft);
            btnBtmLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
            DisplayMetrics displayMetrics = MainAccount.this.getResources().getDisplayMetrics();
            int dialogWidth = (int)(displayMetrics.widthPixels * 0.85);
            int dialogHeight = (int)(displayMetrics.heightPixels * 0.35);
            dialog.getWindow().setLayout(dialogWidth, dialogHeight);

            dialog.show();
        }
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
}
