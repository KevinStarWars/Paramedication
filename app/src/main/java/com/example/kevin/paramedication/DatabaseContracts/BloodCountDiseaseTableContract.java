package com.example.kevin.paramedication.DatabaseContracts;

import android.provider.BaseColumns;

/**
 * PRIMARY KEY - ID
 * INT - BLOOD COUNT ID
 * INT - DISEASE ID
 */

public class BloodCountDiseaseTableContract {

    public class BloodCountDiseaseTableEntry implements BaseColumns{
        public static final String TABLE_NAME = "Blood_Count_Disease_Relation";

        public static final String COLUMN_BLOOD_COUNT_ID = "Blood_Count_ID";
        public static final String COLUMN_DISEASE_ID = "Disease_ID";
    }
}
