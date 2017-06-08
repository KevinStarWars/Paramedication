package com.example.kevin.paramedication.DatabaseContracts;

//defines the medication table in sqlite database

import android.provider.BaseColumns;

// ID|DrugName

public class MedicationTableContract{
    public static final class MedicationTableEntry  implements BaseColumns{
        public static final String TABLE_NAME = "Drugs";
        public static String COLUMN_DRUG_NAME = "Drug_Name";
    }
}
