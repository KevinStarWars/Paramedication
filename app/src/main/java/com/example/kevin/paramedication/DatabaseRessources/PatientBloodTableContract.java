package com.example.kevin.paramedication.DatabaseRessources;

import android.provider.BaseColumns;

/**
 * ID|PatientID|BloodID
 */

public class PatientBloodTableContract {
    public static class PatientBloodTableEntry implements BaseColumns {
        public static final String TABLE_NAME = "Patient Blood Relation";
        public static final String PATIENT_ID = "Patient ID";
        public static final String BLOOD_ID = "Blood ID";
    }
}
