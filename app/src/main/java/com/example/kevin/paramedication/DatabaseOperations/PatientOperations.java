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

/**
 * Created by kevin on 08.06.17.
 */

public class PatientOperations {

    private String[] patientColumns = {
            PatientTableContract.PatientTableEntry._ID,
            PatientTableContract.PatientTableEntry.COLUMN_HOSPITAL_ID
    };

    public PatientRecord createPatientRecord(String name, SQLiteDatabase database) {

        ContentValues values = new ContentValues();
        values.put(PatientTableContract.PatientTableEntry.COLUMN_HOSPITAL_ID, name);

        long insertId = database.insert(PatientTableContract.PatientTableEntry.TABLE_NAME, null, values);

        Cursor cursor = database.query(PatientTableContract.PatientTableEntry.TABLE_NAME, patientColumns,
                PatientTableContract.PatientTableEntry._ID + "=" + insertId, null, null, null, null);

        cursor.moveToFirst();
        PatientRecord record = cursorToPatientRecord(cursor);
        cursor.close();

        return record;
    }

    private PatientRecord cursorToPatientRecord(Cursor cursor) {

        int idIndex = cursor.getColumnIndex(PatientTableContract.PatientTableEntry._ID);
        int idHospital = cursor.getColumnIndex(PatientTableContract.PatientTableEntry.COLUMN_HOSPITAL_ID);

        int id = cursor.getInt(idIndex);
        long hospitalId = cursor.getLong(idHospital);

        return new PatientRecord(id, hospitalId);
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
            Log.d(LOG_TAG, "ID: " + record.getId() + ", Content: " + record.print());
            cursor.moveToNext();
        }
        cursor.close();

        return List;
    }


}
