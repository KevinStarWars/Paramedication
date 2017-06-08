package com.example.kevin.paramedication.DatabaseObjects;


public class PatientRecord {

    private int id;
    private long hospitalId;

    public PatientRecord(int id, long hospitalId) {
        this.id = id;
        this.hospitalId = hospitalId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHospitalId(long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public long getHospitalId() {
        return hospitalId;
    }

    public String print(){
        return "ID: " + this.id + " Hospital ID: " + this.hospitalId;
    }

}
