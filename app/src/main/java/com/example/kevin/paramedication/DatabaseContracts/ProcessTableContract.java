package com.example.kevin.paramedication.DatabaseContracts;


import android.provider.BaseColumns;

/**
 * PRIMARY KEY - ID
 * INT - DIAGNOSIS HELP
 * INT - DATABASE HELP
 * INT - MEDICATION HELP
 * INT - PATIENT HELP
 * INT - INFO HELP
 */

public class ProcessTableContract {

    public class ProcessTableEntry implements BaseColumns {
        public final static String TABLE_NAME = "Process";
        public final static String COLUMN_DIAGNOSIS_HELP = "Diagnosis_Help"; // integer: 1 = true, 0 = false
        public final static String COLUMN_DATABASE_HELP = "Database_Help";
        public final static String COLUMN_MEDICATION_HELP = "Medication_Help";
        public final static String COLUMN_PATIENT_HELP = "Patient_Help";
        public final static String COLUMN_INFO_HELP = "Info_Help";
    }
}
