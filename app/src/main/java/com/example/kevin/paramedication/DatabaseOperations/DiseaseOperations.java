package com.example.kevin.paramedication.DatabaseOperations;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kevin.paramedication.DatabaseContracts.DiseaseTableContract;
import com.example.kevin.paramedication.DatabaseObjects.DiseaseRecord;

import java.util.ArrayList;
import java.util.List;


public class DiseaseOperations {

    static final String LOG_TAG = DiseaseOperations.class.getSimpleName();

    private String[] diseaseColumns = {
            DiseaseTableContract.DiseaseTableEntry._ID,
            DiseaseTableContract.DiseaseTableEntry.COLUMN_DISEASE_NAME,
            DiseaseTableContract.DiseaseTableEntry.COLUMN_DISEASE_INCIDENCE
    };

    public DiseaseRecord createDiseaseRecord(String name, Integer incidence, SQLiteDatabase database) {

        List<DiseaseRecord> currentDatabase = getAllDiseaseRecords(database);
        for (int i = 0; i < currentDatabase.size(); i++) {
            if (currentDatabase.get(i).getName().equals(name)) {
                if (currentDatabase.get(i).getIncidence() == incidence) {
                    return currentDatabase.get(i);
                }
            }
        }

        ContentValues values = new ContentValues();
        values.put(DiseaseTableContract.DiseaseTableEntry.COLUMN_DISEASE_NAME, name);
        values.put(DiseaseTableContract.DiseaseTableEntry.COLUMN_DISEASE_INCIDENCE, incidence);

        long insertId = database.insert(DiseaseTableContract.DiseaseTableEntry.TABLE_NAME, null, values);

        Cursor cursor = database.query(DiseaseTableContract.DiseaseTableEntry.TABLE_NAME, diseaseColumns,
                DiseaseTableContract.DiseaseTableEntry._ID + "=" + insertId, null, null, null, null);

        cursor.moveToFirst();
        DiseaseRecord record = cursorToDiseaseRecord(cursor);
        cursor.close();

        Log.d(LOG_TAG, record.toString());

        return record;
    }

    private DiseaseRecord cursorToDiseaseRecord(Cursor cursor) {

        int id = cursor.getInt(cursor.getColumnIndex(DiseaseTableContract.DiseaseTableEntry._ID));
        String name = cursor.getString(cursor.getColumnIndex(DiseaseTableContract.DiseaseTableEntry.COLUMN_DISEASE_NAME));
        int incidence = cursor.getInt(cursor.getColumnIndex(DiseaseTableContract.DiseaseTableEntry.COLUMN_DISEASE_INCIDENCE));

        return new DiseaseRecord(id, name, incidence);
    }

    public List<DiseaseRecord> getAllDiseaseRecords(SQLiteDatabase database) {
        List<DiseaseRecord> List = new ArrayList<>();

        Cursor cursor = database.query(DiseaseTableContract.DiseaseTableEntry.TABLE_NAME,
                diseaseColumns, null, null, null, null, null, null);

        cursor.moveToFirst();
        DiseaseRecord record;

        while (!cursor.isAfterLast()) {
            record = cursorToDiseaseRecord(cursor);
            List.add(record);
            cursor.moveToNext();
        }
        cursor.close();

        return List;
    }

}
