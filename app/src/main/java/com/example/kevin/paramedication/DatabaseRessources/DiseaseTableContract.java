package com.example.kevin.paramedication.DatabaseRessources;

import android.provider.BaseColumns;

//defines disease table database in sqlite database

// ID|Name|LeukocyteMin|LeukocyteMax|ErythrocyteMin|ErythrocyteMax|HemoglobinMin|
// HemoglobinMax|HematocritMin|HematocritMax|MCVMin|MCVMax|MCHMin|MCHMax|
// MCHCMin|MCHCMax|PlateletMin|PlateletMax|ReticulocytesMin|ReticulocytesMax|
// MPVMin|MPVMax|RDWMin|RDWMax

public class DiseaseTableContract {

    public static final class DiseaseTableEntry implements BaseColumns{
        public static final String TABLE_NAME = "Diseases";
        public static final String DISEASE_NAME = "Name";
        public static final String LEUKOCYTE_MIN = "Leukocyte Min";
        public static final String LEUKOCYTE_MAX = "Leukocyte Max";
        public static final String ERYTHROCYTE_MIN = "Erythrocyte Min";
        public static final String ERYTHROCYTE_MAX = "Erythrocyte Max";
        public static final String HEMOGLOBIN_MIN = "Hemoglobin Min";
        public static final String HEMOGLOBIN_MAX = "Hemoglobin Max";
        public static final String HEMATOCRIT_MIN = "Hematocrit Min";
        public static final String HEMATOCRIT_MAX = "Hematocrit Max";
        public static final String MCV_Min = "MCV Min";
        public static final String MCV_Max = "MCV Max";
        public static final String MCH_Min = "MCH Min";
        public static final String MCH_Max = "MCH Max";
        public static final String MCHC_Min = "MCHC Min";
        public static final String MCHC_Max = "MCHC Max";
        public static final String PLATELET_MIN = "Platelet Min";
        public static final String PLATELET_MAX = "Platelet Max";
        public static final String RETICULOCYTES_MIN = "Reticulocytes Min";
        public static final String RETICULOCYTES_MAX = "Reticulocytes Max";
        public static final String MPV_MIN = "MPV Min";
        public static final String MPV_MAX = "MPV Max";
        public static final String RDW_MIN = "RDW Min";
        public static final String RDW_MAX = "RDW Max";
    }
}
