package com.example.kevin.paramedication.DatabaseRessources;

import android.provider.BaseColumns;

/**
 * defines medication interaction entries in sqlite database
 */

// ID|DrugID1|DrugID2|TypeOfInteraction
public class MedicationInteractionTableContract {
    public static class MedicationInteractionEntry implements BaseColumns{
        public static final String DRUG_ID1 = "Drug ID 1";
        public static final String DRUG_ID2 = "Drug ID 2";
        public static final String TYPE_OF_INTERACTION = "Type of Interaction";
    }
}
