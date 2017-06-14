package com.example.kevin.paramedication.DatabaseObjects;


public class PatientBloodcountRecord {

    private int id;
    private int patientId;
    private int bloodcountId;

    public PatientBloodcountRecord(int id, int patientId, int bloodcountId) {
        this.id = id;
        this.patientId = patientId;
        this.bloodcountId = bloodcountId;
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

    public int getBloodcountId() {
        return bloodcountId;
    }

    public void setBloodcountId(int bloodcountId) {
        this.bloodcountId = bloodcountId;
    }

    @Override
    public String toString() {
        return "PatientBloodcountRecord{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", bloodcountId=" + bloodcountId +
                '}';
    }
}