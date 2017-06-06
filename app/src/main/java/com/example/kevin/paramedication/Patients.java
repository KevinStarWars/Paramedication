package com.example.kevin.paramedication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Patients extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);
        initializeHomeScreen();
    }

    // initializes home screen
    private void initializeHomeScreen(){
        String PatientID = getPatientIDFromView();
        Button button = (Button) findViewById(R.id.getPatientData);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ((ViewGroup)view.getParent()).removeAllViews();
                // TODO: create vertical Linear Layout which shows PatientID, related Disease,
                // TODO: related Blood Count, Entry and Button to get new BloodCount from other Patient
            }
        });
    }

    // reads entry and saves data in string
    @NonNull
    private String getPatientIDFromView(){
        EditText entryField = (EditText) findViewById(R.id.PatientID);
        return entryField.getText().toString();
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
