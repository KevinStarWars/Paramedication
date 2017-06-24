package com.example.kevin.paramedication.DatabaseOperations;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kevin.paramedication.DatabaseContracts.DiseaseMedicationTableContract;
import com.example.kevin.paramedication.DatabaseObjects.DiseaseMedicationRelationRecord;

import java.util.ArrayList;
import java.util.List;


public class DiseaseMedicationRelationOperations {

    static final String LOG_TAG = DiseaseMedicationRelationOperations.class.getSimpleName();

    private String[] diseaseMedicationColumns = {
            DiseaseMedicationTableContract.DiseaseMedicationEntry._ID,
            DiseaseMedicationTableContract.DiseaseMedicationEntry.COLUMN_DISEASE_ID,
            DiseaseMedicationTableContract.DiseaseMedicationEntry.COLUMN_DRUG_ID
    };

    public DiseaseMedicationRelationRecord createDiseaseMedicationRelationRecord(int diseaseId, int drugId, SQLiteDatabase database) {

        List<DiseaseMedicationRelationRecord> currentDatabase = getAllDiseaseMedicationRelationRecord(database);

        for (int i = 0; i < currentDatabase.size(); i++) {
            if (currentDatabase.get(i).getDiseaseId() == diseaseId && currentDatabase.get(i).getDrugId() == drugId) {
                return new DiseaseMedicationRelationRecord(currentDatabase.get(i).getId(), currentDatabase.get(i).getDiseaseId(), currentDatabase.get(i).getDrugId());
            }
        }

        ContentValues values = new ContentValues();
        values.put(DiseaseMedicationTableContract.DiseaseMedicationEntry.COLUMN_DISEASE_ID, diseaseId);
        values.put(DiseaseMedicationTableContract.DiseaseMedicationEntry.COLUMN_DRUG_ID, drugId);

        long insertId = database.insert(DiseaseMedicationTableContract.DiseaseMedicationEntry.TABLE_NAME, null, values);

        Cursor cursor = database.query(DiseaseMedicationTableContract.DiseaseMedicationEntry.TABLE_NAME, diseaseMedicationColumns,
                DiseaseMedicationTableContract.DiseaseMedicationEntry._ID + "=" + insertId, null, null, null, null);

        cursor.moveToFirst();
        DiseaseMedicationRelationRecord record = cursorToDiseaseMedicationRelationRecord(cursor);
        cursor.close();

        Log.d(LOG_TAG, record.toString());

        return record;
    }

    private DiseaseMedicationRelationRecord cursorToDiseaseMedicationRelationRecord(Cursor cursor) {

        int idIndex = cursor.getColumnIndex(DiseaseMedicationTableContract.DiseaseMedicationEntry._ID);
        int idDisease = cursor.getColumnIndex(DiseaseMedicationTableContract.DiseaseMedicationEntry.COLUMN_DISEASE_ID);
        int idDrug = cursor.getColumnIndex(DiseaseMedicationTableContract.DiseaseMedicationEntry.COLUMN_DRUG_ID);

        int id = cursor.getInt(idIndex);
        int diseaseId = cursor.getInt(idDisease);
        int drugId = cursor.getInt(idDrug);

        return new DiseaseMedicationRelationRecord(id, diseaseId, drugId);
    }

    public List<DiseaseMedicationRelationRecord> getAllDiseaseMedicationRelationRecord(SQLiteDatabase database) {
        List<DiseaseMedicationRelationRecord> List = new ArrayList<>();

        Cursor cursor = database.query(DiseaseMedicationTableContract.DiseaseMedicationEntry.TABLE_NAME,
                diseaseMedicationColumns, null, null, null, null, null, null);

        cursor.moveToFirst();
        DiseaseMedicationRelationRecord record;

        while (!cursor.isAfterLast()) {
            record = cursorToDiseaseMedicationRelationRecord(cursor);
            List.add(record);
            cursor.moveToNext();
        }
        cursor.close();

        return List;
    }


}
