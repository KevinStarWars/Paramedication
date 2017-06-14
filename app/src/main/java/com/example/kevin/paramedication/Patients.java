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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevin.paramedication.DatabaseObjects.BloodCountRecord;
import com.example.kevin.paramedication.DatabaseObjects.DiseaseRecord;
import com.example.kevin.paramedication.DatabaseObjects.MedicationRecord;
import com.example.kevin.paramedication.DatabaseObjects.PatientBloodcountRecord;
import com.example.kevin.paramedication.DatabaseObjects.PatientDiseaseRecord;
import com.example.kevin.paramedication.DatabaseObjects.PatientMedicationRecord;
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

public class Patients extends AppCompatActivity {

    public final static String LOG_TAG = Patients.class.getSimpleName();

    private List<Integer> idList = new ArrayList<>();

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
        setContentView(R.layout.activity_patients);

        dataSource = new DbDataSource(this);
        Log.d(LOG_TAG, "Opening database.");
        dataSource.open();


        initializeHomeScreen();
    }

    // initializes home screen
    private void initializeHomeScreen(){

        enableAutocomplete();

        Button button = (Button) findViewById(R.id.getPatientData);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PatientRecord record = searchForPatient(getPatientIDFromView());
                if (record.getId() == -1){
                    Toast.makeText(getApplicationContext(), "invalid id", Toast.LENGTH_LONG).show();
                }
                else{
                    LinearLayout printArea = (LinearLayout) findViewById(R.id.printArea);
                    printArea.removeAllViews();
                    createTextView("Patient ID: ", "" + record.getHospitalId());
                    createDivider();
                    idList = searchForRelation(record.getId());
                    printBloodCount(getPatientBloodcount(idList));
                    printDisease(getPatientDisease(idList));
                    printMedication(getPatientMedication(idList));
                }
            }
        });
    }


    private void enableAutocomplete(){
        List<PatientRecord> records = PatientOps.getAllPatientRecords(dataSource.database);
        List<String> tmpList = new ArrayList<>();
        for (int i = 0; i < records.size(); i++){
            tmpList.add("" + records.get(i).getHospitalId());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdownlist, tmpList);
        AutoCompleteTextView entryList = (AutoCompleteTextView) findViewById(R.id.PatientID);
        entryList.setAdapter(adapter);
    }
    // reads entry and saves data in string
    @NonNull
    private String getPatientIDFromView(){
        AutoCompleteTextView entryField = (AutoCompleteTextView) findViewById(R.id.PatientID);
        return entryField.getText().toString();
    }

    // Send intent in order to open DiagnosisActivity
    public void changeToDiagnosis(View view){
        dataSource.close();
        Intent myIntent = new Intent(this, Diagnosis.class);
        this.startActivity(myIntent);
    }

    // Send intent in order to open MedicationActivity
    public void changeToMedication(View view){
        dataSource.close();
        Intent myIntent = new Intent(this, Medication.class);
        this.startActivity(myIntent);
    }

    // Send intent in order to open InfoActivity
    public void changeToInfo(View view){
        dataSource.close();
        Intent myIntent = new Intent(this, Info.class);
        this.startActivity(myIntent);
    }

    // Send intent in order to open PatientsActivity
    public void changeToDatabase(View view){
        dataSource.close();
        Intent myIntent = new Intent(this, Database.class);
        this.startActivity(myIntent);
    }

    // search for Patient in database
    private PatientRecord searchForPatient(String id){
        if (id.isEmpty()){return new PatientRecord(-1, -1, "male");}
        List<PatientRecord> currentDatabase = PatientOps.getAllPatientRecords(dataSource.database);
        for (int i = 0; i < currentDatabase.size(); i++){
            if (currentDatabase.get(i).getHospitalId() == Long.parseLong(id)){
                return currentDatabase.get(i);
            }
        }
        return new PatientRecord(-1, -1, "male");
    }

    // returns a list which includes all relations found
    // relations are seperated by -1
    private List<Integer> searchForRelation(int patId){
        List<Integer> ids = new ArrayList<>();
        List<PatientBloodcountRecord> currentDatabase = PatientBloodcountOps.getAllPatientBloodcountRecord(dataSource.database);
        for (int i = 0; i < currentDatabase.size(); i++){
            if (currentDatabase.get(i).getPatientId() == patId){
                ids.add(currentDatabase.get(i).getBloodcountId());
            }
        }
        ids.add(-1);
        List<PatientDiseaseRecord> currentPatientDiseaseDatabase = PatientDiseaseOps.getAllPatientDiseaseRecord(dataSource.database);
        for (int i = 0; i < currentPatientDiseaseDatabase.size(); i++){
            if (currentPatientDiseaseDatabase.get(i).getPatientId() == patId){
                ids.add(currentPatientDiseaseDatabase.get(i).getDiseaseId());
            }
        }
        ids.add(-1);
        List<PatientMedicationRecord> currentPatientMedicationDatabase = PatientMedicationOps.getAllPatientMedicationRecord(dataSource.database);
        for (int i = 0; i< currentPatientMedicationDatabase.size(); i++){
            if (currentPatientMedicationDatabase.get(i).getPatientId() == patId){
                ids.add(currentPatientMedicationDatabase.get(i).getMedicationId());
            }
        }
        ids.add(-1);
        return ids;
    }

    private BloodCountRecord getPatientBloodcount(List<Integer> ids){
        List<BloodCountRecord> currentDatabase = BloodCountOps.getAllBloodCountRecords(dataSource.database);
        int j = 0;
        while (ids.get(j) != -1){
            for (int i = 0; i < currentDatabase.size(); i++){
                if (currentDatabase.get(i).getId() == ids.get(j)){
                    ids.remove(0);
                    ids.remove(0);
                    return currentDatabase.get(i);
                }
            }
            j++;
        }
        return new BloodCountRecord(-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1);
    }

    private void printBloodCount(BloodCountRecord record){
        if (record.getId() == -1){
            createTextView("No Blood Count found.", "");
        }
        else {
            createTextView("Leukocyte: ", "" + record.getLeukocyte());
            createTextView("Erythrocyte: ", "" + record.getErythrocyte());
            createTextView("Hemoglobin: ", "" + record.getHemoglobin());
            createTextView("Hematocrit: ", "" + record.getHematocrit());
            createTextView("MCV: ", "" + record.getMcv());
            createTextView("MCH: ", "" + record.getMch());
            createTextView("MCHC: ", "" + record.getMchc());
            createTextView("Platelet: ", "" + record.getPlatelet());
            createTextView("Reticulocytes: ", "" + record.getReticulocytes());
            createTextView("MPV: ", "" + record.getMpv());
            createTextView("RDW: ", "" + record.getRdw());
            createDivider();
        }

    }

    private List<DiseaseRecord> getPatientDisease(List<Integer> ids){
        List<DiseaseRecord> actualDisease = new ArrayList<>();
        List<DiseaseRecord> currentDatabase = DiseaseOps.getAllDiseaseRecords(dataSource.database);

        int j = 0;
        while (ids.get(j) != -1){
            for (int i = 0; i < currentDatabase.size(); i++){
                if (currentDatabase.get(i).getId() == ids.get(j)){
                    actualDisease.add(currentDatabase.get(i));
                }
            }
            j++;
        }

        while (j >= 0){
            ids.remove(0);
            j--;
        }

        if (actualDisease.size()>0){
            return actualDisease;
        }
        else{
            List<DiseaseRecord> listDisease = new ArrayList<>();
            listDisease.add(new DiseaseRecord(-1, "-1"));
            return listDisease;
        }
    }

    private void printDisease(List<DiseaseRecord> diseases){
        boolean firstRun = true;
        if (diseases.get(0).getId() == -1){
            createTextView("No disease found.","");
        }
        else {
            for (int i = 0; i < diseases.size(); i++){
                if (firstRun){
                    createTextView("Disease: ", diseases.get(i).getName());
                    firstRun = false;
                }
                else{
                    createTextView("", diseases.get(i).getName());
                }
            }

        }
        createDivider();
    }

    private List<MedicationRecord> getPatientMedication(List<Integer> ids){
        List<MedicationRecord> actualMedication = new ArrayList<>();
        List<MedicationRecord> currentDatabase = MedicationOps.getAllMedicationRecords(dataSource.database);

        int j = 0;
        while (ids.get(j) != -1){
            for (int i = 0; i < currentDatabase.size(); i++){
                if (currentDatabase.get(i).getId() == ids.get(j)){
                    actualMedication.add(currentDatabase.get(i));
                }
            }
            j++;
        }

        while (j >= 0){
            ids.remove(0);
            j--;
        }

        if (actualMedication.size()>0){
            return actualMedication;
        }
        else{
            List<MedicationRecord> listMedication = new ArrayList<>();
            listMedication.add(new MedicationRecord(-1, "-1"));
            return listMedication;
        }
    }

    private void printMedication(List<MedicationRecord> drugs){
        boolean firstRun = true;
        if (drugs.get(0).getId() == -1){
            createTextView("No disease found.","");
        }
        else {
            for (int i = 0; i < drugs.size(); i++){
                if (firstRun){
                    createTextView("Medication: ", drugs.get(i).getDrugName());
                    firstRun = false;
                }
                else{
                    createTextView("", drugs.get(i).getDrugName());
                }
            }

        }
    }

    private void createTextView(String text, String data){
        LinearLayout linearLayout = createLinearLayout();
        TextView view = new TextView(this);
        view.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        view.setPadding(16, 16, 16, 16);
        view.setTextSize(15);
        view.setTextColor(Color.parseColor("#47525E"));
        view.setText(text);
        view.setGravity(Gravity.CENTER_HORIZONTAL);
        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        textView.setPadding(16, 16, 16, 16);
        textView.setTextSize(15);
        textView.setTextColor(Color.parseColor("#47525E"));
        textView.setText(data);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        linearLayout.addView(view);
        linearLayout.addView(textView);
    }

    private LinearLayout createLinearLayout(){
        LinearLayout printArea = (LinearLayout) findViewById(R.id.editableSpace);
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setPadding(16,16,16,16);
        printArea.addView(linearLayout);
        return linearLayout;
    }

    private void createDivider(){
        LinearLayout printArea = (LinearLayout) findViewById(R.id.editableSpace);
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1));
        linearLayout.setBackgroundColor(Color.parseColor("#000000"));
        printArea.addView(linearLayout);
    }
}
