package com.example.kevin.paramedication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Medication extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);
    }

    public void changeToDatabase(View view){
        Intent myIntent = new Intent(this, Database.class);
        this.startActivity(myIntent);
    }

    public void changeToDiagnosis(View view){
        Intent myIntent = new Intent(this, Diagnosis.class);
        this.startActivity(myIntent);
    }

    public void changeToInfo(View view){
        Intent myIntent = new Intent(this, Info.class);
        this.startActivity(myIntent);
    }

    public void changeToPatients(View view){
        Intent myIntent = new Intent(this, Patients.class);
        this.startActivity(myIntent);
    }
}
