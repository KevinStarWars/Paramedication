package com.example.kevin.paramedication.DatabaseObjects;

public class DiseaseRecord {

    private long id;
    private String name;

    public DiseaseRecord(long id, String name){
        this.id = id;
        this.name = name;
    }

    public long getId(){
        return this.id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String print(){
        return "ID: " + this.id + " Disease name: " + this.name;
    }

}
