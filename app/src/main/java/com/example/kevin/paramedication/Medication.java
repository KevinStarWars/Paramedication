package com.example.kevin.paramedication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.kevin.paramedication.DatabaseObjects.MedicationInteractionRecord;
import com.example.kevin.paramedication.DatabaseObjects.MedicationRecord;
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

import java.util.ArrayList;
import java.util.List;

public class Medication extends AppCompatActivity {

    public static final String LOG_TAG = Medication.class.getSimpleName();

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
        setContentView(R.layout.activity_medication);

        dataSource = new DbDataSource(this);
        Log.d(LOG_TAG, "Opening database.");
        dataSource.open();

        Button button = (Button) findViewById(R.id.addDrugInteraction);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pasteToDatabase();
            }
        });

        enableAutocomplete();
    }

    public void changeToDatabase(View view){
        dataSource.close();
        Intent myIntent = new Intent(this, Database.class);
        this.startActivity(myIntent);
    }

    public void changeToDiagnosis(View view){
        dataSource.close();
        Intent myIntent = new Intent(this, Diagnosis.class);
        this.startActivity(myIntent);
    }

    public void changeToInfo(View view){
        dataSource.close();
        Intent myIntent = new Intent(this, Info.class);
        this.startActivity(myIntent);
    }

    public void changeToPatients(View view){
        dataSource.close();
        Intent myIntent = new Intent(this, Patients.class);
        this.startActivity(myIntent);
    }

    private void enableAutocomplete(){
        AutoCompleteTextView entryDrugOne = (AutoCompleteTextView) findViewById(R.id.autocompleteDrugI);
        AutoCompleteTextView entryDrugTwo = (AutoCompleteTextView) findViewById(R.id.autocompleteDrugII);

        List<MedicationRecord> currentDatabase = MedicationOps.getAllMedicationRecords(dataSource.database);
        List<String> tmpList = new ArrayList<>();

        for (int i  = 0; i < currentDatabase.size(); i++){
            tmpList.add(currentDatabase.get(i).getDrugName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdownlist, tmpList);

        entryDrugOne.setAdapter(adapter);
        entryDrugTwo.setAdapter(adapter);
    }

    private void pasteToDatabase() {
        AutoCompleteTextView entryDrugOne = (AutoCompleteTextView) findViewById(R.id.autocompleteDrugI);
        AutoCompleteTextView entryDrugTwo = (AutoCompleteTextView) findViewById(R.id.autocompleteDrugII);
        AutoCompleteTextView typeOfInteraction = (AutoCompleteTextView) findViewById(R.id.interaction);

        if (!entryDrugOne.getText().toString().isEmpty()){
            if (!entryDrugTwo.getText().toString().isEmpty()){
                if (!typeOfInteraction.getText().toString().isEmpty()) {
                    if (!entryDrugOne.getText().toString().equals(entryDrugTwo.getText().toString())) {

                        MedicationRecord drug1 = MedicationOps.createMedicationRecord(entryDrugOne.getText().toString(), dataSource.database);
                        MedicationRecord drug2 = MedicationOps.createMedicationRecord(entryDrugTwo.getText().toString(), dataSource.database);

                        MedicationInteractionRecord insertedObject = MedicationInteractionOps.createDiseaseMedicationRelationRecord(drug1.getId(), drug2.getId(),
                                typeOfInteraction.getText().toString(), dataSource.database);

                        if (insertedObject.getId() == -1) {
                            Toast.makeText(getApplicationContext(), "Interaction is already in database", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "added successfully", Toast.LENGTH_LONG).show();
                        }

                        enableAutocomplete();
                    }
                }
            }
        }
        else Toast.makeText(getApplicationContext(), "please enter something", Toast.LENGTH_LONG).show();

    }
}
