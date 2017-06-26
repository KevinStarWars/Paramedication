package com.example.kevin.paramedication.DatabaseOperations;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kevin.paramedication.DatabaseContracts.PatientBloodCountTableContract;
import com.example.kevin.paramedication.DatabaseObjects.PatientBloodCountRecord;

import java.util.ArrayList;
import java.util.List;

public class PatientBloodCountOperations {

    static final String LOG_TAG = PatientBloodCountOperations.class.getSimpleName();


    private String[] patientBloodCountColumns = {
            PatientBloodCountTableContract.PatientBloodCountTableEntry._ID,
            PatientBloodCountTableContract.PatientBloodCountTableEntry.COLUMN_PATIENT_ID,
            PatientBloodCountTableContract.PatientBloodCountTableEntry.COLUMN_BLOOD_ID
    };

    public PatientBloodCountRecord createPatientBloodCountRecord(int patientId, int bloodId, SQLiteDatabase database) {

        List<PatientBloodCountRecord> currentDatabase = getAllPatientBloodCountRecord(database);

        for (int i = 0; i < currentDatabase.size(); i++){
            if (currentDatabase.get(i).getPatientId() == patientId &&
                    currentDatabase.get(i).getBloodCountId() == bloodId) {
                return currentDatabase.get(i);
            }
        }

        ContentValues values = new ContentValues();
        values.put(PatientBloodCountTableContract.PatientBloodCountTableEntry.COLUMN_PATIENT_ID, patientId);
        values.put(PatientBloodCountTableContract.PatientBloodCountTableEntry.COLUMN_BLOOD_ID, bloodId);

        long insertId = database.insert(PatientBloodCountTableContract.PatientBloodCountTableEntry.TABLE_NAME, null, values);


        Cursor cursor = database.query(PatientBloodCountTableContract.PatientBloodCountTableEntry.TABLE_NAME, patientBloodCountColumns,
                PatientBloodCountTableContract.PatientBloodCountTableEntry._ID + "=" + insertId, null, null, null, null);

        cursor.moveToFirst();
        PatientBloodCountRecord record = cursorToPatientBloodCountRecord(cursor);
        cursor.close();

        Log.d(LOG_TAG, record.toString());

        return record;
    }

    private PatientBloodCountRecord cursorToPatientBloodCountRecord(Cursor cursor) {

        int idIndex = cursor.getColumnIndex(PatientBloodCountTableContract.PatientBloodCountTableEntry._ID);
        int idPatient = cursor.getColumnIndex(PatientBloodCountTableContract.PatientBloodCountTableEntry.COLUMN_PATIENT_ID);
        int idBlood = cursor.getColumnIndex(PatientBloodCountTableContract.PatientBloodCountTableEntry.COLUMN_BLOOD_ID);

        int id = cursor.getInt(idIndex);
        int patientId = cursor.getInt(idPatient);
        int bloodId = cursor.getInt(idBlood);

        return new PatientBloodCountRecord(id, patientId, bloodId);
    }

    public List<PatientBloodCountRecord> getAllPatientBloodCountRecord(SQLiteDatabase database) {
        List<PatientBloodCountRecord> List = new ArrayList<>();

        Cursor cursor = database.query(PatientBloodCountTableContract.PatientBloodCountTableEntry.TABLE_NAME,
                patientBloodCountColumns, null, null, null, null, null, null);

        cursor.moveToFirst();
        PatientBloodCountRecord record;

        while (!cursor.isAfterLast()) {
            record = cursorToPatientBloodCountRecord(cursor);
            List.add(record);
            cursor.moveToNext();
        }
        cursor.close();

        return List;
    }
}
