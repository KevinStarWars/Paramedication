package com.example.kevin.paramedication.DatabaseContracts;

import android.provider.BaseColumns;

/**
 * Defines Disease medication table in sqlite database
 */

// ID|Disease ID|MedicationID

public class DiseaseMedicationTableContract{
    public static final class DiseaseMedicationEntry implements BaseColumns{
        public static final String TABLE_NAME = "Disease_Medication";

        public static final String COLUMN_DISEASE_ID = "Disease_ID";
        public static final String COLUMN_DRUG_ID = "Drug_ID";
    }
}
