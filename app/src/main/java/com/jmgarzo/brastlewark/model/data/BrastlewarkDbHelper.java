package com.jmgarzo.brastlewark.model.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jmgarzo on 2/22/2018.
 */

public class BrastlewarkDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "brastlewark.db";

    private final String SQL_CREATE_INHABITANTS_TABLE =
            "CREATE TABLE " + BrastlewarkContract.InhabitantsEntry.TABLE_NAME + " ( " +
                    BrastlewarkContract.InhabitantsEntry._ID + " INTEGER PRIMARY KEY , " +
                    BrastlewarkContract.InhabitantsEntry.NAME + " TEXT NOT NULL, " +
                    BrastlewarkContract.InhabitantsEntry.THUMBNAIL + " TEXT NOT NULL, " +
                    BrastlewarkContract.InhabitantsEntry.AGE + " INTEGER NOT NULL, " +
                    BrastlewarkContract.InhabitantsEntry.WEIGHT + " REAL NOT NULL, " +
                    BrastlewarkContract.InhabitantsEntry.HEIGHT + " REAL NOT NULL, " +
                    BrastlewarkContract.InhabitantsEntry.HAIR_COLOR + " TEXT NOT NULL " +
                    " );";


    private final String SQL_CREATE_PROFESSIONS_TABLE =
            "CREATE TABLE " + BrastlewarkContract.ProfessionsEntry.TABLE_NAME + " ( " +
                    BrastlewarkContract.ProfessionsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                    BrastlewarkContract.InhabitantsEntry.NAME + " TEXT  UNIQUE NOT NULL " +
                    " );";

    private final String SQL_CREATE_INHABITANT_PROFESSION =
            "CREATE TABLE " + BrastlewarkContract.InhabitantProfessionEntry.TABLE_NAME + " ( " +
                    BrastlewarkContract.InhabitantProfessionEntry.INHABITANT_ID + " INTEGER , " +
                    BrastlewarkContract.InhabitantProfessionEntry.PROFESSION_ID + " INTEGER, " +
                    " PRIMARY KEY( " +
                    BrastlewarkContract.InhabitantProfessionEntry.INHABITANT_ID + " , " +
                    BrastlewarkContract.InhabitantProfessionEntry.PROFESSION_ID +
                    " ), " +
                    " FOREIGN KEY( " + BrastlewarkContract.InhabitantProfessionEntry.INHABITANT_ID + " ) REFERENCES " +
                    BrastlewarkContract.InhabitantsEntry.TABLE_NAME + " ( " + BrastlewarkContract.InhabitantsEntry._ID + ") ON DELETE CASCADE, " +
                    "FOREIGN KEY ( " + BrastlewarkContract.InhabitantProfessionEntry.PROFESSION_ID + " ) REFERENCES " +
                    BrastlewarkContract.ProfessionsEntry.TABLE_NAME + " ( " + BrastlewarkContract.ProfessionsEntry._ID + ") ON DELETE CASCADE " +
                    ");";


    public BrastlewarkDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_INHABITANTS_TABLE);
        db.execSQL(SQL_CREATE_PROFESSIONS_TABLE);
        db.execSQL(SQL_CREATE_INHABITANT_PROFESSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + BrastlewarkContract.InhabitantsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BrastlewarkContract.ProfessionsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BrastlewarkContract.InhabitantProfessionEntry.TABLE_NAME);
    }
}
