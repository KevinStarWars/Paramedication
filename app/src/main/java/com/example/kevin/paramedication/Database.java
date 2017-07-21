package com.example.kevin.paramedication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevin.paramedication.DatabaseObjects.BloodRecord;
import com.example.kevin.paramedication.DatabaseObjects.DiseaseRecord;
import com.example.kevin.paramedication.DatabaseObjects.MedicationRecord;
import com.example.kevin.paramedication.DatabaseOperations.BloodOperations;
import com.example.kevin.paramedication.DatabaseOperations.DbDataSource;
import com.example.kevin.paramedication.DatabaseOperations.DiseaseBloodRelationOperations;
import com.example.kevin.paramedication.DatabaseOperations.DiseaseMedicationRelationOperations;
import com.example.kevin.paramedication.DatabaseOperations.DiseaseOperations;
import com.example.kevin.paramedication.DatabaseOperations.MedicationOperations;
import com.example.kevin.paramedication.DatabaseOperations.ProcessOperations;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Database extends AppCompatActivity {

    private static final String LOG_TAG = Database.class.getSimpleName();
    // global vars
    List<Integer> entryList = new ArrayList<>();

    private DbDataSource dataSource;
    private BloodOperations BloodOps = new BloodOperations();
    private DiseaseBloodRelationOperations DiseaseBloodOps = new DiseaseBloodRelationOperations();
    private DiseaseMedicationRelationOperations DiseaseMedicationOps = new DiseaseMedicationRelationOperations();
    private DiseaseOperations DiseaseOps = new DiseaseOperations();
    private MedicationOperations MedicationOps = new MedicationOperations();

    // onCreate method determines which Activity is in use and handles all initializations
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        dataSource = new DbDataSource(this);
        Log.d(LOG_TAG, "Opening database.");
        dataSource.open();

        initializeMedicationEntryField(R.id.drug1);
        addToEntryList();
        configTabs();
        setOnClickListenerForRadioButtonFemale();
        setOnClickListenerForRadioButtonMale();
        setOnClickListenerForButtonInMedication();
        setOnClickListenerForGetResultButton();
        setOnClickListenerForUpdateButton();
        setOnClickListenerForSaveButton();

        if (getHelp(1)) {
            displayHelp();
        }
    }

    /*_____________________________________General______________________________________________*/

    // Configs Tabs in Layout
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

    // Send intent in order to open DiagnosisActivity and closes database connection
    public void changeToDiagnosis(View view) {
        dataSource.close();
        Intent myIntent = new Intent(this, Diagnosis.class);
        this.startActivity(myIntent);
    }

    // Send intent in order to open MedicationActivity and closes database connection
    public void changeToMedication(View view) {
        dataSource.close();
        Intent myIntent = new Intent(this, Medication.class);
        this.startActivity(myIntent);
    }

    // Send intent in order to open InfoActivity and closes database connection
    public void changeToInfo(View view) {
        dataSource.close();
        Intent myIntent = new Intent(this, Info.class);
        this.startActivity(myIntent);
    }

    // Send intent in order to open PatientsActivity and closes database connection
    public void changeToPatients(View view) {
        dataSource.close();
        Intent myIntent = new Intent(this, Patients.class);
        this.startActivity(myIntent);
    }

    // initializes the first AutoCompleteTextView in Medication
    // enables:
    // autocomplete and open drop down menu right away
    private void initializeMedicationEntryField(int id) {
        enableInitialAutocomplete(id);
        setOnClickListenerForAutoCompleteTextView(id);
    }

    // enables AutoCompleteTextViews to use Autocomplete on database entries
    private void enableInitialAutocomplete(int id) {
        AutoCompleteTextView entry = (AutoCompleteTextView) findViewById(id);
        List<MedicationRecord> records = MedicationOps.getAllMedicationRecords(dataSource.database);
        List<String> tmpList = new ArrayList<>();

        for (int i = 0; i < records.size(); i++) {
            tmpList.add(records.get(i).getDrugName());
        }

        Collections.sort(tmpList);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdownlist, tmpList);
        entry.setAdapter(adapter);
    }

    // sets OnClickListener for first AutoCompleteTextView in Medication
    private void setOnClickListenerForAutoCompleteTextView(int id) {
        final AutoCompleteTextView entry = (AutoCompleteTextView) findViewById(id);
        entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entry.setDropDownWidth(convertToDp(200));
                entry.showDropDown();
            }
        });
    }

    // Creates a new default TextView
    private TextView createNewTextView(String text) {
        final TextView textView = new TextView(this);
        final LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        textView.setLayoutParams(textViewParams);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView.setTextColor(Color.parseColor("#47525E"));
        textView.setText(text);
        return textView;
    }

    // sets on click listener for female radio button
    // enables:
    // only one radio button is checkable
    private void setOnClickListenerForRadioButtonFemale() {
        final RadioButton femaleButton = (RadioButton) findViewById(R.id.femaleButtonDatabase);
        femaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton maleButton = (RadioButton) findViewById(R.id.maleButtonDatabase);
                if (maleButton.isChecked()) {
                    maleButton.setChecked(false);
                }
                femaleButton.setChecked(true);
            }
        });
    }

    // sets on click listener for male radio button
    // enables:
    // only one radio button is checkable
    private void setOnClickListenerForRadioButtonMale() {
        final RadioButton maleButton = (RadioButton) findViewById(R.id.maleButtonDatabase);
        maleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton femaleButton = (RadioButton) findViewById(R.id.femaleButtonDatabase);
                if (femaleButton.isChecked()) {
                    femaleButton.setChecked(false);
                }
                maleButton.setChecked(true);
            }
        });
    }

    // sets on click listener for button in medication interface
    // enables:
    // removes itself
    // creation of another AutoCompleteTextView
    private void setOnClickListenerForButtonInMedication() {
        Button button = (Button) findViewById(R.id.addButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((ViewGroup) v.getParent()).removeView(v);
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.drugs);
                linearLayout.addView(createLinearLayout(createNewTextView("Drug"), createNewEntry()));
                linearLayout.addView(createNewButton("Add more"));
            }
        });
    }

    // Creates Layout which surrounds TextView and EditText of Medication
    private LinearLayout createLinearLayout(TextView TView, AutoCompleteTextView entry) {
        final LinearLayout linear = new LinearLayout(this);
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linear.setLayoutParams(layoutParams);
        linear.setOrientation(LinearLayout.HORIZONTAL);
        linear.setPadding(convertToDp(8), convertToDp(8), convertToDp(8), convertToDp(8));
        linear.addView(TView);
        linear.addView(entry);
        return linear;
    }

    // Creates a new default EditText
    private AutoCompleteTextView createNewEntry() {

        final AutoCompleteTextView entry = new AutoCompleteTextView(this);
        final LinearLayout.LayoutParams entryViewParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2f);

        entry.setHint(R.string.drugName);
        entryViewParams.setMargins(convertToDp(8), convertToDp(8), convertToDp(8), convertToDp(8));
        entry.setLayoutParams(entryViewParams);
        entry.setId(View.generateViewId());

        List<MedicationRecord> records = MedicationOps.getAllMedicationRecords(dataSource.database);
        List<String> tmpList = new ArrayList<>();

        for (int i = 0; i < records.size(); i++) {
            tmpList.add(records.get(i).getDrugName());
        }

        Collections.sort(tmpList);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdownlist, tmpList);
        entry.setAdapter(adapter);

        entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entry.showDropDown();
            }
        });

        entryList.add(entry.getId());

        //enableInitialAutocomplete(entry.getId());
        //setOnClickListenerForAutoCompleteTextView(entry.getId());
        return entry;
    }

    // Creates a new button for new drugs in medication
    private Button createNewButton(String text) {
        final Button button = new Button(this);
        final LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonParams.setMargins(convertToDp(16), convertToDp(16), convertToDp(16), convertToDp(16));
        button.setLayoutParams(buttonParams);
        button.setText(text);
        button.setBackgroundColor(ContextCompat.getColor(this, R.color.backgroundRed));
        button.setGravity(Gravity.CENTER_HORIZONTAL);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((ViewGroup) v.getParent()).removeView(v);
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.drugs);
                linearLayout.addView(createLinearLayout(createNewTextView("Drug"), createNewEntry()));
                linearLayout.addView(createNewButton("Add more"));
            }
        });
        return button;
    }

    // sets on click listener for result button
    // enables:
    // if any radio button is checked and a disease name is entered:
    // removes itself
    // prints disease name, blood count, and medication to linear layout
    // adds a button for checking the input and one for pushing data to database
    private void setOnClickListenerForGetResultButton() {
        Button getResultButton = (Button) findViewById(R.id.getResult);
        getResultButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (radioButtonIsChecked()) {
                    if (!getDisease().equals("")) {
                        if (valuesAreValid()) {
                            setDefaultInterfacesVisibility(View.GONE);
                            loadValuesFromDefaultInterface();
                            setSaveInterfacesVisibility(View.VISIBLE);
                            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.medicationLayout);
                            printDrugList(linearLayout);
                        } else {
                            Toast.makeText(getApplicationContext(), "Values are not valid.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Please enter a valid drug name.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please select Gender.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // sets on click listener for update button
    // enables:
    // update values which were changed after displaying them
    private void setOnClickListenerForUpdateButton(){
        Button getResultButton = (Button) findViewById(R.id.databaseUpdateButton);
        getResultButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (radioButtonIsChecked()) {
                    if (!getDisease().equals("")) {
                        if (valuesAreValid()) {
                            loadValuesFromDefaultInterface();

                            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.medicationLayout);
                            printDrugList(linearLayout);

                            Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Values are not valid.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Please enter a valid drug name.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please select Gender.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // sets on click listener for save button
    // enables:
    // push data to database, store it there and reset all edit views
    private void setOnClickListenerForSaveButton(){
        Button button = (Button) findViewById(R.id.databaseSaveButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSaveInterfacesVisibility(View.GONE);
                addDiseaseToDatabase();
                resetAllFields();
                setDefaultInterfacesVisibility(View.VISIBLE);

                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // sets default interfaces visibility depending on parameter
    private void setDefaultInterfacesVisibility(int visibility) {
        NestedScrollView view = (NestedScrollView) findViewById(R.id.resultTab);
        view.setVisibility(visibility);
    }

    // sets save interfaces visibility depending on parameter
    private void setSaveInterfacesVisibility(int visibility) {
        ScrollView view = (ScrollView) findViewById(R.id.createdResult);
        view.setVisibility(visibility);
    }

    // loads user input from default interface and prints it to the save interface
    private void loadValuesFromDefaultInterface() {

        int[] defaultInterfaceEditTextIds = {R.id.diseaseName,
                R.id.leukocyteMin, R.id.leukocyteMax,
                R.id.erythrocyteMin, R.id.erythrocyteMax,
                R.id.hemoglobinMin, R.id.hemoglobinMax,
                R.id.hematocritMin, R.id.hematocritMax,
                R.id.mcvMin, R.id.mcvMax,
                R.id.mchMin, R.id.mchMax,
                R.id.mchcMin, R.id.mchcMax,
                R.id.plateletMin, R.id.plateletMax,
                R.id.reticulocytesMin, R.id.reticulocytesMax,
                R.id.mpvMin, R.id.mpvMax,
                R.id.rdwMin, R.id.rdwMax};

        int[] saveInterfaceTextViewIds = {R.id.diseaseValue,
                R.id.leukocyteMinValue, R.id.leukocyteMaxValue,
                R.id.erythrocyteMinValue, R.id.erythrocyteMaxValue,
                R.id.hemoglobinMinValue, R.id.hemoglobinMaxValue,
                R.id.hematocritMinValue, R.id.hematocritMaxValue,
                R.id.mcvMinValue, R.id.mcvMaxValue,
                R.id.mchMinValue, R.id.mchMaxValue,
                R.id.mchcMinValue, R.id.mchcMaxValue,
                R.id.plateletMinValue, R.id.plateletMaxValue,
                R.id.reticulocytesMinValue, R.id.reticulocytesMaxValue,
                R.id.mpvMinValue, R.id.mpvMaxValue,
                R.id.rdwMinValue, R.id.rdwMaxValue};

        String[] units = {"",
                getString(R.string.leukocyteUnit), getString(R.string.leukocyteUnit),
                getString(R.string.erythrocyteUnit), getString(R.string.erythrocyteUnit),
                getString(R.string.hemoglobinUnit), getString(R.string.hemoglobinUnit),
                getString(R.string.hematocritUnit), getString(R.string.hematocritUnit),
                getString(R.string.mcvUnit), getString(R.string.mcvUnit),
                getString(R.string.mchUnit), getString(R.string.mchUnit),
                getString(R.string.mchcUnit), getString(R.string.mchcUnit),
                getString(R.string.plateletUnit), getString(R.string.plateletUnit),
                getString(R.string.reticulocytesUnit), getString(R.string.reticulocytesUnit),
                getString(R.string.mpvUnit), getString(R.string.mpvUnit),
                getString(R.string.rdwUnit), getString(R.string.rdwUnit)};

        for (int i = 0; i < defaultInterfaceEditTextIds.length; i++){
            TextView textView = (TextView) findViewById(saveInterfaceTextViewIds[i]);
            EditText editText = (EditText) findViewById(defaultInterfaceEditTextIds[i]);

            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setText(editText.getText() + " " + units[i]);
        }
    }

    // checks if any RadioButton is checked.
    // returns true, if so. false if not
    private boolean radioButtonIsChecked() {
        RadioButton femaleButton = (RadioButton) findViewById(R.id.femaleButtonDatabase);
        RadioButton maleButton = (RadioButton) findViewById(R.id.maleButtonDatabase);
        return femaleButton.isChecked() || maleButton.isChecked();
    }

    // gets disease name from EditText
    @NonNull
    private String getDisease() {
        EditText entryField = (EditText) findViewById(R.id.diseaseName);
        return entryField.getText().toString();
    }

    // prints drugs which were entered
    private void printDrugList(LinearLayout linearLayout) {
        linearLayout.removeAllViews();

        boolean firstRun = false;
        List<String> drugList = getDrugs();

        for (int i = 0; i < drugList.size(); i++) {

            if (!drugList.get(i).equals("") && !firstRun) {
                linearLayout.addView(createLinearLayout(createNewTextView("Medication"), createNewTextView(drugList.get(i))));
                firstRun = true;
            } else if (!drugList.get(i).equals("") && firstRun) {
                linearLayout.addView(createLinearLayout(createNewTextView(""), createNewTextView(drugList.get(i))));
            }
        }
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

    // creates new linear layout for disease in result screen
    private LinearLayout createLinearLayout(TextView TViewI, TextView TViewII) {
        final LinearLayout linear = new LinearLayout(this);
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linear.setLayoutParams(layoutParams);
        linear.setOrientation(LinearLayout.HORIZONTAL);
        linear.setPadding(8, 8, 8, 8);
        linear.addView(TViewI);
        linear.addView(TViewII);
        return linear;
    }

    // adds disease to database with all relations to medication and blood
    // if drug exists in database it is saved in list
    // if drug does not exist in database it is created
    private void addDiseaseToDatabase() {

        List<String> drugList = getDrugs();
        List<MedicationRecord> medicationList = new ArrayList<>();
        List<MedicationRecord> currentDatabase = MedicationOps.getAllMedicationRecords(dataSource.database);

        for (int i = 0; i < drugList.size(); i++) {
            for (int j = 0; j < currentDatabase.size(); j++) {
                if (drugList.get(i).equals(currentDatabase.get(j).getDrugName())) {
                    medicationList.add(currentDatabase.get(j));
                } else
                    medicationList.add(MedicationOps.createMedicationRecord(drugList.get(i), dataSource.database));
            }
        }

        BloodRecord insertedBloodRecord = BloodOps.createBloodRecord(getLeukocyteMin(), getLeukocyteMax(), getErythrocyteMin(), getErythrocyteMax(), getHemoglobinMin(), getHemoglobinMax(), getHematocritMin(), getHematocritMax(),
                getMCVMin(), getMCVMax(), getMCHMin(), getMCHMax(), getMCHCMin(), getMCHCMax(), getPlateletMin(), getPlateletMax(), getReticulocytesMin(), getReticulocytesMax(),
                getMPVMin(), getMPVMax(), getRDWMin(), getRDWMax(), getGender(), dataSource.database);

        // inserts disease into database
        DiseaseRecord insertedDiseaseRecord = DiseaseOps.createDiseaseRecord(getDisease(), dataSource.database);

        // inserts relation between disease and blood record
        DiseaseBloodOps.createDiseaseBloodRelationRecord(insertedBloodRecord.getId(), insertedDiseaseRecord.getId(), dataSource.database);

        // inserts relation between disease and medication
        for (int i = 0; i < medicationList.size(); i++) {
            DiseaseMedicationOps.createDiseaseMedicationRelationRecord(insertedDiseaseRecord.getId(), medicationList.get(i).getId(), dataSource.database);
        }
    }


    // get all entries from user
    @NonNull
    private String getGender() {
        RadioButton femaleButton = (RadioButton) findViewById(R.id.femaleButtonDatabase);
        if (femaleButton.isChecked()) {
            return "f";
        } else {
            return "m";
        }
    }

    @NonNull
    private String getLeukocyteMin() {
        EditText entryField = (EditText) findViewById(R.id.leukocyteMin);
        if (entryField.getText().toString().isEmpty()) {
            return "0";
        } else {
            return entryField.getText().toString();
        }
    }

    @NonNull
    private String getLeukocyteMax() {
        EditText entryField = (EditText) findViewById(R.id.leukocyteMax);
        if (entryField.getText().toString().isEmpty()) {
            return "0";
        } else {
            return entryField.getText().toString();
        }
    }

    @NonNull
    private String getErythrocyteMin() {
        EditText entryField = (EditText) findViewById(R.id.erythrocyteMin);
        if (entryField.getText().toString().isEmpty()) {
            return "0";
        } else {
            return entryField.getText().toString();
        }
    }

    @NonNull
    private String getErythrocyteMax() {
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

    // empties all edit views
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

    // add default field to entryList
    private void addToEntryList() {
        EditText drugEntry = (EditText) findViewById(R.id.drug1);
        entryList.add(drugEntry.getId());
    }

    // checks if values are valid. meaning that min values are lower than max values
    private boolean valuesAreValid() {
        Integer[] ids = {
                R.id.leukocyteMin,
                R.id.leukocyteMax,
                R.id.erythrocyteMin,
                R.id.erythrocyteMax,
                R.id.hemoglobinMin,
                R.id.hemoglobinMax,
                R.id.hematocritMin,
                R.id.hematocritMax,
                R.id.mcvMin,
                R.id.mcvMax,
                R.id.mchMin,
                R.id.mchMax,
                R.id.mchcMin,
                R.id.mchcMax,
                R.id.plateletMin,
                R.id.plateletMax,
                R.id.reticulocytesMin,
                R.id.reticulocytesMax,
                R.id.mpvMin,
                R.id.mpvMax,
                R.id.rdwMin,
                R.id.rdwMax
        };

        if (!(convertToDefaultDouble(getLeukocyteMin()) < convertToDefaultDouble(getLeukocyteMax()) &&
                convertToDefaultDouble(getErythrocyteMin()) < convertToDefaultDouble(getErythrocyteMax()) &&
                convertToDefaultDouble(getHemoglobinMin()) < convertToDefaultDouble(getHemoglobinMax()) &&
                convertToDefaultDouble(getHematocritMin()) < convertToDefaultDouble(getHematocritMax()) &&
                convertToDefaultDouble(getMCVMin()) < convertToDefaultDouble(getMCVMax()) &&
                convertToDefaultDouble(getMCHMin()) < convertToDefaultDouble(getMCHMax()) &&
                convertToDefaultDouble(getMCHCMin()) < convertToDefaultDouble(getMCHCMax()) &&
                convertToDefaultDouble(getPlateletMin()) < convertToDefaultDouble(getPlateletMax()) &&
                convertToDefaultDouble(getReticulocytesMin()) < convertToDefaultDouble(getReticulocytesMax()) &&
                convertToDefaultDouble(getMPVMin()) < convertToDefaultDouble(getMPVMax()) &&
                convertToDefaultDouble(getRDWMin()) < convertToDefaultDouble(getRDWMax()))) {
            return false;
        }

        for (Integer id : ids) {
            EditText editText = (EditText) findViewById(id);
            if ((editText.getText().toString().matches("[0-9]+[.,]?[0-9]*")) && !editText.getText().toString().isEmpty()) {
                return false;
            }
        }

        return true;
    }

    // converts converts DP to actual pixels
    private int convertToDp(float sizeInDp){
        float scale = getResources().getDisplayMetrics().density;
        return (int) (sizeInDp*scale + 0.5f);
    }

    private double convertToDefaultDouble(String number){
        Locale theLocale = Locale.getDefault();
        NumberFormat numberFormat = DecimalFormat.getInstance(theLocale);
        Number theNumber;
        try {
            theNumber = numberFormat.parse(number);
            return theNumber.doubleValue();
        }
        catch (ParseException e){
            Log.d(LOG_TAG, e.getMessage());
            return 0d;
        }
    }

    private boolean getHelp(int id) {
        ProcessOperations processOperations = new ProcessOperations();
        return processOperations.getEntry(id, dataSource.database).isDatabaseHelp();
    }

    private void displayHelp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Database.this);
        builder.setTitle("Instruction");
        builder.setMessage(R.string.databaseHelp);

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
                        processOperations.updateEntry("database", dataSource.database);
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
