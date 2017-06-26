package com.example.kevin.paramedication.DatabaseContracts;

import android.provider.BaseColumns;

/**
 * PRIMARY KEY - ID
 * INT - DISEASE ID
 * INT - DRUG ID
 */

public class DiseaseMedicationTableContract {
    public static final class DiseaseMedicationEntry implements BaseColumns {
        public static final String TABLE_NAME = "Disease_Medication";

        public static final String COLUMN_DISEASE_ID = "Disease_ID";
        public static final String COLUMN_DRUG_ID = "Drug_ID";
    }
}
