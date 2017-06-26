package com.example.kevin.paramedication.DatabaseObjects;

public class BloodRecord {

    private int id;
    private double leukocyteMin;
    private double leukocyteMax;
    private double erythrocyteMin;
    private double erythrocyteMax;
    private double hemoglobinMin;
    private double hemoglobinMax;
    private double hematocritMin;
    private double hematocritMax;
    private double mcvMin;
    private double mcvMax;
    private double mchMin;
    private double mchMax;
    private double mchcMin;
    private double mchcMax;
    private double plateletMin;
    private double plateletMax;
    private double reticulocytesMin;
    private double reticulocytesMax;
    private double mpvMin;
    private double mpvMax;
    private double rdwMin;
    private double rdwMax;
    private String gender;

    public BloodRecord(int id, double leukocyteMin, double leukocyteMax, double erythrocyteMin, double erythrocyteMax, double hemoglobinMin, double hemoglobinMax,
                       double hematocritMin, double hematocritMax, double mcvMin, double mcvMax, double mchMin, double mchMax, double mchcMin, double mchcMax,
                       double plateletMin, double plateletMax, double reticulocytesMin, double reticulocytesMax, double mpvMin, double mpvMax, double rdwMin,
                       double rdwMax, String gender) {
        this.id = id;
        this.leukocyteMin = leukocyteMin;
        this.leukocyteMax = leukocyteMax;
        this.erythrocyteMin = erythrocyteMin;
        this.erythrocyteMax = erythrocyteMax;
        this.hemoglobinMin = hemoglobinMin;
        this.hemoglobinMax = hemoglobinMax;
        this.hematocritMin = hematocritMin;
        this.hematocritMax = hematocritMax;
        this.mcvMin = mcvMin;
        this.mcvMax = mcvMax;
        this.mchMin = mchMin;
        this.mchMax = mchMax;
        this.mchcMin = mchcMin;
        this.mchcMax = mchcMax;
        this.plateletMin = plateletMin;
        this.plateletMax = plateletMax;
        this.reticulocytesMin = reticulocytesMin;
        this.reticulocytesMax = reticulocytesMax;
        this.mpvMin = mpvMin;
        this.mpvMax = mpvMax;
        this.rdwMin = rdwMin;
        this.rdwMax = rdwMax;
        this.gender = gender;
    }

    public BloodRecord() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLeukocyteMin() {
        return leukocyteMin;
    }

    public double getLeukocyteMax() {
        return leukocyteMax;
    }

    public double getErythrocyteMin() {
        return erythrocyteMin;
    }

    public double getErythrocyteMax() {
        return erythrocyteMax;
    }

    public double getHemoglobinMin() {
        return hemoglobinMin;
    }

    public double getHemoglobinMax() {
        return hemoglobinMax;
    }

    public double getHematocritMin() {
        return hematocritMin;
    }

    public double getHematocritMax() {
        return hematocritMax;
    }

    public double getMcvMin() {
        return mcvMin;
    }

    public double getMcvMax() {
        return mcvMax;
    }

    public double getMchMin() {
        return mchMin;
    }

    public double getMchMax() {
        return mchMax;
    }

    public double getMchcMin() {
        return mchcMin;
    }

    public double getMchcMax() {
        return mchcMax;
    }

    public double getPlateletMin() {
        return plateletMin;
    }

    public double getPlateletMax() {
        return plateletMax;
    }

    public double getReticulocytesMin() {
        return reticulocytesMin;
    }

    public double getReticulocytesMax() {
        return reticulocytesMax;
    }

    public double getMpvMin() {
        return mpvMin;
    }

    public double getMpvMax() {
        return mpvMax;
    }

    public double getRdwMin() {
        return rdwMin;
    }

    public double getRdwMax() {
        return rdwMax;
    }

    public String getGender() {
        return this.gender;
    }

    @Override
    public String toString() {
        return "BloodRecord{" +
                "id=" + id +
                ", leukocyteMin=" + leukocyteMin +
                ", leukocyteMax=" + leukocyteMax +
                ", erythrocyteMin=" + erythrocyteMin +
                ", erythrocyteMax=" + erythrocyteMax +
                ", hemoglobinMin=" + hemoglobinMin +
                ", hemoglobinMax=" + hemoglobinMax +
                ", hematocritMin=" + hematocritMin +
                ", hematocritMax=" + hematocritMax +
                ", mcvMin=" + mcvMin +
                ", mcvMax=" + mcvMax +
                ", mchMin=" + mchMin +
                ", mchMax=" + mchMax +
                ", mchcMin=" + mchcMin +
                ", mchcMax=" + mchcMax +
                ", plateletMin=" + plateletMin +
                ", plateletMax=" + plateletMax +
                ", reticulocytesMin=" + reticulocytesMin +
                ", reticulocytesMax=" + reticulocytesMax +
                ", mpvMin=" + mpvMin +
                ", mpvMax=" + mpvMax +
                ", rdwMin=" + rdwMin +
                ", rdwMax=" + rdwMax +
                ", Gender=" + gender +
                '}';
    }
}
