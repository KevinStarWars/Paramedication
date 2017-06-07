package com.example.kevin.paramedication.DatabaseContracts;

import android.provider.BaseColumns;

/**
 * defines medication interaction entries in sqlite database
 */

// ID|DrugID1|DrugID2|TypeOfInteraction
public class MedicationInteractionTableContract {
    public static class MedicationInteractionEntry implements BaseColumns{
        public static final String TABLE_NAME = "Medication_Interaction";
        public static final String DRUG_ID1 = "Drug_ID_1";
        public static final String DRUG_ID2 = "Drug_ID_2";
        public static final String TYPE_OF_INTERACTION = "Type_of_Interaction";
    }
}
