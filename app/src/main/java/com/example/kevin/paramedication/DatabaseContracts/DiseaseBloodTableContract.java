package com.example.kevin.paramedication.DatabaseContracts;

import android.provider.BaseColumns;

/**
 * PRIMARY KEY - ID
 * INT - DISEASE ID
 * INT - BLOOD ID
 */

public class DiseaseBloodTableContract {
    public static class DiseaseBloodEntry implements BaseColumns {
        public static final String TABLE_NAME = "Disease_Blood_Relation";

        public static final String COLUMN_DISEASE_ID = "Disease_ID";
        public static final String COLUMN_BLOOD_ID = "Blood_ID";
    }
}
