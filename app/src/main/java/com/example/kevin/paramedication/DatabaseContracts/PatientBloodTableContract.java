package com.example.kevin.paramedication.DatabaseContracts;

import android.provider.BaseColumns;

/**
 * ID|PatientID|BloodID
 */

public class PatientBloodTableContract {
    public static class PatientBloodTableEntry implements BaseColumns {
        public static final String TABLE_NAME = "Patient_Blood_Relation";
        public static final String PATIENT_ID = "Patient_ID";
        public static final String BLOOD_ID = "Blood_ID";
    }
}
