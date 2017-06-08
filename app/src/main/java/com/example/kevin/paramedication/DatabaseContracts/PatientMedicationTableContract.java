package com.example.kevin.paramedication.DatabaseContracts;

import android.provider.BaseColumns;

/**
 * ID|PatientID|Drug ID
 */

public class PatientMedicationTableContract{
    public static class PatientMedicationTableEntry  implements BaseColumns {
        public static final String TABLE_NAME = "Patient_Medication_Relation";
        public static final String COLUMN_PATIENT_ID = "Patient_ID";
        public static final String COLUMN_DRUG_ID = "Drug_ID";
    }
}
