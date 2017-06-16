package com.example.kevin.paramedication.DatabaseOperations;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kevin.paramedication.DatabaseContracts.PatientBloodcountTableContract;
import com.example.kevin.paramedication.DatabaseObjects.PatientBloodcountRecord;

import java.util.ArrayList;
import java.util.List;

public class PatientBloodCountOperations {

    static final String LOG_TAG = PatientBloodCountOperations.class.getSimpleName();


    private String[] patientBloodcountColumns = {
            PatientBloodcountTableContract.PatientBloodcountTableEntry._ID,
            PatientBloodcountTableContract.PatientBloodcountTableEntry.COLUMN_PATIENT_ID,
            PatientBloodcountTableContract.PatientBloodcountTableEntry.COLUMN_BLOOD_ID
    };

    public PatientBloodcountRecord createPatientBloodcountRecord(int patientId, int bloodId, SQLiteDatabase database) {

        List<PatientBloodcountRecord> currentDatabase = getAllPatientBloodcountRecord(database);

        for (int i = 0; i < currentDatabase.size(); i++){
            if (currentDatabase.get(i).getPatientId() == patientId &&
                    currentDatabase.get(i).getBloodcountId() == bloodId){
                return currentDatabase.get(i);
            }
        }

        ContentValues values = new ContentValues();
        values.put(PatientBloodcountTableContract.PatientBloodcountTableEntry.COLUMN_PATIENT_ID, patientId);
        values.put(PatientBloodcountTableContract.PatientBloodcountTableEntry.COLUMN_BLOOD_ID, bloodId);

        long insertId = database.insert(PatientBloodcountTableContract.PatientBloodcountTableEntry.TABLE_NAME, null, values);


        Cursor cursor = database.query(PatientBloodcountTableContract.PatientBloodcountTableEntry.TABLE_NAME, patientBloodcountColumns,
                PatientBloodcountTableContract.PatientBloodcountTableEntry._ID + "=" + insertId, null, null, null, null);

        cursor.moveToFirst();
        PatientBloodcountRecord record = cursorToPatientBloodcountRecord(cursor);
        cursor.close();

        Log.d(LOG_TAG, record.toString());

        return record;
    }

    private PatientBloodcountRecord cursorToPatientBloodcountRecord(Cursor cursor) {

        int idIndex = cursor.getColumnIndex(PatientBloodcountTableContract.PatientBloodcountTableEntry._ID);
        int idPatient = cursor.getColumnIndex(PatientBloodcountTableContract.PatientBloodcountTableEntry.COLUMN_PATIENT_ID);
        int idBlood = cursor.getColumnIndex(PatientBloodcountTableContract.PatientBloodcountTableEntry.COLUMN_BLOOD_ID);

        int id = cursor.getInt(idIndex);
        int patientId = cursor.getInt(idPatient);
        int bloodId = cursor.getInt(idBlood);

        return new PatientBloodcountRecord(id, patientId, bloodId);
    }

    public List<PatientBloodcountRecord> getAllPatientBloodcountRecord(SQLiteDatabase database) {
        List<PatientBloodcountRecord> List = new ArrayList<>();

        Cursor cursor = database.query(PatientBloodcountTableContract.PatientBloodcountTableEntry.TABLE_NAME,
                patientBloodcountColumns, null, null, null, null, null, null);

        cursor.moveToFirst();
        PatientBloodcountRecord record;

        while (!cursor.isAfterLast()) {
            record = cursorToPatientBloodcountRecord(cursor);
            List.add(record);
            Log.d(LOG_TAG, "ID: " + record.getId() + ", Content: " + record.toString());
            cursor.moveToNext();
        }
        cursor.close();

        return List;
    }
}
