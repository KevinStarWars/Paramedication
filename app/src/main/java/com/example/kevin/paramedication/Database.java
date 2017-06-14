package com.example.kevin.paramedication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevin.paramedication.DatabaseObjects.BloodRecord;
import com.example.kevin.paramedication.DatabaseObjects.DiseaseRecord;
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
import java.util.Arrays;
import java.util.List;

public class Database extends AppCompatActivity {

    private static final String LOG_TAG = Database.class.getSimpleName();
    // global vars
    List<Integer> entryList = new ArrayList<>();
    boolean firstRun = false;
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

    // onCreate method determines which Activity is in use
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        dataSource = new DbDataSource(this);
        Log.d(LOG_TAG, "Opening database.");
        dataSource.open();

        enableAutocomplete();
        addToEntryList();
        configTabs();

        // Config add more Button in Medication tab
        Button button = (Button) findViewById(R.id.addButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((ViewGroup) v.getParent()).removeView(v);
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.drugs);
                linearLayout.addView(createNewLinearLayoutMedication(createNewDrugTextView("Drug"), createNewEntry()));
                linearLayout.addView(createNewButton("Add more"));
            }
        });

        // Configs check Input Button in Control Screen tab
        Button getResultButton = (Button) findViewById(R.id.getResult);
        getResultButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((ViewGroup) v.getParent()).removeView(v);
                printResult();
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.resultTab);
                linearLayout.addView(createNewResultButton("Check Input"));
                linearLayout.addView(createAddButton("Add to database"));
            }
        });
    }

    /*_____________________________________General______________________________________________*/

    // Config Tabs
    public void configTabs() {
        TabHost host = (TabHost) findViewById(R.id.tabHost);
        host.setup();
        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Disease Info");
        host.addTab(spec);
        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Disease Medication");
        host.addTab(spec);
        //Tab 3
        spec = host.newTabSpec("Tab Three");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Control Screen");
        host.addTab(spec);
    }

    // Send intent in order to open DiagnosisActivity
    public void changeToDiagnosis(View view) {
        dataSource.close();
        Intent myIntent = new Intent(this, Diagnosis.class);
        this.startActivity(myIntent);
    }

    // Send intent in order to open MedicationActivity
    public void changeToMedication(View view) {
        dataSource.close();
        Intent myIntent = new Intent(this, Medication.class);
        this.startActivity(myIntent);
    }

    // Send intent in order to open InfoActivity
    public void changeToInfo(View view) {
        dataSource.close();
        Intent myIntent = new Intent(this, Info.class);
        this.startActivity(myIntent);
    }

    // Send intent in order to open PatientsActivity
    public void changeToPatients(View view) {
        dataSource.close();
        Intent myIntent = new Intent(this, Patients.class);
        this.startActivity(myIntent);
    }

    // Creates a new default TextView
    private TextView createNewTextView(String text) {
        final TextView textView = new TextView(this);
        final LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        textView.setLayoutParams(textViewParams);
        textView.setPadding(16, 16, 16, 16);
        textView.setTextSize(15);
        textView.setTextColor(Color.parseColor("#47525E"));
        textView.setText(text);
        return textView;
    }

    // empties all editviews
    private void resetAllFields() {

        Integer[] fieldArray = new Integer[]{R.id.diseaseName, R.id.leukocyteMin, R.id.leukocyteMax, R.id.erythrocyteMin, R.id.erythrocyteMax, R.id.hemoglobinMin, R.id.hemoglobinMax,
                R.id.hematocritMin, R.id.hematocritMax, R.id.mcvMin, R.id.mcvMax, R.id.mchMin, R.id.mchMax, R.id.mchcMin, R.id.mchcMax, R.id.plateletMin, R.id.plateletMax, R.id.reticulocytesMin,
                R.id.reticulocytesMax, R.id.mpvMin, R.id.mpvMax, R.id.rdwMin, R.id.rdwMax};
        List<Integer> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(fieldArray));

        for (int i = 0; i < entryList.size(); i++) {
            fields.add(entryList.get(i));
        }
        for (int i = 0; i < fields.size(); i++) {
            EditText tmpEdit = (EditText) findViewById(fields.get(i));
            tmpEdit.setText("");
        }
    }

    /*_____________________________________Disease Info_________________________________________*/

    // get all entries from user
    @NonNull
    private String getDisease() {
        EditText entryField = (EditText) findViewById(R.id.diseaseName);
        return entryField.getText().toString();
    }

    @NonNull
    private String getLeukoMin() {
        EditText entryField = (EditText) findViewById(R.id.leukocyteMin);
        if (entryField.getText().toString().isEmpty()) {
            return "0";
        } else {
            return entryField.getText().toString();
        }
    }

    @NonNull
    private String getLeukoMax() {
        EditText entryField = (EditText) findViewById(R.id.leukocyteMax);
        if (entryField.getText().toString().isEmpty()) {
            return "0";
        } else {
            return entryField.getText().toString();
        }
    }

    @NonNull
    private String getEryMin() {
        EditText entryField = (EditText) findViewById(R.id.erythrocyteMin);
        if (entryField.getText().toString().isEmpty()) {
            return "0";
        } else {
            return entryField.getText().toString();
        }
    }

    @NonNull
    private String getEryMax() {
        EditText entryField = (EditText) findViewById(R.id.erythrocyteMax);
        if (entryField.getText().toString().isEmpty()) {
            return "0";
        } else {
            return entryField.getText().toString();
        }
    }

    @NonNull
    private String getHemoglobinMin() {
        EditText entryField = (EditText) findViewById(R.id.hemoglobinMin);
        if (entryField.getText().toString().isEmpty()) {
            return "0";
        } else {
            return entryField.getText().toString();
        }
    }

    @NonNull
    private String getHemoglobinMax() {
        EditText entryField = (EditText) findViewById(R.id.hemoglobinMax);
        if (entryField.getText().toString().isEmpty()) {
            return "0";
        } else {
            return entryField.getText().toString();
        }
    }

    @NonNull
    private String getHematocritMin() {
        EditText entryField = (EditText) findViewById(R.id.hematocritMin);
        if (entryField.getText().toString().isEmpty()) {
            return "0";
        } else {
            return entryField.getText().toString();
        }
    }

    @NonNull
    private String getHematocritMax() {
        EditText entryField = (EditText) findViewById(R.id.hematocritMax);
        if (entryField.getText().toString().isEmpty()) {
            return "0";
        } else {
            return entryField.getText().toString();
        }
    }

    @NonNull
    private String getMCVMin() {
        EditText entryField = (EditText) findViewById(R.id.mcvMin);
        if (entryField.getText().toString().isEmpty()) {
            return "0";
        } else {
            return entryField.getText().toString();
        }
    }

    @NonNull
    private String getMCVMax() {
        EditText entryField = (EditText) findViewById(R.id.mcvMax);
        if (entryField.getText().toString().isEmpty()) {
            return "0";
        } else {
            return entryField.getText().toString();
        }
    }

    @NonNull
    private String getMCHMin() {
        EditText entryField = (EditText) findViewById(R.id.mchMin);
        if (entryField.getText().toString().isEmpty()) {
            return "0";
        } else {
            return entryField.getText().toString();
        }
    }

    @NonNull
    private String getMCHMax() {
        EditText entryField = (EditText) findViewById(R.id.mchMax);
        if (entryField.getText().toString().isEmpty()) {
            return "0";
        } else {
            return entryField.getText().toString();
        }
    }

    @NonNull
    private String getMCHCMin() {
        EditText entryField = (EditText) findViewById(R.id.mchcMin);
        if (entryField.getText().toString().isEmpty()) {
            return "0";
        } else {
            return entryField.getText().toString();
        }
    }

    @NonNull
    private String getMCHCMax() {
        EditText entryField = (EditText) findViewById(R.id.mchcMax);
        if (entryField.getText().toString().isEmpty()) {
            return "0";
        } else {
            return entryField.getText().toString();
        }
    }

    @NonNull
    private String getPlateletMin() {
        EditText entryField = (EditText) findViewById(R.id.plateletMin);
        if (entryField.getText().toString().isEmpty()) {
            return "0";
        } else {
            return entryField.getText().toString();
        }
    }

    @NonNull
    private String getPlateletMax() {
        EditText entryField = (EditText) findViewById(R.id.plateletMax);
        if (entryField.getText().toString().isEmpty()) {
            return "0";
        } else {
            return entryField.getText().toString();
        }
    }

    @NonNull
    private String getReticulocytesMin() {
        EditText entryField = (EditText) findViewById(R.id.reticulocytesMin);
        if (entryField.getText().toString().isEmpty()) {
            return "0";
        } else {
            return entryField.getText().toString();
        }
    }

    @NonNull
    private String getReticulocytesMax() {
        EditText entryField = (EditText) findViewById(R.id.reticulocytesMax);
        if (entryField.getText().toString().isEmpty()) {
            return "0";
        } else {
            return entryField.getText().toString();
        }
    }

    @NonNull
    private String getMPVMin() {
        EditText entryField = (EditText) findViewById(R.id.mpvMin);
        if (entryField.getText().toString().isEmpty()) {
            return "0";
        } else {
            return entryField.getText().toString();
        }
    }

    @NonNull
    private String getMPVMax() {
        EditText entryField = (EditText) findViewById(R.id.mpvMax);
        if (entryField.getText().toString().isEmpty()) {
            return "0";
        } else {
            return entryField.getText().toString();
        }
    }

    @NonNull
    private String getRDWMin() {
        EditText entryField = (EditText) findViewById(R.id.rdwMin);
        if (entryField.getText().toString().isEmpty()) {
            return "0";
        } else {
            return entryField.getText().toString();
        }
    }

    @NonNull
    private String getRDWMax() {
        EditText entryField = (EditText) findViewById(R.id.rdwMax);
        if (entryField.getText().toString().isEmpty()) {
            return "0";
        } else {
            return entryField.getText().toString();
        }
    }

    /*_____________________________________Disease Medication___________________________________*/

    private void enableAutocomplete(){
        AutoCompleteTextView entry = (AutoCompleteTextView) findViewById(R.id.drug1);
        List<MedicationRecord> records = MedicationOps.getAllMedicationRecords(dataSource.database);
        List<String> tmpList = new ArrayList<>();

        for (int i = 0; i < records.size(); i++){
            tmpList.add(records.get(i).getDrugName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdownlist, tmpList);
        entry.setAdapter(adapter);
    }

    // Creates a new default TextView
    private TextView createNewDrugTextView(String text) {
        final TextView textView = new TextView(this);
        final LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(textViewParams);
        textView.setPadding(16, 16, 16, 16);
        textView.setTextSize(15);
        textView.setTextColor(Color.parseColor("#47525E"));
        textView.setText(text);
        return textView;
    }

    // add default field to entryList
    private void addToEntryList() {
        EditText drugEntry = (EditText) findViewById(R.id.drug1);
        entryList.add(drugEntry.getId());
    }

    // Creates Layout which surrounds TextView and EditText of Medication
    private LinearLayout createNewLinearLayoutMedication(TextView TView, AutoCompleteTextView entry) {
        final LinearLayout linear = new LinearLayout(this);
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linear.setLayoutParams(layoutParams);
        linear.setOrientation(LinearLayout.HORIZONTAL);
        linear.setPadding(16, 16, 16, 16);
        linear.addView(TView);
        linear.addView(entry);
        return linear;
    }

    // Creates a new default EditText
    private AutoCompleteTextView createNewEntry() {

        AutoCompleteTextView entry = new AutoCompleteTextView(this);
        entry.setHint("Drug");
        entry.setDropDownWidth(500);
        entry.setId(View.generateViewId());

        entryList.add(entry.getId());

        List<MedicationRecord> records = MedicationOps.getAllMedicationRecords(dataSource.database);
        List<String> tmpList = new ArrayList<>();

        for (int i = 0; i < records.size(); i++){
            tmpList.add(records.get(i).getDrugName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdownlist, tmpList);
        entry.setAdapter(adapter);
        return entry;
    }

    // Creates a new button for new drugs in medication
    private Button createNewButton(String text) {
        final Button button = new Button(this);
        final LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonParams.setMargins(8, 8, 8, 8);
        button.setLayoutParams(buttonParams);
        button.setText(text);
        button.setBackgroundColor(getResources().getColor(R.color.backgroundRed));
        button.setGravity(Gravity.CENTER_HORIZONTAL);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((ViewGroup) v.getParent()).removeView(v);
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.drugs);
                linearLayout.addView(createNewLinearLayoutMedication(createNewDrugTextView("Drug"), createNewEntry()));
                linearLayout.addView(createNewButton("Add more"));
            }
        });
        return button;
    }

    // searches all EditTexts for Entries and returns List of Strings with drug names
    private List<String> getDrugs() {
        List<String> drugList = new ArrayList<>();
        for (int i = 0; i < entryList.size(); ++i) {
            EditText drug = (EditText) findViewById(entryList.get(i));
            drugList.add(drug.getText().toString());
        }
        return drugList;
    }

    /*_____________________________________Control Screen_______________________________________*/


    // creates a new button for controls in control screen
    private Button createNewResultButton(String text) {
        final Button button = new Button(this);
        final LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonParams.setMargins(8, 8, 8, 8);
        button.setLayoutParams(buttonParams);
        button.setText(text);
        button.setBackgroundColor(getResources().getColor(R.color.backgroundRed));
        button.setGravity(Gravity.CENTER_HORIZONTAL);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((ViewGroup) v.getParent()).removeView(v);
                printResult();
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.resultTab);
                linearLayout.addView(createNewResultButton("Check Input"));
                linearLayout.addView(createAddButton("Add to database"));
            }
        });
        return button;
    }

    // creates new linear layout which surrounds three text views in Database control screen
    private LinearLayout createNewLinearLayoutResult(TextView TViewI, TextView TViewII, TextView TViewIII) {
        final LinearLayout linear = new LinearLayout(this);
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linear.setLayoutParams(layoutParams);
        linear.setOrientation(LinearLayout.HORIZONTAL);
        linear.setPadding(8, 8, 8, 8);
        linear.addView(TViewI);
        linear.addView(TViewII);
        linear.addView(TViewIII);
        return linear;
    }

    // creates new linear layout for disease in result screen
    private LinearLayout createNewLinearLayoutDisease(TextView TViewI, TextView TViewII) {
        final LinearLayout linear = new LinearLayout(this);
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linear.setLayoutParams(layoutParams);
        linear.setOrientation(LinearLayout.HORIZONTAL);
        linear.setPadding(8, 8, 8, 8);
        linear.addView(TViewI);
        linear.addView(TViewII);
        return linear;
    }

    // creates Button, which enables user to send disease to database
    private Button createAddButton(String text) {
        final Button button = new Button(this);
        final LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonParams.setMargins(8, 8, 8, 8);
        button.setLayoutParams(buttonParams);
        button.setText(text);
        button.setBackgroundColor(getResources().getColor(R.color.backgroundRed));
        button.setGravity(Gravity.CENTER_HORIZONTAL);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LinearLayout printArea = (LinearLayout) findViewById(R.id.resultTab);
                printArea.removeAllViews();
                printArea.addView(createNewResultButton("Check Input"));
                try {
                    addDiseaseToDatabase();
                    resetAllFields();
                } catch (Exception e) {
                    Log.d(LOG_TAG, e.getMessage());
                }
                Toast.makeText(getApplicationContext(), "added successfully", Toast.LENGTH_LONG).show();
            }
        });
        return button;
    }

    // adds disease to database with all relations to medication and blood
    private void addDiseaseToDatabase() {

        List<String> drugList = getDrugs();
        List<MedicationRecord> medicationList = new ArrayList<>();

        // if drug exists in database it is saved in list
        // if drug does not exist in database it is created
        for (int i = 0; i < drugList.size(); i++) {
            MedicationRecord drug = MedicationOps.getDrug(drugList.get(i), dataSource.database);
            if (drug.getId() == -1) {
                medicationList.add(MedicationOps.createMedicationRecord(drugList.get(i), dataSource.database));
            }
            else{
                medicationList.add(drug);
            }
        }

        // inserts Blood record into database
        BloodRecord insertedBloodRecord = BloodOps.createBloodRecord(getLeukoMin(), getLeukoMax(), getEryMin(), getEryMax(), getHemoglobinMin(), getHemoglobinMax(), getHematocritMin(), getHematocritMax(),
                getMCVMin(), getMCVMax(), getMCHMin(), getMCHMax(), getMCHCMin(), getMCHCMax(), getPlateletMin(), getPlateletMax(), getReticulocytesMin(), getReticulocytesMax(),
                getMPVMin(), getMPVMax(), getRDWMin(), getRDWMax(), dataSource.database);

        // inserts disease into database
        DiseaseRecord insertedDiseaseRecord = DiseaseOps.createDiseaseRecord(getDisease(), dataSource.database);

        // inserts relation between disease and blood record
        DiseaseBloodOps.createDiseaseBloodRelationRecord(insertedBloodRecord.getId(), insertedDiseaseRecord.getId(), dataSource.database);

        // inserts relation between disease and medication
        for (int i = 0; i < medicationList.size(); i++) {
            DiseaseMedicationOps.createDiseaseMedicationRelationRecord(insertedDiseaseRecord.getId(), medicationList.get(i).getId(), dataSource.database);
        }
    }

    // prints result to result screen
    public void printResult() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.resultTab);
        List<String> drugList = getDrugs();
        linearLayout.removeAllViews();
        linearLayout.addView(createNewLinearLayoutDisease(createNewTextView(getString(R.string.disease)), createNewTextView(getDisease())));
        linearLayout.addView(createNewLinearLayoutResult(createNewTextView(getString(R.string.leukocyte)), createNewTextView(getLeukoMin()), createNewTextView(getLeukoMax())));
        linearLayout.addView(createNewLinearLayoutResult(createNewTextView(getString(R.string.erythrocyte)), createNewTextView(getEryMin()), createNewTextView(getEryMax())));
        linearLayout.addView(createNewLinearLayoutResult(createNewTextView(getString(R.string.hemoglobin)), createNewTextView(getHemoglobinMin()), createNewTextView(getHemoglobinMax())));
        linearLayout.addView(createNewLinearLayoutResult(createNewTextView(getString(R.string.hematocrit)), createNewTextView(getHematocritMin()), createNewTextView(getHematocritMax())));
        linearLayout.addView(createNewLinearLayoutResult(createNewTextView(getString(R.string.mcv)), createNewTextView(getMCVMin()), createNewTextView(getMCVMax())));
        linearLayout.addView(createNewLinearLayoutResult(createNewTextView(getString(R.string.MCH)), createNewTextView(getMCHMin()), createNewTextView(getMCHMax())));
        linearLayout.addView(createNewLinearLayoutResult(createNewTextView(getString(R.string.mchc)), createNewTextView(getMCHCMin()), createNewTextView(getMCHCMax())));
        linearLayout.addView(createNewLinearLayoutResult(createNewTextView(getString(R.string.platelet)), createNewTextView(getPlateletMin()), createNewTextView(getPlateletMax())));
        linearLayout.addView(createNewLinearLayoutResult(createNewTextView(getString(R.string.reticulocytes)), createNewTextView(getReticulocytesMin()), createNewTextView(getReticulocytesMax())));
        linearLayout.addView(createNewLinearLayoutResult(createNewTextView(getString(R.string.mpv)), createNewTextView(getMPVMin()), createNewTextView(getMPVMax())));
        linearLayout.addView(createNewLinearLayoutResult(createNewTextView(getString(R.string.rdw)), createNewTextView(getRDWMin()), createNewTextView(getRDWMax())));
        for (int i = 0; i < drugList.size(); i++) {

            if (!drugList.get(i).equals("") && !firstRun) {
                linearLayout.addView(createNewLinearLayoutDisease(createNewTextView("Medication"), createNewTextView(drugList.get(i))));
                firstRun = true;
            } else if (!drugList.get(i).equals("") && firstRun) {
                linearLayout.addView(createNewLinearLayoutDisease(createNewTextView(""), createNewTextView(drugList.get(i))));
            }
        }

    }

}
