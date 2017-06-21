package com.example.kevin.paramedication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevin.paramedication.DatabaseObjects.BloodCountDiseaseRecord;
import com.example.kevin.paramedication.DatabaseObjects.BloodCountMedicationRecord;
import com.example.kevin.paramedication.DatabaseObjects.BloodCountRecord;
import com.example.kevin.paramedication.DatabaseObjects.DiseaseRecord;
import com.example.kevin.paramedication.DatabaseObjects.MedicationRecord;
import com.example.kevin.paramedication.DatabaseObjects.PatientBloodcountRecord;
import com.example.kevin.paramedication.DatabaseObjects.PatientRecord;
import com.example.kevin.paramedication.DatabaseOperations.BloodCountDiseaseOperations;
import com.example.kevin.paramedication.DatabaseOperations.BloodCountMedicationOperations;
import com.example.kevin.paramedication.DatabaseOperations.BloodCountOperations;
import com.example.kevin.paramedication.DatabaseOperations.DbDataSource;
import com.example.kevin.paramedication.DatabaseOperations.DiseaseOperations;
import com.example.kevin.paramedication.DatabaseOperations.MedicationOperations;
import com.example.kevin.paramedication.DatabaseOperations.PatientBloodCountOperations;
import com.example.kevin.paramedication.DatabaseOperations.PatientOperations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.view.Gravity.CENTER_HORIZONTAL;

public class Patients extends AppCompatActivity {

    public final static String LOG_TAG = Patients.class.getSimpleName();

    private DbDataSource dataSource;
    private BloodCountOperations BloodCountOps = new BloodCountOperations();
    private DiseaseOperations DiseaseOps = new DiseaseOperations();
    private MedicationOperations MedicationOps = new MedicationOperations();
    private PatientBloodCountOperations PatientBloodCountOps = new PatientBloodCountOperations();
    private BloodCountDiseaseOperations BloodCountDiseaseOps = new BloodCountDiseaseOperations();
    private BloodCountMedicationOperations   BloodCountMedicationOps = new BloodCountMedicationOperations();
    private PatientOperations PatientOps = new PatientOperations();

    // defines which .xml file is used and handles all initializations
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);

        dataSource = new DbDataSource(this);
        Log.d(LOG_TAG, "Opening database.");
        dataSource.open();

        enableAutocomplete();
        setOnClickListenerForResultButton();
        setOnClickListenerForAutoCompleteTextView();
        //initializeHomeScreen();
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
    public void changeToDatabase(View view) {
        dataSource.close();
        Intent myIntent = new Intent(this, Database.class);
        this.startActivity(myIntent);
    }

    // sets on click listener for auto compete text view
    // enables:
    // opens drop down menu right away
    private void setOnClickListenerForAutoCompleteTextView(){
        final AutoCompleteTextView view = (AutoCompleteTextView) findViewById(R.id.PatientID);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.showDropDown();
            }
        });
    }

    // sets default screens visibility depending on parameter
    private boolean setDefaultScreenVisibility(int visibility){
        LinearLayout defaultScreen = (LinearLayout) findViewById(R.id.editableSpace);
        defaultScreen.setVisibility(visibility);
        return true;
    }

    // sets result screen visibility depending on parameter
    private boolean setResultScreenVisibility(int visibility){
        ScrollView resultScreen = (ScrollView) findViewById(R.id.patientResultView);
        resultScreen.setVisibility(visibility);
        return true;
    }

    // sets on click listener for result button
    // enables:
    // sets visibility for screens, initializes patient values and triggers printing
    private void setOnClickListenerForResultButton(){
        Button button  = (Button) findViewById(R.id.getPatientData);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (patientIdIsValid()) {
                    initializePatientValues();
                    setDefaultScreenVisibility(View.GONE);
                    setResultScreenVisibility(View.VISIBLE);
                }
            }
        });
    }

    // gets all patient ids and lets autocomplete text view complete users input
    private void enableAutocomplete() {
        List<PatientRecord> records = PatientOps.getAllPatientRecords(dataSource.database);
        List<Long> tmpList = new ArrayList<>();

        for (int i = 0; i < records.size(); i++) {
            tmpList.add(records.get(i).getHospitalId());
        }

        Collections.sort(tmpList);

        ArrayAdapter<Long> adapter = new ArrayAdapter<>(this, R.layout.dropdownlist, tmpList);
        AutoCompleteTextView entryList = (AutoCompleteTextView) findViewById(R.id.PatientID);
        entryList.setAdapter(adapter);
    }

    // checks if patientId is valid
    private boolean patientIdIsValid(){
        List<PatientRecord> tmpList = PatientOps.getAllPatientRecords(dataSource.database);
        for (int i = 0; i < tmpList.size(); i++){
            if (getPatientId().equals(String.valueOf(tmpList.get(i).getHospitalId()))){
                return true;
            }
        }
        Toast.makeText(this, "Id is not valid.", Toast.LENGTH_SHORT).show();
        return false;
    }

    // sets patient id, blood counts, diseases and medication
    // prints when all is set
    private void initializePatientValues(){
        setPatientId(getPatientId());
        List<Integer> bloodCountIds = getBloodCountIds(getDatabasePatientId());
        print(getBloodCounts(bloodCountIds));
    }

    // reads entry and saves data in string
    @NonNull
    private String getPatientId() {
        AutoCompleteTextView entryField = (AutoCompleteTextView) findViewById(R.id.PatientID);
        return entryField.getText().toString();
    }

    // sets patient id in result screen
    private void setPatientId(String patientId){
        TextView view = (TextView) findViewById(R.id.PatientIDValue);
        view.setText(patientId);
    }

    // gets primary key id from patient based on individual id
    private int getDatabasePatientId(){
        List<PatientRecord> tmpList = PatientOps.getAllPatientRecords(dataSource.database);

        for (int i = 0; i < tmpList.size(); i++){
            if (String.valueOf(tmpList.get(i).getHospitalId()).equals(getPatientId())){
                return tmpList.get(i).getId();
            }
        }
        return -1;
    }

    // gets primary key id from blood counts based on primary key patient id
    private List<Integer> getBloodCountIds(int patientId){
        List<Integer> idList = new ArrayList<>();
        List<PatientBloodcountRecord> tmpList = PatientBloodCountOps.getAllPatientBloodcountRecord(dataSource.database);

        for (int i = 0; i < tmpList.size(); i++){
            if (tmpList.get(i).getPatientId() == patientId){
                idList.add(tmpList.get(i).getBloodcountId());
            }
        }
        return idList;
    }

    // gets blood counts based on primary key id from blood count
    private List<BloodCountRecord> getBloodCounts (List<Integer> bloodCountIds){
        List<BloodCountRecord> bloodCountRecordList = new ArrayList<>();
        List<BloodCountRecord> tmpList = BloodCountOps.getAllBloodCountRecords(dataSource.database);

        for (int i = 0; i < bloodCountIds.size(); i++){
            for (int j = 0; j < tmpList.size(); j++){
                if (bloodCountIds.get(i) == tmpList.get(j).getId()) {
                    bloodCountRecordList.add(tmpList.get(j));
                }
            }
        }

        return bloodCountRecordList;
    }

    // prints defaults and extras
    private void print(List<BloodCountRecord> list){
        boolean firstRun = true;

        for (int i = 0; i < list.size(); i++) {
            if (firstRun) {
                setTimestamp(list.get(i));
                setErythrocyte(list.get(i));
                setLeukocyte(list.get(i));
                setHemoglobin(list.get(i));
                setHematocrit(list.get(i));
                setMcv(list.get(i));
                setMch(list.get(i));
                setMchc(list.get(i));
                setPlatelet(list.get(i));
                setReticulocytes(list.get(i));
                setMpv(list.get(i));
                setRdw(list.get(i));

                setDiseases(getDiseases(getDiseasesId(list.get(i))));
                setMedication(getMedication(getMedicationId(list.get(i))));

                createDivider();

                firstRun = false;
            }
            else {
                addExtraDate(list.get(i));
                addExtraDisease(getDiseases(getDiseasesId(list.get(i))));
                addExtraMedication(getMedication(getMedicationId(list.get(i))));
                addExtraBloodCount(list.get(i));
                createDivider();
            }

        }
    }

    // gets primary key id from disease based on blood count id
    private List<BloodCountDiseaseRecord> getDiseasesId(BloodCountRecord bloodCountRecord){
        List<BloodCountDiseaseRecord> tmpList = BloodCountDiseaseOps.getAllBloodCountDiseaseRecord(dataSource.database);
        List<BloodCountDiseaseRecord> returnList = new ArrayList<>();

        for (int i = 0; i < tmpList.size(); i++){
            if (tmpList.get(i).getBloodCountId() == bloodCountRecord.getId()){
                returnList.add(tmpList.get(i));
            }
        }

        return returnList;
    }

    // gets diseases based on primary key id from diseases
    private List<DiseaseRecord> getDiseases(List<BloodCountDiseaseRecord> bloodCountDiseaseRecords){
        List<DiseaseRecord> tmpList = DiseaseOps.getAllDiseaseRecords(dataSource.database);
        List<DiseaseRecord> returnList = new ArrayList<>();

        for (int i = 0; i < bloodCountDiseaseRecords.size(); i++){
            for (int j = 0; j < tmpList.size(); j++){
                if (bloodCountDiseaseRecords.get(i).getDiseaseId() == tmpList.get(j).getId()){
                    returnList.add(tmpList.get(j));
                }
            }
        }

        return returnList;
    }

    // concatenates several different diseases to one string and prints String
    private void setDiseases(List<DiseaseRecord> diseaseRecords){
        String diseases = "";

        for (int i = 0; i < diseaseRecords.size(); i++){
            diseases += diseaseRecords.get(i).getName() + "\n";
        }

        TextView view = (TextView) findViewById(R.id.diseaseValue);
        view.setText(diseases);
    }

    // gets primary key id from medication depending on blood count id
    private List<BloodCountMedicationRecord> getMedicationId(BloodCountRecord bloodCountRecord){
        List<BloodCountMedicationRecord> tmpList = BloodCountMedicationOps.getAllBloodCountMedicationRecord(dataSource.database);
        List<BloodCountMedicationRecord> returnList  = new ArrayList<>();

        for (int i = 0; i < tmpList.size(); i++){
            if (bloodCountRecord.getId() == tmpList.get(i).getId()){
                returnList.add(tmpList.get(i));
            }
        }

        return returnList;
    }

    // gets medication linked to blood count through relational database
    private List<MedicationRecord> getMedication(List<BloodCountMedicationRecord> bloodCountMedicationRecords){
        List<MedicationRecord> tmpList = MedicationOps.getAllMedicationRecords(dataSource.database);
        List<MedicationRecord> returnList = new ArrayList<>();

        for (int i = 0; i < bloodCountMedicationRecords.size(); i++){
            for (int j = 0; j < tmpList.size(); j++){
                if (bloodCountMedicationRecords.get(i).getMedicationId() == tmpList.get(j).getId()){
                    returnList.add(tmpList.get(j));
                }
            }
        }

        return returnList;
    }

    // concatenates medication to one string and prints string
    private void setMedication(List<MedicationRecord> medicationRecords){
        String medication = "";

        for (int i = 0; i < medicationRecords.size(); i++){
            medication += medicationRecords.get(i).getDrugName() + "\n";
        }

        TextView view = (TextView) findViewById(R.id.patientRecommendedMedication);
        view.setText(medication);
    }

    // print blood count details
    private void setTimestamp(BloodCountRecord record){
        TextView view = (TextView) findViewById(R.id.timestampValue);
        view.setText(record.getTimestamp());
    }

    private void setErythrocyte(BloodCountRecord record){
        TextView view = (TextView) findViewById(R.id.erythrocyteValue);
        view.setText(String.valueOf(record.getErythrocyte()));
    }

    private void setLeukocyte(BloodCountRecord record){
        TextView view = (TextView) findViewById(R.id.leukocyteValue);
        view.setText(String.valueOf(record.getLeukocyte()));
    }

    private void setHemoglobin(BloodCountRecord record){
        TextView view = (TextView) findViewById(R.id.hemoglobinValue);
        view.setText(String.valueOf(record.getHemoglobin()));
    }

    private void setHematocrit(BloodCountRecord record){
        TextView view = (TextView) findViewById(R.id.hematocritValue);
        view.setText(String.valueOf(record.getHematocrit()));
    }

    private void setMcv(BloodCountRecord record){
        TextView view = (TextView) findViewById(R.id.mcvValue);
        view.setText(String.valueOf(record.getMcv()));
    }

    private void setMch(BloodCountRecord record){
        TextView view = (TextView) findViewById(R.id.mchValue);
        view.setText(String.valueOf(record.getMch()));
    }

    private void setMchc(BloodCountRecord record){
        TextView view = (TextView) findViewById(R.id.mchcValue);
        view.setText(String.valueOf(record.getMchc()));
    }

    private void setPlatelet(BloodCountRecord record){
        TextView view = (TextView) findViewById(R.id.plateletValue);
        view.setText(String.valueOf(record.getPlatelet()));
    }

    private void setReticulocytes(BloodCountRecord record){
        TextView view = (TextView) findViewById(R.id.reticulocytesValue);
        view.setText(String.valueOf(record.getReticulocytes()));
    }

    private void setMpv(BloodCountRecord record){
        TextView view =(TextView) findViewById(R.id.mpvValue);
        view.setText(String.valueOf(record.getMpv()));
    }

    private void setRdw(BloodCountRecord record){
        TextView view = (TextView) findViewById(R.id.rdwValue);
        view.setText(String.valueOf(record.getRdw()));
    }

    // if more than one blood count is found, it will create an extra blood count field
    private void addExtraBloodCount(BloodCountRecord bloodCountRecord){
        LinearLayout linearLayout= (LinearLayout) findViewById(R.id.patientResultLinearLayout);

        linearLayout.addView(createLinearLayout(createTextView(getString(R.string.leukocyte)), createTextView(String.valueOf(bloodCountRecord.getLeukocyte()))));
        linearLayout.addView(createLinearLayout(createTextView(getString(R.string.erythrocyte)), createTextView(String.valueOf(bloodCountRecord.getErythrocyte()))));
        linearLayout.addView(createLinearLayout(createTextView(getString(R.string.hemoglobin)), createTextView(String.valueOf(bloodCountRecord.getHemoglobin()))));
        linearLayout.addView(createLinearLayout(createTextView(getString(R.string.hematocrit)), createTextView(String.valueOf(bloodCountRecord.getHematocrit()))));
        linearLayout.addView(createLinearLayout(createTextView(getString(R.string.mcv)), createTextView(String.valueOf(bloodCountRecord.getMcv()))));
        linearLayout.addView(createLinearLayout(createTextView(getString(R.string.MCH)), createTextView(String.valueOf(bloodCountRecord.getMch()))));
        linearLayout.addView(createLinearLayout(createTextView(getString(R.string.mchc)), createTextView(String.valueOf(bloodCountRecord.getMchc()))));
        linearLayout.addView(createLinearLayout(createTextView(getString(R.string.platelet)), createTextView(String.valueOf(bloodCountRecord.getPlatelet()))));
        linearLayout.addView(createLinearLayout(createTextView(getString(R.string.reticulocytes)), createTextView(String.valueOf(bloodCountRecord.getReticulocytes()))));
        linearLayout.addView(createLinearLayout(createTextView(getString(R.string.mpv)), createTextView(String.valueOf(bloodCountRecord.getMpv()))));
        linearLayout.addView(createLinearLayout(createTextView(getString(R.string.rdw)), createTextView(String.valueOf(bloodCountRecord.getRdw()))));
    }

    //if more than one blood count is found, it will create an extra data field
    private void addExtraDate(BloodCountRecord bloodCountRecord){
        LinearLayout linearLayout= (LinearLayout) findViewById(R.id.patientResultLinearLayout);
        linearLayout.addView(createLinearLayout(createTextView(getString(R.string.time)), createTextView(bloodCountRecord.getTimestamp())));
    }

    // if more than one blood count is found, it will create an extra disease field
    private void addExtraDisease(List<DiseaseRecord> diseaseRecords){
        String diseases = "";
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.patientResultLinearLayout);

        if (diseaseRecords.size() != 0) {

            for (int i = 0; i < diseaseRecords.size(); i++) {
                diseases += diseaseRecords.get(i).getName() + "\n";
            }

            linearLayout.addView(createLinearLayout(createTextView(getString(R.string.disease)), createTextView(diseases)));
        }
        else linearLayout.addView(createLinearLayout(createTextView(getString(R.string.disease)), createTextView("No Disease found.")));
    }

    // if more than one blood count is found, it will create an extra medication field
    private void addExtraMedication(List<MedicationRecord> medicationRecords){
        String medication = "";
        LinearLayout linearLayout= (LinearLayout) findViewById(R.id.patientResultLinearLayout);

        for (int i = 0; i < medicationRecords.size(); i++){
            medication += medicationRecords.get(i).getDrugName() + "\n";
        }

        linearLayout.addView(createLinearLayout(createTextView(getString(R.string.recommendedMedication)), createTextView(medication)));
    }

    // creates a linear layout which takes two text views as parameters
    private LinearLayout createLinearLayout(TextView view1, TextView view2){
        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        linearLayout.setLayoutParams(layoutParams);
        linearLayout.addView(view1);
        linearLayout.addView(view2);
        linearLayout.setPadding(convertToDp(16),convertToDp(16),convertToDp(16),convertToDp(16));

        return linearLayout;
    }

    // creates a text view
    private TextView createTextView(String text){
        TextView view = new TextView(this);

        view.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        view.setGravity(CENTER_HORIZONTAL);
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        view.setText(text);
        view.setTextColor(ContextCompat.getColor(this, R.color.textColor));

        return view;
    }

    // creates a divider so patient file is better readable
    private void createDivider() {
        LinearLayout printArea = (LinearLayout) findViewById(R.id.patientResultLinearLayout);
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, convertToDp(1)));
        linearLayout.setBackgroundColor(Color.parseColor("#000000"));
        printArea.addView(linearLayout);
    }

    // converts dp to actual pixels
    private int convertToDp(float sizeInDp){
        float scale = getResources().getDisplayMetrics().density;
        return (int) (sizeInDp*scale + 0.5f);
    }
}
