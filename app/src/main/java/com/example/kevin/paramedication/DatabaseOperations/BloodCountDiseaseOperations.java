package com.example.kevin.paramedication.DatabaseOperations;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kevin.paramedication.DatabaseContracts.BloodCountDiseaseTableContract;
import com.example.kevin.paramedication.DatabaseObjects.BloodCountDiseaseRecord;

import java.util.ArrayList;
import java.util.List;


public class BloodCountDiseaseOperations {

    final static String LOG_TAG = BloodCountDiseaseOperations.class.getSimpleName();

    private String[] bloodCountDiseaseColumns = {
            BloodCountDiseaseTableContract.BloodCountDiseaseTableEntry._ID,
            BloodCountDiseaseTableContract.BloodCountDiseaseTableEntry.COLUMN_BLOOD_COUNT_ID,
            BloodCountDiseaseTableContract.BloodCountDiseaseTableEntry.COLUMN_DISEASE_ID
    };

    public BloodCountDiseaseRecord createBloodCountDiseaseRecord(int bloodCountId, int diseaseId, SQLiteDatabase database) {

        List<BloodCountDiseaseRecord> currentDatabase = getAllBloodCountDiseaseRecord(database);

        for (int i = 0; i < currentDatabase.size(); i++) {
            if (currentDatabase.get(i).getBloodCountId() == bloodCountId && currentDatabase.get(i).getDiseaseId() == diseaseId) {
                return new BloodCountDiseaseRecord(currentDatabase.get(i).getId(), currentDatabase.get(i).getBloodCountId(), currentDatabase.get(i).getDiseaseId());
            }
        }

        ContentValues values = new ContentValues();
        values.put(BloodCountDiseaseTableContract.BloodCountDiseaseTableEntry.COLUMN_BLOOD_COUNT_ID, bloodCountId);
        values.put(BloodCountDiseaseTableContract.BloodCountDiseaseTableEntry.COLUMN_DISEASE_ID, diseaseId);

        long insertId = database.insert(BloodCountDiseaseTableContract.BloodCountDiseaseTableEntry.TABLE_NAME, null, values);

        Cursor cursor = database.query(BloodCountDiseaseTableContract.BloodCountDiseaseTableEntry.TABLE_NAME, bloodCountDiseaseColumns,
                BloodCountDiseaseTableContract.BloodCountDiseaseTableEntry._ID + "=" + insertId, null, null, null, null);

        cursor.moveToFirst();
        BloodCountDiseaseRecord record = cursorToBloodCountDiseaseRecord(cursor);
        cursor.close();

        return record;
    }

    private BloodCountDiseaseRecord cursorToBloodCountDiseaseRecord(Cursor cursor) {

        int idIndex = cursor.getColumnIndex(BloodCountDiseaseTableContract.BloodCountDiseaseTableEntry._ID);
        int idBloodCount = cursor.getColumnIndex(BloodCountDiseaseTableContract.BloodCountDiseaseTableEntry.COLUMN_BLOOD_COUNT_ID);
        int idDisease = cursor.getColumnIndex(BloodCountDiseaseTableContract.BloodCountDiseaseTableEntry.COLUMN_DISEASE_ID);

        int id = cursor.getInt(idIndex);
        int bloodCountId = cursor.getInt(idBloodCount);
        int diseaseId = cursor.getInt(idDisease);

        return new BloodCountDiseaseRecord(id, bloodCountId, diseaseId);
    }

    public List<BloodCountDiseaseRecord> getAllBloodCountDiseaseRecord(SQLiteDatabase database) {
        List<BloodCountDiseaseRecord> List = new ArrayList<>();

        Cursor cursor = database.query(BloodCountDiseaseTableContract.BloodCountDiseaseTableEntry.TABLE_NAME,
                bloodCountDiseaseColumns, null, null, null, null, null, null);

        cursor.moveToFirst();
        BloodCountDiseaseRecord record;

        while (!cursor.isAfterLast()) {
            record = cursorToBloodCountDiseaseRecord(cursor);
            List.add(record);
            Log.d(LOG_TAG, "ID: " + record.getId() + ", Content: " + record.toString());
            cursor.moveToNext();
        }
        cursor.close();

        return List;
    }
}
