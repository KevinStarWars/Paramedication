package com.example.kevin.paramedication.DatabaseRessources;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Helper for Creating and initializing the SQLite Database
 */

public class DBHelper extends SQLiteOpenHelper{

    // database name
    private static final String DATABASE_NAME = "paramedication.db";
    // if you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 1;
    //Constructor
    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){

        // Creates Disease Table
        final String SQL_CREATE_DISEASE_TABLE = "CREATE TABLE " + DiseaseTableContract.DiseaseTableEntry.TABLE_NAME + " (" +
                DiseaseTableContract.DiseaseTableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DiseaseTableContract.DiseaseTableEntry.DISEASE_NAME + " TEXT NOT NULL, " +
                DiseaseTableContract.DiseaseTableEntry.LEUKOCYTE_MIN + " TEXT NOT NULL, " +
                DiseaseTableContract.DiseaseTableEntry.LEUKOCYTE_MAX + " TEXT NOT NULL, " +
                DiseaseTableContract.DiseaseTableEntry.ERYTHROCYTE_MIN + " TEXT NOT NULL, " +
                DiseaseTableContract.DiseaseTableEntry.ERYTHROCYTE_MAX + " TEXT NOT NULL, " +
                DiseaseTableContract.DiseaseTableEntry.HEMOGLOBIN_MIN + " TEXT NOT NULL, " +
                DiseaseTableContract.DiseaseTableEntry.HEMOGLOBIN_MAX + " TEXT NOT NULL, " +
                DiseaseTableContract.DiseaseTableEntry.HEMATOCRIT_MIN + " TEXT NOT NULL, " +
                DiseaseTableContract.DiseaseTableEntry.HEMATOCRIT_MAX + " TEXT NOT NULL, " +
                DiseaseTableContract.DiseaseTableEntry.MCV_Min + " TEXT NOT NULL, " +
                DiseaseTableContract.DiseaseTableEntry.MCV_Max + " TEXT NOT NULL, " +
                DiseaseTableContract.DiseaseTableEntry.MCH_Min + " TEXT NOT NULL, " +
                DiseaseTableContract.DiseaseTableEntry.MCH_Max + " TEXT NOT NULL, " +
                DiseaseTableContract.DiseaseTableEntry.MCHC_Min + " TEXT NOT NULL, " +
                DiseaseTableContract.DiseaseTableEntry.MCHC_Max + " TEXT NOT NULL, " +
                DiseaseTableContract.DiseaseTableEntry.PLATELET_MIN + " TEXT NOT NULL, " +
                DiseaseTableContract.DiseaseTableEntry.PLATELET_MAX + " TEXT NOT NULL, " +
                DiseaseTableContract.DiseaseTableEntry.RETICULOCYTES_MIN + " TEXT NOT NULL, " +
                DiseaseTableContract.DiseaseTableEntry.RETICULOCYTES_MAX + " TEXT NOT NULL, " +
                DiseaseTableContract.DiseaseTableEntry.MPV_MIN + " TEXT NOT NULL, " +
                DiseaseTableContract.DiseaseTableEntry.MPV_MAX + " TEXT NOT NULL, " +
                DiseaseTableContract.DiseaseTableEntry.RDW_MIN + " TEXT NOT NULL, " +
                DiseaseTableContract.DiseaseTableEntry.RDW_MAX + " TEXT NOT NULL, " +
                "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_DISEASE_TABLE);

        // Creates Disease Medication Table
        final String SQL_CREATE_DISEASE_MEDICATION_TABLE = "CREATE TABLE " + DiseaseMedicationTableContract.DiseaseMedicationEntry.TABLE_NAME + " (" +
                DiseaseMedicationTableContract.DiseaseMedicationEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DiseaseMedicationTableContract.DiseaseMedicationEntry.DiseaseID + " TEXT NOT NULL, " +
                DiseaseMedicationTableContract.DiseaseMedicationEntry.DrugID + " INTEGER NOT NULL, " +
                "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_DISEASE_MEDICATION_TABLE);

        // creates Medication interaction table
        final String SQL_CREATE_MEDICATION_INTERACTION_TABLE = "CREATE TABLE " + MedicationInteractionTableContract.MedicationInteractionEntry.TABLE_NAME + " (" +
                MedicationInteractionTableContract.MedicationInteractionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MedicationInteractionTableContract.MedicationInteractionEntry.DRUG_ID1 + " INTEGER NOT NULL, " +
                MedicationInteractionTableContract.MedicationInteractionEntry.DRUG_ID2 + " INTEGER NOT NULL, " +
                MedicationInteractionTableContract.MedicationInteractionEntry.TYPE_OF_INTERACTION + " TEXT NOT NULL, " +
                "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_MEDICATION_INTERACTION_TABLE);

        // creates medication table
        final String SQL_CREATE_MEDICATION_TABLE = "CREATE TABLE " + MedicationTableContract.MedicationTableEntry.TABLE_NAME + " (" +
                MedicationTableContract.MedicationTableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MedicationTableContract.MedicationTableEntry.DRUG_NAME + " TEXT NOT NULL, " +
                "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_MEDICATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // For now simply drop the table and create a new one. This means if you change the
        // DATABASE_VERSION the table will be dropped.
        // In a production app, this method might be modified to ALTER the table
        // instead of dropping it, so that existing data is not deleted.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DiseaseMedicationTableContract.DiseaseMedicationEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
