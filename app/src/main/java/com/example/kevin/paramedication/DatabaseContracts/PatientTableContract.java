package com.example.kevin.paramedication.DatabaseContracts;

import android.provider.BaseColumns;

/**
 * ID|HostpitalID
 */

public class PatientTableContract {
    public static class PatientTableEntry  implements BaseColumns {
        public static final String TABLE_NAME = "Patients";
        public static final String COLUMN_HOSPITAL_ID = "Hospital_ID";
        public static final String COLUMN_GENDER = "Gender";
    }
}
