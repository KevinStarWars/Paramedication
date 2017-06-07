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
        public static final String LEUKOCYTE_MIN = "Leukocyte_Min";
        public static final String LEUKOCYTE_MAX = "Leukocyte_Max";
        public static final String ERYTHROCYTE_MIN = "Erythrocyte_Min";
        public static final String ERYTHROCYTE_MAX = "Erythrocyte_Max";
        public static final String HEMOGLOBIN_MIN = "Hemoglobin_Min";
        public static final String HEMOGLOBIN_MAX = "Hemoglobin_Max";
        public static final String HEMATOCRIT_MIN = "Hematocrit_Min";
        public static final String HEMATOCRIT_MAX = "Hematocrit_Max";
        public static final String MCV_Min = "MCV_Min";
        public static final String MCV_Max = "MCV_Max";
        public static final String MCH_Min = "MCH_Min";
        public static final String MCH_Max = "MCH_Max";
        public static final String MCHC_Min = "MCHC_Min";
        public static final String MCHC_Max = "MCHC_Max";
        public static final String PLATELET_MIN = "Platelet_Min";
        public static final String PLATELET_MAX = "Platelet_Max";
        public static final String RETICULOCYTES_MIN = "Reticulocytes_Min";
        public static final String RETICULOCYTES_MAX = "Reticulocytes_Max";
        public static final String MPV_MIN = "MPV_Min";
        public static final String MPV_MAX = "MPV_Max";
        public static final String RDW_MIN = "RDW_Min";
        public static final String RDW_MAX = "RDW_Max";
    }
}
