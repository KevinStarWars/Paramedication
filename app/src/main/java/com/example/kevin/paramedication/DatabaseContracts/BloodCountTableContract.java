package com.example.kevin.paramedication.DatabaseContracts;

import android.provider.BaseColumns;

public class BloodCountTableContract {

    public static final class BloodCountTableEntry implements BaseColumns {
        public static final String TABLE_NAME = "BloodCount";

        public static final String COLUMN_LEUKOCYTE = "Leukocyte";
        public static final String COLUMN_ERYTHROCYTE = "Erythrocyte";
        public static final String COLUMN_HEMOGLOBIN = "Hemoglobin";
        public static final String COLUMN_HEMATOCRIT = "Hematocrit";
        public static final String COLUMN_MCV = "MCV";
        public static final String COLUMN_MCH = "MCH";
        public static final String COLUMN_MCHC = "MCHC";
        public static final String COLUMN_PLATELET = "Platelet";
        public static final String COLUMN_RETICULOCYTES = "Reticulocytes";
        public static final String COLUMN_MPV = "MPV";
        public static final String COLUMN_RDW = "RDW";
    }
}
