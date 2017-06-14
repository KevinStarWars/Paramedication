package com.example.kevin.paramedication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.kevin.paramedication.DatabaseOperations.BloodCountOperations;
import com.example.kevin.paramedication.DatabaseOperations.BloodOperations;
import com.example.kevin.paramedication.DatabaseOperations.DbDataSource;
import com.example.kevin.paramedication.DatabaseOperations.DiseaseBloodRelationOperations;
import com.example.kevin.paramedication.DatabaseOperations.DiseaseMedicationRelationOperations;
import com.example.kevin.paramedication.DatabaseOperations.DiseaseOperations;
import com.example.kevin.paramedication.DatabaseOperations.MedicationInteractionOperations;
import com.example.kevin.paramedication.DatabaseOperations.MedicationOperations;
import com.example.kevin.paramedication.DatabaseOperations.PatientBloodcountOperations;
import com.example.kevin.paramedication.DatabaseOperations.PatientDiseaseOperations;
import com.example.kevin.paramedication.DatabaseOperations.PatientMedicationOperations;
import com.example.kevin.paramedication.DatabaseOperations.PatientOperations;

public class Info extends AppCompatActivity {

    public static final String LOG_TAG = Info.class.getSimpleName();

    private DbDataSource dataSource;
    private BloodCountOperations BloodCountOps = new BloodCountOperations();
    private BloodOperations BloodOps = new BloodOperations();
    private DiseaseBloodRelationOperations DiseaseBloodOps = new DiseaseBloodRelationOperations();
    private DiseaseMedicationRelationOperations DiseaseMedicationOps = new DiseaseMedicationRelationOperations();
    private DiseaseOperations DiseaseOps = new DiseaseOperations();
    private MedicationInteractionOperations MedicationInteractionOps = new MedicationInteractionOperations();
    private MedicationOperations MedicationOps = new MedicationOperations();
    private PatientBloodcountOperations PatientBloodcountOps = new PatientBloodcountOperations();
    private PatientDiseaseOperations PatientDiseaseOps = new PatientDiseaseOperations();
    private PatientMedicationOperations PatientMedicationOps= new PatientMedicationOperations();
    private PatientOperations PatientOps = new PatientOperations();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        dataSource = new DbDataSource(this);
        Log.d(LOG_TAG, "Opening database.");
        dataSource.open();
    }

    public void changeToDatabase(View view){
        dataSource.close();
        Intent myIntent = new Intent(this, Database.class);
        this.startActivity(myIntent);
    }

    public void changeToMedication(View view){
        dataSource.close();
        Intent myIntent = new Intent(this, Medication.class);
        this.startActivity(myIntent);
    }

    public void changeToDiagnosis(View view){
        dataSource.close();
        Intent myIntent = new Intent(this, Diagnosis.class);
        this.startActivity(myIntent);
    }

    public void changeToPatients(View view){
        dataSource.close();
        Intent myIntent = new Intent(this, Patients.class);
        this.startActivity(myIntent);
    }


    public void sendMail(View view){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto",getString(R.string.supportMail), null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject));
//        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }
}
