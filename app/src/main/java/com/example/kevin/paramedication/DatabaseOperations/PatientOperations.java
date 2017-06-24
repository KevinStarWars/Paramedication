package com.example.kevin.paramedication.DatabaseOperations;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kevin.paramedication.DatabaseContracts.PatientTableContract;
import com.example.kevin.paramedication.DatabaseObjects.PatientRecord;

import java.util.ArrayList;
import java.util.List;

import static com.example.kevin.paramedication.MainActivity.LOG_TAG;


public class PatientOperations {

    private String[] patientColumns = {
            PatientTableContract.PatientTableEntry._ID,
            PatientTableContract.PatientTableEntry.COLUMN_HOSPITAL_ID,
            PatientTableContract.PatientTableEntry.COLUMN_GENDER
    };

    public PatientRecord createPatientRecord(String name, String gender, SQLiteDatabase database) {

        try {
            List<PatientRecord> currentDatabase = getAllPatientRecords(database);

            if (name.isEmpty()) {
                name = "0";
            }

            for (int i = 0; i < currentDatabase.size(); i++) {
                if (Long.parseLong(name) == currentDatabase.get(i).getHospitalId()) {
                    return currentDatabase.get(i);
                }
            }

            ContentValues values = new ContentValues();
            values.put(PatientTableContract.PatientTableEntry.COLUMN_HOSPITAL_ID, name);
            values.put(PatientTableContract.PatientTableEntry.COLUMN_GENDER, gender);

            long insertId = database.insert(PatientTableContract.PatientTableEntry.TABLE_NAME, null, values);


            Cursor cursor = database.query(PatientTableContract.PatientTableEntry.TABLE_NAME, patientColumns,
                    PatientTableContract.PatientTableEntry._ID + "=" + insertId, null, null, null, null);

            cursor.moveToFirst();
            PatientRecord record = cursorToPatientRecord(cursor);
            cursor.close();

            Log.d(LOG_TAG, record.toString());

            return record;
        } catch (Exception e) {
            e.printStackTrace();
            return new PatientRecord(-1, -1, "male");
        }
    }

    private PatientRecord cursorToPatientRecord(Cursor cursor) {

        int idIndex = cursor.getColumnIndex(PatientTableContract.PatientTableEntry._ID);
        int idHospital = cursor.getColumnIndex(PatientTableContract.PatientTableEntry.COLUMN_HOSPITAL_ID);
        int idGender = cursor.getColumnIndex(PatientTableContract.PatientTableEntry.COLUMN_GENDER);

        int id = cursor.getInt(idIndex);
        long hospitalId = cursor.getLong(idHospital);
        String gender = cursor.getString(idGender);

        return new PatientRecord(id, hospitalId, gender);
    }

    public List<PatientRecord> getAllPatientRecords(SQLiteDatabase database) {
        List<PatientRecord> List = new ArrayList<>();

        Cursor cursor = database.query(PatientTableContract.PatientTableEntry.TABLE_NAME,
                patientColumns, null, null, null, null, null, null);

        cursor.moveToFirst();
        PatientRecord record;

        while (!cursor.isAfterLast()) {
            record = cursorToPatientRecord(cursor);
            List.add(record);
            cursor.moveToNext();
        }
        cursor.close();

        return List;
    }


}
