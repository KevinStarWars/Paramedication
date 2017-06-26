package com.example.kevin.paramedication.DatabaseContracts;

import android.provider.BaseColumns;

/**
 * ID|PatientID|BloodID
 */

public class PatientBloodCountTableContract {
    public static class PatientBloodCountTableEntry implements BaseColumns {
        public static final String TABLE_NAME = "Patient_Blood_Relation";
        public static final String COLUMN_PATIENT_ID = "Patient_ID";
        public static final String COLUMN_BLOOD_ID = "Blood_ID";
    }
}
