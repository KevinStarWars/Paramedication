package com.example.kevin.paramedication.DatabaseContracts;

import android.provider.BaseColumns;

/**
 * PRIMARY KEY - ID
 * INT - DRUG ID 1
 * INT - DRUG ID 2
 * STRING - TYPE OF INTERACTION
 */

public class MedicationInteractionTableContract {
    public static class MedicationInteractionEntry implements BaseColumns {
        public static final String TABLE_NAME = "Medication_Interaction";
        public static final String COLUMN_DRUG_ID1 = "Drug_ID_1";
        public static final String COLUMN_DRUG_ID2 = "Drug_ID_2";
        public static final String COLUMN_TYPE_OF_INTERACTION = "Type_of_Interaction";
    }
}
