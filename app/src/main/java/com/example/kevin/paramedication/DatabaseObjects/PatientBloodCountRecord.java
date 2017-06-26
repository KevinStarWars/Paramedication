package com.example.kevin.paramedication.DatabaseObjects;


public class PatientBloodCountRecord {

    private int id;
    private int patientId;
    private int bloodCountId;

    public PatientBloodCountRecord(int id, int patientId, int bloodCountId) {
        this.id = id;
        this.patientId = patientId;
        this.bloodCountId = bloodCountId;
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

    public int getBloodCountId() {
        return bloodCountId;
    }

    @Override
    public String toString() {
        return "PatientBloodCountRecord{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", bloodCountId=" + bloodCountId +
                '}';
    }
}
