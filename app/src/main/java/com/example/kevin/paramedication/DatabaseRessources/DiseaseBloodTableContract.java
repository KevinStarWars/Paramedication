package com.example.kevin.paramedication.DatabaseRessources;

import android.provider.BaseColumns;

/**
 * ID|DiseaseID|BloodID
 */

public class DiseaseBloodTableContract{
    public static class DiseaseBloodEntry implements BaseColumns {
        public static final String TABLE_NAME = "Disease Blood Relation";
        public static final String DISEASE_ID = "Disease ID";
        public static final String BLOOD_ID = "Blood ID";
    }
}
