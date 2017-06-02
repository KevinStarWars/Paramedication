package com.example.kevin.paramedication.DatabaseRessources;

import android.provider.BaseColumns;

/**
 * ID|HostpitalID
 */

public class PatientTableContract {
    public static class PatientTableEntry  implements BaseColumns {
        public static final String TABLE_NAME = "Patients";
        public static final String HOSPITAL_ID = "Hospital ID";
    }
}
