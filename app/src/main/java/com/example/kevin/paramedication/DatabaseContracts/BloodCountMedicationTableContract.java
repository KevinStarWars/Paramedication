package com.example.kevin.paramedication.DatabaseContracts;

import android.provider.BaseColumns;

/**
 * PRIMARY KEY - ID
 * INT - BLOOD COUNT ID
 * INT - MEDICATION ID
 */

public class BloodCountMedicationTableContract {

    public class BloodCountMedicationTableEntry implements BaseColumns{
        public static final String TABLE_NAME = "Blood_Count_Medication_Relation_Table";

        public static final String COLUMN_BLOOD_COUNT_ID = "Blood_Count_ID";
        public static final String COLUMN_MEDICATION_ID = "Medication_ID";
    }

}
