package com.example.kevin.paramedication.DatabaseOperations;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kevin.paramedication.DatabaseContracts.DiseaseTableContract;
import com.example.kevin.paramedication.DatabaseObjects.DiseaseRecord;

import java.util.ArrayList;
import java.util.List;

import static com.example.kevin.paramedication.MainActivity.LOG_TAG;


public class DiseaseOperations {


    private String[] diseaseColumns = {
            DiseaseTableContract.DiseaseTableEntry._ID,
            DiseaseTableContract.DiseaseTableEntry.COLUMN_DISEASE_NAME
    };

    public DiseaseRecord createDiseaseRecord(String name, SQLiteDatabase database) {

        List<DiseaseRecord> currentDatabase = getAllDiseaseRecords(database);
        for (int i = 0; i < currentDatabase.size(); i++) {
            if (currentDatabase.get(i).getName().equals(name)) {
                return currentDatabase.get(i);
            }
        }

        ContentValues values = new ContentValues();
        values.put(DiseaseTableContract.DiseaseTableEntry.COLUMN_DISEASE_NAME, name);

        long insertId = database.insert(DiseaseTableContract.DiseaseTableEntry.TABLE_NAME, null, values);

        Cursor cursor = database.query(DiseaseTableContract.DiseaseTableEntry.TABLE_NAME, diseaseColumns,
                DiseaseTableContract.DiseaseTableEntry._ID + "=" + insertId, null, null, null, null);

        cursor.moveToFirst();
        DiseaseRecord record = cursorToDiseaseRecord(cursor);
        cursor.close();

        return record;
    }

    private DiseaseRecord cursorToDiseaseRecord(Cursor cursor) {

        int idIndex = cursor.getColumnIndex(DiseaseTableContract.DiseaseTableEntry._ID);
        int idName = cursor.getColumnIndex(DiseaseTableContract.DiseaseTableEntry.COLUMN_DISEASE_NAME);

        int id = cursor.getInt(idIndex);
        String name = cursor.getString(idName);

        return new DiseaseRecord(id, name);
    }

    private List<DiseaseRecord> getAllDiseaseRecords(SQLiteDatabase database) {
        List<DiseaseRecord> List = new ArrayList<>();

        Cursor cursor = database.query(DiseaseTableContract.DiseaseTableEntry.TABLE_NAME,
                diseaseColumns, null, null, null, null, null, null);

        cursor.moveToFirst();
        DiseaseRecord record;

        while (!cursor.isAfterLast()) {
            record = cursorToDiseaseRecord(cursor);
            List.add(record);
            Log.d(LOG_TAG, "ID: " + record.getId() + ", Content: " + record.print());
            cursor.moveToNext();
        }
        cursor.close();

        return List;
    }

}
