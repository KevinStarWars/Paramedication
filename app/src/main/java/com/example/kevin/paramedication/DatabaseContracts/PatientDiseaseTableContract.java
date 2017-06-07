package com.example.kevin.paramedication.DatabaseContracts;

import android.provider.BaseColumns;

/**
 * Contract describes database table entries for Patient Disease Relation
 */

public class PatientDiseaseTableContract {
    public static class PatientDiseaseTableEntry  implements BaseColumns{
        public static final String TABLE_NAME = "Patient_Disease_Relation";
        public static final String PATIENT_ID = "Patient_ID";
        public static final String DISEASE_ID = "Disease_ID";
    }
}
