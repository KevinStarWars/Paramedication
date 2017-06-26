package com.example.kevin.paramedication.DatabaseOperations;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kevin.paramedication.DatabaseContracts.ProcessTableContract;
import com.example.kevin.paramedication.DatabaseObjects.ProcessRecord;

import java.util.ArrayList;
import java.util.List;

public class ProcessOperations {

    final static String LOG_TAG = ProcessOperations.class.getSimpleName();

    private String[] processColumns = {
            ProcessTableContract.ProcessTableEntry._ID,
            ProcessTableContract.ProcessTableEntry.COLUMN_DIAGNOSIS_HELP,
            ProcessTableContract.ProcessTableEntry.COLUMN_PATIENT_HELP,
            ProcessTableContract.ProcessTableEntry.COLUMN_DATABASE_HELP,
            ProcessTableContract.ProcessTableEntry.COLUMN_MEDICATION_HELP,
            ProcessTableContract.ProcessTableEntry.COLUMN_INFO_HELP
    };

    ProcessRecord createProcessRecord(int diagnosis, int patient, int database, int medication, int info, SQLiteDatabase sqLiteDatabase) {

        ContentValues values = new ContentValues();
        values.put(ProcessTableContract.ProcessTableEntry.COLUMN_DIAGNOSIS_HELP, diagnosis);
        values.put(ProcessTableContract.ProcessTableEntry.COLUMN_PATIENT_HELP, patient);
        values.put(ProcessTableContract.ProcessTableEntry.COLUMN_DATABASE_HELP, database);
        values.put(ProcessTableContract.ProcessTableEntry.COLUMN_MEDICATION_HELP, medication);
        values.put(ProcessTableContract.ProcessTableEntry.COLUMN_INFO_HELP, info);

        long insertId = sqLiteDatabase.insert(ProcessTableContract.ProcessTableEntry.TABLE_NAME, null, values);

        Cursor cursor = sqLiteDatabase.query(ProcessTableContract.ProcessTableEntry.TABLE_NAME, processColumns,
                ProcessTableContract.ProcessTableEntry._ID + "=" + insertId, null, null, null, null);

        cursor.moveToFirst();
        ProcessRecord record = cursorToProcessRecord(cursor);
        cursor.close();

        Log.d(LOG_TAG, record.toString());

        return record;
    }

    private ProcessRecord cursorToProcessRecord(Cursor cursor) {

        int id = cursor.getInt(cursor.getColumnIndex(ProcessTableContract.ProcessTableEntry._ID));
        int diagnosis = cursor.getInt(cursor.getColumnIndex(ProcessTableContract.ProcessTableEntry.COLUMN_DIAGNOSIS_HELP));
        int patient = cursor.getInt(cursor.getColumnIndex(ProcessTableContract.ProcessTableEntry.COLUMN_PATIENT_HELP));
        int database = cursor.getInt(cursor.getColumnIndex(ProcessTableContract.ProcessTableEntry.COLUMN_DATABASE_HELP));
        int medication = cursor.getInt(cursor.getColumnIndex(ProcessTableContract.ProcessTableEntry.COLUMN_MEDICATION_HELP));
        int info = cursor.getInt(cursor.getColumnIndex(ProcessTableContract.ProcessTableEntry.COLUMN_INFO_HELP));

        boolean diagnosisValue = true;
        boolean patientValue = true;
        boolean databaseValue = true;
        boolean medicationValue = true;
        boolean infoValue = true;

        if (diagnosis == 0) {
            diagnosisValue = false;
        }
        if (patient == 0) {
            patientValue = false;
        }
        if (database == 0) {
            databaseValue = false;
        }
        if (medication == 0) {
            medicationValue = false;
        }
        if (info == 0) {
            infoValue = false;
        }

        return new ProcessRecord(id, diagnosisValue, patientValue, databaseValue, medicationValue, infoValue);
    }

    private List<ProcessRecord> getAllProcessRecords(SQLiteDatabase database) {
        List<ProcessRecord> List = new ArrayList<>();

        Cursor cursor = database.query(ProcessTableContract.ProcessTableEntry.TABLE_NAME,
                processColumns, null, null, null, null, null, null);

        cursor.moveToFirst();
        ProcessRecord record;

        while (!cursor.isAfterLast()) {
            record = cursorToProcessRecord(cursor);
            List.add(record);
            cursor.moveToNext();
        }
        cursor.close();

        return List;
    }

    public ProcessRecord getEntry(int id, SQLiteDatabase sqLiteDatabase) {

        Cursor cursor = sqLiteDatabase.query(ProcessTableContract.ProcessTableEntry.TABLE_NAME, processColumns,
                ProcessTableContract.ProcessTableEntry._ID + "=" + id, null, null, null, null);

        cursor.moveToFirst();

        return cursorToProcessRecord(cursor);
    }

    public void updateEntry(String activity, SQLiteDatabase sqLiteDatabase) {
        if (activity.equals("diagnosis")) {
            ContentValues values = new ContentValues();
            values.put(ProcessTableContract.ProcessTableEntry.COLUMN_DIAGNOSIS_HELP, 0);
            sqLiteDatabase.update(ProcessTableContract.ProcessTableEntry.TABLE_NAME, values, ProcessTableContract.ProcessTableEntry._ID + "= 1", null);
        }
        if (activity.equals("patients")) {
            ContentValues values = new ContentValues();
            values.put(ProcessTableContract.ProcessTableEntry.COLUMN_PATIENT_HELP, 0);
            sqLiteDatabase.update(ProcessTableContract.ProcessTableEntry.TABLE_NAME, values, ProcessTableContract.ProcessTableEntry._ID + "= 1", null);
        }
        if (activity.equals("database")) {
            ContentValues values = new ContentValues();
            values.put(ProcessTableContract.ProcessTableEntry.COLUMN_DATABASE_HELP, 0);
            sqLiteDatabase.update(ProcessTableContract.ProcessTableEntry.TABLE_NAME, values, ProcessTableContract.ProcessTableEntry._ID + "= 1", null);
        }
        if (activity.equals("medication")) {
            ContentValues values = new ContentValues();
            values.put(ProcessTableContract.ProcessTableEntry.COLUMN_MEDICATION_HELP, 0);
            sqLiteDatabase.update(ProcessTableContract.ProcessTableEntry.TABLE_NAME, values, ProcessTableContract.ProcessTableEntry._ID + "= 1", null);
        }
        if (activity.equals("info")) {
            ContentValues values = new ContentValues();
            values.put(ProcessTableContract.ProcessTableEntry.COLUMN_INFO_HELP, 0);
            sqLiteDatabase.update(ProcessTableContract.ProcessTableEntry.TABLE_NAME, values, ProcessTableContract.ProcessTableEntry._ID + "= 1", null);
        }

        List<ProcessRecord> processRecords = getAllProcessRecords(sqLiteDatabase);
        Log.d(LOG_TAG, processRecords.get(0).toString());
    }

}
