package com.example.kevin.paramedication.DatabaseOperations;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.kevin.paramedication.DatabaseContracts.BloodCountDiseaseTableContract;
import com.example.kevin.paramedication.DatabaseContracts.BloodCountMedicationTableContract;
import com.example.kevin.paramedication.DatabaseContracts.BloodCountTableContract;
import com.example.kevin.paramedication.DatabaseContracts.BloodTableContract;
import com.example.kevin.paramedication.DatabaseContracts.DiseaseBloodTableContract;
import com.example.kevin.paramedication.DatabaseContracts.DiseaseMedicationTableContract;
import com.example.kevin.paramedication.DatabaseContracts.DiseaseTableContract;
import com.example.kevin.paramedication.DatabaseContracts.MedicationInteractionTableContract;
import com.example.kevin.paramedication.DatabaseContracts.MedicationTableContract;
import com.example.kevin.paramedication.DatabaseContracts.PatientBloodCountTableContract;
import com.example.kevin.paramedication.DatabaseContracts.PatientTableContract;
import com.example.kevin.paramedication.DatabaseContracts.ProcessTableContract;
import com.example.kevin.paramedication.DatabaseObjects.BloodRecord;
import com.example.kevin.paramedication.DatabaseObjects.DiseaseRecord;
import com.example.kevin.paramedication.DatabaseObjects.MedicationRecord;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Helper for Creating and initializing the SQLite Database
 */

class DbHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = DbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "paramedication.db";
    private static final int DATABASE_VERSION = 1;
    private Context context;

    DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            createTables(sqLiteDatabase);
            initializeProcess(sqLiteDatabase);
            createNormalValues(sqLiteDatabase);
            createNormalCount(sqLiteDatabase);
            insertDiseaseNone(sqLiteDatabase);
            DiseaseFromCsv("DiseaseTable.csv", sqLiteDatabase);
            medicationInteractionFromCsv("MedicationInteractionTable.csv", sqLiteDatabase);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error creating tables: " + e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }

    private void createTables(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_BLOOD_COUNT_DISEASE_TABLE = "CREATE TABLE " + BloodCountDiseaseTableContract.BloodCountDiseaseTableEntry.TABLE_NAME + " (" +
                BloodCountDiseaseTableContract.BloodCountDiseaseTableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                BloodCountDiseaseTableContract.BloodCountDiseaseTableEntry.COLUMN_BLOOD_COUNT_ID + " INTEGER NOT NULL, " +
                BloodCountDiseaseTableContract.BloodCountDiseaseTableEntry.COLUMN_DISEASE_ID + " INTEGER NOT NULL" +
                "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_BLOOD_COUNT_DISEASE_TABLE);

        String SQL_CREATE_PATIENT_BLOOD_COUNT_TABLE = "CREATE TABLE " + PatientBloodCountTableContract.PatientBloodCountTableEntry.TABLE_NAME + " (" +
                PatientBloodCountTableContract.PatientBloodCountTableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PatientBloodCountTableContract.PatientBloodCountTableEntry.COLUMN_PATIENT_ID + " INTEGER NOT NULL, " +
                PatientBloodCountTableContract.PatientBloodCountTableEntry.COLUMN_BLOOD_ID + " INTEGER NOT NULL" +
                "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_PATIENT_BLOOD_COUNT_TABLE);

        String SQL_CREATE_MEDICATION_TABLE = "CREATE TABLE " + MedicationTableContract.MedicationTableEntry.TABLE_NAME + " (" +
                MedicationTableContract.MedicationTableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MedicationTableContract.MedicationTableEntry.COLUMN_DRUG_NAME + " TEXT NOT NULL" +
                "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_MEDICATION_TABLE);

        String SQL_CREATE_BLOOD_COUNT_TABLE = "CREATE TABLE " + BloodCountTableContract.BloodCountTableEntry.TABLE_NAME + " (" +
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
                BloodCountTableContract.BloodCountTableEntry.COLUMN_RDW + " TEXT NOT NULL, " +
                BloodCountTableContract.BloodCountTableEntry.COLUMN_TIMESTAMP + " DEFAULT CURRENT_TIMESTAMP NOT NULL" +
                "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_BLOOD_COUNT_TABLE);

        String SQL_CREATE_DISEASE_TABLE = "CREATE TABLE " + DiseaseTableContract.DiseaseTableEntry.TABLE_NAME + " (" +
                DiseaseTableContract.DiseaseTableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DiseaseTableContract.DiseaseTableEntry.COLUMN_DISEASE_NAME + " TEXT NOT NULL" +
                "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_DISEASE_TABLE);

        String SQL_CREATE_BLOOD_TABLE = "CREATE TABLE " + BloodTableContract.BloodTableEntry.TABLE_NAME + " (" +
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
                BloodTableContract.BloodTableEntry.COLUMN_RDW_MAX + " TEXT NOT NULL," +
                BloodTableContract.BloodTableEntry.COLUMN_GENDER + " TEXT NOT NULL" +
                "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_BLOOD_TABLE);

        String SQL_CREATE_PATIENT_TABLE = "CREATE TABLE " + PatientTableContract.PatientTableEntry.TABLE_NAME + " (" +
                PatientTableContract.PatientTableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PatientTableContract.PatientTableEntry.COLUMN_HOSPITAL_ID + " INTEGER NOT NULL, " +
                PatientTableContract.PatientTableEntry.COLUMN_GENDER + " TEXT NOT NULL" +
                "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_PATIENT_TABLE);

        String SQL_CREATE_DISEASE_BLOOD_TABLE = "CREATE TABLE " + DiseaseBloodTableContract.DiseaseBloodEntry.TABLE_NAME + " (" +
                DiseaseBloodTableContract.DiseaseBloodEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DiseaseBloodTableContract.DiseaseBloodEntry.COLUMN_DISEASE_ID + " INTEGER NOT NULL, " +
                DiseaseBloodTableContract.DiseaseBloodEntry.COLUMN_BLOOD_ID + " INTEGER NOT NULL" +
                "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_DISEASE_BLOOD_TABLE);

        String SQL_CREATE_DISEASE_MEDICATION_TABLE = "CREATE TABLE " + DiseaseMedicationTableContract.DiseaseMedicationEntry.TABLE_NAME + " (" +
                DiseaseMedicationTableContract.DiseaseMedicationEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DiseaseMedicationTableContract.DiseaseMedicationEntry.COLUMN_DISEASE_ID + " INTEGER NOT NULL, " +
                DiseaseMedicationTableContract.DiseaseMedicationEntry.COLUMN_DRUG_ID + " INTEGER NOT NULL" +
                "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_DISEASE_MEDICATION_TABLE);

        String SQL_CREATE_MEDICATION_INTERACTION_TABLE = "CREATE TABLE " + MedicationInteractionTableContract.MedicationInteractionEntry.TABLE_NAME + " (" +
                MedicationInteractionTableContract.MedicationInteractionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MedicationInteractionTableContract.MedicationInteractionEntry.COLUMN_DRUG_ID1 + " INTEGER NOT NULL, " +
                MedicationInteractionTableContract.MedicationInteractionEntry.COLUMN_DRUG_ID2 + " INTEGER NOT NULL, " +
                MedicationInteractionTableContract.MedicationInteractionEntry.COLUMN_TYPE_OF_INTERACTION + " TEXT NOT NULL" +
                "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_MEDICATION_INTERACTION_TABLE);

        String SQL_CREATE_BLOOD_COUNT_MEDICATION_TABLE = "CREATE TABLE " + BloodCountMedicationTableContract.BloodCountMedicationTableEntry.TABLE_NAME + " (" +
                BloodCountMedicationTableContract.BloodCountMedicationTableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                BloodCountMedicationTableContract.BloodCountMedicationTableEntry.COLUMN_BLOOD_COUNT_ID + " INTEGER NOT NULL, " +
                BloodCountMedicationTableContract.BloodCountMedicationTableEntry.COLUMN_MEDICATION_ID + " INTEGER NOT NULL" +
                "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_BLOOD_COUNT_MEDICATION_TABLE);

        String SQL_CREATE_PROCESS_TABLE = "CREATE TABLE " + ProcessTableContract.ProcessTableEntry.TABLE_NAME + " (" +
                ProcessTableContract.ProcessTableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ProcessTableContract.ProcessTableEntry.COLUMN_DIAGNOSIS_HELP + " INTEGER NOT NULL," +
                ProcessTableContract.ProcessTableEntry.COLUMN_PATIENT_HELP + " INTEGER NOT NULL," +
                ProcessTableContract.ProcessTableEntry.COLUMN_DATABASE_HELP + " INTEGER NOT NULL," +
                ProcessTableContract.ProcessTableEntry.COLUMN_MEDICATION_HELP + " INTEGER NOT NULL," +
                ProcessTableContract.ProcessTableEntry.COLUMN_INFO_HELP + " INTEGER NOT NULL" +
                "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_PROCESS_TABLE);
        Log.d(LOG_TAG, "Tables created");
    }

    private void initializeProcess(SQLiteDatabase sqLiteDatabase) {
        ProcessOperations processOperations = new ProcessOperations();
        processOperations.createProcessRecord(1, 1, 1, 1, 1, sqLiteDatabase);
    }
    // female id = 1, male id = 2
    private void createNormalValues(SQLiteDatabase database) {
        ContentValues values = new ContentValues();

        String[] columnArray = {BloodTableContract.BloodTableEntry.COLUMN_LEUKOCYTE_MIN,
                BloodTableContract.BloodTableEntry.COLUMN_LEUKOCYTE_MAX,
                BloodTableContract.BloodTableEntry.COLUMN_ERYTHROCYTE_MIN,
                BloodTableContract.BloodTableEntry.COLUMN_ERYTHROCYTE_MAX,
                BloodTableContract.BloodTableEntry.COLUMN_HEMOGLOBIN_MIN,
                BloodTableContract.BloodTableEntry.COLUMN_HEMOGLOBIN_MAX,
                BloodTableContract.BloodTableEntry.COLUMN_HEMATOCRIT_MIN,
                BloodTableContract.BloodTableEntry.COLUMN_HEMATOCRIT_MAX,
                BloodTableContract.BloodTableEntry.COLUMN_MCV_MIN,
                BloodTableContract.BloodTableEntry.COLUMN_MCV_MAX,
                BloodTableContract.BloodTableEntry.COLUMN_MCH_MIN,
                BloodTableContract.BloodTableEntry.COLUMN_MCH_MAX,
                BloodTableContract.BloodTableEntry.COLUMN_MCHC_MIN,
                BloodTableContract.BloodTableEntry.COLUMN_MCHC_MAX,
                BloodTableContract.BloodTableEntry.COLUMN_PLATELET_MIN,
                BloodTableContract.BloodTableEntry.COLUMN_PLATELET_MAX,
                BloodTableContract.BloodTableEntry.COLUMN_RETICULOCYTES_MIN,
                BloodTableContract.BloodTableEntry.COLUMN_RETICULOCYTES_MAX,
                BloodTableContract.BloodTableEntry.COLUMN_MPV_MIN,
                BloodTableContract.BloodTableEntry.COLUMN_MPV_MAX,
                BloodTableContract.BloodTableEntry.COLUMN_RDW_MIN,
                BloodTableContract.BloodTableEntry.COLUMN_RDW_MAX,
                BloodTableContract.BloodTableEntry.COLUMN_GENDER
        };
        List<String> columns = new ArrayList<>();
        String[] femaleValues = {"3800", // Leukocyte Min
                "10500", // Leukocyte Max
                "3,9", // Erythrocyte Min
                "5,3", // Erythrocyte Max
                "12", // Hemoglobin Max
                "16", //Hemoglobin Min
                "37", // Hematocrit Min
                "48", // Hematocrit Max
                "85", // MCV Min
                "95", // MCV Max
                "28", // MCH Min
                "34", // MCH Max
                "33", // MCHC Min
                "36", // MCHC Max
                "140000", // Platelets Min
                "345000", // Platelets Max
                "3", // Reticulocytes Min
                "18", // Reticulocytes Max
                "7,5", // MPV Min
                "11,5", // MPV Max
                "11,6", // RDW Min
                "14,6", //RDW Max
                "f"};  // gender
        String[] maleValues = {"3800", // Leukocyte Min
                "10500", // Leukocyte Max
                "4,3", // Erythrocyte Min
                "5,7", // Erythrocytes Max
                "13,5", // Hemoglobin Min
                "17", // Hemoglobin Max
                "40", // Hematocrit Min
                "52", // Hematocrit Max
                "85", // MCV Min
                "95", // MCV Max
                "28", // MCH Min
                "34", // MCH Max
                "33", // MCHC Min
                "36", // MCHC Max
                "140000", // Platelets Min
                "345000", // Platelets Max
                "3", // Reticulocytes Min
                "18", // Reticulocytes Max
                "7,5", // MPV Min
                "11,5", // MPV Max
                "11,6", // RDW Min
                "14,6", // RDW Max
                "m"}; // gender

        columns.addAll(Arrays.asList(columnArray));

        for (int i = 0; i < columns.size(); i++) {
            values.put(columns.get(i), femaleValues[i]);
        }

        database.insert(BloodTableContract.BloodTableEntry.TABLE_NAME,
                null,
                values);

        Log.d(LOG_TAG, "Inserted female normal Values");

        values.clear();

        for (int i = 0; i < columns.size(); i++) {
            values.put(columns.get(i), maleValues[i]);
        }

        database.insert(BloodTableContract.BloodTableEntry.TABLE_NAME,
                null, values);

        Log.d(LOG_TAG, "Inserted male normal Values");
    }

    private void createNormalCount(SQLiteDatabase database) {

        List<String> columns = new ArrayList<>();
        ContentValues values = new ContentValues();

        String[] columnArray = {
                BloodCountTableContract.BloodCountTableEntry.COLUMN_LEUKOCYTE,
                BloodCountTableContract.BloodCountTableEntry.COLUMN_ERYTHROCYTE,
                BloodCountTableContract.BloodCountTableEntry.COLUMN_HEMOGLOBIN,
                BloodCountTableContract.BloodCountTableEntry.COLUMN_HEMATOCRIT,
                BloodCountTableContract.BloodCountTableEntry.COLUMN_MCV,
                BloodCountTableContract.BloodCountTableEntry.COLUMN_MCH,
                BloodCountTableContract.BloodCountTableEntry.COLUMN_MCHC,
                BloodCountTableContract.BloodCountTableEntry.COLUMN_PLATELET,
                BloodCountTableContract.BloodCountTableEntry.COLUMN_RETICULOCYTES,
                BloodCountTableContract.BloodCountTableEntry.COLUMN_MPV,
                BloodCountTableContract.BloodCountTableEntry.COLUMN_RDW
        };

        // source
        String[] bloodValues = {"7000",
                "5",
                "15",
                "42",
                "90",
                "30",
                "35",
                "200000",
                "50000",
                "10",
                "12"};

        columns.addAll(Arrays.asList(columnArray));

        for (int i = 0; i < columns.size(); i++) {
            values.put(columns.get(i), bloodValues[i]);
        }

        database.insert(BloodCountTableContract.BloodCountTableEntry.TABLE_NAME,
                null,
                values);

        Log.d(LOG_TAG, "Normal values created.");

    }

    private void insertDiseaseNone(SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put(DiseaseTableContract.DiseaseTableEntry.COLUMN_DISEASE_NAME, "None");
        database.insert(DiseaseTableContract.DiseaseTableEntry.TABLE_NAME, null, values);
    }

    private void DiseaseFromCsv(String filename, SQLiteDatabase database) {
        try {
            BloodOperations bloodOperations = new BloodOperations();
            DiseaseOperations diseaseOperations = new DiseaseOperations();
            DiseaseBloodRelationOperations diseaseBloodRelationOperations = new DiseaseBloodRelationOperations();
            MedicationOperations medicationOperations = new MedicationOperations();
            DiseaseMedicationRelationOperations diseaseMedicationRelationOperations = new DiseaseMedicationRelationOperations();

            int diseaseName = 0;
            int erythrocyteMin = 1;
            int erythrocyteMax = 2;
            int leukocyteMin = 3;
            int leukocyteMax = 4;
            int hemoglobinMin = 5;
            int hemoglobinMax = 6;
            int hematocritMin = 7;
            int hematocritMax = 8;
            int mcvMin = 9;
            int mcvMax = 10;
            int mchMin = 11;
            int mchMax = 12;
            int mchcMin = 13;
            int mchcMax = 14;
            int plateletMin = 15;
            int plateletMax = 16;
            int reticulocytesMin = 17;
            int reticulocytesMax = 18;
            int mpvMin = 19;
            int mpvMax = 20;
            int rdwMin = 21;
            int rdwMax = 22;
            int gender = 23;

            try {
                BufferedReader buffer = new BufferedReader(new InputStreamReader(context.getAssets().open(filename)));
                String line;
                try {
                    while ((line = buffer.readLine()) != null) {
                        String[] str = line.split(",");
                        for (int i = 0; i < str.length; i++) {
                            if (str[i].isEmpty()) {
                                str[i] = "0";
                            }
                        }
                        BloodRecord bloodRecord = bloodOperations.createBloodRecord(
                                str[leukocyteMin],
                                str[leukocyteMax],
                                str[erythrocyteMin],
                                str[erythrocyteMax],
                                str[hemoglobinMin],
                                str[hemoglobinMax],
                                str[hematocritMin],
                                str[hematocritMax],
                                str[mcvMin],
                                str[mcvMax],
                                str[mchMin],
                                str[mchMax],
                                str[mchcMin],
                                str[mchcMax],
                                str[plateletMin],
                                str[plateletMax],
                                str[reticulocytesMin],
                                str[reticulocytesMax],
                                str[mpvMin],
                                str[mpvMax],
                                str[rdwMin],
                                str[rdwMax],
                                str[gender],
                                database);
                        DiseaseRecord diseaseRecord = diseaseOperations.createDiseaseRecord(str[diseaseName], database);
                        diseaseBloodRelationOperations.createDiseaseBloodRelationRecord(bloodRecord.getId(), diseaseRecord.getId(), database);

                        for (int j = 24; j < str.length; j++) {
                            MedicationRecord medicationRecord = medicationOperations.createMedicationRecord(str[j], database);
                            diseaseMedicationRelationOperations.createDiseaseMedicationRelationRecord(diseaseRecord.getId(), medicationRecord.getId(), database);
                        }

                    }
                } catch (IOException e) {
                    Log.d(LOG_TAG, e.getMessage());
                }
            } catch (FileNotFoundException e) {
                Log.d(LOG_TAG, e.getMessage());
            }
        } catch (IOException e) {
            Log.d(LOG_TAG, e.getMessage());
        }

    }

    private void medicationInteractionFromCsv(String filename, SQLiteDatabase database) {
        try {
            MedicationOperations medicationOperations = new MedicationOperations();
            MedicationInteractionOperations medicationInteractionOperations = new MedicationInteractionOperations();

            int drugI = 0;
            int drugII = 1;
            int typeOfInteraction = 2;

            try {
                BufferedReader buffer = new BufferedReader(new InputStreamReader(context.getAssets().open(filename)));
                String line;
                try {
                    while ((line = buffer.readLine()) != null) {
                        String[] str = line.split(",");
                        for (int i = 0; i < str.length; i++) {
                            if (str[i].isEmpty()) {
                                str[i] = "0";
                            }
                        }
                        MedicationRecord medicationRecord1 = medicationOperations.createMedicationRecord(str[drugI], database);
                        MedicationRecord medicationRecord2 = medicationOperations.createMedicationRecord(str[drugII], database);

                        medicationInteractionOperations.createDiseaseMedicationRelationRecord(medicationRecord1.getId(), medicationRecord2.getId(), str[typeOfInteraction], database);

                    }
                } catch (IOException e) {
                    Log.d(LOG_TAG, e.getMessage());
                }
            } catch (FileNotFoundException e) {
                Log.d(LOG_TAG, e.getMessage());
            }
        } catch (IOException e) {
            Log.d(LOG_TAG, e.getMessage());
        }
    }

}
