package com.example.kevin.paramedication.DatabaseObjects;


public class ProcessRecord {
    private int id;
    private boolean diagnosisHelp;
    private boolean databaseHelp;
    private boolean patientHelp;
    private boolean infoHelp;
    private boolean medicationHelp;

    public ProcessRecord(int id, boolean diagnosisHelp, boolean patientHelp, boolean databaseHelp, boolean medicationHelp, boolean infoHelp) {
        this.id = id;
        this.diagnosisHelp = diagnosisHelp;
        this.patientHelp = patientHelp;
        this.databaseHelp = databaseHelp;
        this.medicationHelp = medicationHelp;
        this.infoHelp = infoHelp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDiagnosisHelp() {
        return diagnosisHelp;
    }

    public boolean isDatabaseHelp() {
        return databaseHelp;
    }

    public boolean isPatientHelp() {
        return patientHelp;
    }

    public boolean isInfoHelp() {
        return infoHelp;
    }

    public boolean isMedicationHelp() {
        return medicationHelp;
    }

    @Override
    public String toString() {
        return "ProcessRecord{" +
                "id=" + id +
                ", diagnosisHelp=" + diagnosisHelp +
                ", databaseHelp=" + databaseHelp +
                ", patientHelp=" + patientHelp +
                ", infoHelp=" + infoHelp +
                ", medicationHelp=" + medicationHelp +
                '}';
    }
}
