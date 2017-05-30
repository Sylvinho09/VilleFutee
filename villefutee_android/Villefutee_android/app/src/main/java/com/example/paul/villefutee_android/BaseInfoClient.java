package com.example.paul.villefutee_android;



import android.content.Context;

import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteDatabase.CursorFactory;

import android.database.sqlite.SQLiteOpenHelper;



/**

 * Created by Paul on 29/05/2017.

 */



public class BaseInfoClient extends SQLiteOpenHelper {



    private static final String TABLE_RESEAUX = "table_reseaux";

    private static final String COL_IDRESEAUX = "IDRESEAUX";

    private static final String COL_NOM = "NOM";

    private static final String COL_DESCRIPTION = "DESCRIPTION";

    private static final String COL_POLITIQUEJOIN = "POLITIQUEJOIN";



    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_RESEAUX + " ("

            + COL_IDRESEAUX + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NOM + " TEXT NOT NULL, "

            + COL_DESCRIPTION + " TEXT NOT NULL,"+ COL_POLITIQUEJOIN + " TEXT NOT NULL);";



    public BaseInfoClient(Context context, String nom, CursorFactory

            cursorfactory, int version) {

        super(context, nom, cursorfactory, version);

    }

    @Override

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_BDD);

    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE " + TABLE_RESEAUX + ";");

        onCreate(db);

    }

}