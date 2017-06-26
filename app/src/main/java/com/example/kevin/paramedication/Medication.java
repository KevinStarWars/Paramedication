package com.example.kevin.paramedication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevin.paramedication.DatabaseObjects.MedicationRecord;
import com.example.kevin.paramedication.DatabaseOperations.DbDataSource;
import com.example.kevin.paramedication.DatabaseOperations.MedicationInteractionOperations;
import com.example.kevin.paramedication.DatabaseOperations.MedicationOperations;
import com.example.kevin.paramedication.DatabaseOperations.ProcessOperations;

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
        setOnClickListenerForSaveButton();

        if (getHelp(1)) {
            displayHelp();
        }
    }

    //these methods are used in order to switch between activities
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

    // sets OnClickListener for button
    // enables:
    // paste to database
    private void setOnClickListenerForDrugInteraction() {
        Button button = (Button) findViewById(R.id.addDrugInteraction);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (checkIfValid()) {
                    setDefaultInterfacesVisibility(View.GONE);
                    setSaveInterfacesVisibility(View.VISIBLE);
                    modifyTextViews();
                }
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

    private void SetOnClickListenerForBothAutoCompleteTextViews(int id[]) {
        for (int anId : id) {
            final AutoCompleteTextView drugView = (AutoCompleteTextView) findViewById(anId);
            drugView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drugView.setDropDownWidth(convertToDp(200));
                    drugView.showDropDown();
                }
            });
        }
    }

    private void setDefaultInterfacesVisibility(int visibility) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.printArea1);
        linearLayout.setVisibility(visibility);
    }

    private void setSaveInterfacesVisibility(int visibility){
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.printArea2);
        linearLayout.setVisibility(visibility);
    }

    private void setOnClickListenerForSaveButton(){
        Button button = (Button) findViewById(R.id.SaveDrugInteraction);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSaveInterfacesVisibility(View.GONE);
                pasteToDatabase();
                enableAutocomplete(autoCompleteViewIds);
                emptyViews();
                setDefaultInterfacesVisibility(View.VISIBLE);
            }
        });
    }

    private boolean checkIfValid(){
        AutoCompleteTextView entryDrugOne = (AutoCompleteTextView) findViewById(R.id.autocompleteDrugI);
        AutoCompleteTextView entryDrugTwo = (AutoCompleteTextView) findViewById(R.id.autocompleteDrugII);
        EditText typeOfInteraction = (EditText) findViewById(R.id.interaction);

        if (!entryDrugOne.getText().toString().isEmpty()) {
            if (!entryDrugTwo.getText().toString().isEmpty()) {
                if (!typeOfInteraction.getText().toString().isEmpty()) {
                    if (!entryDrugOne.getText().toString().equals(entryDrugTwo.getText().toString())) {
                        return true;
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter an interaction", Toast.LENGTH_LONG).show();
                    return false;
                }
            } else {
                Toast.makeText(getApplicationContext(), "Please enter a valid name for Drug II", Toast.LENGTH_LONG).show();
                return false;
            }
        } else{
            Toast.makeText(getApplicationContext(), "Please enter a valid name for Drug I", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }

    private void modifyTextViews(){

        TextView saveTextViewDrugI = (TextView) findViewById(R.id.saveDrugIValue);
        TextView saveTextViewDrugII = (TextView) findViewById(R.id.saveDrugIIValue);
        TextView saveTextViewInteraction = (TextView) findViewById(R.id.saveInteraction);

        AutoCompleteTextView entryDrugOne = (AutoCompleteTextView) findViewById(R.id.autocompleteDrugI);
        AutoCompleteTextView entryDrugTwo = (AutoCompleteTextView) findViewById(R.id.autocompleteDrugII);
        EditText typeOfInteraction = (EditText) findViewById(R.id.interaction);

        saveTextViewDrugI.setText(entryDrugOne.getText().toString());
        saveTextViewDrugII.setText(entryDrugTwo.getText().toString());
        saveTextViewInteraction.setText(typeOfInteraction.getText().toString());
    }

    private void emptyViews(){
        AutoCompleteTextView entryDrugOne = (AutoCompleteTextView) findViewById(R.id.autocompleteDrugI);
        AutoCompleteTextView entryDrugTwo = (AutoCompleteTextView) findViewById(R.id.autocompleteDrugII);
        EditText typeOfInteraction = (EditText) findViewById(R.id.interaction);

        entryDrugOne.setText("");
        entryDrugTwo.setText("");
        typeOfInteraction.setText("");
    }

    private void pasteToDatabase() {
        AutoCompleteTextView entryDrugOne = (AutoCompleteTextView) findViewById(R.id.autocompleteDrugI);
        AutoCompleteTextView entryDrugTwo = (AutoCompleteTextView) findViewById(R.id.autocompleteDrugII);
        EditText typeOfInteraction = (EditText) findViewById(R.id.interaction);

        MedicationInteractionOps.createDiseaseMedicationRelationRecord(
                MedicationOps.createMedicationRecord(entryDrugOne.getText().toString(), dataSource.database).getId(),
                MedicationOps.createMedicationRecord(entryDrugTwo.getText().toString(), dataSource.database).getId(),
                typeOfInteraction.getText().toString(),
                dataSource.database);
    }

    // converts dp to actual pixels
    private int convertToDp(float sizeInDp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (sizeInDp * scale + 0.5f);
    }

    private boolean getHelp(int id) {
        ProcessOperations processOperations = new ProcessOperations();
        return processOperations.getEntry(id, dataSource.database).isMedicationHelp();
    }

    private void displayHelp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Medication.this);
        builder.setTitle("Instruction");
        builder.setMessage(R.string.medicationHelp);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ProcessOperations processOperations = new ProcessOperations();
                        processOperations.updateEntry("medication", dataSource.database);
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
