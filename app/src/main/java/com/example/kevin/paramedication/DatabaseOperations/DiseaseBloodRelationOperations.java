package com.example.kevin.paramedication.DatabaseOperations;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kevin.paramedication.DatabaseContracts.DiseaseBloodTableContract;
import com.example.kevin.paramedication.DatabaseObjects.DiseaseBloodRelationRecord;

import java.util.ArrayList;
import java.util.List;

import static com.example.kevin.paramedication.MainActivity.LOG_TAG;


public class DiseaseBloodRelationOperations {

    private String[] diseaseBloodColumns = {
            DiseaseBloodTableContract.DiseaseBloodEntry._ID,
            DiseaseBloodTableContract.DiseaseBloodEntry.COLUMN_BLOOD_ID,
            DiseaseBloodTableContract.DiseaseBloodEntry.COLUMN_DISEASE_ID
    };

    public DiseaseBloodRelationRecord createDiseaseBloodRelationRecord(int bloodId, int diseaseId, SQLiteDatabase database) {

        ContentValues values = new ContentValues();
        values.put(DiseaseBloodTableContract.DiseaseBloodEntry.COLUMN_BLOOD_ID, bloodId);
        values.put(DiseaseBloodTableContract.DiseaseBloodEntry.COLUMN_DISEASE_ID, diseaseId);

        long insertId = database.insert(DiseaseBloodTableContract.DiseaseBloodEntry.TABLE_NAME, null, values);

        Cursor cursor = database.query(DiseaseBloodTableContract.DiseaseBloodEntry.TABLE_NAME, diseaseBloodColumns,
                DiseaseBloodTableContract.DiseaseBloodEntry._ID + "=" + insertId, null, null, null, null);

        cursor.moveToFirst();
        DiseaseBloodRelationRecord record = cursorToDiseaseBloodRelationRecord(cursor);
        cursor.close();

        return record;
    }

    private DiseaseBloodRelationRecord cursorToDiseaseBloodRelationRecord(Cursor cursor) {

        int idIndex = cursor.getColumnIndex(DiseaseBloodTableContract.DiseaseBloodEntry._ID);
        int idBlood = cursor.getColumnIndex(DiseaseBloodTableContract.DiseaseBloodEntry.COLUMN_BLOOD_ID);
        int idDisease = cursor.getColumnIndex(DiseaseBloodTableContract.DiseaseBloodEntry.COLUMN_DISEASE_ID);

        int id = cursor.getInt(idIndex);
        int bloodId = cursor.getInt(idBlood);
        int diseaseId = cursor.getInt(idDisease);

        return new DiseaseBloodRelationRecord(id, bloodId, diseaseId);
    }

    public List<DiseaseBloodRelationRecord> getAllDiseaseBloodRelationRecord(SQLiteDatabase database) {
        List<DiseaseBloodRelationRecord> List = new ArrayList<>();

        Cursor cursor = database.query(DiseaseBloodTableContract.DiseaseBloodEntry.TABLE_NAME,
                diseaseBloodColumns, null, null, null, null, null, null);

        cursor.moveToFirst();
        DiseaseBloodRelationRecord record;

        while (!cursor.isAfterLast()) {
            record = cursorToDiseaseBloodRelationRecord(cursor);
            List.add(record);
            Log.d(LOG_TAG, "ID: " + record.getId() + ", Content: " + record.print());
            cursor.moveToNext();
        }
        cursor.close();

        return List;
    }

}
