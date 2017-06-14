package com.example.kevin.paramedication.DatabaseObjects;


public class DiseaseBloodRelationRecord {

    private int id;
    private int bloodId;
    private int diseaseId;

    public DiseaseBloodRelationRecord(int id, int bloodId, int diseaseId) {
        this.id = id;
        this.bloodId = bloodId;
        this.diseaseId = diseaseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBloodId() {
        return bloodId;
    }

    public void setBloodId(int bloodId) {
        this.bloodId = bloodId;
    }

    public int getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(int diseaseId) {
        this.diseaseId = diseaseId;
    }

    public String print() {
        return "ID: " + getId() + " BloodID: " + getBloodId() + " DiseaseID: " + getDiseaseId();
    }
}
