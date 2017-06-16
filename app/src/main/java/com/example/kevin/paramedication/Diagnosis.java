package com.example.kevin.paramedication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
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
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevin.paramedication.DatabaseObjects.BloodCountRecord;
import com.example.kevin.paramedication.DatabaseObjects.BloodRecord;
import com.example.kevin.paramedication.DatabaseObjects.DiseaseBloodRelationRecord;
import com.example.kevin.paramedication.DatabaseObjects.DiseaseMedicationRelationRecord;
import com.example.kevin.paramedication.DatabaseObjects.DiseaseRecord;
import com.example.kevin.paramedication.DatabaseObjects.MedicationRecord;
import com.example.kevin.paramedication.DatabaseObjects.PatientRecord;
import com.example.kevin.paramedication.DatabaseOperations.BloodCountOperations;
import com.example.kevin.paramedication.DatabaseOperations.BloodOperations;
import com.example.kevin.paramedication.DatabaseOperations.DbDataSource;
import com.example.kevin.paramedication.DatabaseOperations.DiseaseBloodRelationOperations;
import com.example.kevin.paramedication.DatabaseOperations.DiseaseMedicationRelationOperations;
import com.example.kevin.paramedication.DatabaseOperations.DiseaseOperations;
import com.example.kevin.paramedication.DatabaseOperations.MedicationOperations;
import com.example.kevin.paramedication.DatabaseOperations.PatientBloodCountOperations;
import com.example.kevin.paramedication.DatabaseOperations.PatientDiseaseOperations;
import com.example.kevin.paramedication.DatabaseOperations.PatientMedicationOperations;
import com.example.kevin.paramedication.DatabaseOperations.PatientOperations;

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

    // these are used in order to display the actual disease and the recommended medication
    List<DiseaseRecord> diseaseRecords = new ArrayList<>();
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
    private PatientDiseaseOperations PatientDiseaseOps = new PatientDiseaseOperations();
    private PatientMedicationOperations PatientMedicationOps = new PatientMedicationOperations();
    private PatientOperations PatientOps = new PatientOperations();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);

        entryList.add(drug1);

        dataSource = new DbDataSource(this);
        Log.d(LOG_TAG, "Opening database.");
        dataSource.open();

        createDummyEntry();

        initializeTabs();
        initializeMedicationButton();
        initializeRadioButtons();
        setOnClickListenerForDiagnosis();
        configureDefaultAutoCompleteView();
    }

    /*_____________________________________General______________________________________________*/

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

    // initializes medication button
    // enables:
    // deletes itself and adds a new button and a new TextView
    private void initializeMedicationButton() {
        Button button = (Button) findViewById(R.id.addButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((ViewGroup) v.getParent()).removeView(v);
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.drugs);
                linearLayout.addView(createNewLinearLayout(createNewTextView("Drug"), createNewEntry("drug")));
                linearLayout.addView(createNewButton("Add more"));
            }
        });
    }

    // creates linear layout with TextView next to EditText
    public LinearLayout createNewLinearLayout(TextView view, EditText entry) {
        final LinearLayout linear = new LinearLayout(this);
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linear.setLayoutParams(layoutParams);
        linear.setOrientation(LinearLayout.HORIZONTAL);
        linear.setPadding(8, 8, 8, 8);
        linear.addView(view);
        linear.addView(entry);
        return linear;
    }

    // creates new text view with text as parameter
    public TextView createNewTextView(String text) {
        final TextView textView = new TextView(this);
        final LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        textViewParams.setMargins(8, 8, 8, 8);
        textView.setLayoutParams(textViewParams);
        textView.setPadding(8, 8, 8, 8);
        textView.setTextSize(15);
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
                linearLayout.addView(createNewLinearLayout(createNewTextView("Drug"), createNewEntry("Drug")));
                linearLayout.addView(createNewButton("Add more"));
            }
        });
        return button;
    }

    // creates new editText field with hint as parameter
    public AutoCompleteTextView createNewEntry(String hint) {
        final AutoCompleteTextView entry = new AutoCompleteTextView(this);
        final LinearLayout.LayoutParams entryViewParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        entryViewParams.setMargins(8, 8, 8, 8);
        entry.setLayoutParams(entryViewParams);
        entry.setHint(hint);
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
    public void setOnClickListenerForDiagnosis() {
        Button resultButton = (Button) findViewById(R.id.getResult);
        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioButtonIsChecked()) {
                    if (getHospitalID() > 0) {
                        initializePatient();
                        initializePatientBloodCountRecord();
                        print(compareToDiseases());
                    } else {
                        Toast.makeText(getApplicationContext(), "Please enter a valid Hospital ID", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please choose a Gender", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // checks if radio button is checked and returns true if so
    private boolean radioButtonIsChecked() {
        RadioButton femaleButton = (RadioButton) findViewById(R.id.femaleButton);
        RadioButton maleButton = (RadioButton) findViewById(R.id.maleButton);

        return femaleButton.isChecked() || maleButton.isChecked();
    }

    // gets entered hospital id from entry field
    private Long getHospitalID() {
        EditText hospitalID = (EditText) findViewById(R.id.hospital_Id);
        List<PatientRecord> currentDatabase = PatientOps.getAllPatientRecords(dataSource.database);

        for (int i = 0; i< currentDatabase.size(); i++){
            if (currentDatabase.get(i).getHospitalId() == Long.parseLong(hospitalID.getText().toString())){
                return 0L;
            }
        }


        if (hospitalID.getText().toString().equals("")) {
            return 0L;
        } else return Long.parseLong(hospitalID.getText().toString());
    }

    // pushes Blood Count from entry to database
    private void initializePatientBloodCountRecord() {
        patBloodRecord.setLeukocyte(Double.parseDouble(getLeukocyteVal()));
        patBloodRecord.setErythrocyte(Double.parseDouble(getErythrocyteVal()));
        patBloodRecord.setHemoglobin(Double.parseDouble(getHemoglobinVal()));
        patBloodRecord.setHematocrit(Double.parseDouble(getHematocritVal()));
        patBloodRecord.setMcv(Double.parseDouble(getMcvVal()));
        patBloodRecord.setMch(Double.parseDouble(getMchVal()));
        patBloodRecord.setMchc(Double.parseDouble(getMchcVal()));
        patBloodRecord.setPlatelet(Double.parseDouble(getPlateletVal()));
        patBloodRecord.setReticulocytes(Double.parseDouble(getReticulocytesVal()));
        patBloodRecord.setMpv(Double.parseDouble(getMpvVal()));
        patBloodRecord.setRdw(Double.parseDouble(getRdwVal()));
    }

    // gets leukocyte value from entry field
    @NonNull
    private String getLeukocyteVal() {
        EditText entry = (EditText) findViewById(R.id.leukocyteVal);
        if (entry.getText().toString().isEmpty()) {
            patBloodRecord.setLeukocyte(0);
            return "0";
        } else {
            patBloodRecord.setLeukocyte(Double.parseDouble(entry.getText().toString()));
            return entry.getText().toString();
        }
    }

    // gets erythrocyte value from entry field
    @NonNull
    private String getErythrocyteVal() {
        EditText entry = (EditText) findViewById(R.id.erythrocyteVal);
        if (entry.getText().toString().isEmpty()) {
            patBloodRecord.setErythrocyte(0);
            return "0";
        } else {
            patBloodRecord.setErythrocyte(Double.parseDouble(entry.getText().toString()));
            return entry.getText().toString();
        }
    }

    // gets hemoglobin value from entry field
    @NonNull
    private String getHemoglobinVal() {
        EditText entry = (EditText) findViewById(R.id.hemoglobinVal);
        if (entry.getText().toString().isEmpty()) {
            patBloodRecord.setHemoglobin(0);
            return "0";
        } else {
            patBloodRecord.setHemoglobin(Double.parseDouble(entry.getText().toString()));
            return entry.getText().toString();
        }
    }

    // gets hematocrit value from entry field
    @NonNull
    private String getHematocritVal() {
        EditText entry = (EditText) findViewById(R.id.hematocritVal);
        if (entry.getText().toString().isEmpty()) {
            patBloodRecord.setHematocrit(0);
            return "0";
        } else {
            patBloodRecord.setHematocrit(Double.parseDouble(entry.getText().toString()));
            return entry.getText().toString();
        }
    }

    // gets mcv value from entry field
    // if entry field is empty it is calculated from values
    @NonNull
    private String getMcvVal() {
        EditText entry = (EditText) findViewById(R.id.mcvVal);
        if (entry.getText().toString().isEmpty()) {
            Double mcv = Double.parseDouble(getMchVal()) / Double.parseDouble(getMchcVal());
            patBloodRecord.setMcv(mcv);
            return mcv.toString();
        } else {
            patBloodRecord.setMcv(Double.parseDouble(entry.getText().toString()));
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
            Double mch = (Double.parseDouble(getHemoglobinVal()) * Math.pow(10, 13)) / (Double.parseDouble(getErythrocyteVal()) * Math.pow(10, 12));
            patBloodRecord.setMch(mch);
            return mch.toString();
        } else {
            patBloodRecord.setMch(Double.parseDouble(entry.getText().toString()));
            return entry.getText().toString();
        }
    }

    // gets mchc value from entry field
    // if field is empty, it is calculated from other values
    @NonNull
    private String getMchcVal() {
        EditText entry = (EditText) findViewById(R.id.mchcVal);
        if (entry.getText().toString().isEmpty()) {
            Double mchc = Double.parseDouble(getHemoglobinVal()) / Double.parseDouble(getHematocritVal());
            patBloodRecord.setMchc(mchc);
            return mchc.toString();
        } else {
            patBloodRecord.setMchc(Double.parseDouble(entry.getText().toString()));
            return entry.getText().toString();
        }
    }

    @NonNull
    private String getPlateletVal() {
        EditText entry = (EditText) findViewById(R.id.plateletVal);
        if (entry.getText().toString().isEmpty()) {
            patBloodRecord.setPlatelet(0);
            return "0";
        } else {
            patBloodRecord.setPlatelet(Double.parseDouble(entry.getText().toString()));
            return entry.getText().toString();
        }
    }

    @NonNull
    private String getReticulocytesVal() {
        EditText entry = (EditText) findViewById(R.id.reticulocytesVal);
        if (entry.getText().toString().isEmpty()) {
            patBloodRecord.setReticulocytes(0);
            return "0";
        } else {
            patBloodRecord.setReticulocytes(Double.parseDouble(entry.getText().toString()));
            return entry.getText().toString();
        }
    }

    @NonNull
    private String getMpvVal() {
        EditText entry = (EditText) findViewById(R.id.mpvVal);
        if (entry.getText().toString().isEmpty()) {
            patBloodRecord.setMpv(0);
            return "0";
        } else {
            patBloodRecord.setMpv(Double.parseDouble(entry.getText().toString()));
            return entry.getText().toString();
        }
    }

    @NonNull
    private String getRdwVal() {
        EditText entry = (EditText) findViewById(R.id.rdwVal);
        if (entry.getText().toString().isEmpty()) {
            patBloodRecord.setRdw(0);
            return "0";
        } else {
            patBloodRecord.setRdw(Double.parseDouble(entry.getText().toString()));
            return entry.getText().toString();
        }
    }


    // checks if patient is female or male
    private boolean isFemale() {
        RadioButton gender = (RadioButton) findViewById(R.id.femaleButton);
        return gender.isChecked();
    }

    private void initializePatient() {
        if (isFemale()) {
            patRecord.setGender("f");
        } else {
            patRecord.setGender("m");
        }
        patRecord.setHospitalId(getHospitalID());

    }

    // creates relational entry for patient and blood count
    private void createPatientBloodCountEntry() {
        if (patRecord.getId() != -1) {
            if (patBloodRecord.getId() != -1) {
                PatientBloodCountOps.createPatientBloodcountRecord(patRecord.getId(), patBloodRecord.getId(), dataSource.database);
            }
        }
    }

    // compares values from entry field to database and then decides which disease
    private List<DiseaseRecord> compareToDiseases() {
        diseaseRecords.clear();

        List<DiseaseRecord> diseaseDatabase = DiseaseOps.getAllDiseaseRecords(dataSource.database);
        List<BloodRecord> bloodDatabase = BloodOps.getAllBloodRecords(dataSource.database);
        List<DiseaseBloodRelationRecord> diseaseBloodDatabase = DiseaseBloodOps.getAllDiseaseBloodRelationRecord(dataSource.database);
        for (int i = 0; i < diseaseBloodDatabase.size(); i++) {
            for (int j = 0; j < diseaseDatabase.size(); j++) {
                for (int k = 0; k < bloodDatabase.size(); k++) {
                    if (diseaseBloodDatabase.get(i).getDiseaseId() == diseaseDatabase.get(j).getId() && diseaseBloodDatabase.get(i).getBloodId() == bloodDatabase.get(k).getId()) {
                        if (bloodDatabase.get(k).getLeukocyteMin() < patBloodRecord.getLeukocyte() && bloodDatabase.get(k).getLeukocyteMax() > patBloodRecord.getLeukocyte()) {
                            if (bloodDatabase.get(k).getErythrocyteMin() < patBloodRecord.getErythrocyte() && bloodDatabase.get(k).getErythrocyteMax() > patBloodRecord.getErythrocyte()) {
                                if (bloodDatabase.get(k).getHemoglobinMin() < patBloodRecord.getHemoglobin() && bloodDatabase.get(k).getHemoglobinMax() > patBloodRecord.getHemoglobin()) {
                                    if (bloodDatabase.get(k).getHematocritMin() < patBloodRecord.getHematocrit() && bloodDatabase.get(k).getHematocritMax() > patBloodRecord.getHematocrit()) {
                                        if (bloodDatabase.get(k).getMcvMin() < patBloodRecord.getMcv() && bloodDatabase.get(k).getMcvMax() > patBloodRecord.getMcv()) {
                                            if (bloodDatabase.get(k).getMchMin() < patBloodRecord.getMch() && bloodDatabase.get(k).getMchMax() > patBloodRecord.getMch()) {
                                                if (bloodDatabase.get(k).getMchcMin() < patBloodRecord.getMchc() && bloodDatabase.get(k).getMchcMax() > patBloodRecord.getMchc()) {
                                                    if (bloodDatabase.get(k).getPlateletMin() < patBloodRecord.getPlatelet() && bloodDatabase.get(k).getPlateletMax() > patBloodRecord.getPlatelet()) {
                                                        if (bloodDatabase.get(k).getReticulocytesMin() < patBloodRecord.getReticulocytes() && bloodDatabase.get(k).getReticulocytesMax() > patBloodRecord.getReticulocytes()) {
                                                            if (bloodDatabase.get(k).getMpvMin() < patBloodRecord.getMpv() && bloodDatabase.get(k).getMpvMax() > patBloodRecord.getMpv()) {
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
        return diseaseRecords;
    }

    // prints summary of entered values to screen
    private void print(List<DiseaseRecord> list) {


        LinearLayout printArea = (LinearLayout) findViewById(R.id.printAreaDiagnosis);
        printArea.removeAllViews();


        createUpdateButton(printArea);
        printPatientID(printArea);
        printDisease(printArea, list);
        printRecommendedMedication(printArea, list);
        printCurrentMedication(printArea);
        printBloodCount(printArea);
        printSaveButton(printArea);

    }

    // creates update button for disease result
    private void createUpdateButton(LinearLayout printArea) {
        Button view = new Button(this);
        String text = "Update";
        view.setText(text);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(24, 24, 24, 24);
        view.setLayoutParams(params);

        view.setTextSize(15);
        view.setBackgroundColor(Color.parseColor("#FFAEAE"));
        view.setTextColor(Color.parseColor("#47525E"));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializePatientBloodCountRecord();
                initializePatient();
                print(compareToDiseases());
                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
            }
        });
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setPadding(16, 16, 16, 16);

        printArea.addView(linearLayout);
        linearLayout.addView(view);
    }

    // prints patient id to screen
    private void printPatientID(LinearLayout printArea) {
        printArea.addView(createLinearLayout(createTextView("Patient ID: "), createTextView("" + patRecord.getHospitalId())));
        createDivider(printArea);
    }

    // creates new TextView
    private TextView createTextView(String text) {
        TextView view = new TextView(this);
        view.setText(text);
        view.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        view.setPadding(16, 16, 16, 16);
        view.setTextSize(15);
        view.setTextColor(Color.parseColor("#47525E"));
        view.setGravity(Gravity.CENTER_HORIZONTAL);
        return view;
    }

    private LinearLayout createLinearLayout(TextView view1, TextView view2) {
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setPadding(16, 16, 16, 16);
        linearLayout.addView(view1);
        linearLayout.addView(view2);
        return linearLayout;
    }

    private void printDisease(LinearLayout printArea, List<DiseaseRecord> list) {

        boolean firstRun = true;

        if (!list.isEmpty()) {

            for (int i = 0; i < list.size(); i++) {
                if (firstRun) {
                    printArea.addView(createLinearLayout(createTextView("Related to: "), createTextView(list.get(i).getName())));
                    firstRun = false;
                } else {
                    printArea.addView(createLinearLayout(createTextView(""), createTextView(list.get(i).getName())));
                }
            }
            createDivider(printArea);
        } else {
            printArea.addView(createLinearLayout(createTextView("Related to: "), createTextView("No relation found")));
            createDivider(printArea);
        }
    }

    private void printRecommendedMedication(LinearLayout printArea, List<DiseaseRecord> list) {

        boolean firstRun = true;

        recommendedMedication.clear();

        List<MedicationRecord> medicationList = MedicationOps.getAllMedicationRecords(dataSource.database);
        List<DiseaseMedicationRelationRecord> diseaseMedicationList = DiseaseMedicationOps.getAllDiseaseMedicationRelationRecord(dataSource.database);

        if (list.isEmpty()) {
            printArea.addView(createLinearLayout(createTextView("Recommended Medication: "), createTextView("No recommendation")));

            createDivider(printArea);
        } else {
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < medicationList.size(); j++) {
                    for (int k = 0; k < diseaseMedicationList.size(); k++) {
                        if (list.get(i).getId() == diseaseMedicationList.get(k).getDiseaseId() && medicationList.get(j).getId() == diseaseMedicationList.get(k).getDrugId()) {
                            recommendedMedication.add(medicationList.get(j));
                        }
                    }
                }
            }

            for (int i = 0; i < recommendedMedication.size(); i++) {
                if (firstRun) {
                    firstRun = false;

                    printArea.addView(createLinearLayout(createTextView("Recommended Medication: "), createTextView(recommendedMedication.get(i).getDrugName())));
                } else {
                    printArea.addView(createLinearLayout(createTextView(""), createTextView(recommendedMedication.get(i).getDrugName())));
                }
            }
            createDivider(printArea);
        }
    }

    private void printCurrentMedication(LinearLayout printArea) {

        boolean firstRun = true;

        EditText drugField = (EditText) findViewById(drug1);

        if ((entryList.size() == 1) && (drugField.getText().toString().equals(""))) {
            printArea.addView(createLinearLayout(createTextView("Current Medication: "), createTextView("No current medication found")));
            createDivider(printArea);
        } else {
            for (int i = 0; i < entryList.size(); i++) {
                EditText entryField = (EditText) findViewById(entryList.get(i));
                if (!entryField.getText().toString().equals("")) {
                    if (firstRun) {
                        printArea.addView(createLinearLayout(createTextView("Current Medication:"), createTextView(entryField.getText().toString())));
                        firstRun = false;
                    } else {
                        printArea.addView(createLinearLayout(createTextView(""), createTextView(entryField.getText().toString())));
                    }
                }
            }
            createDivider(printArea);
        }
    }

    private void printBloodCount(LinearLayout printArea) {
        printArea.addView(createLinearLayout(createTextView("Leukocyte: "), createTextView("" + patBloodRecord.getLeukocyte())));
        printArea.addView(createLinearLayout(createTextView("Erythrocyte: "), createTextView("" + patBloodRecord.getErythrocyte())));
        printArea.addView(createLinearLayout(createTextView("Hemoglobin: "), createTextView("" + patBloodRecord.getHemoglobin())));
        printArea.addView(createLinearLayout(createTextView("Hematocrit: "), createTextView("" + patBloodRecord.getHematocrit())));
        printArea.addView(createLinearLayout(createTextView("MCV: "), createTextView("" + patBloodRecord.getMcv())));
        printArea.addView(createLinearLayout(createTextView("MCH: "), createTextView("" + patBloodRecord.getMch())));
        printArea.addView(createLinearLayout(createTextView("MCHC: "), createTextView("" + patBloodRecord.getMchc())));
        printArea.addView(createLinearLayout(createTextView("Platelet: "), createTextView("" + patBloodRecord.getPlatelet())));
        printArea.addView(createLinearLayout(createTextView("Reticulocytes: "), createTextView("" + patBloodRecord.getReticulocytes())));
        printArea.addView(createLinearLayout(createTextView("MPV: "), createTextView("" + patBloodRecord.getMpv())));
        printArea.addView(createLinearLayout(createTextView("RWD: "), createTextView("" + patBloodRecord.getRdw())));
    }

    private void printSaveButton(LinearLayout printArea) {
        Button saveButton = new Button(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        String text = "Save to Database";

        params.setMargins(24, 24, 24, 24);
        saveButton.setLayoutParams(params);
        saveButton.setText(text);
        saveButton.setBackgroundColor(Color.parseColor("#FFAEAE"));
        saveButton.setTextColor(Color.parseColor("#47525E"));

        printArea.addView(saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertPatient();
                createRecommendedMedication();
                createBloodCountEntry();
                createPatientBloodCountEntry();
                createDiseaseEntries();
                Toast.makeText(getApplicationContext(), "saved", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createDiseaseEntries() {
        for (int i = 0; i < diseaseRecords.size(); i++) {
            PatientDiseaseOps.createPatientDiseaseRecord(patRecord.getId(), diseaseRecords.get(i).getId(), dataSource.database);
        }
    }

    private void createRecommendedMedication() {
        for (int i = 0; i < recommendedMedication.size(); i++) {
            PatientMedicationOps.createPatientMedicationRecord(patRecord.getId(), recommendedMedication.get(i).getId(), dataSource.database);
        }
    }

    // inserts patient from entry field to database
    private void insertPatient() {
        if (isFemale()) {
            patRecord = PatientOps.createPatientRecord(getHospitalID().toString(), "f", dataSource.database);
        } else {
            patRecord = PatientOps.createPatientRecord(getHospitalID().toString(), "m", dataSource.database);
        }
    }

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

    private void createDivider(LinearLayout printArea) {
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1));
        linearLayout.setBackgroundColor(Color.parseColor("#000000"));
        printArea.addView(linearLayout);
    }

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
                entryDrugOne.showDropDown();
            }
        });
    }

    private void createDummyEntry() {
        PatientOps.createPatientRecord("117", "m", dataSource.database);
        BloodCountOps.createBloodCountRecord("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", dataSource.database);
        DiseaseOps.createDiseaseRecord("Fieber", dataSource.database); //ID 2
        BloodOps.createBloodRecord("1", "10", "1", "10", "1", "10", "1", "10", "1", "10", "1", "10", "1", "10", "1", "10", "1", "10", "1", "10", "1", "10", "f", dataSource.database); // ID: 3
        DiseaseBloodOps.createDiseaseBloodRelationRecord(3, 2, dataSource.database);
        MedicationOps.createMedicationRecord("Aspirin", dataSource.database); // ID: 1
        MedicationOps.createMedicationRecord("Ibuprofen", dataSource.database); // ID: 2
        DiseaseMedicationOps.createDiseaseMedicationRelationRecord(2, 1, dataSource.database);
        DiseaseMedicationOps.createDiseaseMedicationRelationRecord(2, 2, dataSource.database);
        DiseaseOps.createDiseaseRecord("Kopfschmerzen", dataSource.database); //ID2
        PatientBloodCountOps.createPatientBloodcountRecord(1, 1, dataSource.database);
        PatientDiseaseOps.createPatientDiseaseRecord(1, 1, dataSource.database);
        PatientDiseaseOps.createPatientDiseaseRecord(1, 2, dataSource.database);
        PatientMedicationOps.createPatientMedicationRecord(1, 1, dataSource.database);
        PatientMedicationOps.createPatientMedicationRecord(1, 2, dataSource.database);
    }


}