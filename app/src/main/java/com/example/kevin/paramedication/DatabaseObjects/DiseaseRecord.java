package com.example.kevin.paramedication.DatabaseObjects;

public class DiseaseRecord {

    private int id;
    private String name;
    private int incidence;

    public DiseaseRecord() {
    }

    public DiseaseRecord(int id, String name, int incidence) {
        this.id = id;
        this.name = name;
        this.incidence = incidence;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIncidence() {
        return incidence;
    }

    public void setIncidence(int incidence) {
        this.incidence = incidence;
    }

    @Override
    public String toString() {
        return "DiseaseRecord{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", incidence=" + incidence +
                '}';
    }
}
