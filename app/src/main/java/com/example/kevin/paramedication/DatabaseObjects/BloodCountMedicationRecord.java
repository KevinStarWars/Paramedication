package com.example.kevin.paramedication.DatabaseObjects;

public class BloodCountMedicationRecord {

    private int id;
    private int bloodCountId;
    private int medicationId;

    public BloodCountMedicationRecord(int id, int bloodCountId, int medicationId) {
        this.id = id;
        this.bloodCountId = bloodCountId;
        this.medicationId = medicationId;
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

    public int getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(int medicationId) {
        this.medicationId = medicationId;
    }

    @Override
    public String toString() {
        return "BloodCountMedicationRecord{" +
                "id=" + id +
                ", bloodCountId=" + bloodCountId +
                ", medicationId=" + medicationId +
                '}';
    }
}
