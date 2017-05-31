package com.example.kevin.paramedication.DatabaseRessources;

import android.provider.BaseColumns;

/**
 * Defines Disease medication table in sqlite database
 */

// ID|DiseaseID|MedicationID

public class DiseaseMedicationTableContract{
    public static final class DiseaseMedicationEntry implements BaseColumns{
        public static final String TABLE_NAME = "Disease Medication";
        public static final String DiseaseID = "Disease ID";
        public static final String DrugID = "Drug ID";
    }
}
