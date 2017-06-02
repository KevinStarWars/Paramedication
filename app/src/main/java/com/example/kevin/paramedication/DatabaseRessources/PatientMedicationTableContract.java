package com.example.kevin.paramedication.DatabaseRessources;

import android.provider.BaseColumns;

/**
 * ID|PatientID|DrugID
 */

public class PatientMedicationTableContract{
    public static class PatientMedicationTableEntry  implements BaseColumns {
        public static final String TABLE_NAME = "Patient Medication Relation";
        public static final String PATIENT_ID = "Patient ID";
        public static final String DRUG_ID = "Drug ID";
    }
}
