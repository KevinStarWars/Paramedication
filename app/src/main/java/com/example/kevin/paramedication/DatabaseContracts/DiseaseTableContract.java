package com.example.kevin.paramedication.DatabaseContracts;

import android.provider.BaseColumns;

/**
 * PRIMARY KEY - ID
 * STRING - DISEASE NAME
 */

public class DiseaseTableContract {

    public static final class DiseaseTableEntry implements BaseColumns {
        public static final String TABLE_NAME = "Diseases";
        public static final String COLUMN_DISEASE_NAME = "Name";
    }
}
