package com.example.kevin.paramedication.DatabaseOperations;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kevin.paramedication.DatabaseContracts.PatientDiseaseTableContract;
import com.example.kevin.paramedication.DatabaseObjects.PatientDiseaseRecord;

import java.util.ArrayList;
import java.util.List;

public class PatientDiseaseOperations {

    static final String LOG_TAG = PatientDiseaseOperations.class.getSimpleName();

    private String[] patientDiseaseColumns = {
            PatientDiseaseTableContract.PatientDiseaseTableEntry._ID,
            PatientDiseaseTableContract.PatientDiseaseTableEntry.COLUMN_PATIENT_ID,
            PatientDiseaseTableContract.PatientDiseaseTableEntry.COLUMN_DISEASE_ID
    };

    public PatientDiseaseRecord createPatientDiseaseRecord(int patientId, int diseaseId, SQLiteDatabase database) {

        List<PatientDiseaseRecord> currentDatabase = getAllPatientDiseaseRecord(database);

        for (int i = 0; i < currentDatabase.size(); i++) {
            if (currentDatabase.get(i).getPatientId() == patientId && currentDatabase.get(i).getDiseaseId() == diseaseId) {
                return currentDatabase.get(i);
            }
        }

        ContentValues values = new ContentValues();
        values.put(PatientDiseaseTableContract.PatientDiseaseTableEntry.COLUMN_PATIENT_ID, patientId);
        values.put(PatientDiseaseTableContract.PatientDiseaseTableEntry.COLUMN_DISEASE_ID, diseaseId);

        long insertId = database.insert(PatientDiseaseTableContract.PatientDiseaseTableEntry.TABLE_NAME, null, values);

        Cursor cursor = database.query(PatientDiseaseTableContract.PatientDiseaseTableEntry.TABLE_NAME, patientDiseaseColumns,
                PatientDiseaseTableContract.PatientDiseaseTableEntry._ID + "=" + insertId, null, null, null, null);

        cursor.moveToFirst();
        PatientDiseaseRecord record = cursorToPatientDiseaseRecord(cursor);
        cursor.close();

        return record;
    }

    private PatientDiseaseRecord cursorToPatientDiseaseRecord(Cursor cursor) {

        int idIndex = cursor.getColumnIndex(PatientDiseaseTableContract.PatientDiseaseTableEntry._ID);
        int idPatient = cursor.getColumnIndex(PatientDiseaseTableContract.PatientDiseaseTableEntry.COLUMN_PATIENT_ID);
        int idDisease = cursor.getColumnIndex(PatientDiseaseTableContract.PatientDiseaseTableEntry.COLUMN_DISEASE_ID);

        int id = cursor.getInt(idIndex);
        int patientId = cursor.getInt(idPatient);
        int diseaseId = cursor.getInt(idDisease);

        return new PatientDiseaseRecord(id, patientId, diseaseId);
    }

    public List<PatientDiseaseRecord> getAllPatientDiseaseRecord(SQLiteDatabase database) {
        List<PatientDiseaseRecord> List = new ArrayList<>();

        Cursor cursor = database.query(PatientDiseaseTableContract.PatientDiseaseTableEntry.TABLE_NAME,
                patientDiseaseColumns, null, null, null, null, null, null);

        cursor.moveToFirst();
        PatientDiseaseRecord record;

        while (!cursor.isAfterLast()) {
            record = cursorToPatientDiseaseRecord(cursor);
            List.add(record);
            Log.d(LOG_TAG, "ID: " + record.getId() + ", Content: " + record.toString());
            cursor.moveToNext();
        }
        cursor.close();

        return List;
    }
}
