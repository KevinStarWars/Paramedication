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
import com.example.kevin.paramedication.DatabaseOperations.DbDataSource;
import com.example.kevin.paramedication.DatabaseOperations.DiseaseOperations;
import com.example.kevin.paramedication.DatabaseOperations.MedicationOperations;
import com.example.kevin.paramedication.DatabaseOperations.PatientBloodCountOperations;
import com.example.kevin.paramedication.DatabaseOperations.PatientDiseaseOperations;
import com.example.kevin.paramedication.DatabaseOperations.PatientMedicationOperations;
import com.example.kevin.paramedication.DatabaseOperations.PatientOperations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Patients extends AppCompatActivity {

    public final static String LOG_TAG = Patients.class.getSimpleName();

    private DbDataSource dataSource;
    private BloodCountOperations BloodCountOps = new BloodCountOperations();
    private DiseaseOperations DiseaseOps = new DiseaseOperations();
    private MedicationOperations MedicationOps = new MedicationOperations();
    private PatientBloodCountOperations PatientBloodcountOps = new PatientBloodCountOperations();
    private PatientDiseaseOperations PatientDiseaseOps = new PatientDiseaseOperations();
    private PatientMedicationOperations PatientMedicationOps = new PatientMedicationOperations();
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
    private void initializeHomeScreen() {

        enableAutocomplete();
        setOnClickListenerForAutoCompleteTextView();

        Button button = (Button) findViewById(R.id.getPatientData);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                PatientRecord record = searchForPatient(getPatientIDFromView());

                if (record.getId() == -1) {
                    Toast.makeText(getApplicationContext(), "invalid id", Toast.LENGTH_LONG).show();
                } else {

                    List<BloodCountRecord> BloodCountRecordList = getPatientBloodcount();
                    List<DiseaseRecord> DiseaseRecordList = getPatientDisease();
                    List<MedicationRecord> MedicationRecordList = getPatientMedication();

                    LinearLayout printArea = (LinearLayout) findViewById(R.id.printArea);
                    printArea.removeAllViews();

                    createTextView("Patient ID: ", "" + record.getHospitalId());
                    createDivider();
                    printBloodCount(BloodCountRecordList);
                    printDisease(DiseaseRecordList);
                    printMedication(MedicationRecordList);
                }
            }
        });
    }

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

    // reads entry and saves data in string
    @NonNull
    private String getPatientIDFromView() {
        AutoCompleteTextView entryField = (AutoCompleteTextView) findViewById(R.id.PatientID);
        return entryField.getText().toString();
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

    // search for Patient in database
    private PatientRecord searchForPatient(String id) {

        if (id.isEmpty()) {
            return new PatientRecord(-1, -1, "male");
        }

        List<PatientRecord> currentDatabase = PatientOps.getAllPatientRecords(dataSource.database);
        for (int i = 0; i < currentDatabase.size(); i++) {
            if (currentDatabase.get(i).getHospitalId() == Long.parseLong(id)) {
                return currentDatabase.get(i);
            }
        }

        return new PatientRecord(-1, -1, "male");
    }

    private List<BloodCountRecord> getPatientBloodcount() {

        List<BloodCountRecord> relatedBloodCounts = new ArrayList<>();

        long id = Long.parseLong(getPatientIDFromView());

        List<PatientRecord> currentPatients = PatientOps.getAllPatientRecords(dataSource.database);
        List<BloodCountRecord> currentBloodCount = BloodCountOps.getAllBloodCountRecords(dataSource.database);
        List<PatientBloodcountRecord> currentPatientBloodCount = PatientBloodcountOps.getAllPatientBloodcountRecord(dataSource.database);

        for (int i = 0; i < currentPatients.size(); i++) {
            for (int j = 0; j < currentBloodCount.size(); j++) {
                for (int k = 0; k < currentPatientBloodCount.size(); k++) {
                    if (id == currentPatients.get(i).getHospitalId() && currentPatients.get(i).getId() == currentPatientBloodCount.get(k).getPatientId() && currentBloodCount.get(j).getId() == currentPatientBloodCount.get(k).getBloodcountId()) {
                        relatedBloodCounts.add(currentBloodCount.get(j));
                    }
                }
            }
        }

        return relatedBloodCounts;

    }

    private void printBloodCount(List<BloodCountRecord> list) {

        if (list.size() == 0) {
            createTextView("Blood Count:", "No Blood Count Found.");
            createDivider();
        } else {
            for (int i = 0; i < list.size(); i++) {
                createTextView("Leukocyte: ", "" + list.get(i).getLeukocyte());
                createTextView("Erythrocyte: ", "" + list.get(i).getErythrocyte());
                createTextView("Hemoglobin: ", "" + list.get(i).getHemoglobin());
                createTextView("Hematocrit: ", "" + list.get(i).getHematocrit());
                createTextView("MCV: ", "" + list.get(i).getMcv());
                createTextView("MCH: ", "" + list.get(i).getMch());
                createTextView("MCHC: ", "" + list.get(i).getMchc());
                createTextView("Platelet: ", "" + list.get(i).getPlatelet());
                createTextView("Reticulocytes: ", "" + list.get(i).getReticulocytes());
                createTextView("MPV: ", "" + list.get(i).getMpv());
                createTextView("RDW: ", "" + list.get(i).getRdw());
                createDivider();
            }
        }

    }

    private List<DiseaseRecord> getPatientDisease() {

        List<DiseaseRecord> diseaseRecords = new ArrayList<>();

        long id = Long.parseLong(getPatientIDFromView());

        List<PatientRecord> currentPatients = PatientOps.getAllPatientRecords(dataSource.database);
        List<DiseaseRecord> currentDiseases = DiseaseOps.getAllDiseaseRecords(dataSource.database);
        List<PatientDiseaseRecord> currentPatientDiseaseRecords = PatientDiseaseOps.getAllPatientDiseaseRecord(dataSource.database);

        for (int i = 0; i < currentPatients.size(); i++) {
            for (int j = 0; j < currentDiseases.size(); j++) {
                for (int k = 0; k < currentPatientDiseaseRecords.size(); k++) {
                    if (currentPatients.get(i).getHospitalId() == id && currentPatientDiseaseRecords.get(k).getPatientId() == currentPatients.get(i).getId() && currentPatientDiseaseRecords.get(k).getDiseaseId() == currentDiseases.get(j).getId()) {
                        diseaseRecords.add(currentDiseases.get(j));
                    }
                }
            }
        }
        return diseaseRecords;
    }

    private void printDisease(List<DiseaseRecord> diseases) {

        List<String> diseaseNames = new ArrayList<>();

        boolean firstRun = true;

        if (diseases.get(0).getId() == -1) {
            createTextView("Disease: ", "No disease found.");
        } else {
            for (int i = 0; i < diseases.size(); i++) {
                if (firstRun) {
                    createTextView("Disease: ", diseases.get(i).getName());
                    diseaseNames.add(diseases.get(i).getName());
                    firstRun = false;
                } else {
                    boolean existsAlready = false;

                    for (int j = 0; j < diseaseNames.size(); j++) {
                        if (diseaseNames.get(j).equals(diseases.get(i).getName())) {
                            existsAlready = true;
                        }
                    }

                    if (!existsAlready) {
                        createTextView("", diseases.get(i).getName());
                    }
                }
            }

        }
        createDivider();
    }

    private List<MedicationRecord> getPatientMedication() {
        long id = Long.parseLong(getPatientIDFromView());

        List<MedicationRecord> medicationRecordList = new ArrayList<>();

        List<PatientRecord> currentPatients = PatientOps.getAllPatientRecords(dataSource.database);
        List<MedicationRecord> currentMedication = MedicationOps.getAllMedicationRecords(dataSource.database);
        List<PatientMedicationRecord> currentPatientMedication = PatientMedicationOps.getAllPatientMedicationRecord(dataSource.database);

        for (int i = 0; i < currentPatients.size(); i++) {
            for (int j = 0; j < currentMedication.size(); j++) {
                for (int k = 0; k < currentPatientMedication.size(); k++) {
                    if (currentPatients.get(i).getHospitalId() == id && currentPatients.get(i).getId() == currentPatientMedication.get(k).getPatientId() && currentMedication.get(j).getId() == currentPatientMedication.get(k).getMedicationId()) {
                        medicationRecordList.add(currentMedication.get(j));
                    }
                }
            }
        }

        return medicationRecordList;
    }

    private void printMedication(List<MedicationRecord> drugs) {

        boolean firstRun = true;

        if (drugs.size() == 0) {
            createTextView("Medication: ", "No Medication found.");
        } else {
            for (int i = 0; i < drugs.size(); i++) {

                if (firstRun) {
                    createTextView("Medication: ", drugs.get(i).getDrugName());
                    firstRun = false;
                } else {
                    createTextView("", drugs.get(i).getDrugName());
                }
            }

        }
    }

    private void createTextView(String text, String data) {
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

    private LinearLayout createLinearLayout() {
        LinearLayout printArea = (LinearLayout) findViewById(R.id.editableSpace);
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setPadding(16, 16, 16, 16);
        printArea.addView(linearLayout);
        return linearLayout;
    }

    private void createDivider() {
        LinearLayout printArea = (LinearLayout) findViewById(R.id.editableSpace);
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1));
        linearLayout.setBackgroundColor(Color.parseColor("#000000"));
        printArea.addView(linearLayout);
    }

    private void setOnClickListenerForAutoCompleteTextView(){
        final AutoCompleteTextView entry = (AutoCompleteTextView) findViewById(R.id.PatientID);
        entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entry.showDropDown();
            }
        });
    }
}
