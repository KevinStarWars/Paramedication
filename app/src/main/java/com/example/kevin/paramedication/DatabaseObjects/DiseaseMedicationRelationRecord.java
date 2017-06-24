package com.example.kevin.paramedication.DatabaseObjects;


public class DiseaseMedicationRelationRecord {

    private int id;
    private int diseaseId;
    private int drugId;

    public DiseaseMedicationRelationRecord(int id, int diseaseId, int drugId) {
        this.id = id;
        this.diseaseId = diseaseId;
        this.drugId = drugId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(int diseaseId) {
        this.diseaseId = diseaseId;
    }

    public int getDrugId() {
        return drugId;
    }

    public void setDrugId(int drugId) {
        this.drugId = drugId;
    }

    @Override
    public String toString() {
        return "DiseaseMedicationRelationRecord{" +
                "id=" + id +
                ", diseaseId=" + diseaseId +
                ", drugId=" + drugId +
                '}';
    }
}
