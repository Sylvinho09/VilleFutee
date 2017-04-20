package com.example.sylvinho.villefutee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Button;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.content.Context;
import android.widget.Toast;

public class MainAccount extends AppCompatActivity {
    private String[] mesItems;
    private String[] mesCommerces;
    private ListView reseauList;
    private ListView reseauListRight;
    /** liste des produits préférés (stockés dans un fichier qui doit être lu ou créé dans le onCreate **/
    private String[] produitsPrefs;
    static final int PRODUCT_CHOICE =1; /** identifiant de l'intent généré **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_account);
        final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        final FrameLayout fm = (FrameLayout) findViewById(R.id.nav_view);
        final FrameLayout fm2 = (FrameLayout) findViewById(R.id.nav_viewright);

        //ImageButton navLauncher = (ImageButton) findViewById(R.id.imageButton3);
        // Button buttonright = (Button) findViewById(R.id.buttonnav);
        mesItems = getResources().getStringArray(R.array.items);
        mesCommerces = getResources().getStringArray(R.array.commerces);
        reseauList = (ListView) findViewById(R.id.left_drawer);
        reseauList.setAdapter(new ArrayAdapter<String>(this, R.layout.navline, mesItems));
        reseauListRight = (ListView) findViewById(R.id.left_drawer2);
        reseauListRight.setAdapter(new ArrayAdapter<String>(this, R.layout.navline, mesCommerces));

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
    }

    /** Fonctionnement : la nouvelle activité écrira dans le fichier "produitsPrefs" seulement quand le client
     appuiera sur "Valider", retournant RESULT_HAS_CHANGED si au moins 1 produit a été retiré/ajouté et donc
     on devra mettre à jour la liste des produits préférés du client. Sinon retourne RESULT_UNCHANGED et on ne fait rien. **/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == PRODUCT_CHOICE)
        {
        Toast.makeText(this, "dans le onActivityResult", Toast.LENGTH_LONG);
        }
    }
}
