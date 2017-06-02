package com.example.kevin.paramedication.DatabaseRessources;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;

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


        /**
         * Defines and creates Disease Table
         * Schema:
         * ID|Disease Name
         */
        final String SQL_CREATE_DISEASE_TABLE = "CREATE TABLE " + DiseaseTableContract.DiseaseTableEntry.TABLE_NAME + " (" +
                DiseaseTableContract.DiseaseTableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DiseaseTableContract.DiseaseTableEntry.DISEASE_NAME + " TEXT NOT NULL, " +
                "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_DISEASE_TABLE);


        /**
         * Defines and creates Blood Table
         * ID|LeukocyteMin|LeukocyteMax|ErythrocyteMin|ErythrocyteMax|HemoglobinMin|
         * HemoglobinMax|HematocritMin|HematocritMax|MCVMin|MCVMax|MCHMin|MCHMax|
         * MCHCMin|MCHCMax|PlateletMin|PlateletMax|ReticulocytesMin|ReticulocytesMax|
         * MPVMin|MPVMax|RDWMin|RDWMax
         */
        final String SQL_CREATE_BLOOD_TABLE = "CREATE TABLE " + BloodTableContract.BloodTableEntry.TABLE_NAME + " (" +
                BloodTableContract.BloodTableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                BloodTableContract.BloodTableEntry.LEUKOCYTE_MIN + " TEXT NOT NULL, " +
                BloodTableContract.BloodTableEntry.LEUKOCYTE_MAX + " TEXT NOT NULL, " +
                BloodTableContract.BloodTableEntry.ERYTHROCYTE_MIN + " TEXT NOT NULL, " +
                BloodTableContract.BloodTableEntry.ERYTHROCYTE_MAX + " TEXT NOT NULL, " +
                BloodTableContract.BloodTableEntry.HEMOGLOBIN_MIN + " TEXT NOT NULL, " +
                BloodTableContract.BloodTableEntry.HEMOGLOBIN_MAX + " TEXT NOT NULL, " +
                BloodTableContract.BloodTableEntry.HEMATOCRIT_MIN + " TEXT NOT NULL, " +
                BloodTableContract.BloodTableEntry.HEMATOCRIT_MAX + " TEXT NOT NULL, " +
                BloodTableContract.BloodTableEntry.MCV_Min + " TEXT NOT NULL, " +
                BloodTableContract.BloodTableEntry.MCV_Max + " TEXT NOT NULL, " +
                BloodTableContract.BloodTableEntry.MCH_Min + " TEXT NOT NULL, " +
                BloodTableContract.BloodTableEntry.MCH_Max + " TEXT NOT NULL, " +
                BloodTableContract.BloodTableEntry.MCHC_Min + " TEXT NOT NULL, " +
                BloodTableContract.BloodTableEntry.MCHC_Max + " TEXT NOT NULL, " +
                BloodTableContract.BloodTableEntry.PLATELET_MIN + " TEXT NOT NULL, " +
                BloodTableContract.BloodTableEntry.PLATELET_MAX + " TEXT NOT NULL, " +
                BloodTableContract.BloodTableEntry.RETICULOCYTES_MIN + " TEXT NOT NULL, " +
                BloodTableContract.BloodTableEntry.RETICULOCYTES_MAX + " TEXT NOT NULL, " +
                BloodTableContract.BloodTableEntry.MPV_MIN + " TEXT NOT NULL, " +
                BloodTableContract.BloodTableEntry.MPV_MAX + " TEXT NOT NULL, " +
                BloodTableContract.BloodTableEntry.RDW_MIN + " TEXT NOT NULL, " +
                BloodTableContract.BloodTableEntry.RDW_MAX + " TEXT NOT NULL, " +
                "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_BLOOD_TABLE);


        /**
         * Defines and creates Patient Table
         * Schema:
         * ID|HostpitalID
         */
        final String SQL_CREATE_PATIENT_TABLE = "CREATE TABLE " + PatientTableContract.PatientTableEntry.TABLE_NAME+ " (" +
                PatientTableContract.PatientTableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PatientTableContract.PatientTableEntry.HOSPITAL_ID + " INTEGER NOT NULL, " +
                "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_DISEASE_TABLE);


        /**
         * Defines and creates Medication Table
         * Schema:
         * ID|Drug name
         */
        final String SQL_CREATE_MEDICATION_TABLE = "CREATE TABLE " + MedicationTableContract.MedicationTableEntry.TABLE_NAME + " (" +
                MedicationTableContract.MedicationTableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MedicationTableContract.MedicationTableEntry.DRUG_NAME + " TEXT NOT NULL, " +
                "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_MEDICATION_TABLE);


        /**
         * Defines and creates Disease Blood Relation Table
         * Schema:
         * ID|DiseaseID|BloodID
         */
        final String SQL_CREATE_DISEASE_BLOOD_TABLE = "CREATE TABLE " + DiseaseBloodTableContract.DiseaseBloodEntry.TABLE_NAME + " (" +
                DiseaseBloodTableContract.DiseaseBloodEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DiseaseBloodTableContract.DiseaseBloodEntry.DISEASE_ID + " INTEGER NOT NULL, " +
                DiseaseBloodTableContract.DiseaseBloodEntry.BLOOD_ID + " INTEGER NOT NULL, " +
                "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_DISEASE_BLOOD_TABLE);


        /**
         * Defines and creates Disease Medication Relation Table
         * Schema:
         * ID|DiseaseID|DrugID
         */
        final String SQL_CREATE_DISEASE_MEDICATION_TABLE = "CREATE TABLE " + DiseaseMedicationTableContract.DiseaseMedicationEntry.TABLE_NAME + " (" +
                DiseaseMedicationTableContract.DiseaseMedicationEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DiseaseMedicationTableContract.DiseaseMedicationEntry.DiseaseID + " INTEGER NOT NULL, " +
                DiseaseMedicationTableContract.DiseaseMedicationEntry.DrugID + " INTEGER NOT NULL, " +
                "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_DISEASE_MEDICATION_TABLE);


        /**
         * Defines and creates Medication Medication relation Table
         * Schema:
         * ID|DrugID1|DrugID2|type of interaction
         */
        final String SQL_CREATE_MEDICATION_INTERACTION_TABLE = "CREATE TABLE " + MedicationInteractionTableContract.MedicationInteractionEntry.TABLE_NAME + " (" +
                MedicationInteractionTableContract.MedicationInteractionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MedicationInteractionTableContract.MedicationInteractionEntry.DRUG_ID1 + " INTEGER NOT NULL, " +
                MedicationInteractionTableContract.MedicationInteractionEntry.DRUG_ID2 + " INTEGER NOT NULL, " +
                MedicationInteractionTableContract.MedicationInteractionEntry.TYPE_OF_INTERACTION + " TEXT NOT NULL, " +
                "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_MEDICATION_INTERACTION_TABLE);


        /**
         * Defines and creates Patient Medication Relation Table
         * Schema:
         * ID|PatientID|DrugID
         */
        final String SQL_CREATE_PATIENT_MEDICATION_TABLE = "CREATE TABLE " + PatientMedicationTableContract.PatientMedicationTableEntry.TABLE_NAME + " (" +
                PatientMedicationTableContract.PatientMedicationTableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PatientMedicationTableContract.PatientMedicationTableEntry.PATIENT_ID + " INTEGER NOT NULL, " +
                PatientMedicationTableContract.PatientMedicationTableEntry.DRUG_ID + " INTEGER NOT NULL, " +
                "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_PATIENT_MEDICATION_TABLE);

        /**
         * Defines and creates Patient Blood Relation Table
         * Schema:
         * ID|PatientID|BloodID
         */
        final String SQL_CREATE_PATIENT_BLOOD_TABLE = "CREATE TABLE " + PatientBloodTableContract.PatientBloodTableEntry.TABLE_NAME + " (" +
                PatientBloodTableContract.PatientBloodTableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PatientBloodTableContract.PatientBloodTableEntry.PATIENT_ID + " INTEGER NOT NULL, " +
                PatientBloodTableContract.PatientBloodTableEntry.BLOOD_ID + " INTEGER NOT NULL, " +
                "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_PATIENT_BLOOD_TABLE);


        /**
         * Defines and creates Patient Disease Relation Table
         * Schema:
         * ID|PatientID|BloodID
         */
        final String SQL_CREATE_PATIENT_DISEASE_TABLE = "CREATE TABLE " + PatientDiseaseTableContract.PatientDiseaseTableEntry.TABLE_NAME + " (" +
                PatientDiseaseTableContract.PatientDiseaseTableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PatientDiseaseTableContract.PatientDiseaseTableEntry.PATIENT_ID + " INTEGER NOT NULL, " +
                PatientDiseaseTableContract.PatientDiseaseTableEntry.DISEASE_ID + " INTEGER NOT NULL, " +
                "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_PATIENT_DISEASE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // For now simply drop the table and create a new one. This means if you change the
        // DATABASE_VERSION the table will be dropped.
        // In a production app, this method might be modified to ALTER the table
        // instead of dropping it, so that existing data is not deleted.
        ArrayList<String> TableList = new ArrayList<String>();
        TableList.addAll(Arrays.asList(
                DiseaseMedicationTableContract.DiseaseMedicationEntry.TABLE_NAME,
                DiseaseTableContract.DiseaseTableEntry.TABLE_NAME,
                MedicationInteractionTableContract.MedicationInteractionEntry.TABLE_NAME,
                MedicationTableContract.MedicationTableEntry.TABLE_NAME));
        for (int j = 0; j < TableList.size(); j++) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TableList.get(j));
        }
        onCreate(sqLiteDatabase);
    }
}
