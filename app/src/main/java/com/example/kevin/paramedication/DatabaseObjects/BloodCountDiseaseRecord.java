package com.example.kevin.paramedication.DatabaseObjects;



public class BloodCountDiseaseRecord {

    private int id;
    private int bloodCountId;
    private int diseaseId;

    public BloodCountDiseaseRecord(int id, int bloodCountId, int diseaseId) {
        this.id = id;
        this.bloodCountId = bloodCountId;
        this.diseaseId = diseaseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBloodCountId() {
        return bloodCountId;
    }

    public void setBloodCountId(int bloodCountId) {
        this.bloodCountId = bloodCountId;
    }

    public int getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(int diseaseId) {
        this.diseaseId = diseaseId;
    }

    @Override
    public String toString() {
        return "BloodCountDiseaseRecord{" +
                "id=" + id +
                ", bloodCountId=" + bloodCountId +
                ", diseaseId=" + diseaseId +
                '}';
    }
}
