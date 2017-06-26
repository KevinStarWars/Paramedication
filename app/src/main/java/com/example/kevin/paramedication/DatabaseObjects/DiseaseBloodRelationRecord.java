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

    public int getDiseaseId() {
        return diseaseId;
    }

    @Override
    public String toString() {
        return "DiseaseBloodRelationRecord{" +
                "id=" + id +
                ", bloodId=" + bloodId +
                ", diseaseId=" + diseaseId +
                '}';
    }
}
