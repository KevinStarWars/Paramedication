package com.example.kevin.paramedication.DatabaseContracts;


import android.provider.BaseColumns;

/**
 * PRIMARY KEY - ID
 * STRING - DRUG NAME
 */

public class MedicationTableContract {
    public static final class MedicationTableEntry implements BaseColumns {
        public static final String TABLE_NAME = "Drugs";
        public static String COLUMN_DRUG_NAME = "Drug_Name";
    }
}
