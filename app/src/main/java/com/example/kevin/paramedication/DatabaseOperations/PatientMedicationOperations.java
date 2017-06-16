package com.example.kevin.paramedication.DatabaseOperations;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kevin.paramedication.DatabaseContracts.PatientMedicationTableContract;
import com.example.kevin.paramedication.DatabaseObjects.PatientMedicationRecord;

import java.util.ArrayList;
import java.util.List;

public class PatientMedicationOperations {

    static final String LOG_TAG = PatientMedicationOperations.class.getSimpleName();

    private String[] patientMedicationColumn = {
            PatientMedicationTableContract.PatientMedicationTableEntry._ID,
            PatientMedicationTableContract.PatientMedicationTableEntry.COLUMN_PATIENT_ID,
            PatientMedicationTableContract.PatientMedicationTableEntry.COLUMN_DRUG_ID
    };

    public PatientMedicationRecord createPatientMedicationRecord(int patientId, int drugId, SQLiteDatabase database) {

        List<PatientMedicationRecord> currentDatabase = getAllPatientMedicationRecord(database);

        for (int i = 0; i < currentDatabase.size(); i++) {
            if (patientId == currentDatabase.get(i).getPatientId() && currentDatabase.get(i).getMedicationId() == drugId) {
                return currentDatabase.get(i);
            }
        }

        ContentValues values = new ContentValues();
        values.put(PatientMedicationTableContract.PatientMedicationTableEntry.COLUMN_PATIENT_ID, patientId);
        values.put(PatientMedicationTableContract.PatientMedicationTableEntry.COLUMN_DRUG_ID, drugId);

        long insertId = database.insert(PatientMedicationTableContract.PatientMedicationTableEntry.TABLE_NAME, null, values);

        Cursor cursor = database.query(PatientMedicationTableContract.PatientMedicationTableEntry.TABLE_NAME, patientMedicationColumn,
                PatientMedicationTableContract.PatientMedicationTableEntry._ID + "=" + insertId, null, null, null, null);

        cursor.moveToFirst();
        PatientMedicationRecord record = cursorToPatientMedicationRecord(cursor);
        cursor.close();

        return record;
    }

    private PatientMedicationRecord cursorToPatientMedicationRecord(Cursor cursor) {

        int idIndex = cursor.getColumnIndex(PatientMedicationTableContract.PatientMedicationTableEntry._ID);
        int idPatient = cursor.getColumnIndex(PatientMedicationTableContract.PatientMedicationTableEntry.COLUMN_PATIENT_ID);
        int idDrug = cursor.getColumnIndex(PatientMedicationTableContract.PatientMedicationTableEntry.COLUMN_DRUG_ID);

        int id = cursor.getInt(idIndex);
        int patientId = cursor.getInt(idPatient);
        int drugId = cursor.getInt(idDrug);

        return new PatientMedicationRecord(id, patientId, drugId);
    }

    public List<PatientMedicationRecord> getAllPatientMedicationRecord(SQLiteDatabase database) {
        List<PatientMedicationRecord> List = new ArrayList<>();

        Cursor cursor = database.query(PatientMedicationTableContract.PatientMedicationTableEntry.TABLE_NAME,
                patientMedicationColumn, null, null, null, null, null, null);

        cursor.moveToFirst();
        PatientMedicationRecord record;

        while (!cursor.isAfterLast()) {
            record = cursorToPatientMedicationRecord(cursor);
            List.add(record);
            Log.d(LOG_TAG, "ID: " + record.getId() + ", Content: " + record.toString());
            cursor.moveToNext();
        }
        cursor.close();

        return List;
    }
}
