package com.example.kevin.paramedication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Patients extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);
    }

    // Send intent in order to open DiagnosisActivity
    public void changeToDiagnosis(View view){
        Intent myIntent = new Intent(this, Diagnosis.class);
        this.startActivity(myIntent);
    }

    // Send intent in order to open MedicationActivity
    public void changeToMedication(View view){
        Intent myIntent = new Intent(this, Medication.class);
        this.startActivity(myIntent);
    }

    // Send intent in order to open InfoActivity
    public void changeToInfo(View view){
        Intent myIntent = new Intent(this, Info.class);
        this.startActivity(myIntent);
    }

    // Send intent in order to open PatientsActivity
    public void changeToDatabase(View view){
        Intent myIntent = new Intent(this, Database.class);
        this.startActivity(myIntent);
    }
}
