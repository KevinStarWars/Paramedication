package com.example.kevin.paramedication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
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

import com.example.kevin.paramedication.DatabaseObjects.BloodCountRecord;
import com.example.kevin.paramedication.DatabaseObjects.BloodRecord;
import com.example.kevin.paramedication.DatabaseObjects.DiseaseBloodRelationRecord;
import com.example.kevin.paramedication.DatabaseObjects.DiseaseMedicationRelationRecord;
import com.example.kevin.paramedication.DatabaseObjects.DiseaseRecord;
import com.example.kevin.paramedication.DatabaseObjects.MedicationInteractionRecord;
import com.example.kevin.paramedication.DatabaseObjects.MedicationRecord;
import com.example.kevin.paramedication.DatabaseObjects.PatientRecord;
import com.example.kevin.paramedication.DatabaseOperations.BloodCountDiseaseOperations;
import com.example.kevin.paramedication.DatabaseOperations.BloodCountMedicationOperations;
import com.example.kevin.paramedication.DatabaseOperations.BloodCountOperations;
import com.example.kevin.paramedication.DatabaseOperations.BloodOperations;
import com.example.kevin.paramedication.DatabaseOperations.DbDataSource;
import com.example.kevin.paramedication.DatabaseOperations.DiseaseBloodRelationOperations;
import com.example.kevin.paramedication.DatabaseOperations.DiseaseMedicationRelationOperations;
import com.example.kevin.paramedication.DatabaseOperations.DiseaseOperations;
import com.example.kevin.paramedication.DatabaseOperations.MedicationInteractionOperations;
import com.example.kevin.paramedication.DatabaseOperations.MedicationOperations;
import com.example.kevin.paramedication.DatabaseOperations.PatientBloodCountOperations;
import com.example.kevin.paramedication.DatabaseOperations.PatientOperations;
import com.example.kevin.paramedication.DatabaseOperations.ProcessOperations;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.kevin.paramedication.R.id.drug1;

public class Diagnosis extends AppCompatActivity {

    // log_tag which is used to debug
    public final static String LOG_TAG = Diagnosis.class.getSimpleName();

    // the actual patient blood count and record
    PatientRecord patRecord = new PatientRecord();
    BloodCountRecord patBloodRecord = new BloodCountRecord();
    BloodRecord normalValuesFemale = new BloodRecord();
    BloodRecord normalValuesMale = new BloodRecord();
    BloodCountRecord normalCount = new BloodCountRecord();

    // these are used in order to display the actual disease and the recommended medication
    List<DiseaseRecord> diseaseRecords = new ArrayList<>();
    List<MedicationRecord> currentMedication = new ArrayList<>();
    List<MedicationRecord> recommendedMedication = new ArrayList<>();

    // used to keep track of all drug entry fields
    List<Integer> entryList = new ArrayList<>();

    // different classes which are used in order to communicate with SQLite database
    private DbDataSource dataSource;
    private BloodCountOperations BloodCountOps = new BloodCountOperations();
    private BloodOperations BloodOps = new BloodOperations();
    private DiseaseBloodRelationOperations DiseaseBloodOps = new DiseaseBloodRelationOperations();
    private DiseaseMedicationRelationOperations DiseaseMedicationOps = new DiseaseMedicationRelationOperations();
    private DiseaseOperations DiseaseOps = new DiseaseOperations();
    private MedicationOperations MedicationOps = new MedicationOperations();
    private PatientBloodCountOperations PatientBloodCountOps = new PatientBloodCountOperations();
    private PatientOperations PatientOps = new PatientOperations();
    private BloodCountDiseaseOperations BloodCountDiseaseOps = new BloodCountDiseaseOperations();
    private BloodCountMedicationOperations BloodCountMedicationOps = new BloodCountMedicationOperations();
    private MedicationInteractionOperations MedicationInteractionOps = new MedicationInteractionOperations();

    // handles which .xml file is used and all initializations
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);

        entryList.add(drug1);

        dataSource = new DbDataSource(this);
        Log.d(LOG_TAG, "Opening database.");
        dataSource.open();

        List<MedicationRecord> medicationRecordList = MedicationOps.getAllMedicationRecords(dataSource.database);

        for (int i = 0; i < medicationRecordList.size(); i++) {
            Log.d(LOG_TAG, medicationRecordList.get(i).toString());
        }

        initializeTabs();
        initializeNormalValues();
        initializeMedicationButton();
        initializeRadioButtons();
        setOnClickListenerForDiagnosis();
        setOnClickListenerForUpdateButton();
        setOnClickListenerForSaveButton();
        configureDefaultAutoCompleteView();

        if (getHelp(1)) {
            displayHelp();
        }
    }

    // Send intent in order to open Database
    public void changeToDatabase(View view) {
        dataSource.close();
        Intent myIntent = new Intent(this, Database.class);
        this.startActivity(myIntent);
    }

    // Send intent in order to open Medication
    public void changeToMedication(View view) {
        dataSource.close();
        Intent myIntent = new Intent(this, Medication.class);
        this.startActivity(myIntent);
    }

    // Send intent in order to open Info
    public void changeToInfo(View view) {
        dataSource.close();
        Intent myIntent = new Intent(this, Info.class);
        this.startActivity(myIntent);
    }

    // send intent in order to open Patients
    public void changeToPatients(View view) {
        dataSource.close();
        Intent myIntent = new Intent(this, Patients.class);
        this.startActivity(myIntent);
    }

    // initializes Tabs
    public void initializeTabs() {
        TabHost host = (TabHost) findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Patients");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Blood Count");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Tab Three");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Medication");
        host.addTab(spec);

        //Tab 4
        spec = host.newTabSpec("Tab Four");
        spec.setContent(R.id.tab4);
        spec.setIndicator("Result");
        host.addTab(spec);
    }

    private void initializeNormalValues() {
        normalValuesFemale = BloodOps.getById(1, dataSource.database);
        normalValuesMale = BloodOps.getById(2, dataSource.database);
        normalCount = BloodCountOps.getById(1, dataSource.database);
    }

    // initializes medication button
    // enables:
    // deletes itself and adds a new button and a new TextView
    private void initializeMedicationButton() {
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

    // creates linear layout with TextView next to EditText
    public LinearLayout createLinearLayout(TextView view, EditText entry) {
        final LinearLayout linear = new LinearLayout(this);
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linear.setLayoutParams(layoutParams);
        linear.setOrientation(LinearLayout.HORIZONTAL);
        linear.setPadding(convertToDp(16), convertToDp(16), convertToDp(16), convertToDp(16));
        linear.addView(view);
        linear.addView(entry);
        return linear;
    }

    // creates new text view with text as parameter
    public TextView createNewTextView(String text) {
        final TextView textView = new TextView(this);
        final LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        textView.setLayoutParams(textViewParams);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView.setText(text);
        return textView;
    }

    // creates new button with text as parameter
    public Button createNewButton(String text) {
        final Button button = new Button(this);
        final LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonParams.setMargins(16, 16, 16, 16);
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

    // creates new editText field with hint as parameter
    public AutoCompleteTextView createNewEntry() {
        final AutoCompleteTextView entry = new AutoCompleteTextView(this);
        final LinearLayout.LayoutParams entryViewParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2f);

        entry.setHint(R.string.drugName);
        entry.setLayoutParams(entryViewParams);
        entry.setId(View.generateViewId());
        entryList.add(entry.getId());

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

        return entry;
    }

    // disables both radio buttons being checked at the same time
    private void initializeRadioButtons() {
        final RadioButton femaleButton = (RadioButton) findViewById(R.id.femaleButton);
        final RadioButton maleButton = (RadioButton) findViewById(R.id.maleButton);

        femaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (maleButton.isChecked()) {
                    maleButton.setChecked(false);
                }
            }
        });

        maleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (femaleButton.isChecked()) {
                    femaleButton.setChecked(false);
                }
            }
        });
    }

    // sets on Click listener for get Result button
    // enables:
    // sets visibility of different screens and prints values, such as disease, blood count and medication
    public void setOnClickListenerForDiagnosis() {
        Button resultButton = (Button) findViewById(R.id.getResult);
        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioButtonIsChecked()) {
                    if (getHospitalID() > 0) {
                        updateResult();
                        setDefaultScreenVisibility(View.GONE);
                        setResultScreenVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(getApplicationContext(), "Please enter a valid Hospital ID", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please choose a Gender", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // sets on click listener for update button
    // enables:
    // if values have changed, prints new values
    private void setOnClickListenerForUpdateButton(){
        Button button = (Button) findViewById(R.id.diagnosisUpdateButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioButtonIsChecked()){
                    if (getHospitalID() > 0){
                        updateResult();
                        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    // updates result
    private void updateResult(){
        setHospitalId();
        setGender();
        initializePatientBloodCountRecord();
        printBloodCount();
        printPatientInfo();
        compareToDiseases();
        printDisease();
        printCurrentMedication();
        printRecommendedMedication();
    }

    // checks if radio button is checked and returns true if so
    private boolean radioButtonIsChecked() {
        RadioButton femaleButton = (RadioButton) findViewById(R.id.femaleButton);
        RadioButton maleButton = (RadioButton) findViewById(R.id.maleButton);

        return femaleButton.isChecked() || maleButton.isChecked();
    }

    // sets individual id set by user
    private void setHospitalId(){
        patRecord.setHospitalId(getHospitalID());
    }

    // gets entered hospital id from entry field
    private Long getHospitalID() {
        EditText hospitalID = (EditText) findViewById(R.id.hospital_Id);
        return Long.parseLong(hospitalID.getText().toString());
    }

    // sets gender
    private void setGender(){
        if (isFemale()){
            patRecord.setGender("f");
        }
        else patRecord.setGender("m");
    }

    // checks if patient is female or male
    private boolean isFemale() {
        RadioButton gender = (RadioButton) findViewById(R.id.femaleButton);
        return gender.isChecked();
    }

    // sets default screen visibility depending on parameter
    private boolean setDefaultScreenVisibility(int visibility){
        Button button = (Button) findViewById(R.id.getResult);
        button.setVisibility(visibility);
        return true;
    }

    // sets default screen visibility depending on parameter
    private boolean setResultScreenVisibility(int visibility){
        ScrollView view = (ScrollView) findViewById(R.id.diagnosisResultArea);
        view.setVisibility(visibility);
        return true;
    }

    // pushes Blood Count from entry to database
    private void initializePatientBloodCountRecord() {
        patBloodRecord.setLeukocyte(convertToDefaultDouble(getLeukocyteVal()));
        patBloodRecord.setErythrocyte(convertToDefaultDouble(getErythrocyteVal()));
        patBloodRecord.setHemoglobin(convertToDefaultDouble(getHemoglobinVal()));
        patBloodRecord.setHematocrit(convertToDefaultDouble(getHematocritVal()));
        patBloodRecord.setMcv(convertToDefaultDouble(getMcvVal()));
        patBloodRecord.setMch(convertToDefaultDouble(getMchVal()));
        patBloodRecord.setMchc(convertToDefaultDouble(getMchcVal()));
        patBloodRecord.setPlatelet(convertToDefaultDouble(getPlateletVal()));
        patBloodRecord.setReticulocytes(convertToDefaultDouble(getReticulocytesVal()));
        patBloodRecord.setMpv(convertToDefaultDouble(getMpvVal()));
        patBloodRecord.setRdw(convertToDefaultDouble(getRdwVal()));
    }

    // gets leukocyte value from entry field
    @NonNull
    private String getLeukocyteVal() {
        EditText entry = (EditText) findViewById(R.id.leukocyteVal);
        if (entry.getText().toString().isEmpty()) {
            patBloodRecord.setLeukocyte(normalCount.getLeukocyte());
            return String.valueOf(patBloodRecord.getLeukocyte());
        } else {
            patBloodRecord.setLeukocyte(convertToDefaultDouble(entry.getText().toString()));
            return entry.getText().toString();
        }
    }

    // gets erythrocyte value from entry field
    @NonNull
    private String getErythrocyteVal() {
        EditText entry = (EditText) findViewById(R.id.erythrocyteVal);
        if (entry.getText().toString().isEmpty()) {
            patBloodRecord.setErythrocyte(normalCount.getErythrocyte());
            return String.valueOf(patBloodRecord.getErythrocyte());
        } else {
            patBloodRecord.setErythrocyte(convertToDefaultDouble(entry.getText().toString()));
            return entry.getText().toString();
        }
    }

    // gets hemoglobin value from entry field
    @NonNull
    private String getHemoglobinVal() {
        EditText entry = (EditText) findViewById(R.id.hemoglobinVal);
        if (entry.getText().toString().isEmpty()) {
            patBloodRecord.setHemoglobin(normalCount.getHemoglobin());
            return String.valueOf(patBloodRecord.getHemoglobin());
        } else {
            patBloodRecord.setHemoglobin(convertToDefaultDouble(entry.getText().toString()));
            return entry.getText().toString();
        }
    }

    // gets hematocrit value from entry field
    @NonNull
    private String getHematocritVal() {
        EditText entry = (EditText) findViewById(R.id.hematocritVal);
        if (entry.getText().toString().isEmpty()) {
            patBloodRecord.setHematocrit(normalCount.getHematocrit());
            return String.valueOf(patBloodRecord.getHematocrit());
        } else {
            patBloodRecord.setHematocrit(convertToDefaultDouble(entry.getText().toString()));
            return entry.getText().toString();
        }
    }

    // gets mcv value from entry field
    // if entry field is empty it is calculated from values
    @NonNull
    private String getMcvVal() {
        EditText entry = (EditText) findViewById(R.id.mcvVal);
        if (entry.getText().toString().isEmpty()) {
            if (!getMchcVal().isEmpty() && !getMchVal().isEmpty()) {
                Double mcv = (convertToDefaultDouble(getMchVal()) / convertToDefaultDouble(getMchcVal())) * 100;
                patBloodRecord.setMcv(mcv);
                return String.valueOf(mcv);
            } else {
                patBloodRecord.setMcv(normalCount.getMcv());
                return String.valueOf(patBloodRecord.getMcv());
            }
        } else {
            patBloodRecord.setMcv(convertToDefaultDouble(entry.getText().toString()));
            return entry.getText().toString();
        }
    }

    // gets mch value from entry field
    // if entry field is empty it is calculated from values
    // calculate:
    // entered units:
    // hemoglobin: g/dl -> pg/l -> *10^13
    // erythrocytes: 10^6/µl -> 10^12/l ->*10^12
    @NonNull
    private String getMchVal() {
        EditText entry = (EditText) findViewById(R.id.mchVal);
        if (entry.getText().toString().isEmpty()) {
            if (!getErythrocyteVal().isEmpty() && !getHemoglobinVal().isEmpty()) {
                Double mch = (convertToDefaultDouble(getHemoglobinVal()) * Math.pow(10, 13)) / (convertToDefaultDouble(getErythrocyteVal()) * Math.pow(10, 12));
                patBloodRecord.setMch(mch);
                return String.valueOf(mch);
            } else {
                patBloodRecord.setMch(normalCount.getMch());
                return String.valueOf(patBloodRecord.getMch());
            }
        } else {
            patBloodRecord.setMch(convertToDefaultDouble(entry.getText().toString()));
            return entry.getText().toString();
        }
    }

    // gets mchc value from entry field
    // if field is empty, it is calculated from other values
    @NonNull
    private String getMchcVal() {
        EditText entry = (EditText) findViewById(R.id.mchcVal);
        if (entry.getText().toString().isEmpty()) {
            patBloodRecord.setMchc(normalCount.getMchc());
            return String.valueOf(patBloodRecord.getMchc());
        } else {
            patBloodRecord.setMchc(convertToDefaultDouble(entry.getText().toString()));
            return entry.getText().toString();
        }
    }

    // get platelet value from entry field
    @NonNull
    private String getPlateletVal() {
        EditText entry = (EditText) findViewById(R.id.plateletVal);
        if (entry.getText().toString().isEmpty()) {
            patBloodRecord.setPlatelet(normalCount.getPlatelet());
            return String.valueOf(patBloodRecord.getPlatelet());
        } else {
            patBloodRecord.setPlatelet(convertToDefaultDouble(entry.getText().toString()));
            return entry.getText().toString();
        }
    }

    // get reticulocytes value from entry field
    @NonNull
    private String getReticulocytesVal() {
        EditText entry = (EditText) findViewById(R.id.reticulocytesVal);
        if (entry.getText().toString().isEmpty()) {
            patBloodRecord.setReticulocytes(normalCount.getReticulocytes());
            return String.valueOf(patBloodRecord.getReticulocytes());
        } else {
            patBloodRecord.setReticulocytes(convertToDefaultDouble(entry.getText().toString()));
            return entry.getText().toString();
        }
    }

    // get mpv value from entry field
    @NonNull
    private String getMpvVal() {
        EditText entry = (EditText) findViewById(R.id.mpvVal);
        if (entry.getText().toString().isEmpty()) {
            patBloodRecord.setMpv(normalCount.getMpv());
            return String.valueOf(patBloodRecord.getMpv());
        } else {
            patBloodRecord.setMpv(convertToDefaultDouble(entry.getText().toString()));
            return entry.getText().toString();
        }
    }

    // get rdw value from field
    @NonNull
    private String getRdwVal() {
        EditText entry = (EditText) findViewById(R.id.rdwVal);
        if (entry.getText().toString().isEmpty()) {
            patBloodRecord.setRdw(normalCount.getRdw());
            return String.valueOf(patBloodRecord.getRdw());
        } else {
            patBloodRecord.setRdw(convertToDefaultDouble(entry.getText().toString()));
            return entry.getText().toString();
        }
    }

    // compares values from entry field to database and then decides which disease
    private void compareToDiseases() {
        diseaseRecords.clear();

        List<DiseaseRecord> diseaseDatabase = DiseaseOps.getAllDiseaseRecords(dataSource.database);
        List<BloodRecord> bloodDatabase = BloodOps.getAllBloodRecords(dataSource.database);
        List<DiseaseBloodRelationRecord> diseaseBloodDatabase = DiseaseBloodOps.getAllDiseaseBloodRelationRecord(dataSource.database);
        for (int i = 0; i < diseaseBloodDatabase.size(); i++) {
            for (int j = 0; j < diseaseDatabase.size(); j++) {
                for (int k = 0; k < bloodDatabase.size(); k++) {
                    if (diseaseBloodDatabase.get(i).getDiseaseId() == diseaseDatabase.get(j).getId() && diseaseBloodDatabase.get(i).getBloodId() == bloodDatabase.get(k).getId()) {
                        if ((bloodDatabase.get(k).getLeukocyteMin() < patBloodRecord.getLeukocyte() && bloodDatabase.get(k).getLeukocyteMax() > patBloodRecord.getLeukocyte()) ||
                                (bloodDatabase.get(k).getLeukocyteMin() == 0 && bloodDatabase.get(k).getLeukocyteMax() == 0)) {
                            if ((bloodDatabase.get(k).getErythrocyteMin() < patBloodRecord.getErythrocyte() && bloodDatabase.get(k).getErythrocyteMax() > patBloodRecord.getErythrocyte()) ||
                                    (bloodDatabase.get(k).getErythrocyteMin() == 0 && bloodDatabase.get(k).getErythrocyteMax() == 0)) {
                                if ((bloodDatabase.get(k).getHemoglobinMin() < patBloodRecord.getHemoglobin() && bloodDatabase.get(k).getHemoglobinMax() > patBloodRecord.getHemoglobin()) ||
                                        (bloodDatabase.get(k).getHemoglobinMin() == 0 && bloodDatabase.get(k).getHemoglobinMax() == 0)) {
                                    if ((bloodDatabase.get(k).getHematocritMin() < patBloodRecord.getHematocrit() && bloodDatabase.get(k).getHematocritMax() > patBloodRecord.getHematocrit()) ||
                                            (bloodDatabase.get(k).getHematocritMin() == 0 && bloodDatabase.get(k).getHematocritMax() == 0)) {
                                        if ((bloodDatabase.get(k).getMcvMin() < patBloodRecord.getMcv() && bloodDatabase.get(k).getMcvMax() > patBloodRecord.getMcv()) ||
                                                (bloodDatabase.get(k).getMcvMin() == 0 && bloodDatabase.get(k).getMcvMax() == 0)) {
                                            if ((bloodDatabase.get(k).getMchMin() < patBloodRecord.getMch() && bloodDatabase.get(k).getMchMax() > patBloodRecord.getMch()) ||
                                                    (bloodDatabase.get(k).getMchMin() == 0 && bloodDatabase.get(k).getMchMax() == 0)) {
                                                if ((bloodDatabase.get(k).getMchcMin() < patBloodRecord.getMchc() && bloodDatabase.get(k).getMchcMax() > patBloodRecord.getMchc()) ||
                                                        (bloodDatabase.get(k).getMchcMin() == 0 && bloodDatabase.get(k).getMchcMax() == 0)) {
                                                    if ((bloodDatabase.get(k).getPlateletMin() < patBloodRecord.getPlatelet() && bloodDatabase.get(k).getPlateletMax() > patBloodRecord.getPlatelet()) ||
                                                            (bloodDatabase.get(k).getPlateletMin() == 0 && bloodDatabase.get(k).getPlateletMax() == 0)) {
                                                        if ((bloodDatabase.get(k).getReticulocytesMin() < patBloodRecord.getReticulocytes() && bloodDatabase.get(k).getReticulocytesMax() > patBloodRecord.getReticulocytes()) ||
                                                                (bloodDatabase.get(k).getReticulocytesMin() == 0 && bloodDatabase.get(k).getReticulocytesMax() == 0)) {
                                                            if ((bloodDatabase.get(k).getMpvMin() < patBloodRecord.getMpv() && bloodDatabase.get(k).getMpvMax() > patBloodRecord.getMpv()) ||
                                                                    (bloodDatabase.get(k).getMpvMin() == 0 && bloodDatabase.get(k).getMpvMax() == 0)) {
                                                                if ((bloodDatabase.get(k).getRdwMin() < patBloodRecord.getRdw() && bloodDatabase.get(k).getRdwMax() > patBloodRecord.getRdw()) ||
                                                                        (bloodDatabase.get(k).getRdwMin() == 0 && bloodDatabase.get(k).getRdwMax() == 0)) {
                                                                    if (bloodDatabase.get(k).getGender().equals(patRecord.getGender())) {
                                                                        diseaseRecords.add(diseaseDatabase.get(j));
                                                                        Log.d(LOG_TAG, "Disease: " + diseaseDatabase.get(j).getName());
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // prints possible diseases to disease field
    private void printDisease() {
        String disease = "";

        if (!diseaseRecords.isEmpty()) {
            for (int i = 0; i < diseaseRecords.size(); i++) {
                disease += diseaseRecords.get(i).getName() + "\n";
            }

            TextView view = (TextView) findViewById(R.id.diagnosisDiseaseId);
            view.setText(disease);

        } else {

            TextView view = (TextView) findViewById(R.id.diagnosisDiseaseId);
            view.setText(R.string.noDiseaseFound);

        }
    }

    // prints recommended medication to screen
    private void printRecommendedMedication() {

        String recommendedMedicationStr = "";

        recommendedMedication.clear();

        List<MedicationRecord> medicationList = MedicationOps.getAllMedicationRecords(dataSource.database);
        List<DiseaseMedicationRelationRecord> diseaseMedicationList = DiseaseMedicationOps.getAllDiseaseMedicationRelationRecord(dataSource.database);

        if (diseaseRecords.isEmpty()) {
            setRecommendedMedication("Could not find a recommended Medication.");
        } else {
            for (int i = 0; i < diseaseRecords.size(); i++) {
                for (int j = 0; j < medicationList.size(); j++) {
                    for (int k = 0; k < diseaseMedicationList.size(); k++) {
                        if (diseaseRecords.get(i).getId() == diseaseMedicationList.get(k).getDiseaseId() && medicationList.get(j).getId() == diseaseMedicationList.get(k).getDrugId()) {
                            boolean insert = true;
                            for (int l = 0; l < recommendedMedication.size(); l++){
                                if (recommendedMedication.get(l).getDrugName().equals(medicationList.get(j).getDrugName())){
                                    insert = false;
                                }
                            }
                            if (insert){
                                recommendedMedication.add(medicationList.get(j));
                            }
                        }
                    }
                }
            }

            checkForInteraction();

            for (int i = 0; i < recommendedMedication.size(); i++) {
                recommendedMedicationStr += recommendedMedication.get(i).getDrugName() + "\n";
            }

            if (recommendedMedicationStr.equals("")){
                recommendedMedicationStr = "Could not find a recommended Medication, because of medication interaction.";
            }

            setRecommendedMedication(recommendedMedicationStr);
        }
    }

    // checks for medication interaction and removes recommended drugs which interact with current medication
    // returns a list which includes current and recommended medication
    private void checkForInteraction(){
        List<MedicationInteractionRecord> tmpList = MedicationInteractionOps.getAllMedicationInteractionRecord(dataSource.database);

        for (int i = 0; i < tmpList.size(); i++){
            for (int j = 0; j < recommendedMedication.size(); j++){
                for (int k = 0; k < currentMedication.size(); k++){
                    if ((currentMedication.get(k).getId() == tmpList.get(i).getDrugId1() && recommendedMedication.get(j).getId() == tmpList.get(i).getDrugId2()) ||
                            (currentMedication.get(k).getId() == tmpList.get(i).getDrugId2() && recommendedMedication.get(j).getId() == tmpList.get(i).getDrugId1())) {
                        recommendedMedication.remove(j);
                    }
                }
            }
        }
        for (int i = 0; i < currentMedication.size(); i++) {
            recommendedMedication.add(currentMedication.get(i));
        }
    }

    // sets medication value to medication field
    private void setRecommendedMedication(String text){
        TextView view = (TextView) findViewById(R.id.diagnosisRecommendedMedication);
        view.setText(text);
    }

    // prints current medication to screen
    private void printCurrentMedication() {

        String currentMedicationStr = "";

        currentMedication.clear();

        EditText drugField = (EditText) findViewById(drug1);

        if ((entryList.size() == 1) && (drugField.getText().toString().equals(""))) {
            setCurrentMedication("No current Medication found.");
        } else {
            for (int i = 0; i < entryList.size(); i++) {
                EditText entryField = (EditText) findViewById(entryList.get(i));
                if (!entryField.getText().toString().equals("")) {
                    currentMedication.add(MedicationOps.createMedicationRecord(entryField.getText().toString(), dataSource.database));
                }
            }
            for (int i = 0; i < currentMedication.size(); i++){
                currentMedicationStr += currentMedication.get(i).getDrugName() + "\n";
            }
            setCurrentMedication(currentMedicationStr);
        }


    }

    // sets medication value to medication field
    private void setCurrentMedication(String text){
        TextView view = (TextView) findViewById(R.id.diagnosisCurrentMedication);
        view.setText(text);
    }

    // prints blood count to screen
    private void printBloodCount() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        int[] idList = {R.id.diagnosisLeukocyte,
        R.id.diagnosisErythrocyte,
        R.id.diagnosisHemoglobin,
        R.id.diagnosisHematocrit,
        R.id.diagnosisMcv,
        R.id.diagnosisMch,
        R.id.diagnosisMchc,
        R.id.diagnosisPlatelet,
        R.id.diagnosisReticulocytes,
        R.id.diagnosisMpv,
        R.id.diagnosisRdw};

        double[] valueList = {patBloodRecord.getLeukocyte(),
                patBloodRecord.getErythrocyte(),
        patBloodRecord.getHemoglobin(),
        patBloodRecord.getHematocrit(),
        patBloodRecord.getMcv(),
        patBloodRecord.getMch(),
        patBloodRecord.getMchc(),
        patBloodRecord.getPlatelet(),
        patBloodRecord.getReticulocytes(),
        patBloodRecord.getMpv(),
        patBloodRecord.getRdw()};

        for (int i = 0; i < valueList.length; i++){
            TextView view = (TextView) findViewById(idList[i]);
            view.setText(String.valueOf(decimalFormat.format(valueList[i])));
        }
    }

    // prints patient info to screen
    private void printPatientInfo(){
        TextView view = (TextView) findViewById(R.id.diagnosisPatientId);
        view.setText(String.valueOf(patRecord.getHospitalId()));
    }

    // sets on click listener for save button
    // enables:
    // saves data to database and switches visibility of screens
    private void setOnClickListenerForSaveButton(){
        Button button = (Button) findViewById(R.id.diagnosisSaveButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioButtonIsChecked()) {
                    if (getHospitalID() > 0) {
                        saveToDatabase();
                        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                        setResultScreenVisibility(View.GONE);
                        setDefaultScreenVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    // method which saves data to database
    private void saveToDatabase(){
        insertPatient();
        createBloodCountEntry();
        createPatientBloodCountEntry();
        createBloodCountDiseaseEntry();
        createBloodCountMedicationEntry();
    }

    // inserts patient from entry field to database
    private void insertPatient() {
        if (isFemale()) {
            patRecord = PatientOps.createPatientRecord(getHospitalID().toString(), "f", dataSource.database);
        } else {
            patRecord = PatientOps.createPatientRecord(getHospitalID().toString(), "m", dataSource.database);
        }
    }

    // method which creates the  blood count entry depending on values entered
    private void createBloodCountEntry() {
        patBloodRecord = BloodCountOps.createBloodCountRecord(String.valueOf(patBloodRecord.getLeukocyte()),
                String.valueOf(patBloodRecord.getErythrocyte()),
                String.valueOf(patBloodRecord.getHemoglobin()),
                String.valueOf(patBloodRecord.getHematocrit()),
                String.valueOf(patBloodRecord.getMcv()),
                String.valueOf(patBloodRecord.getMch()),
                String.valueOf(patBloodRecord.getMchc()),
                String.valueOf(patBloodRecord.getPlatelet()),
                String.valueOf(patBloodRecord.getReticulocytes()),
                String.valueOf(patBloodRecord.getMpv()),
                String.valueOf(patBloodRecord.getRdw()),
                dataSource.database);
    }

    // creates relational entry for patient and blood count
    private void createPatientBloodCountEntry() {
        if (patRecord.getId() != -1) {
            if (patBloodRecord.getId() != -1) {
                PatientBloodCountOps.createPatientBloodCountRecord(patRecord.getId(), patBloodRecord.getId(), dataSource.database);
            }
        }
    }

    // creates blood count entry which is linked to disease
    private void createBloodCountDiseaseEntry(){
        if (patBloodRecord.getId() > 0) {
            for (int i = 0; i < diseaseRecords.size(); i++){
                if (diseaseRecords.get(i).getId() > 0) {
                    BloodCountDiseaseOps.createBloodCountDiseaseRecord(patBloodRecord.getId(), diseaseRecords.get(i).getId(), dataSource.database);
                }
            }
        }
    }

    // creates blood count entry which is linked to medication
    private void createBloodCountMedicationEntry(){
        if (patBloodRecord.getId() > 0){
            for (int i = 0; i < recommendedMedication.size(); i++){
                if (recommendedMedication.get(i).getId() > 0){
                    BloodCountMedicationOps.createBloodCountMedicationRecord(patBloodRecord.getId(), recommendedMedication.get(i).getId(), dataSource.database);
                }
            }
        }
    }

    // configures default auto complete and loads new list from database
    private void configureDefaultAutoCompleteView() {

        final AutoCompleteTextView entryDrugOne = (AutoCompleteTextView) findViewById(R.id.drug1);

        List<MedicationRecord> currentDatabase = MedicationOps.getAllMedicationRecords(dataSource.database);
        List<String> tmpList = new ArrayList<>();

        for (int i = 0; i < currentDatabase.size(); i++) {
            tmpList.add(currentDatabase.get(i).getDrugName());
        }

        Collections.sort(tmpList);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdownlist, tmpList);

        entryDrugOne.setAdapter(adapter);

        entryDrugOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entryDrugOne.setDropDownWidth(convertToDp(200));
                entryDrugOne.showDropDown();
            }
        });
    }

    // converts dp to actual pixels
    private int convertToDp(float sizeInDp){
        float scale = getResources().getDisplayMetrics().density;
        return (int) (sizeInDp*scale + 0.5f);
    }

    private double convertToDefaultDouble(String number){
        number = number.replace(",", ".");
        return Double.parseDouble(number);
    }

    private boolean getHelp(int id) {
        ProcessOperations processOperations = new ProcessOperations();
        return processOperations.getEntry(id, dataSource.database).isDiagnosisHelp();
    }

    private void displayHelp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Diagnosis.this);
        builder.setTitle("Instruction");
        builder.setMessage(R.string.diagnosisHelp);

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
                        processOperations.updateEntry("diagnosis", dataSource.database);
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}