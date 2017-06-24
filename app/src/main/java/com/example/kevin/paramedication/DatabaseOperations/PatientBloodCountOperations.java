package com.example.kevin.paramedication.DatabaseOperations;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kevin.paramedication.DatabaseContracts.PatientBloodcountTableContract;
import com.example.kevin.paramedication.DatabaseObjects.PatientBloodCountRecord;

import java.util.ArrayList;
import java.util.List;

public class PatientBloodCountOperations {

    static final String LOG_TAG = PatientBloodCountOperations.class.getSimpleName();


    private String[] patientBloodCountColumns = {
            PatientBloodcountTableContract.PatientBloodcountTableEntry._ID,
            PatientBloodcountTableContract.PatientBloodcountTableEntry.COLUMN_PATIENT_ID,
            PatientBloodcountTableContract.PatientBloodcountTableEntry.COLUMN_BLOOD_ID
    };

    public PatientBloodCountRecord createPatientBloodCountRecord(int patientId, int bloodId, SQLiteDatabase database) {

        List<PatientBloodCountRecord> currentDatabase = getAllPatientBloodCountRecord(database);

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


        Cursor cursor = database.query(PatientBloodcountTableContract.PatientBloodcountTableEntry.TABLE_NAME, patientBloodCountColumns,
                PatientBloodcountTableContract.PatientBloodcountTableEntry._ID + "=" + insertId, null, null, null, null);

        cursor.moveToFirst();
        PatientBloodCountRecord record = cursorToPatientBloodCountRecord(cursor);
        cursor.close();

        Log.d(LOG_TAG, record.toString());

        return record;
    }

    private PatientBloodCountRecord cursorToPatientBloodCountRecord(Cursor cursor) {

        int idIndex = cursor.getColumnIndex(PatientBloodcountTableContract.PatientBloodcountTableEntry._ID);
        int idPatient = cursor.getColumnIndex(PatientBloodcountTableContract.PatientBloodcountTableEntry.COLUMN_PATIENT_ID);
        int idBlood = cursor.getColumnIndex(PatientBloodcountTableContract.PatientBloodcountTableEntry.COLUMN_BLOOD_ID);

        int id = cursor.getInt(idIndex);
        int patientId = cursor.getInt(idPatient);
        int bloodId = cursor.getInt(idBlood);

        return new PatientBloodCountRecord(id, patientId, bloodId);
    }

    public List<PatientBloodCountRecord> getAllPatientBloodCountRecord(SQLiteDatabase database) {
        List<PatientBloodCountRecord> List = new ArrayList<>();

        Cursor cursor = database.query(PatientBloodcountTableContract.PatientBloodcountTableEntry.TABLE_NAME,
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
