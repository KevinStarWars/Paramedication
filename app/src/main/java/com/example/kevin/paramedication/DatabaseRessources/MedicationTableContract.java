package com.example.kevin.paramedication.DatabaseRessources;

//defines the medication table in sqlite database

import android.provider.BaseColumns;

// ID|DrugName

public class MedicationTableContract{
    public static final class MedicationTableEntry  implements BaseColumns{
        public static String DRUG_NAME = "Drug Name";
    }
}
