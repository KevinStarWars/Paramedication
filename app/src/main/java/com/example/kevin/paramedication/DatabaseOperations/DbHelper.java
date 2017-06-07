package com.example.kevin.paramedication.DatabaseOperations;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.kevin.paramedication.DatabaseContracts.BloodTableContract;
import com.example.kevin.paramedication.DatabaseContracts.DiseaseBloodTableContract;
import com.example.kevin.paramedication.DatabaseContracts.DiseaseMedicationTableContract;
import com.example.kevin.paramedication.DatabaseContracts.DiseaseTableContract;
import com.example.kevin.paramedication.DatabaseContracts.MedicationInteractionTableContract;
import com.example.kevin.paramedication.DatabaseContracts.MedicationTableContract;
import com.example.kevin.paramedication.DatabaseContracts.PatientBloodTableContract;
import com.example.kevin.paramedication.DatabaseContracts.PatientDiseaseTableContract;
import com.example.kevin.paramedication.DatabaseContracts.PatientMedicationTableContract;
import com.example.kevin.paramedication.DatabaseContracts.PatientTableContract;

/**
 * Helper for Creating and initializing the SQLite Database
 */

class DbHelper extends SQLiteOpenHelper{

    private static final String LOG_TAG = DbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "paramedication.db";
    private static final int DATABASE_VERSION = 1;

    private final String SQL_CREATE_PATIENT_DISEASE_TABLE = "CREATE TABLE " + PatientDiseaseTableContract.PatientDiseaseTableEntry.TABLE_NAME + " (" +
            PatientDiseaseTableContract.PatientDiseaseTableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            PatientDiseaseTableContract.PatientDiseaseTableEntry.PATIENT_ID + " INTEGER NOT NULL, " +
            PatientDiseaseTableContract.PatientDiseaseTableEntry.DISEASE_ID + " INTEGER NOT NULL" +
            "); ";


    private final String SQL_CREATE_BLOOD_TABLE = "CREATE TABLE " + BloodTableContract.BloodTableEntry.TABLE_NAME + " (" +
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
            BloodTableContract.BloodTableEntry.RDW_MAX + " TEXT NOT NULL" +
            "); ";


    private final String SQL_CREATE_DISEASE_TABLE = "CREATE TABLE " + DiseaseTableContract.DiseaseTableEntry.TABLE_NAME + " (" +
            DiseaseTableContract.DiseaseTableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DiseaseTableContract.DiseaseTableEntry.DISEASE_NAME + " TEXT NOT NULL" +
            "); ";


    private final String SQL_CREATE_PATIENT_TABLE = "CREATE TABLE " + PatientTableContract.PatientTableEntry.TABLE_NAME+ " (" +
            PatientTableContract.PatientTableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            PatientTableContract.PatientTableEntry.HOSPITAL_ID + " INTEGER NOT NULL" +
            "); ";


    private final String SQL_CREATE_MEDICATION_TABLE = "CREATE TABLE " + MedicationTableContract.MedicationTableEntry.TABLE_NAME + " (" +
            MedicationTableContract.MedicationTableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            MedicationTableContract.MedicationTableEntry.DRUG_NAME + " TEXT NOT NULL" +
            "); ";


    private final String SQL_CREATE_DISEASE_BLOOD_TABLE = "CREATE TABLE " + DiseaseBloodTableContract.DiseaseBloodEntry.TABLE_NAME + " (" +
            DiseaseBloodTableContract.DiseaseBloodEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DiseaseBloodTableContract.DiseaseBloodEntry.DISEASE_ID + " INTEGER NOT NULL, " +
            DiseaseBloodTableContract.DiseaseBloodEntry.BLOOD_ID + " INTEGER NOT NULL" +
            "); ";


    private final String SQL_CREATE_DISEASE_MEDICATION_TABLE = "CREATE TABLE " + DiseaseMedicationTableContract.DiseaseMedicationEntry.TABLE_NAME + " (" +
            DiseaseMedicationTableContract.DiseaseMedicationEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DiseaseMedicationTableContract.DiseaseMedicationEntry.DiseaseID + " INTEGER NOT NULL, " +
            DiseaseMedicationTableContract.DiseaseMedicationEntry.DrugID + " INTEGER NOT NULL" +
            "); ";


    private final String SQL_CREATE_MEDICATION_INTERACTION_TABLE = "CREATE TABLE " + MedicationInteractionTableContract.MedicationInteractionEntry.TABLE_NAME + " (" +
            MedicationInteractionTableContract.MedicationInteractionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            MedicationInteractionTableContract.MedicationInteractionEntry.DRUG_ID1 + " INTEGER NOT NULL, " +
            MedicationInteractionTableContract.MedicationInteractionEntry.DRUG_ID2 + " INTEGER NOT NULL, " +
            MedicationInteractionTableContract.MedicationInteractionEntry.TYPE_OF_INTERACTION + " TEXT NOT NULL" +
            "); ";


    private final String SQL_CREATE_PATIENT_MEDICATION_TABLE = "CREATE TABLE " + PatientMedicationTableContract.PatientMedicationTableEntry.TABLE_NAME + " (" +
            PatientMedicationTableContract.PatientMedicationTableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            PatientMedicationTableContract.PatientMedicationTableEntry.PATIENT_ID + " INTEGER NOT NULL, " +
            PatientMedicationTableContract.PatientMedicationTableEntry.DRUG_ID + " INTEGER NOT NULL" +
            "); ";


    private final String SQL_CREATE_PATIENT_BLOOD_TABLE = "CREATE TABLE " + PatientBloodTableContract.PatientBloodTableEntry.TABLE_NAME + " (" +
            PatientBloodTableContract.PatientBloodTableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            PatientBloodTableContract.PatientBloodTableEntry.PATIENT_ID + " INTEGER NOT NULL, " +
            PatientBloodTableContract.PatientBloodTableEntry.BLOOD_ID + " INTEGER NOT NULL" +
            "); ";

    DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        try {
            sqLiteDatabase.execSQL(SQL_CREATE_DISEASE_TABLE);
            sqLiteDatabase.execSQL(SQL_CREATE_BLOOD_TABLE);
            sqLiteDatabase.execSQL(SQL_CREATE_PATIENT_TABLE);
            sqLiteDatabase.execSQL(SQL_CREATE_MEDICATION_TABLE);
            sqLiteDatabase.execSQL(SQL_CREATE_DISEASE_BLOOD_TABLE);
            sqLiteDatabase.execSQL(SQL_CREATE_DISEASE_MEDICATION_TABLE);
            sqLiteDatabase.execSQL(SQL_CREATE_MEDICATION_INTERACTION_TABLE);
            sqLiteDatabase.execSQL(SQL_CREATE_PATIENT_MEDICATION_TABLE);
            sqLiteDatabase.execSQL(SQL_CREATE_PATIENT_BLOOD_TABLE);
            sqLiteDatabase.execSQL(SQL_CREATE_PATIENT_DISEASE_TABLE);

            Log.d(LOG_TAG, "Tables created");
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Error creating tables: " + e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }
}
