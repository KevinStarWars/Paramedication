package com.example.kevin.paramedication.DatabaseOperations;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kevin.paramedication.DatabaseContracts.DiseaseMedicationTableContract;
import com.example.kevin.paramedication.DatabaseObjects.DiseaseMedicationRelationRecord;

import java.util.ArrayList;
import java.util.List;

import static com.example.kevin.paramedication.MainActivity.LOG_TAG;


public class DiseaseMedicationRelationOperations {

    private String[] diseaseMedicationColumns = {
            DiseaseMedicationTableContract.DiseaseMedicationEntry._ID,
            DiseaseMedicationTableContract.DiseaseMedicationEntry.COLUMN_DISEASE_ID,
            DiseaseMedicationTableContract.DiseaseMedicationEntry.COLUMN_DRUG_ID
    };






    public DiseaseMedicationRelationRecord createDiseaseMedicationRelationRecord(int diseaseId, int drugId, SQLiteDatabase database) {

        ContentValues values = new ContentValues();
        values.put(DiseaseMedicationTableContract.DiseaseMedicationEntry.COLUMN_DISEASE_ID, diseaseId);
        values.put(DiseaseMedicationTableContract.DiseaseMedicationEntry.COLUMN_DRUG_ID, drugId);

        long insertId = database.insert(DiseaseMedicationTableContract.DiseaseMedicationEntry.TABLE_NAME, null, values);

        Cursor cursor = database.query(DiseaseMedicationTableContract.DiseaseMedicationEntry.TABLE_NAME, diseaseMedicationColumns,
                DiseaseMedicationTableContract.DiseaseMedicationEntry._ID + "=" + insertId, null, null, null, null);

        cursor.moveToFirst();
        DiseaseMedicationRelationRecord record = cursorToDiseaseMedicationRelationRecord(cursor);
        cursor.close();

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
            Log.d(LOG_TAG, "ID: " + record.getId() + ", Content: " + record.print());
            cursor.moveToNext();
        }
        cursor.close();

        return List;
    }




}
