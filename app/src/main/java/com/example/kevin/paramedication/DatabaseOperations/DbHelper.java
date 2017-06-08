package com.example.kevin.paramedication.DatabaseOperations;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.kevin.paramedication.DatabaseContracts.BloodCountTableContract;
import com.example.kevin.paramedication.DatabaseContracts.BloodTableContract;
import com.example.kevin.paramedication.DatabaseContracts.DiseaseBloodTableContract;
import com.example.kevin.paramedication.DatabaseContracts.DiseaseMedicationTableContract;
import com.example.kevin.paramedication.DatabaseContracts.DiseaseTableContract;
import com.example.kevin.paramedication.DatabaseContracts.MedicationInteractionTableContract;
import com.example.kevin.paramedication.DatabaseContracts.MedicationTableContract;
import com.example.kevin.paramedication.DatabaseContracts.PatientBloodcountTableContract;
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
            PatientDiseaseTableContract.PatientDiseaseTableEntry.COLUMN_PATIENT_ID + " INTEGER NOT NULL, " +
            PatientDiseaseTableContract.PatientDiseaseTableEntry.COLUMN_DISEASE_ID + " INTEGER NOT NULL" +
            "); ";


    private final String SQL_CREATE_BLOOD_TABLE = "CREATE TABLE " + BloodTableContract.BloodTableEntry.TABLE_NAME + " (" +
            BloodTableContract.BloodTableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            BloodTableContract.BloodTableEntry.COLUMN_LEUKOCYTE_MIN + " TEXT NOT NULL, " +
            BloodTableContract.BloodTableEntry.COLUMN_LEUKOCYTE_MAX + " TEXT NOT NULL, " +
            BloodTableContract.BloodTableEntry.COLUMN_ERYTHROCYTE_MIN + " TEXT NOT NULL, " +
            BloodTableContract.BloodTableEntry.COLUMN_ERYTHROCYTE_MAX + " TEXT NOT NULL, " +
            BloodTableContract.BloodTableEntry.COLUMN_HEMOGLOBIN_MIN + " TEXT NOT NULL, " +
            BloodTableContract.BloodTableEntry.COLUMN_HEMOGLOBIN_MAX + " TEXT NOT NULL, " +
            BloodTableContract.BloodTableEntry.COLUMN_HEMATOCRIT_MIN + " TEXT NOT NULL, " +
            BloodTableContract.BloodTableEntry.COLUMN_HEMATOCRIT_MAX + " TEXT NOT NULL, " +
            BloodTableContract.BloodTableEntry.COLUMN_MCV_MIN + " TEXT NOT NULL, " +
            BloodTableContract.BloodTableEntry.COLUMN_MCV_MAX + " TEXT NOT NULL, " +
            BloodTableContract.BloodTableEntry.COLUMN_MCH_MIN + " TEXT NOT NULL, " +
            BloodTableContract.BloodTableEntry.COLUMN_MCH_MAX + " TEXT NOT NULL, " +
            BloodTableContract.BloodTableEntry.COLUMN_MCHC_MIN + " TEXT NOT NULL, " +
            BloodTableContract.BloodTableEntry.COLUMN_MCHC_MAX + " TEXT NOT NULL, " +
            BloodTableContract.BloodTableEntry.COLUMN_PLATELET_MIN + " TEXT NOT NULL, " +
            BloodTableContract.BloodTableEntry.COLUMN_PLATELET_MAX + " TEXT NOT NULL, " +
            BloodTableContract.BloodTableEntry.COLUMN_RETICULOCYTES_MIN + " TEXT NOT NULL, " +
            BloodTableContract.BloodTableEntry.COLUMN_RETICULOCYTES_MAX + " TEXT NOT NULL, " +
            BloodTableContract.BloodTableEntry.COLUMN_MPV_MIN + " TEXT NOT NULL, " +
            BloodTableContract.BloodTableEntry.COLUMN_MPV_MAX + " TEXT NOT NULL, " +
            BloodTableContract.BloodTableEntry.COLUMN_RDW_MIN + " TEXT NOT NULL, " +
            BloodTableContract.BloodTableEntry.COLUMN_RDW_MAX + " TEXT NOT NULL" +
            "); ";

    private final String SQL_CREATE_BLOODCOUNT_TABLE = "CREATE TABLE " + BloodCountTableContract.BloodCountTableEntry.TABLE_NAME + " (" +
            BloodCountTableContract.BloodCountTableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            BloodCountTableContract.BloodCountTableEntry.COLUMN_LEUKOCYTE + " TEXT NOT NULL, " +
            BloodCountTableContract.BloodCountTableEntry.COLUMN_ERYTHROCYTE + " TEXT NOT NULL, " +
            BloodCountTableContract.BloodCountTableEntry.COLUMN_HEMOGLOBIN + " TEXT NOT NULL, " +
            BloodCountTableContract.BloodCountTableEntry.COLUMN_HEMATOCRIT + " TEXT NOT NULL, " +
            BloodCountTableContract.BloodCountTableEntry.COLUMN_MCV + " TEXT NOT NULL, " +
            BloodCountTableContract.BloodCountTableEntry.COLUMN_MCH + " TEXT NOT NULL, " +
            BloodCountTableContract.BloodCountTableEntry.COLUMN_MCHC + " TEXT NOT NULL, " +
            BloodCountTableContract.BloodCountTableEntry.COLUMN_PLATELET + " TEXT NOT NULL, " +
            BloodCountTableContract.BloodCountTableEntry.COLUMN_RETICULOCYTES + " TEXT NOT NULL, " +
            BloodCountTableContract.BloodCountTableEntry.COLUMN_MPV + " TEXT NOT NULL, " +
            BloodCountTableContract.BloodCountTableEntry.COLUMN_RDW + " TEXT NOT NULL" +
            "); ";


    private final String SQL_CREATE_DISEASE_TABLE = "CREATE TABLE " + DiseaseTableContract.DiseaseTableEntry.TABLE_NAME + " (" +
            DiseaseTableContract.DiseaseTableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DiseaseTableContract.DiseaseTableEntry.COLUMN_DISEASE_NAME + " TEXT NOT NULL" +
            "); ";


    private final String SQL_CREATE_PATIENT_TABLE = "CREATE TABLE " + PatientTableContract.PatientTableEntry.TABLE_NAME+ " (" +
            PatientTableContract.PatientTableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            PatientTableContract.PatientTableEntry.COLUMN_HOSPITAL_ID + " INTEGER NOT NULL" +
            "); ";


    private final String SQL_CREATE_MEDICATION_TABLE = "CREATE TABLE " + MedicationTableContract.MedicationTableEntry.TABLE_NAME + " (" +
            MedicationTableContract.MedicationTableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            MedicationTableContract.MedicationTableEntry.COLUMN_DRUG_NAME + " TEXT NOT NULL" +
            "); ";


    private final String SQL_CREATE_DISEASE_BLOOD_TABLE = "CREATE TABLE " + DiseaseBloodTableContract.DiseaseBloodEntry.TABLE_NAME + " (" +
            DiseaseBloodTableContract.DiseaseBloodEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DiseaseBloodTableContract.DiseaseBloodEntry.COLUMN_DISEASE_ID + " INTEGER NOT NULL, " +
            DiseaseBloodTableContract.DiseaseBloodEntry.COLUMN_BLOOD_ID + " INTEGER NOT NULL" +
            "); ";


    private final String SQL_CREATE_DISEASE_MEDICATION_TABLE = "CREATE TABLE " + DiseaseMedicationTableContract.DiseaseMedicationEntry.TABLE_NAME + " (" +
            DiseaseMedicationTableContract.DiseaseMedicationEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DiseaseMedicationTableContract.DiseaseMedicationEntry.COLUMN_DISEASE_ID + " INTEGER NOT NULL, " +
            DiseaseMedicationTableContract.DiseaseMedicationEntry.COLUMN_DRUG_ID + " INTEGER NOT NULL" +
            "); ";


    private final String SQL_CREATE_MEDICATION_INTERACTION_TABLE = "CREATE TABLE " + MedicationInteractionTableContract.MedicationInteractionEntry.TABLE_NAME + " (" +
            MedicationInteractionTableContract.MedicationInteractionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            MedicationInteractionTableContract.MedicationInteractionEntry.COLUMN_DRUG_ID1 + " INTEGER NOT NULL, " +
            MedicationInteractionTableContract.MedicationInteractionEntry.COLUMN_DRUG_ID2 + " INTEGER NOT NULL, " +
            MedicationInteractionTableContract.MedicationInteractionEntry.COLUMN_TYPE_OF_INTERACTION + " TEXT NOT NULL" +
            "); ";


    private final String SQL_CREATE_PATIENT_MEDICATION_TABLE = "CREATE TABLE " + PatientMedicationTableContract.PatientMedicationTableEntry.TABLE_NAME + " (" +
            PatientMedicationTableContract.PatientMedicationTableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            PatientMedicationTableContract.PatientMedicationTableEntry.COLUMN_PATIENT_ID + " INTEGER NOT NULL, " +
            PatientMedicationTableContract.PatientMedicationTableEntry.COLUMN_DRUG_ID + " INTEGER NOT NULL" +
            "); ";


    private final String SQL_CREATE_PATIENT_BLOODCOUNT_TABLE = "CREATE TABLE " + PatientBloodcountTableContract.PatientBloodcountTableEntry.TABLE_NAME + " (" +
            PatientBloodcountTableContract.PatientBloodcountTableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            PatientBloodcountTableContract.PatientBloodcountTableEntry.COLUMN_PATIENT_ID + " INTEGER NOT NULL, " +
            PatientBloodcountTableContract.PatientBloodcountTableEntry.COLUMN_BLOOD_ID + " INTEGER NOT NULL" +
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
            sqLiteDatabase.execSQL(SQL_CREATE_PATIENT_BLOODCOUNT_TABLE);
            sqLiteDatabase.execSQL(SQL_CREATE_PATIENT_DISEASE_TABLE);
            sqLiteDatabase.execSQL(SQL_CREATE_BLOODCOUNT_TABLE);
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
