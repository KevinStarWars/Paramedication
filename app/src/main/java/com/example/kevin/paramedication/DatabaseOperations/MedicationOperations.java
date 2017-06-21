package com.example.kevin.paramedication.DatabaseOperations;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kevin.paramedication.DatabaseContracts.MedicationTableContract;
import com.example.kevin.paramedication.DatabaseObjects.MedicationRecord;

import java.util.ArrayList;
import java.util.List;


public class MedicationOperations {

    private static final String LOG_TAG = MedicationOperations.class.getSimpleName();

    private String[] medColumns = {
            MedicationTableContract.MedicationTableEntry._ID,
            MedicationTableContract.MedicationTableEntry.COLUMN_DRUG_NAME
    };

    public MedicationRecord createMedicationRecord(String name, SQLiteDatabase database) {

        if (name.equals("")) {
            Log.d(LOG_TAG, "Cannot insert empty Drug.");
            return new MedicationRecord(-1, "-1");
        }

        List<MedicationRecord> currentDatabase = getAllMedicationRecords(database);

        for (int i = 0; i < currentDatabase.size(); i++) {
            if (currentDatabase.get(i).getDrugName().equals(name)) {
                return currentDatabase.get(i);
            }
        }

        ContentValues values = new ContentValues();
        values.put(MedicationTableContract.MedicationTableEntry.COLUMN_DRUG_NAME, name);

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
        int idName = cursor.getColumnIndex(MedicationTableContract.MedicationTableEntry.COLUMN_DRUG_NAME);

        int id = cursor.getInt(idIndex);
        String name = cursor.getString(idName);

        return new MedicationRecord(id, name);
    }


    public List<MedicationRecord> getAllMedicationRecords(SQLiteDatabase database) {
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

}
