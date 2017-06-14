package com.example.kevin.paramedication.DatabaseOperations;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kevin.paramedication.DatabaseContracts.BloodTableContract;
import com.example.kevin.paramedication.DatabaseObjects.BloodRecord;

import java.util.ArrayList;
import java.util.List;

import static com.example.kevin.paramedication.MainActivity.LOG_TAG;

public class BloodOperations {

    private String[] bloodColumns = {
            BloodTableContract.BloodTableEntry._ID,
            BloodTableContract.BloodTableEntry.COLUMN_LEUKOCYTE_MIN,
            BloodTableContract.BloodTableEntry.COLUMN_LEUKOCYTE_MAX,
            BloodTableContract.BloodTableEntry.COLUMN_ERYTHROCYTE_MIN,
            BloodTableContract.BloodTableEntry.COLUMN_ERYTHROCYTE_MAX,
            BloodTableContract.BloodTableEntry.COLUMN_HEMOGLOBIN_MIN,
            BloodTableContract.BloodTableEntry.COLUMN_HEMOGLOBIN_MAX,
            BloodTableContract.BloodTableEntry.COLUMN_HEMATOCRIT_MIN,
            BloodTableContract.BloodTableEntry.COLUMN_HEMATOCRIT_MAX,
            BloodTableContract.BloodTableEntry.COLUMN_MCV_MIN,
            BloodTableContract.BloodTableEntry.COLUMN_MCV_MAX,
            BloodTableContract.BloodTableEntry.COLUMN_MCH_MIN,
            BloodTableContract.BloodTableEntry.COLUMN_MCH_MAX,
            BloodTableContract.BloodTableEntry.COLUMN_MCHC_MIN,
            BloodTableContract.BloodTableEntry.COLUMN_MCHC_MAX,
            BloodTableContract.BloodTableEntry.COLUMN_PLATELET_MIN,
            BloodTableContract.BloodTableEntry.COLUMN_PLATELET_MAX,
            BloodTableContract.BloodTableEntry.COLUMN_RETICULOCYTES_MIN,
            BloodTableContract.BloodTableEntry.COLUMN_RETICULOCYTES_MAX,
            BloodTableContract.BloodTableEntry.COLUMN_MPV_MIN,
            BloodTableContract.BloodTableEntry.COLUMN_MPV_MAX,
            BloodTableContract.BloodTableEntry.COLUMN_RDW_MIN,
            BloodTableContract.BloodTableEntry.COLUMN_RDW_MAX
    };

    public BloodRecord createBloodRecord(String leukocyteMin, String leukocyteMax, String erythrocyteMin, String erythrocyteMax, String hemoglobinMin, String hemoglobinMax, String hematocritMin, String hematocritMax,
                                         String mcvMin, String mcvMax, String mchMin, String mchMax, String mchcMin, String mchcMax, String plateletMin, String plateletMax, String reticulocytesMin, String reticulocytesMax,
                                         String mpvMin, String mpvMax, String rdwMin, String rdwMax, SQLiteDatabase database) {

        ContentValues values = new ContentValues();
        values.put(BloodTableContract.BloodTableEntry.COLUMN_LEUKOCYTE_MIN, leukocyteMin);
        values.put(BloodTableContract.BloodTableEntry.COLUMN_LEUKOCYTE_MAX, leukocyteMax);
        values.put(BloodTableContract.BloodTableEntry.COLUMN_ERYTHROCYTE_MIN, erythrocyteMin);
        values.put(BloodTableContract.BloodTableEntry.COLUMN_ERYTHROCYTE_MAX, erythrocyteMax);
        values.put(BloodTableContract.BloodTableEntry.COLUMN_HEMOGLOBIN_MIN, hemoglobinMin);
        values.put(BloodTableContract.BloodTableEntry.COLUMN_HEMOGLOBIN_MAX, hemoglobinMax);
        values.put(BloodTableContract.BloodTableEntry.COLUMN_HEMATOCRIT_MIN, hematocritMin);
        values.put(BloodTableContract.BloodTableEntry.COLUMN_HEMATOCRIT_MAX, hematocritMax);
        values.put(BloodTableContract.BloodTableEntry.COLUMN_MCV_MIN, mcvMin);
        values.put(BloodTableContract.BloodTableEntry.COLUMN_MCV_MAX, mcvMax);
        values.put(BloodTableContract.BloodTableEntry.COLUMN_MCH_MIN, mchMin);
        values.put(BloodTableContract.BloodTableEntry.COLUMN_MCH_MAX, mchMax);
        values.put(BloodTableContract.BloodTableEntry.COLUMN_MCHC_MIN, mchcMin);
        values.put(BloodTableContract.BloodTableEntry.COLUMN_MCHC_MAX, mchcMax);
        values.put(BloodTableContract.BloodTableEntry.COLUMN_PLATELET_MIN, plateletMin);
        values.put(BloodTableContract.BloodTableEntry.COLUMN_PLATELET_MAX, plateletMax);
        values.put(BloodTableContract.BloodTableEntry.COLUMN_RETICULOCYTES_MIN, reticulocytesMin);
        values.put(BloodTableContract.BloodTableEntry.COLUMN_RETICULOCYTES_MAX, reticulocytesMax);
        values.put(BloodTableContract.BloodTableEntry.COLUMN_MPV_MIN, mpvMin);
        values.put(BloodTableContract.BloodTableEntry.COLUMN_MPV_MAX, mpvMax);
        values.put(BloodTableContract.BloodTableEntry.COLUMN_RDW_MIN, rdwMin);
        values.put(BloodTableContract.BloodTableEntry.COLUMN_RDW_MAX, rdwMax);

        long insertId = database.insert(BloodTableContract.BloodTableEntry.TABLE_NAME, null, values);

        // SELECT * FROM BloodTableContract.BloodTableEntry.TABLE_NAME WHERE BloodTableContract.BloodTableEntry._ID = insertId
        Cursor cursor = database.query(BloodTableContract.BloodTableEntry.TABLE_NAME, bloodColumns,
                BloodTableContract.BloodTableEntry._ID + "=" + insertId, null, null, null, null);

        cursor.moveToFirst();
        BloodRecord record = cursorToBloodRecord(cursor);
        cursor.close();

        return record;
    }

    private BloodRecord cursorToBloodRecord(Cursor cursor) {

        int idIndex = cursor.getColumnIndex(BloodTableContract.BloodTableEntry._ID);
        int idLeukocyteMin = cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_LEUKOCYTE_MIN);
        int idLeukocyteMax = cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_LEUKOCYTE_MAX);
        int idErythrocyteMin = cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_ERYTHROCYTE_MIN);
        int idErythrocyteMax = cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_ERYTHROCYTE_MAX);
        int idHemoglobinMin = cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_HEMOGLOBIN_MIN);
        int idHemoglobinMax = cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_HEMOGLOBIN_MAX);
        int idHematocritMin = cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_HEMATOCRIT_MIN);
        int idHematocritMax = cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_HEMATOCRIT_MAX);
        int idMcvMin = cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_MCV_MIN);
        int idMcvMax = cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_MCV_MAX);
        int idMchMin = cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_MCH_MIN);
        int idMchMax = cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_MCH_MAX);
        int idMchcMin = cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_MCHC_MIN);
        int idMchcMax = cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_MCHC_MAX);
        int idPlateletMin = cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_PLATELET_MIN);
        int idPlateletMax = cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_PLATELET_MAX);
        int idReticulocytesMin = cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_RETICULOCYTES_MIN);
        int idReticulocytesMax = cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_RETICULOCYTES_MAX);
        int idMpvMin = cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_MPV_MIN);
        int idMpvMax = cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_MPV_MAX);
        int idRdwMin = cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_RDW_MIN);
        int idRdwMax = cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_RDW_MAX);

        int id = cursor.getInt(idIndex);
        double leukocyteMin = cursor.getDouble(idLeukocyteMin);
        double leukocyteMax = cursor.getDouble(idLeukocyteMax);
        double erythocyteMin = cursor.getDouble(idErythrocyteMin);
        double erythocyteMax = cursor.getDouble(idErythrocyteMax);
        double hemoglobinMin = cursor.getDouble(idHemoglobinMin);
        double hemoglobinMax = cursor.getDouble(idHemoglobinMax);
        double hematocritMin = cursor.getDouble(idHematocritMin);
        double hematocritMax = cursor.getDouble(idHematocritMax);
        double mcvMin = cursor.getDouble(idMcvMin);
        double mcvMax = cursor.getDouble(idMcvMax);
        double mchMin = cursor.getDouble(idMchMin);
        double mchMax = cursor.getDouble(idMchMax);
        double mchcMin = cursor.getDouble(idMchcMin);
        double mchcMax = cursor.getDouble(idMchcMax);
        double plateletMin = cursor.getDouble(idPlateletMin);
        double plateletMax = cursor.getDouble(idPlateletMax);
        double reticulocytesMin = cursor.getDouble(idReticulocytesMin);
        double reticulocytesMax = cursor.getDouble(idReticulocytesMax);
        double mpvMin = cursor.getDouble(idMpvMin);
        double mpvMax = cursor.getDouble(idMpvMax);
        double rdwMin = cursor.getDouble(idRdwMin);
        double rdwMax = cursor.getDouble(idRdwMax);

        return new BloodRecord(id, leukocyteMin, leukocyteMax, erythocyteMin, erythocyteMax, hemoglobinMin, hemoglobinMax,
                hematocritMin, hematocritMax, mcvMin, mcvMax, mchMin, mchMax, mchcMin, mchcMax, plateletMin, plateletMax,
                reticulocytesMin, reticulocytesMax, mpvMin, mpvMax, rdwMin, rdwMax);
    }

    public List<BloodRecord> getAllBloodRecords( SQLiteDatabase database) {
        List<BloodRecord> List = new ArrayList<>();

        Cursor cursor = database.query(BloodTableContract.BloodTableEntry.TABLE_NAME,
                bloodColumns, null, null, null, null, null, null);

        cursor.moveToFirst();
        BloodRecord record;

        while (!cursor.isAfterLast()) {
            record = cursorToBloodRecord(cursor);
            List.add(record);
            Log.d(LOG_TAG, "ID: " + record.getId() + ", Content: " + record.toString());
            cursor.moveToNext();
        }
        cursor.close();

        return List;
    }

    public BloodRecord getBloodRecordById(int id, SQLiteDatabase database){
        Cursor cursor = database.query(BloodTableContract.BloodTableEntry.TABLE_NAME, bloodColumns,
                BloodTableContract.BloodTableEntry._ID + "=" + id, null, null, null, null);

        cursor.moveToFirst();
        BloodRecord record = cursorToBloodRecord(cursor);
        cursor.close();

        return record;
    }

}
