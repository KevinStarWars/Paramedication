package com.example.kevin.paramedication.DatabaseObjects;

public class BloodCountRecord {


    private int id;
    private double leukocyte;
    private double erythrocyte;
    private double hemoglobin;
    private double hematocrit;
    private double mcv;
    private double mch;
    private double mchc;
    private double platelet;
    private double reticulocytes;
    private double mpv;
    private double rdw;
    private String timestamp;

    public BloodCountRecord() {
    }

    public BloodCountRecord(int id, double leukocyte, double erythrocyte, double hemoglobin, double hematocrit,
                            double mcv, double mch, double mchc, double platelet, double reticulocytes, double mpv,
                            double rdw, String timestamp) {
        this.id = id;
        this.leukocyte = leukocyte;
        this.erythrocyte = erythrocyte;
        this.hemoglobin = hemoglobin;
        this.hematocrit = hematocrit;
        this.mcv = mcv;
        this.mch = mch;
        this.mchc = mchc;
        this.platelet = platelet;
        this.reticulocytes = reticulocytes;
        this.mpv = mpv;
        this.rdw = rdw;
        this.timestamp  = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLeukocyte() {
        return leukocyte;
    }

    public void setLeukocyte(double leukocyte) {
        this.leukocyte = leukocyte;
    }

    public double getErythrocyte() {
        return erythrocyte;
    }

    public void setErythrocyte(double erythrocyte) {
        this.erythrocyte = erythrocyte;
    }

    public double getHemoglobin() {
        return hemoglobin;
    }

    public void setHemoglobin(double hemoglobin) {
        this.hemoglobin = hemoglobin;
    }

    public double getHematocrit() {
        return hematocrit;
    }

    public void setHematocrit(double hematocrit) {
        this.hematocrit = hematocrit;
    }

    public double getMcv() {
        return mcv;
    }

    public void setMcv(double mcv) {
        this.mcv = mcv;
    }

    public double getMch() {
        return mch;
    }

    public void setMch(double mch) {
        this.mch = mch;
    }

    public double getMchc() {
        return mchc;
    }

    public void setMchc(double mchc) {
        this.mchc = mchc;
    }

    public double getPlatelet() {
        return platelet;
    }

    public void setPlatelet(double platelet) {
        this.platelet = platelet;
    }

    public double getReticulocytes() {
        return reticulocytes;
    }

    public void setReticulocytes(double reticulocytes) {
        this.reticulocytes = reticulocytes;
    }

    public double getMpv() {
        return mpv;
    }

    public void setMpv(double mpv) {
        this.mpv = mpv;
    }

    public double getRdw() {
        return rdw;
    }

    public void setRdw(double rdw) {
        this.rdw = rdw;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "BloodCountRecord{" +
                "id=" + id +
                ", leukocyte=" + leukocyte +
                ", erythrocyte=" + erythrocyte +
                ", hemoglobin=" + hemoglobin +
                ", hematocrit=" + hematocrit +
                ", mcv=" + mcv +
                ", mch=" + mch +
                ", mchc=" + mchc +
                ", platelet=" + platelet +
                ", reticulocytes=" + reticulocytes +
                ", mpv=" + mpv +
                ", rdw=" + rdw +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
