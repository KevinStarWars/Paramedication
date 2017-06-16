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
import com.example.kevin.paramedication.DatabaseOperations.DbDataSource;
import com.example.kevin.paramedication.DatabaseOperations.MedicationInteractionOperations;
import com.example.kevin.paramedication.DatabaseOperations.MedicationOperations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Medication extends AppCompatActivity {

    // this is the log_tag which is used to debug the application
    public static final String LOG_TAG = Medication.class.getSimpleName();

    private int[] autoCompleteViewIds = {R.id.autocompleteDrugI, R.id.autocompleteDrugII};

    // these classes are used in order to communicate with SQLite
    private DbDataSource dataSource;
    private MedicationInteractionOperations MedicationInteractionOps = new MedicationInteractionOperations();
    private MedicationOperations MedicationOps = new MedicationOperations();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);

        dataSource = new DbDataSource(this);
        Log.d(LOG_TAG, "Opening database.");
        dataSource.open();

        setOnClickListenerForDrugInteraction();
        SetOnClickListenerForBothAutoCompleteTextViews(autoCompleteViewIds);
        enableAutocomplete(autoCompleteViewIds);
    }

    public void changeToDatabase(View view) {
        dataSource.close();
        Intent myIntent = new Intent(this, Database.class);
        this.startActivity(myIntent);
    }

    public void changeToDiagnosis(View view) {
        dataSource.close();
        Intent myIntent = new Intent(this, Diagnosis.class);
        this.startActivity(myIntent);
    }

    public void changeToInfo(View view) {
        dataSource.close();
        Intent myIntent = new Intent(this, Info.class);
        this.startActivity(myIntent);
    }

    public void changeToPatients(View view) {
        dataSource.close();
        Intent myIntent = new Intent(this, Patients.class);
        this.startActivity(myIntent);
    }

    private void setOnClickListenerForDrugInteraction() {
        Button button = (Button) findViewById(R.id.addDrugInteraction);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pasteToDatabase();
            }
        });
    }

    private void enableAutocomplete(int id[]) {


        List<MedicationRecord> currentDatabase = MedicationOps.getAllMedicationRecords(dataSource.database);
        List<String> tmpList = new ArrayList<>();

        for (int i = 0; i < currentDatabase.size(); i++) {
            tmpList.add(currentDatabase.get(i).getDrugName());
        }

        Collections.sort(tmpList);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdownlist, tmpList);

        for (int anId : id) {
            AutoCompleteTextView entryDrugOne = (AutoCompleteTextView) findViewById(anId);

            entryDrugOne.setAdapter(adapter);
        }
    }

    private void pasteToDatabase() {
        AutoCompleteTextView entryDrugOne = (AutoCompleteTextView) findViewById(R.id.autocompleteDrugI);
        AutoCompleteTextView entryDrugTwo = (AutoCompleteTextView) findViewById(R.id.autocompleteDrugII);
        AutoCompleteTextView typeOfInteraction = (AutoCompleteTextView) findViewById(R.id.interaction);

        if (!entryDrugOne.getText().toString().isEmpty()) {
            if (!entryDrugTwo.getText().toString().isEmpty()) {
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

                        enableAutocomplete(autoCompleteViewIds);
                    }
                } else
                    Toast.makeText(getApplicationContext(), "Please enter an interaction", Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(getApplicationContext(), "Please enter a valid name for Drug II", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(getApplicationContext(), "Please enter a valid name for Drug I", Toast.LENGTH_LONG).show();

    }

    private void SetOnClickListenerForBothAutoCompleteTextViews(int id[]){
        for (int anId : id) {
            final AutoCompleteTextView drugView = (AutoCompleteTextView) findViewById(anId);
            drugView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drugView.showDropDown();
                }
            });
        }
    }
}
