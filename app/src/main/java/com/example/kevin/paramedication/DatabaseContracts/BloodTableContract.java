package com.example.kevin.paramedication.DatabaseContracts;

import android.provider.BaseColumns;

/**
 * ID|LeukocyteMin|LeukocyteMax|ErythrocyteMin|ErythrocyteMax|HemoglobinMin|
 * HemoglobinMax|HematocritMin|HematocritMax|MCVMin|MCVMax|MCHMin|MCHMax|
 * MCHCMin|MCHCMax|PlateletMin|PlateletMax|ReticulocytesMin|ReticulocytesMax|
 * MPVMin|MPVMax|RDWMin|RDWMax
 */

public class BloodTableContract {
    public static class BloodTableEntry implements BaseColumns {
        public static final String TABLE_NAME = "Blood";

        public static final String COLUMN_LEUKOCYTE_MIN = "Leukocyte_Min";
        public static final String COLUMN_LEUKOCYTE_MAX = "Leukocyte_Max";
        public static final String COLUMN_ERYTHROCYTE_MIN = "Erythrocyte_Min";
        public static final String COLUMN_ERYTHROCYTE_MAX = "Erythrocyte_Max";
        public static final String COLUMN_HEMOGLOBIN_MIN = "Hemoglobin_Min";
        public static final String COLUMN_HEMOGLOBIN_MAX = "Hemoglobin_Max";
        public static final String COLUMN_HEMATOCRIT_MIN = "Hematocrit_Min";
        public static final String COLUMN_HEMATOCRIT_MAX = "Hematocrit_Max";
        public static final String COLUMN_MCV_MIN = "MCV_Min";
        public static final String COLUMN_MCV_MAX = "MCV_MaX";
        public static final String COLUMN_MCH_MIN = "MCH_MiN";
        public static final String COLUMN_MCH_MAX = "MCH_MaX";
        public static final String COLUMN_MCHC_MIN = "MCHC_MiN";
        public static final String COLUMN_MCHC_MAX = "MCHC_MaX";
        public static final String COLUMN_PLATELET_MIN = "Platelet_Min";
        public static final String COLUMN_PLATELET_MAX = "Platelet_Max";
        public static final String COLUMN_RETICULOCYTES_MIN = "Reticulocytes_Min";
        public static final String COLUMN_RETICULOCYTES_MAX = "Reticulocytes_Max";
        public static final String COLUMN_MPV_MIN = "MPV_Min";
        public static final String COLUMN_MPV_MAX = "MPV_Max";
        public static final String COLUMN_RDW_MIN = "RDW_Min";
        public static final String COLUMN_RDW_MAX = "RDW_Max";
    }
}
