package com.example.kevin.paramedication.DatabaseOperations;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kevin.paramedication.DatabaseContracts.DiseaseMedicationTableContract;
import com.example.kevin.paramedication.DatabaseContracts.MedicationInteractionTableContract;
import com.example.kevin.paramedication.DatabaseObjects.MedicationInteractionRecord;

import java.util.ArrayList;
import java.util.List;


public class MedicationInteractionOperations {

    final static String LOG_TAG = MedicationInteractionOperations.class.getSimpleName();

    private String[] medicationInteractionColumn = {
            MedicationInteractionTableContract.MedicationInteractionEntry._ID,
            MedicationInteractionTableContract.MedicationInteractionEntry.COLUMN_DRUG_ID1,
            MedicationInteractionTableContract.MedicationInteractionEntry.COLUMN_DRUG_ID2,
            MedicationInteractionTableContract.MedicationInteractionEntry.COLUMN_TYPE_OF_INTERACTION
    };

    public MedicationInteractionRecord createDiseaseMedicationRelationRecord(int drugID1, int drugId2, String typeOfInteraction, SQLiteDatabase database) {

        List<MedicationInteractionRecord> currentDatabase = getAllMedicationInteractionRecord(database);

        for (int i = 0; i < currentDatabase.size(); i++) {
            if ((currentDatabase.get(i).getDrugId1() == drugID1 && currentDatabase.get(i).getDrugId2() == drugId2) ||
                    (currentDatabase.get(i).getDrugId1() == drugId2 && currentDatabase.get(i).getDrugId2() == drugID1)) {
                    return currentDatabase.get(i);
            }
        }

        ContentValues values = new ContentValues();
        values.put(MedicationInteractionTableContract.MedicationInteractionEntry.COLUMN_DRUG_ID1, drugID1);
        values.put(MedicationInteractionTableContract.MedicationInteractionEntry.COLUMN_DRUG_ID2, drugId2);
        values.put(MedicationInteractionTableContract.MedicationInteractionEntry.COLUMN_TYPE_OF_INTERACTION, typeOfInteraction);

        long insertId = database.insert(MedicationInteractionTableContract.MedicationInteractionEntry.TABLE_NAME, null, values);

        Cursor cursor = database.query(MedicationInteractionTableContract.MedicationInteractionEntry.TABLE_NAME, medicationInteractionColumn,
                DiseaseMedicationTableContract.DiseaseMedicationEntry._ID + "=" + insertId, null, null, null, null);

        cursor.moveToFirst();
        MedicationInteractionRecord record = cursorToMedicationInteractionRecord(cursor);
        cursor.close();

        Log.d(LOG_TAG, record.toString());

        return record;
    }

    private MedicationInteractionRecord cursorToMedicationInteractionRecord(Cursor cursor) {

        int idIndex = cursor.getColumnIndex(MedicationInteractionTableContract.MedicationInteractionEntry._ID);
        int idDrug1 = cursor.getColumnIndex(MedicationInteractionTableContract.MedicationInteractionEntry.COLUMN_DRUG_ID1);
        int idDrug2 = cursor.getColumnIndex(MedicationInteractionTableContract.MedicationInteractionEntry.COLUMN_DRUG_ID2);
        int idType = cursor.getColumnIndex(MedicationInteractionTableContract.MedicationInteractionEntry.COLUMN_TYPE_OF_INTERACTION);

        int id = cursor.getInt(idIndex);
        int drugId1 = cursor.getInt(idDrug1);
        int drugId2 = cursor.getInt(idDrug2);
        String type = cursor.getString(idType);

        return new MedicationInteractionRecord(id, drugId1, drugId2, type);
    }

    public List<MedicationInteractionRecord> getAllMedicationInteractionRecord(SQLiteDatabase database) {
        List<MedicationInteractionRecord> List = new ArrayList<>();

        Cursor cursor = database.query(MedicationInteractionTableContract.MedicationInteractionEntry.TABLE_NAME,
                medicationInteractionColumn, null, null, null, null, null, null);

        cursor.moveToFirst();
        MedicationInteractionRecord record;

        while (!cursor.isAfterLast()) {
            record = cursorToMedicationInteractionRecord(cursor);
            List.add(record);
            cursor.moveToNext();
        }
        cursor.close();

        return List;
    }

}
