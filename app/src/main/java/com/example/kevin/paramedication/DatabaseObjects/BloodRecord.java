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

    public void setLeukocyteMin(double leukocyteMin) {
        this.leukocyteMin = leukocyteMin;
    }

    public double getLeukocyteMax() {
        return leukocyteMax;
    }

    public void setLeukocyteMax(double leukocyteMax) {
        this.leukocyteMax = leukocyteMax;
    }

    public double getErythrocyteMin() {
        return erythrocyteMin;
    }

    public void setErythrocyteMin(double erythrocyteMin) {
        this.erythrocyteMin = erythrocyteMin;
    }

    public double getErythrocyteMax() {
        return erythrocyteMax;
    }

    public void setErythrocyteMax(double erythrocyteMax) {
        this.erythrocyteMax = erythrocyteMax;
    }

    public double getHemoglobinMin() {
        return hemoglobinMin;
    }

    public void setHemoglobinMin(double hemoglobinMin) {
        this.hemoglobinMin = hemoglobinMin;
    }

    public double getHemoglobinMax() {
        return hemoglobinMax;
    }

    public void setHemoglobinMax(double hemoglobinMax) {
        this.hemoglobinMax = hemoglobinMax;
    }

    public double getHematocritMin() {
        return hematocritMin;
    }

    public void setHematocritMin(double hematocritMin) {
        this.hematocritMin = hematocritMin;
    }

    public double getHematocritMax() {
        return hematocritMax;
    }

    public void setHematocritMax(double hematocritMax) {
        this.hematocritMax = hematocritMax;
    }

    public double getMcvMin() {
        return mcvMin;
    }

    public void setMcvMin(double mcvMin) {
        this.mcvMin = mcvMin;
    }

    public double getMcvMax() {
        return mcvMax;
    }

    public void setMcvMax(double mcvMax) {
        this.mcvMax = mcvMax;
    }

    public double getMchMin() {
        return mchMin;
    }

    public void setMchMin(double mchMin) {
        this.mchMin = mchMin;
    }

    public double getMchMax() {
        return mchMax;
    }

    public void setMchMax(double mchMax) {
        this.mchMax = mchMax;
    }

    public double getMchcMin() {
        return mchcMin;
    }

    public void setMchcMin(double mchcMin) {
        this.mchcMin = mchcMin;
    }

    public double getMchcMax() {
        return mchcMax;
    }

    public void setMchcMax(double mchcMax) {
        this.mchcMax = mchcMax;
    }

    public double getPlateletMin() {
        return plateletMin;
    }

    public void setPlateletMin(double plateletMin) {
        this.plateletMin = plateletMin;
    }

    public double getPlateletMax() {
        return plateletMax;
    }

    public void setPlateletMax(double plateletMax) {
        this.plateletMax = plateletMax;
    }

    public double getReticulocytesMin() {
        return reticulocytesMin;
    }

    public void setReticulocytesMin(double reticulocytesMin) {
        this.reticulocytesMin = reticulocytesMin;
    }

    public double getReticulocytesMax() {
        return reticulocytesMax;
    }

    public void setReticulocytesMax(double reticulocytesMax) {
        this.reticulocytesMax = reticulocytesMax;
    }

    public double getMpvMin() {
        return mpvMin;
    }

    public void setMpvMin(double mpvMin) {
        this.mpvMin = mpvMin;
    }

    public double getMpvMax() {
        return mpvMax;
    }

    public void setMpvMax(double mpvMax) {
        this.mpvMax = mpvMax;
    }

    public double getRdwMin() {
        return rdwMin;
    }

    public void setRdwMin(double rdwMin) {
        this.rdwMin = rdwMin;
    }

    public double getRdwMax() {
        return rdwMax;
    }

    public void setRdwMax(double rdwMax) {
        this.rdwMax = rdwMax;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public void initializeWithObject(BloodRecord record) {
        this.id = record.getId();
        this.leukocyteMin = record.getLeukocyteMin();
        this.leukocyteMax = record.getLeukocyteMax();
        this.erythrocyteMin = record.getErythrocyteMin();
        this.erythrocyteMax = record.getErythrocyteMax();
        this.hemoglobinMin = record.getHemoglobinMin();
        this.hemoglobinMax = record.getHemoglobinMax();
        this.hematocritMin = record.getHematocritMin();
        this.hematocritMax = record.getHematocritMax();
        this.mcvMin = record.getMcvMin();
        this.mcvMax = record.getMcvMax();
        this.mchMin = record.getMchMin();
        this.mchMax = record.getMchMax();
        this.mchcMin = record.getMchcMin();
        this.mchcMax = record.getMchcMax();
        this.plateletMin = record.getPlateletMin();
        this.plateletMax = record.getPlateletMax();
        this.reticulocytesMin = record.getReticulocytesMin();
        this.reticulocytesMax = record.getReticulocytesMax();
        this.mpvMin = record.getMpvMin();
        this.mpvMax = record.getMpvMax();
        this.rdwMin = record.getRdwMin();
        this.rdwMax = record.getRdwMax();
        this.gender = record.getGender();
    }
}
