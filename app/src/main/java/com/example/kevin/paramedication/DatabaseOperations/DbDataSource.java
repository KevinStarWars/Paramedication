package com.example.kevin.paramedication.DatabaseOperations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kevin.paramedication.DatabaseContracts.DiseaseTableContract;
import com.example.kevin.paramedication.DatabaseContracts.MedicationTableContract;
import com.example.kevin.paramedication.DatabaseContracts.PatientTableContract;
import com.example.kevin.paramedication.DatabaseObjects.DiseaseRecord;
import com.example.kevin.paramedication.DatabaseObjects.MedicationRecord;
import com.example.kevin.paramedication.DatabaseObjects.PatientRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * handles connection between app and database
 */

public class DbDataSource {

    private static final String LOG_TAG = DbDataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private DbHelper dbHelper;

    public DbDataSource(Context context) {
        Log.d(LOG_TAG, "Unsere DataSource erzeugt jetzt den dbHelper");
        dbHelper = new DbHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "Reference to: " + database.getPath());
    }

    public void close() {
        dbHelper.close();
        Log.d(LOG_TAG, "database closed.");
    }

    // MEDICATION OPERATIONS
    private String[] medColumns = {
            MedicationTableContract.MedicationTableEntry._ID,
            MedicationTableContract.MedicationTableEntry.DRUG_NAME
    };


    public MedicationRecord createMedicationRecord(String name) {

        ContentValues values = new ContentValues();
        values.put(MedicationTableContract.MedicationTableEntry.DRUG_NAME, name);

        long insertId = database.insert(MedicationTableContract.MedicationTableEntry.TABLE_NAME, null, values);

        Log.d(LOG_TAG, "Inserted new drug: ");

        Cursor cursor = database.query(MedicationTableContract.MedicationTableEntry.TABLE_NAME, medColumns,
                MedicationTableContract.MedicationTableEntry._ID + "=" + insertId, null, null, null, null);

        cursor.moveToFirst();
        MedicationRecord record = cursorToMedicationRecord(cursor);

        Log.d(LOG_TAG, record.print());

        cursor.close();

        return record;
    }

    private MedicationRecord cursorToMedicationRecord(Cursor cursor) {

        int idIndex = cursor.getColumnIndex(MedicationTableContract.MedicationTableEntry._ID);
        int idName = cursor.getColumnIndex(MedicationTableContract.MedicationTableEntry.DRUG_NAME);

        long id = cursor.getLong(idIndex);
        String name = cursor.getString(idName);

        return new MedicationRecord(id, name);
    }

    public List<MedicationRecord> getAllMedicationRecords() {
        List<MedicationRecord> List = new ArrayList<>();

        Cursor cursor = database.query(MedicationTableContract.MedicationTableEntry.TABLE_NAME,
                medColumns, null, null, null, null, null, null);

        cursor.moveToFirst();
        MedicationRecord record;

        while (!cursor.isAfterLast()) {
            record = cursorToMedicationRecord(cursor);
            List.add(record);
            Log.d(LOG_TAG, "ID: " + record.getId() + ", Content: " + record.print());
            cursor.moveToNext();
        }
        cursor.close();

        return List;
    }

    // DISEASE OPERATIONS

    private String[] diseaseColumns = {
            DiseaseTableContract.DiseaseTableEntry._ID,
            DiseaseTableContract.DiseaseTableEntry.DISEASE_NAME
    };


    public DiseaseRecord createDiseaseRecord(String name, SQLiteDatabase database){

        ContentValues values = new ContentValues();
        values.put(DiseaseTableContract.DiseaseTableEntry.DISEASE_NAME, name);

        long insertId = database.insert(DiseaseTableContract.DiseaseTableEntry.TABLE_NAME, null, values);

        Cursor cursor = database.query(DiseaseTableContract.DiseaseTableEntry.TABLE_NAME, diseaseColumns,
                DiseaseTableContract.DiseaseTableEntry._ID + "=" + insertId, null, null, null, null);

        cursor.moveToFirst();
        DiseaseRecord record = cursorToDiseaseRecord(cursor);
        cursor.close();

        return record;
    }

    private DiseaseRecord cursorToDiseaseRecord(Cursor cursor){

        int idIndex = cursor.getColumnIndex(DiseaseTableContract.DiseaseTableEntry._ID);
        int idName = cursor.getColumnIndex(DiseaseTableContract.DiseaseTableEntry.DISEASE_NAME);

        long id = cursor.getLong(idIndex);
        String name = cursor.getString(idName);

        return new DiseaseRecord(id, name);
    }

    public List<DiseaseRecord> getAllDiseaseRecords(SQLiteDatabase database){
        List<DiseaseRecord> List = new ArrayList<>();

        Cursor cursor = database.query(DiseaseTableContract.DiseaseTableEntry.TABLE_NAME,
                diseaseColumns, null, null, null, null, null, null);

        cursor.moveToFirst();
        DiseaseRecord record;

        while (!cursor.isAfterLast()){
            record = cursorToDiseaseRecord(cursor);
            List.add(record);
            Log.d(LOG_TAG, "ID: " + record.getId() + ", Content: " + record.print());
            cursor.moveToNext();
        }
        cursor.close();

        return List;
    }

    // PATIENT OPERATIONS

    private String[] patientColumns = {
            PatientTableContract.PatientTableEntry._ID,
            PatientTableContract.PatientTableEntry.HOSPITAL_ID
    };


    public PatientRecord createPatientRecord(String name, SQLiteDatabase database){

        ContentValues values = new ContentValues();
        values.put(PatientTableContract.PatientTableEntry.HOSPITAL_ID, name);

        long insertId = database.insert(PatientTableContract.PatientTableEntry.TABLE_NAME, null, values);

        Cursor cursor = database.query(PatientTableContract.PatientTableEntry.TABLE_NAME, patientColumns,
                PatientTableContract.PatientTableEntry._ID + "=" + insertId, null, null, null, null);

        cursor.moveToFirst();
        PatientRecord record = cursorToPatientRecord(cursor);
        cursor.close();

        return record;
    }

    private PatientRecord cursorToPatientRecord(Cursor cursor){

        int idIndex = cursor.getColumnIndex(PatientTableContract.PatientTableEntry._ID);
        int idHospital = cursor.getColumnIndex(PatientTableContract.PatientTableEntry.HOSPITAL_ID);

        long id = cursor.getLong(idIndex);
        long hospitalId = cursor.getLong(idHospital);

        return new PatientRecord(id, hospitalId);
    }

    public List<PatientRecord> getAllPatientRecords(SQLiteDatabase database){
        List<PatientRecord> List = new ArrayList<>();

        Cursor cursor = database.query(PatientTableContract.PatientTableEntry.TABLE_NAME,
                patientColumns, null, null, null, null, null, null);

        cursor.moveToFirst();
        PatientRecord record;

        while (!cursor.isAfterLast()){
            record = cursorToPatientRecord(cursor);
            List.add(record);
            Log.d(LOG_TAG, "ID: " + record.getId() + ", Content: " + record.print());
            cursor.moveToNext();
        }
        cursor.close();

        return List;
    }

}
