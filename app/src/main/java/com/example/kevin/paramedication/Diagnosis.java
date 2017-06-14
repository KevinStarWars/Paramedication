package com.example.kevin.paramedication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.TextView;

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
import com.example.kevin.paramedication.DatabaseOperations.MedicationInteractionOperations;
import com.example.kevin.paramedication.DatabaseOperations.MedicationOperations;
import com.example.kevin.paramedication.DatabaseOperations.PatientBloodcountOperations;
import com.example.kevin.paramedication.DatabaseOperations.PatientDiseaseOperations;
import com.example.kevin.paramedication.DatabaseOperations.PatientMedicationOperations;
import com.example.kevin.paramedication.DatabaseOperations.PatientOperations;

import java.util.ArrayList;
import java.util.List;

public class Diagnosis extends AppCompatActivity {

    public final static String LOG_TAG = Diagnosis.class.getSimpleName();

    PatientRecord patRecord = new PatientRecord();
    BloodCountRecord patBloodRecord = new BloodCountRecord();
    BloodRecord normalValues = new BloodRecord();

    int numberOfEntries = 1;
    List<Integer> entryList = new ArrayList<>();

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
        setContentView(R.layout.activity_diagnosis);

        entryList.add(R.id.drug1);

        dataSource = new DbDataSource(this);
        Log.d(LOG_TAG, "Opening database.");
        dataSource.open();

        // dummy entry
        PatientOps.createPatientRecord("117", "m", dataSource.database);
        BloodCountOps.createBloodCountRecord("1","2","3","4","5","6","7","8","9","10","11", dataSource.database);
        DiseaseOps.createDiseaseRecord("Fieber", dataSource.database); //ID 1
        DiseaseOps.createDiseaseRecord("Kopfschmerzen", dataSource.database); //ID2
        MedicationOps.createMedicationRecord("Aspirin", dataSource.database);
        MedicationOps.createMedicationRecord("Ibuprofen", dataSource.database);
        PatientBloodcountOps.createPatientBloodcountRecord(1,1, dataSource.database);
        PatientDiseaseOps.createPatientDiseaseRecord(1,1,dataSource.database);
        PatientDiseaseOps.createPatientDiseaseRecord( 1,2,dataSource.database);
        PatientMedicationOps.createPatientMedicationRecord( 1,1,dataSource.database);
        PatientMedicationOps.createPatientMedicationRecord(1,2,dataSource.database);

        initializeTabs();

        initializeMedicationButton();
        initializeRadiobuttons();
        setOnClickListenerForDiagnosis();
    }

    /*_____________________________________General______________________________________________*/

    // initializes Tabs
    public void initializeTabs(){
        TabHost host = (TabHost)findViewById(R.id.tabHost);
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

    // Send intent in order to open Database
    public void changeToDatabase(View view){
        Intent myIntent = new Intent(this, Database.class);
        this.startActivity(myIntent);
    }

    // Send intent in order to open Medication
    public void changeToMedication(View view){
        Intent myIntent = new Intent(this, Medication.class);
        this.startActivity(myIntent);
    }

    // Send intent in order to open Info
    public void changeToInfo(View view){
        Intent myIntent = new Intent(this, Info.class);
        this.startActivity(myIntent);
    }

    public void changeToPatients(View view){
        Intent myIntent = new Intent(this, Patients.class);
        this.startActivity(myIntent);
    }

    // disables both radiobuttons being checked at the same time
    private void initializeRadiobuttons(){
        final RadioButton femaleButton = (RadioButton) findViewById(R.id.femaleButton);
        final RadioButton maleButton = (RadioButton) findViewById(R.id.maleButton);

        femaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (maleButton.isChecked()){
                    maleButton.setChecked(false);
                }
            }
        });

        maleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (femaleButton.isChecked()){
                    femaleButton.setChecked(false);
                }
            }
        });
    }

    /*_____________________________________Blood count__________________________________________*/

    private void initializeBloodCount(SQLiteDatabase database){
        patBloodRecord = pushBloodCountToDatabase(getLeukocyteVal(), getErythrocyteVal(), getHemoglobinVal(), getHematocritVal(), getMcvVal(),
                getMchVal(), getMchcVal(), getPlateletVal(), getReticulocytesVal(), getMpvVal(), getRdwVal(), database);
    }

    private BloodCountRecord pushBloodCountToDatabase(String leukocyte, String erythrocyte, String hemoglobin, String hematocrit,
                                          String mcv, String mch, String mchc, String platelet, String reticulocytes, String mpv,
                                          String rdw, SQLiteDatabase database){

        return BloodCountOps.createBloodCountRecord(leukocyte, erythrocyte, hemoglobin, hematocrit, mcv, mch, mchc, platelet,reticulocytes, mpv, rdw, database);
    }

    @NonNull
    private String getLeukocyteVal(){
        EditText entry = (EditText) findViewById(R.id.leukocyteVal);
        if (entry.getText().toString().isEmpty()){return "0";}
        else{return entry.getText().toString();}
    }

    @NonNull
    private String getErythrocyteVal(){
        EditText entry = (EditText) findViewById(R.id.erythrocyteVal);
        if (entry.getText().toString().isEmpty()){return "0";}
        else return entry.getText().toString();
    }

    @NonNull
    private String getHemoglobinVal(){
        EditText entry = (EditText) findViewById(R.id.hemoglobinVal);
        if (entry.getText().toString().isEmpty()) {
            return "0";
        }
        else return entry.getText().toString();
    }

    @NonNull
    private String getHematocritVal(){
        EditText entry = (EditText) findViewById(R.id.hematocritVal);
        if (entry.getText().toString().isEmpty()){return "0";}
        else return entry.getText().toString();
    }

    @NonNull
    private String getMcvVal(){
        EditText entry = (EditText) findViewById(R.id.mcvVal);
        if (entry.getText().toString().isEmpty()){return "0";}
        else {
            return entry.getText().toString();
        }
    }

    @NonNull
    private String getMchVal(){
        EditText entry = (EditText) findViewById(R.id.mchVal);
        if (entry.getText().toString().isEmpty()){return "0";}
        else return entry.getText().toString();
    }

    @NonNull
    private String getMchcVal(){
        EditText entry = (EditText) findViewById(R.id.mchcVal);
        if (entry.getText().toString().isEmpty()){return "0";}
        else return entry.getText().toString();
    }

    @NonNull
    private String getPlateletVal(){
        EditText entry = (EditText) findViewById(R.id.plateletVal);
        if (entry.getText().toString().isEmpty()){return "0";}
        else return entry.getText().toString();
    }

    @NonNull
    private String getReticulocytesVal(){
        EditText entry = (EditText) findViewById(R.id.reticulocytesVal);
        if (entry.getText().toString().isEmpty()){return "0";}
        else return entry.getText().toString();
    }

    @NonNull
    private String getMpvVal(){
        EditText entry = (EditText) findViewById(R.id.mpvVal);
        if (entry.getText().toString().isEmpty()){return "0";}
        else return entry.getText().toString();
    }

    @NonNull
    private String getRdwVal(){
        EditText entry = (EditText) findViewById(R.id.rdwVal);
        if (entry.getText().toString().isEmpty()){return "0";}
        else return entry.getText().toString();
    }

    /*_____________________________________Current Medication___________________________________*/

    // creates linear layout with TextView next to EditText
    public LinearLayout createNewLinearLayout(TextView view, EditText entry){
        final LinearLayout linear = new LinearLayout(this);
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linear.setLayoutParams(layoutParams);
        linear.setOrientation(LinearLayout.HORIZONTAL);
        linear.setPadding(8,8,8,8);
        linear.addView(view);
        linear.addView(entry);
        return linear;
    }

    // creates new editText field with hint as parameter
    public EditText createNewEntry(String hint){
        final EditText entry = new EditText(this);
        final LinearLayout.LayoutParams entryViewParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        entryViewParams.setMargins(8,8,8,8);
        entry.setLayoutParams(entryViewParams);
        entry.setHint(hint);
        entry.setId(View.generateViewId());
        System.out.println(entry.getId());
        entryList.add(entry.getId());
        for (int i = 0; i < entryList.size(); i++){
            System.out.println(entryList.get(i));
        }
        return entry;
    }

    // creates new button with text as parameter
    public Button createNewButton(String text){
        final Button button = new Button(this);
        final LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonParams.setMargins(16,16,16,16);
        button.setLayoutParams(buttonParams);
        button.setText(text);
        button.setBackgroundColor(getResources().getColor(R.color.backgroundRed));
        button.setGravity(Gravity.CENTER_HORIZONTAL);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((ViewGroup)v.getParent()).removeView(v);
                LinearLayout linearLayout = (LinearLayout)findViewById(R.id.drugs);
                linearLayout.addView(createNewLinearLayout(createNewTextView("Drug"), createNewEntry("Drug")));
                linearLayout.addView(createNewButton("Add more"));
            }
        });
        return button;
    }

    // creates new textview with text as parameter
    public TextView createNewTextView(String text){
        final TextView textView = new TextView(this);
        final LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        textViewParams.setMargins(8,8,8,8);
        textView.setLayoutParams(textViewParams);
        textView.setPadding(8,8,8,8);
        textView.setTextSize(15);
        textView.setText(text);
        return textView;
    }

    private void initializeMedicationButton(){
        Button button = (Button) findViewById(R.id.addButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                numberOfEntries++;
                ((ViewGroup)v.getParent()).removeView(v);
                LinearLayout linearLayout = (LinearLayout)findViewById(R.id.drugs);
                linearLayout.addView(createNewLinearLayout(createNewTextView("Drug"), createNewEntry("drug")));
                linearLayout.addView(createNewButton("Add more"));
            }
        });
    }

    /*_____________________________________Result______________________________________________*/

    public void setOnClickListenerForDiagnosis(){
        Button resultButton = (Button)  findViewById(R.id.getResult);
        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeBloodCount(dataSource.database);
                normalValues.initializeWithObject(getNormalValues(dataSource.database));
                insertPatient(dataSource.database);
                createPatientBloodCountEntry(dataSource.database);
                print(compareToDiseases(dataSource.database));
            }
        });
    }

    private boolean isFemale(){
        RadioButton gender = (RadioButton) findViewById(R.id.femaleButton);
        return gender.isChecked();
    }

    private BloodRecord getNormalValues(SQLiteDatabase database){
        if (isFemale()) {
            return BloodOps.getBloodRecordById(1, database); // 1 = femnale normal values
        }
        else {
            return BloodOps.getBloodRecordById(2, database); // 2 = male normal values
        }
    }

    private void insertPatient(SQLiteDatabase database){
        EditText patientField = (EditText) findViewById(R.id.hospitalId);
        if (isFemale()){patRecord = PatientOps.createPatientRecord(patientField.getText().toString(), "f", database);}
        else {patRecord = PatientOps.createPatientRecord(patientField.getText().toString(), "m", database);}
    }

    private void createPatientBloodCountEntry(SQLiteDatabase database){
        if (patRecord.getId() !=  -1) {
            if (patBloodRecord.getId() != -1) {
                PatientBloodcountOps.createPatientBloodcountRecord(patRecord.getId(), patBloodRecord.getId(), database);
            }
        }
    }

    private List<DiseaseRecord> compareToDiseases(SQLiteDatabase database){
        List<DiseaseRecord> diseaseRecords = new ArrayList<>();
        List<DiseaseRecord> diseaseDatabase = DiseaseOps.getAllDiseaseRecords(database);
        List<BloodRecord> bloodDatabase = BloodOps.getAllBloodRecords(database);
        List<DiseaseBloodRelationRecord> diseaseBloodDatabase = DiseaseBloodOps.getAllDiseaseBloodRelationRecord(database);
        for (int i = 0; i < diseaseBloodDatabase.size(); i++){
            for (int j = 0; j < diseaseDatabase.size(); j++){
                for (int k = 0; k < bloodDatabase.size(); k++){
                    if (diseaseBloodDatabase.get(i).getDiseaseId() == diseaseDatabase.get(j).getId() && diseaseBloodDatabase.get(i).getBloodId() == bloodDatabase.get(k).getId()){
                        if (bloodDatabase.get(k).getLeukocyteMin() < patBloodRecord.getLeukocyte() && bloodDatabase.get(k).getLeukocyteMax() > patBloodRecord.getLeukocyte()){
                            if (bloodDatabase.get(k).getErythrocyteMin() < patBloodRecord.getErythrocyte() && bloodDatabase.get(k).getErythrocyteMax() > patBloodRecord.getErythrocyte()){
                                if (bloodDatabase.get(k).getHemoglobinMin() < patBloodRecord.getHemoglobin() && bloodDatabase.get(k).getHemoglobinMax() > patBloodRecord.getHemoglobin()){
                                    if (bloodDatabase.get(k).getHematocritMin() < patBloodRecord.getHematocrit() && bloodDatabase.get(k).getHematocritMax() > patBloodRecord.getHematocrit()){
                                        if (bloodDatabase.get(k).getMcvMin() < patBloodRecord.getMcv() && bloodDatabase.get(k).getMcvMax() > patBloodRecord.getMcv()){
                                            if (bloodDatabase.get(k).getMchMin() < patBloodRecord.getMch() && bloodDatabase.get(k).getMchMax() > patBloodRecord.getMch()){
                                                if (bloodDatabase.get(k).getMchcMin() < patBloodRecord.getMchc() && bloodDatabase.get(k).getMchcMax() > patBloodRecord.getMchc()){
                                                    if (bloodDatabase.get(k).getPlateletMin() < patBloodRecord.getPlatelet() && bloodDatabase.get(k).getPlateletMax() > patBloodRecord.getPlatelet()){
                                                        if (bloodDatabase.get(k).getReticulocytesMin() < patBloodRecord.getReticulocytes() && bloodDatabase.get(k).getReticulocytesMax() > patBloodRecord.getReticulocytes()){
                                                            if (bloodDatabase.get(k).getMpvMin() < patBloodRecord.getMpv() && bloodDatabase.get(k).getMpvMax() > patBloodRecord.getMpv()){
                                                                diseaseRecords.add(diseaseDatabase.get(j));
                                                                PatientDiseaseOps.createPatientDiseaseRecord(patRecord.getId(), diseaseDatabase.get(j).getId(), database);
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
        return diseaseRecords;
    }

    private void print(List<DiseaseRecord> list) {


        LinearLayout printArea = (LinearLayout) findViewById(R.id.printAreaDiagnosis);
        printArea.removeAllViews();


        createUpdateButton(printArea);
        printPatientID(printArea);
        printDisease(printArea, list);
        printRecommendedMedication(printArea, list);
        printCurrentMedication(printArea);
        printBloodCount(printArea);

    }

    private void createUpdateButton(LinearLayout printArea){
        Button view = new Button(this);
        view.setText("Update");

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(24,24,24,24);
        view.setLayoutParams(params);

        view.setTextSize(15);
        view.setBackgroundColor(Color.parseColor("#FFAEAE"));
        view.setTextColor(Color.parseColor("#47525E"));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeBloodCount(dataSource.database);
                normalValues.initializeWithObject(getNormalValues(dataSource.database));
                insertPatient(dataSource.database);
                createPatientBloodCountEntry(dataSource.database);
                print(compareToDiseases(dataSource.database));
            }
        });
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setPadding(16,16,16,16);

        printArea.addView(linearLayout);
        linearLayout.addView(view);
    }

    private void printPatientID (LinearLayout printArea){
        printArea.addView(createLinearLayout(createTextView("Patient ID: "), createTextView("" + patRecord.getHospitalId())));
        createDivider(printArea);
    }

    private void printDisease(LinearLayout printArea, List<DiseaseRecord> list){

        boolean firstRun = true;

        if (!list.isEmpty()) {

            for (int i = 0; i < list.size(); i++){
                if (firstRun){
                    printArea.addView(createLinearLayout(createTextView("Related to: "), createTextView(list.get(i).getName())));
                    firstRun = false;
                }
                else {
                    printArea.addView(createLinearLayout(createTextView(""), createTextView(list.get(i).getName())));
                }
            }
            createDivider(printArea);
        }
        else {
            printArea.addView(createLinearLayout(createTextView("Related to: "), createTextView("No relation found")));
            createDivider(printArea);
        }
    }

    private void printCurrentMedication(LinearLayout printArea){

        boolean firstRun = true;

        EditText drugField = (EditText) findViewById(R.id.drug1);

        if ((entryList.size() == 1) && (drugField.getText().toString().equals(""))){
            printArea.addView(createLinearLayout(createTextView("Current Medication: "), createTextView("No current medication found")));
            createDivider(printArea);
        }
        else {
            for (int i = 0; i < entryList.size(); i++){
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

    private void printRecommendedMedication(LinearLayout printArea, List<DiseaseRecord> list){

        boolean firstRun = true;

        List<MedicationRecord> recommendedMedication = new ArrayList<>();

        List<MedicationRecord> medicationList = MedicationOps.getAllMedicationRecords(dataSource.database);
        List<DiseaseMedicationRelationRecord> diseaseMedicationList = DiseaseMedicationOps.getAllDiseaseMedicationRelationRecord(dataSource.database);

        if (list.isEmpty()){
            printArea.addView(createLinearLayout(createTextView("Recommended Medication: "), createTextView("No recommendation")));

            createDivider(printArea);
        }
        else {
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < medicationList.size(); j++){
                    for (int k = 0; k < diseaseMedicationList.size(); k++){
                        if (list.get(i).getId() == diseaseMedicationList.get(k).getDiseaseId() && medicationList.get(j).getId() == diseaseMedicationList.get(k).getDrugId()){
                            recommendedMedication.add(medicationList.get(j));
                        }
                    }
                }
            }
            for (int i = 0; i < recommendedMedication.size(); i++){
                if (firstRun){
                    firstRun = false;

                    printArea.addView(createLinearLayout(createTextView("Recommended Medication: "), createTextView(recommendedMedication.get(i).getDrugName())));
                }
                else{
                    printArea.addView(createLinearLayout(createTextView(""), createTextView(recommendedMedication.get(i).getDrugName())));
                }
            }
            createDivider(printArea);
        }
    }

    private void printBloodCount(LinearLayout printArea){
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
        createDivider(printArea);
    }

    private LinearLayout createLinearLayout(TextView view1, TextView view2){
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setPadding(16,16,16,16);
        linearLayout.addView(view1);
        linearLayout.addView(view2);
        return linearLayout;
    }

    private TextView createTextView(String text){
        TextView view = new TextView(this);
        view.setText(text);
        view.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        view.setPadding(16, 16, 16, 16);
        view.setTextSize(15);
        view.setTextColor(Color.parseColor("#47525E"));
        view.setGravity(Gravity.CENTER_HORIZONTAL);
        return view;
    }

    private void createDivider(LinearLayout printArea){
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1));
        linearLayout.setBackgroundColor(Color.parseColor("#000000"));
        printArea.addView(linearLayout);
    }

}