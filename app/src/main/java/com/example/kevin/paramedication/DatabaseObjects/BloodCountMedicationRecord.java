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

    public int getMedicationId() {
        return medicationId;
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
