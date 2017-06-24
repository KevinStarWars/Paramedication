package com.example.kevin.paramedication.DatabaseObjects;


public class MedicationRecord {

    private int id;
    private String drugName;

    public MedicationRecord(int id, String drugName) {
        this.id = id;
        this.drugName = drugName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    @Override
    public String toString() {
        return "MedicationRecord{" +
                "id=" + id +
                ", drugName='" + drugName + '\'' +
                '}';
    }
}
