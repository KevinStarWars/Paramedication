package com.example.kevin.paramedication.DatabaseOperations;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kevin.paramedication.DatabaseContracts.BloodTableContract;
import com.example.kevin.paramedication.DatabaseObjects.BloodRecord;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
            BloodTableContract.BloodTableEntry.COLUMN_RDW_MAX,
            BloodTableContract.BloodTableEntry.COLUMN_GENDER
    };

    public BloodRecord createBloodRecord(String leukocyteMin, String leukocyteMax, String erythrocyteMin, String erythrocyteMax, String hemoglobinMin, String hemoglobinMax, String hematocritMin, String hematocritMax,
                                         String mcvMin, String mcvMax, String mchMin, String mchMax, String mchcMin, String mchcMax, String plateletMin, String plateletMax, String reticulocytesMin, String reticulocytesMax,
                                         String mpvMin, String mpvMax, String rdwMin, String rdwMax, String gender, SQLiteDatabase database) {

        List<BloodRecord> currentDatabase = getAllBloodRecords(database);

        if (leukocyteMin.isEmpty())

        for (int i = 0; i < currentDatabase.size(); i++) {
            if (currentDatabase.get(i).getLeukocyteMin() == convertToDefaultDouble(leukocyteMin) &&
                    currentDatabase.get(i).getLeukocyteMax() == convertToDefaultDouble(leukocyteMax) &&
                    currentDatabase.get(i).getErythrocyteMin() == convertToDefaultDouble(erythrocyteMin) &&
                    currentDatabase.get(i).getErythrocyteMax() == convertToDefaultDouble(erythrocyteMax) &&
                    currentDatabase.get(i).getHemoglobinMin() == convertToDefaultDouble(hemoglobinMin) &&
                    currentDatabase.get(i).getHemoglobinMax() == convertToDefaultDouble(hemoglobinMax) &&
                    currentDatabase.get(i).getHematocritMin() == convertToDefaultDouble(hematocritMin) &&
                    currentDatabase.get(i).getHematocritMax() == convertToDefaultDouble(hematocritMax) &&
                    currentDatabase.get(i).getMcvMin() == convertToDefaultDouble(mcvMin) &&
                    currentDatabase.get(i).getMcvMax() == convertToDefaultDouble(mcvMax) &&
                    currentDatabase.get(i).getMchMin() == convertToDefaultDouble(mchMin) &&
                    currentDatabase.get(i).getMchMax() == convertToDefaultDouble(mchMax) &&
                    currentDatabase.get(i).getMchcMin() == convertToDefaultDouble(mchcMin) &&
                    currentDatabase.get(i).getMchcMax() == convertToDefaultDouble(mchcMax) &&
                    currentDatabase.get(i).getPlateletMin() == convertToDefaultDouble(plateletMin) &&
                    currentDatabase.get(i).getPlateletMax() == convertToDefaultDouble(plateletMax) &&
                    currentDatabase.get(i).getReticulocytesMin() == convertToDefaultDouble(reticulocytesMin) &&
                    currentDatabase.get(i).getReticulocytesMax() == convertToDefaultDouble(reticulocytesMax) &&
                    currentDatabase.get(i).getMpvMin() == convertToDefaultDouble(mpvMin) &&
                    currentDatabase.get(i).getMpvMax() == convertToDefaultDouble(mpvMax) &&
                    currentDatabase.get(i).getRdwMin() == convertToDefaultDouble(rdwMin) &&
                    currentDatabase.get(i).getRdwMax() == convertToDefaultDouble(rdwMax) &&
                    currentDatabase.get(i).getGender().equals(gender)) {
                return currentDatabase.get(i);
            }
        }

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
        values.put(BloodTableContract.BloodTableEntry.COLUMN_GENDER, gender);

        long insertId = database.insert(BloodTableContract.BloodTableEntry.TABLE_NAME, null, values);

        // SELECT * FROM BloodTableContract.BloodTableEntry.TABLE_NAME WHERE BloodTableContract.BloodTableEntry._ID = insertId
        Cursor cursor = database.query(BloodTableContract.BloodTableEntry.TABLE_NAME, bloodColumns,
                BloodTableContract.BloodTableEntry._ID + "=" + insertId, null, null, null, null);


        while (!cursor.moveToNext()) {
            Log.d(LOG_TAG, "not able to move to next.");
        }

        BloodRecord record = cursorToBloodRecord(cursor);
        Log.d(LOG_TAG, record.toString());
        cursor.close();

        return record;
    }

    @org.jetbrains.annotations.Contract("null -> !null")
    private BloodRecord cursorToBloodRecord(Cursor cursor) {

        if (cursor != null) {

            int id = cursor.getInt(cursor.getColumnIndex(BloodTableContract.BloodTableEntry._ID));
            double leukocyteMin = cursor.getDouble(cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_LEUKOCYTE_MIN));
            double leukocyteMax = cursor.getDouble(cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_LEUKOCYTE_MAX));
            double erythocyteMin = cursor.getDouble(cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_ERYTHROCYTE_MIN));
            double erythocyteMax = cursor.getDouble(cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_ERYTHROCYTE_MAX));
            double hemoglobinMin = cursor.getDouble(cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_HEMOGLOBIN_MIN));
            double hemoglobinMax = cursor.getDouble(cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_HEMOGLOBIN_MAX));
            double hematocritMin = cursor.getDouble(cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_HEMATOCRIT_MIN));
            double hematocritMax = cursor.getDouble(cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_HEMATOCRIT_MAX));
            double mcvMin = cursor.getDouble(cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_MCV_MIN));
            double mcvMax = cursor.getDouble(cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_MCV_MAX));
            double mchMin = cursor.getDouble(cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_MCH_MIN));
            double mchMax = cursor.getDouble(cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_MCH_MAX));
            double mchcMin = cursor.getDouble(cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_MCHC_MIN));
            double mchcMax = cursor.getDouble(cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_MCHC_MAX));
            double plateletMin = cursor.getDouble(cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_PLATELET_MIN));
            double plateletMax = cursor.getDouble(cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_PLATELET_MAX));
            double reticulocytesMin = cursor.getDouble(cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_RETICULOCYTES_MIN));
            double reticulocytesMax = cursor.getDouble(cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_RETICULOCYTES_MAX));
            double mpvMin = cursor.getDouble(cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_MPV_MIN));
            double mpvMax = cursor.getDouble(cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_MPV_MAX));
            double rdwMin = cursor.getDouble(cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_RDW_MIN));
            double rdwMax = cursor.getDouble(cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_RDW_MAX));
            String gender = cursor.getString(cursor.getColumnIndex(BloodTableContract.BloodTableEntry.COLUMN_GENDER));

            return new BloodRecord(id, leukocyteMin, leukocyteMax, erythocyteMin, erythocyteMax, hemoglobinMin, hemoglobinMax,
                    hematocritMin, hematocritMax, mcvMin, mcvMax, mchMin, mchMax, mchcMin, mchcMax, plateletMin, plateletMax,
                    reticulocytesMin, reticulocytesMax, mpvMin, mpvMax, rdwMin, rdwMax, gender);
        }
        return new BloodRecord(-1, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, "f");
    }

    public List<BloodRecord> getAllBloodRecords(SQLiteDatabase database) {
        List<BloodRecord> List = new ArrayList<>();

        Cursor cursor = database.query(BloodTableContract.BloodTableEntry.TABLE_NAME,
                bloodColumns, null, null, null, null, null, null);

        cursor.moveToFirst();
        BloodRecord record;

        while (!cursor.isAfterLast()) {
            record = cursorToBloodRecord(cursor);
            if (!cursor.moveToNext()) {
                Log.d(LOG_TAG, "not able to move to next.");
            }
            List.add(record);
        }
        cursor.close();

        return List;
    }

    public BloodRecord getById(int id, SQLiteDatabase database) {
        Cursor cursor = database.query(BloodTableContract.BloodTableEntry.TABLE_NAME, bloodColumns,
                BloodTableContract.BloodTableEntry._ID + "=" + id, null, null, null, null);

        cursor.moveToFirst();

        return cursorToBloodRecord(cursor);
    }

    private double convertToDefaultDouble(String number) {
        if (!number.isEmpty()) {
            Locale theLocale = Locale.getDefault();
            NumberFormat numberFormat = DecimalFormat.getInstance(theLocale);
            Number theNumber;
            try {
                theNumber = numberFormat.parse(number);
                return theNumber.doubleValue();
            } catch (ParseException e) {
                Log.d(LOG_TAG, e.getMessage());
                return 0d;
            }
        } else return 0;
    }
    
}
