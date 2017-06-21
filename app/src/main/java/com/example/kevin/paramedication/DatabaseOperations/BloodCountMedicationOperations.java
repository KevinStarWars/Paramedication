package com.example.kevin.paramedication.DatabaseOperations;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kevin.paramedication.DatabaseContracts.BloodCountMedicationTableContract;
import com.example.kevin.paramedication.DatabaseObjects.BloodCountMedicationRecord;

import java.util.ArrayList;
import java.util.List;


public class BloodCountMedicationOperations {

    final static String LOG_TAG = BloodCountMedicationOperations.class.getSimpleName();

    private String[] bloodCountMedicationColumns = {
            BloodCountMedicationTableContract.BloodCountMedicationTableEntry._ID,
            BloodCountMedicationTableContract.BloodCountMedicationTableEntry.COLUMN_BLOOD_COUNT_ID,
            BloodCountMedicationTableContract.BloodCountMedicationTableEntry.COLUMN_MEDICATION_ID
    };

    public BloodCountMedicationRecord createBloodCountMedicationRecord(int bloodCountId, int MedicationId, SQLiteDatabase database) {

        List<BloodCountMedicationRecord> currentDatabase = getAllBloodCountMedicationRecord(database);

        for (int i = 0; i < currentDatabase.size(); i++) {
            if (currentDatabase.get(i).getBloodCountId() == bloodCountId && currentDatabase.get(i).getMedicationId() == MedicationId) {
                return new BloodCountMedicationRecord(currentDatabase.get(i).getId(), currentDatabase.get(i).getBloodCountId(), currentDatabase.get(i).getMedicationId());
            }
        }

        ContentValues values = new ContentValues();
        values.put(BloodCountMedicationTableContract.BloodCountMedicationTableEntry.COLUMN_BLOOD_COUNT_ID, bloodCountId);
        values.put(BloodCountMedicationTableContract.BloodCountMedicationTableEntry.COLUMN_MEDICATION_ID, MedicationId);

        long insertId = database.insert(BloodCountMedicationTableContract.BloodCountMedicationTableEntry.TABLE_NAME, null, values);

        Cursor cursor = database.query(BloodCountMedicationTableContract.BloodCountMedicationTableEntry.TABLE_NAME, bloodCountMedicationColumns,
                BloodCountMedicationTableContract.BloodCountMedicationTableEntry._ID + "=" + insertId, null, null, null, null);

        cursor.moveToFirst();
        BloodCountMedicationRecord record = cursorToBloodCountMedicationRecord(cursor);
        cursor.close();

        return record;
    }

    private BloodCountMedicationRecord cursorToBloodCountMedicationRecord(Cursor cursor) {

        int idIndex = cursor.getColumnIndex(BloodCountMedicationTableContract.BloodCountMedicationTableEntry._ID);
        int idBloodCount = cursor.getColumnIndex(BloodCountMedicationTableContract.BloodCountMedicationTableEntry.COLUMN_BLOOD_COUNT_ID);
        int idMedication = cursor.getColumnIndex(BloodCountMedicationTableContract.BloodCountMedicationTableEntry.COLUMN_MEDICATION_ID);

        int id = cursor.getInt(idIndex);
        int bloodCountId = cursor.getInt(idBloodCount);
        int MedicationId = cursor.getInt(idMedication);

        return new BloodCountMedicationRecord(id, bloodCountId, MedicationId);
    }

    public List<BloodCountMedicationRecord> getAllBloodCountMedicationRecord(SQLiteDatabase database) {
        List<BloodCountMedicationRecord> List = new ArrayList<>();

        Cursor cursor = database.query(BloodCountMedicationTableContract.BloodCountMedicationTableEntry.TABLE_NAME,
                bloodCountMedicationColumns, null, null, null, null, null, null);

        cursor.moveToFirst();
        BloodCountMedicationRecord record;

        while (!cursor.isAfterLast()) {
            record = cursorToBloodCountMedicationRecord(cursor);
            List.add(record);
            Log.d(LOG_TAG, "ID: " + record.getId() + ", Content: " + record.toString());
            cursor.moveToNext();
        }
        cursor.close();

        return List;
    }
}
