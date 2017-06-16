package com.example.kevin.paramedication.DatabaseOperations;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kevin.paramedication.DatabaseContracts.BloodCountTableContract;
import com.example.kevin.paramedication.DatabaseObjects.BloodCountRecord;

import java.util.ArrayList;
import java.util.List;

import static com.example.kevin.paramedication.MainActivity.LOG_TAG;


public class BloodCountOperations {

    private String[] bloodCountColumns = {
            BloodCountTableContract.BloodCountTableEntry._ID,
            BloodCountTableContract.BloodCountTableEntry.COLUMN_LEUKOCYTE,
            BloodCountTableContract.BloodCountTableEntry.COLUMN_ERYTHROCYTE,
            BloodCountTableContract.BloodCountTableEntry.COLUMN_HEMOGLOBIN,
            BloodCountTableContract.BloodCountTableEntry.COLUMN_HEMATOCRIT,
            BloodCountTableContract.BloodCountTableEntry.COLUMN_MCV,
            BloodCountTableContract.BloodCountTableEntry.COLUMN_MCH,
            BloodCountTableContract.BloodCountTableEntry.COLUMN_MCHC,
            BloodCountTableContract.BloodCountTableEntry.COLUMN_PLATELET,
            BloodCountTableContract.BloodCountTableEntry.COLUMN_RETICULOCYTES,
            BloodCountTableContract.BloodCountTableEntry.COLUMN_MPV,
            BloodCountTableContract.BloodCountTableEntry.COLUMN_RDW
    };

    // creates new entry or returns old entry if entry is already in database
    public BloodCountRecord createBloodCountRecord(String leukocyte, String erythrocyte, String hemoglobin, String hematocrit,
                                                   String mcv, String mch, String mchc, String platelet, String reticulocytes,
                                                   String mpv, String rdw, SQLiteDatabase database) {

        List<BloodCountRecord> currentDatabase = getAllBloodCountRecords(database);
        for (int i = 0; i < currentDatabase.size(); i++) {
            if (currentDatabase.get(i).getLeukocyte() == Double.parseDouble(leukocyte)) {
                if (currentDatabase.get(i).getErythrocyte() == Double.parseDouble(erythrocyte)) {
                    if (currentDatabase.get(i).getHemoglobin() == Double.parseDouble(hemoglobin)) {
                        if (currentDatabase.get(i).getHematocrit() == Double.parseDouble(hematocrit)) {
                            if (currentDatabase.get(i).getMcv() == Double.parseDouble(mcv)) {
                                if (currentDatabase.get(i).getMch() == Double.parseDouble(mch)) {
                                    if (currentDatabase.get(i).getMchc() == Double.parseDouble(mchc)) {
                                        if (currentDatabase.get(i).getPlatelet() == Double.parseDouble(platelet)) {
                                            if (currentDatabase.get(i).getReticulocytes() == Double.parseDouble(reticulocytes)) {
                                                if (currentDatabase.get(i).getMpv() == Double.parseDouble(mpv)) {
                                                    if (currentDatabase.get(i).getRdw() == Double.parseDouble(rdw)) {
                                                        return currentDatabase.get(i);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        ContentValues values = new ContentValues();
        values.put(BloodCountTableContract.BloodCountTableEntry.COLUMN_LEUKOCYTE, leukocyte);
        values.put(BloodCountTableContract.BloodCountTableEntry.COLUMN_ERYTHROCYTE, erythrocyte);
        values.put(BloodCountTableContract.BloodCountTableEntry.COLUMN_HEMOGLOBIN, hemoglobin);
        values.put(BloodCountTableContract.BloodCountTableEntry.COLUMN_HEMATOCRIT, hematocrit);
        values.put(BloodCountTableContract.BloodCountTableEntry.COLUMN_MCV, mcv);
        values.put(BloodCountTableContract.BloodCountTableEntry.COLUMN_MCH, mch);
        values.put(BloodCountTableContract.BloodCountTableEntry.COLUMN_MCHC, mchc);
        values.put(BloodCountTableContract.BloodCountTableEntry.COLUMN_PLATELET, platelet);
        values.put(BloodCountTableContract.BloodCountTableEntry.COLUMN_RETICULOCYTES, reticulocytes);
        values.put(BloodCountTableContract.BloodCountTableEntry.COLUMN_MPV, mpv);
        values.put(BloodCountTableContract.BloodCountTableEntry.COLUMN_RDW, rdw);

        long insertId = database.insert(BloodCountTableContract.BloodCountTableEntry.TABLE_NAME, null, values);

        Cursor cursor = database.query(BloodCountTableContract.BloodCountTableEntry.TABLE_NAME, bloodCountColumns,
                BloodCountTableContract.BloodCountTableEntry._ID + "=" + insertId, null, null, null, null);

        cursor.moveToFirst();
        BloodCountRecord record = cursorToBloodCountRecord(cursor);
        cursor.close();

        return record;
    }

    // Blood Operations

    private BloodCountRecord cursorToBloodCountRecord(Cursor cursor) {

        int idIndex = cursor.getColumnIndex(BloodCountTableContract.BloodCountTableEntry._ID);
        int idLeukocyte = cursor.getColumnIndex(BloodCountTableContract.BloodCountTableEntry.COLUMN_LEUKOCYTE);
        int idErythrocyte = cursor.getColumnIndex(BloodCountTableContract.BloodCountTableEntry.COLUMN_ERYTHROCYTE);
        int idHemoglobin = cursor.getColumnIndex(BloodCountTableContract.BloodCountTableEntry.COLUMN_HEMOGLOBIN);
        int idHematocrit = cursor.getColumnIndex(BloodCountTableContract.BloodCountTableEntry.COLUMN_HEMATOCRIT);
        int idMcv = cursor.getColumnIndex(BloodCountTableContract.BloodCountTableEntry.COLUMN_MCV);
        int idMch = cursor.getColumnIndex(BloodCountTableContract.BloodCountTableEntry.COLUMN_MCH);
        int idMchc = cursor.getColumnIndex(BloodCountTableContract.BloodCountTableEntry.COLUMN_MCHC);
        int idPlatelet = cursor.getColumnIndex(BloodCountTableContract.BloodCountTableEntry.COLUMN_PLATELET);
        int idReticulocytes = cursor.getColumnIndex(BloodCountTableContract.BloodCountTableEntry.COLUMN_RETICULOCYTES);
        int idMpv = cursor.getColumnIndex(BloodCountTableContract.BloodCountTableEntry.COLUMN_MPV);
        int idRdw = cursor.getColumnIndex(BloodCountTableContract.BloodCountTableEntry.COLUMN_RDW);

        int id = cursor.getInt(idIndex);
        double leukocyte = cursor.getDouble(idLeukocyte);
        double erythocyte = cursor.getDouble(idErythrocyte);
        double hemoglobin = cursor.getDouble(idHemoglobin);
        double hematocrit = cursor.getDouble(idHematocrit);
        double mcv = cursor.getDouble(idMcv);
        double mch = cursor.getDouble(idMch);
        double mchc = cursor.getDouble(idMchc);
        double platelet = cursor.getDouble(idPlatelet);
        double reticulocytes = cursor.getDouble(idReticulocytes);
        double mpv = cursor.getDouble(idMpv);
        double rdw = cursor.getDouble(idRdw);

        return new BloodCountRecord(id, leukocyte, erythocyte, hemoglobin, hematocrit, mcv, mch,
                mchc, platelet, reticulocytes, mpv, rdw);
    }

    public List<BloodCountRecord> getAllBloodCountRecords(SQLiteDatabase database) {
        List<BloodCountRecord> List = new ArrayList<>();

        Cursor cursor = database.query(BloodCountTableContract.BloodCountTableEntry.TABLE_NAME,
                bloodCountColumns, null, null, null, null, null, null);

        cursor.moveToFirst();
        BloodCountRecord record;

        while (!cursor.isAfterLast()) {
            record = cursorToBloodCountRecord(cursor);
            List.add(record);
            Log.d(LOG_TAG, "ID: " + record.getId() + ", Content: " + record.print());
            cursor.moveToNext();
        }
        cursor.close();

        return List;
    }


}
