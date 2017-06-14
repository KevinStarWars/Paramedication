package com.example.kevin.paramedication.DatabaseObjects;


public class PatientRecord {

    private int id;
    private long hospitalId;
    private String gender;

    public PatientRecord() {
    }

    public PatientRecord(int id, long hospitalId, String gender) {
        this.id = id;
        this.hospitalId = hospitalId;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "PatientRecord{" +
                "id=" + id +
                ", hospitalId=" + hospitalId +
                ", gender='" + gender + '\'' +
                '}';
    }
}
