package com.example.kevin.paramedication.DatabaseObjects;


public class PatientMedicationRecord {

    private int id;
    private int patientId;
    private int medicationId;

    public PatientMedicationRecord(int id, int patientId, int medicationId) {
        this.id = id;
        this.patientId = patientId;
        this.medicationId = medicationId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(int medicationId) {
        this.medicationId = medicationId;
    }

    @Override
    public String toString() {
        return "PatientMedicationRecord{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", medicationId=" + medicationId +
                '}';
    }
}
