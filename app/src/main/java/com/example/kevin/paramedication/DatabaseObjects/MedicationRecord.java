package com.example.kevin.paramedication.DatabaseObjects;


public class MedicationRecord {

    private long id;
    private String drugName;

    public MedicationRecord(long id, String drugName){
        this.id = id;
        this.drugName = drugName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String print(){
        return "ID: " + this.id + " Drug name: " + this.drugName;
    }

}
