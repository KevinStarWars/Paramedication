package com.example.kevin.paramedication.DatabaseObjects;


public class MedicationInteractionRecord {

    private int id;
    private int drugId1;
    private int drugId2;
    private String type;

    public MedicationInteractionRecord(int id, int drugId1, int drugId2, String type) {
        this.id = id;
        this.drugId1 = drugId1;
        this.drugId2 = drugId2;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDrugId1() {
        return drugId1;
    }

    public int getDrugId2() {
        return drugId2;
    }

    @Override
    public String toString() {
        return "MedicationInteractionRecord{" +
                "id=" + id +
                ", drugId1=" + drugId1 +
                ", drugId2=" + drugId2 +
                ", type='" + type + '\'' +
                '}';
    }
}
