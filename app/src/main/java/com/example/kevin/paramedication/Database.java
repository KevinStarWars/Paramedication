package com.example.kevin.paramedication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Database extends AppCompatActivity {

    // global vars
    List<Integer> entryList = new ArrayList<>();
    boolean firstRun = false;

    // onCreate method determines which Activity is in use
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        addToEntryList();
        configTabs();
        // Config add more Button in Medication tab
        Button button = (Button) findViewById(R.id.addButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((ViewGroup)v.getParent()).removeView(v);
                LinearLayout linearLayout = (LinearLayout)findViewById(R.id.drugs);
                linearLayout.addView(createNewLinearLayoutMedication(createNewTextView("Drug"), createNewEntry()));
                linearLayout.addView(createNewButton("Add more"));
            }
        });

        // Configs check Input Button in Control Screen tab
        Button getResultButton = (Button) findViewById(R.id.getResult);
        getResultButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((ViewGroup)v.getParent()).removeView(v);
                printResult();
                LinearLayout linearLayout = (LinearLayout)findViewById(R.id.resultTab);
                linearLayout.addView(createNewResultButton("Check Input"));
                linearLayout.addView(createAddButton("Add to database"));
            }
        });
    }

    /*_____________________________________General______________________________________________*/

    // Config Tabs
    public void configTabs(){
        TabHost host = (TabHost)findViewById(R.id.tabHost);
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
    public void changeToDiagnosis(View view){
        Intent myIntent = new Intent(this, Diagnosis.class);
        this.startActivity(myIntent);
    }

    // Send intent in order to open MedicationActivity
    public void changeToMedication(View view){
        Intent myIntent = new Intent(this, Medication.class);
        this.startActivity(myIntent);
    }

    // Send intent in order to open InfoActivity
    public void changeToInfo(View view){
        Intent myIntent = new Intent(this, Info.class);
        this.startActivity(myIntent);
    }

    // Send intent in order to open PatientsActivity
    public void changeToPatients(View view){
        Intent myIntent = new Intent(this, Patients.class);
        this.startActivity(myIntent);
    }

    // Creates a new default TextView
    private TextView  createNewTextView(String text){
        final TextView textView = new TextView(this);
        final LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        textView.setLayoutParams(textViewParams);
        textView.setPadding(16,16,16,16);
        textView.setTextSize(15);
        textView.setTextColor(Color.parseColor("#47525E"));
        textView.setText(text);
        return textView;
    }

    /*_____________________________________Disease Info_________________________________________*/

    // get all entries from user
    @NonNull
    private String getDisease(){
        EditText entryField = (EditText) findViewById(R.id.diseaseName);
        return entryField.getText().toString();
    }
    @NonNull
    private String getLeukoMin(){
        EditText entryField = (EditText) findViewById(R.id.leukocyteMin);
        return entryField.getText().toString();
    }
    @NonNull
    private String getLeukoMax(){
        EditText entryField = (EditText) findViewById(R.id.leukocyteMax);
        return entryField.getText().toString();
    }
    @NonNull
    private String getEryMin(){
        EditText entryField = (EditText) findViewById(R.id.erythrocyteMin);
        return entryField.getText().toString();
    }
    @NonNull
    private String getEryMax(){
        EditText entryField = (EditText) findViewById(R.id.erythrocyteMax);
        return entryField.getText().toString();
    }
    @NonNull
    private String getHemoglobinMin(){
        EditText entryField = (EditText) findViewById(R.id.hemoglobinMin);
        return entryField.getText().toString();
    }
    @NonNull
    private String getHemoglobinMax(){
        EditText entryField = (EditText) findViewById(R.id.hemoglobinMax);
        return entryField.getText().toString();
    }
    @NonNull
    private String getHematocritMin(){
        EditText entryField = (EditText) findViewById(R.id.hematocritMin);
        return entryField.getText().toString();
    }
    @NonNull
    private String getHematocritMax(){
        EditText entryField = (EditText) findViewById(R.id.hematocritMax);
        return entryField.getText().toString();
    }
    @NonNull
    private String getMCVMin(){
        EditText entryField = (EditText) findViewById(R.id.mcvMin);
        return entryField.getText().toString();
    }
    @NonNull
    private String getMCVMax(){
        EditText entryField = (EditText) findViewById(R.id.mcvMax);
        return entryField.getText().toString();
    }
    @NonNull
    private String getMCHMin(){
        EditText entryField = (EditText) findViewById(R.id.mchMin);
        return entryField.getText().toString();
    }
    @NonNull
    private String getMCHMax(){
        EditText entryField = (EditText) findViewById(R.id.mchMax);
        return entryField.getText().toString();
    }
    @NonNull
    private String getMCHCMin(){
        EditText entryField = (EditText) findViewById(R.id.mchcMin);
        return entryField.getText().toString();
    }
    @NonNull
    private String getMCHCMax(){
        EditText entryField = (EditText) findViewById(R.id.mchcMax);
        return entryField.getText().toString();
    }
    @NonNull
    private String getPlateletMin(){
        EditText entryField = (EditText) findViewById(R.id.plateletMin);
        return entryField.getText().toString();
    }
    @NonNull
    private String getPlateletMax(){
        EditText entryField = (EditText) findViewById(R.id.plateletMax);
        return entryField.getText().toString();
    }
    @NonNull
    private String getReticulocytesMin(){
        EditText entryField = (EditText) findViewById(R.id.reticulocytesMin);
        return entryField.getText().toString();
    }
    @NonNull
    private String getReticulocytesMax(){
        EditText entryField = (EditText) findViewById(R.id.reticulocytesMax);
        return entryField.getText().toString();
    }
    @NonNull
    private String getMPVMin(){
        EditText entryField = (EditText) findViewById(R.id.mpvMin);
        return entryField.getText().toString();
    }
    @NonNull
    private String getMPVMax(){
        EditText entryField = (EditText) findViewById(R.id.mpvMax);
        return entryField.getText().toString();
    }
    @NonNull
    private String getRDWMin(){
        EditText entryField = (EditText) findViewById(R.id.rdwMin);
        return entryField.getText().toString();
    }
    @NonNull
    private String getRDWMax(){
        EditText entryField = (EditText) findViewById(R.id.rdwMax);
        return entryField.getText().toString();
    }

    /*_____________________________________Disease Medication___________________________________*/

    // add default field to entryList
    private void addToEntryList(){
        EditText drugEntry = (EditText) findViewById(R.id.drug1);
        entryList.add(drugEntry.getId());
    }

    // Creates Layout which surrounds TextView and EditText of Medication
    private LinearLayout createNewLinearLayoutMedication(TextView TView, EditText entry){
        final LinearLayout linear = new LinearLayout(this);
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linear.setLayoutParams(layoutParams);
        linear.setOrientation(LinearLayout.HORIZONTAL);
        linear.setPadding(16,16,16,16);
        linear.addView(TView);
        linear.addView(entry);
        return linear;
    }

    // Creates a new default EditText
    private EditText createNewEntry(){
        final EditText entry = new EditText(this);
        final LinearLayout.LayoutParams entryViewParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        entry.setLayoutParams(entryViewParams);
        entry.setHint("Drug");
        entry.setId(View.generateViewId());
        Integer idInteger = Integer.valueOf(entry.getId());
        entryList.add(idInteger);
        return entry;
    }

    // Creates a new button for new drugs in medication
    private Button createNewButton(String text){
        final Button button = new Button(this);
        final LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonParams.setMargins(8,8,8,8);
        button.setLayoutParams(buttonParams);
        button.setText(text);
        button.setBackgroundColor(getResources().getColor(R.color.backgroundRed));
        button.setGravity(Gravity.CENTER_HORIZONTAL);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((ViewGroup)v.getParent()).removeView(v);
                LinearLayout linearLayout = (LinearLayout)findViewById(R.id.drugs);
                linearLayout.addView(createNewLinearLayoutMedication(createNewTextView("Drug"), createNewEntry()));
                linearLayout.addView(createNewButton("Add more"));
            }
        });
        return button;
    }

    // searches all EditTexts for Entries and returns List of Strings with drug names
    private List<String> getDrugs(){
        List<String> drugList = new ArrayList<>();
        for(int i = 0; i < entryList.size(); ++i) {
            EditText drug = (EditText) findViewById(entryList.get(i));
            drugList.add(drug.getText().toString());
        }
        return drugList;
    }

    /*_____________________________________Control Screen_______________________________________*/


    // creates a new button for controls in control screen
    private Button createNewResultButton(String text){
        final Button button = new Button(this);
        final LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonParams.setMargins(8,8,8,8);
        button.setLayoutParams(buttonParams);
        button.setText(text);
        button.setBackgroundColor(getResources().getColor(R.color.backgroundRed));
        button.setGravity(Gravity.CENTER_HORIZONTAL);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((ViewGroup)v.getParent()).removeView(v);
                printResult();
                LinearLayout linearLayout = (LinearLayout)findViewById(R.id.resultTab);
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
    private Button createAddButton(String text){
        final Button button = new Button(this);
        final LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonParams.setMargins(8,8,8,8);
        button.setLayoutParams(buttonParams);
        button.setText(text);
        button.setBackgroundColor(getResources().getColor(R.color.backgroundRed));
        button.setGravity(Gravity.CENTER_HORIZONTAL);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((ViewGroup)v.getParent()).removeView(v);
                printResult();
                LinearLayout linearLayout = (LinearLayout)findViewById(R.id.resultTab);
                linearLayout.addView(createNewResultButton("Check Input"));
            }
        });
        return button;
    }

    // prints result to result screen
    public void printResult(){
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.resultTab);
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
        List<String> drugList = getDrugs();
        for (int i = 0; i < drugList.size(); i++){

            if (!drugList.get(i).equals("") && !firstRun){
                linearLayout.addView(createNewLinearLayoutDisease(createNewTextView("Medication"), createNewTextView(drugList.get(i))));
                firstRun = true;
            }
            else if (!drugList.get(i).equals("") && firstRun){
                linearLayout.addView(createNewLinearLayoutDisease(createNewTextView(""), createNewTextView(drugList.get(i))));
            }
        }

    }

}
