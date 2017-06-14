package com.example.kevin.paramedication.DatabaseObjects;


public class PatientDiseaseRecord {

    private int id;
    private int patientId;
    private int diseaseId;

    public PatientDiseaseRecord(int id, int patientId, int diseaseId) {
        this.id = id;
        this.patientId = patientId;
        this.diseaseId = diseaseId;
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

    public int getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(int diseaseId) {
        this.diseaseId = diseaseId;
    }

    @Override
    public String toString() {
        return "PatientDiseaseRecord{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", diseaseId=" + diseaseId +
                '}';
    }
}
