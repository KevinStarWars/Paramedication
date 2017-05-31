package com.example.kevin.paramedication;

import android.content.Intent;
import android.os.Bundle;
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

public class Diagnosis extends AppCompatActivity {

    int numberOfEntries = 1;
    List<Integer> entryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);
        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Blood Count");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Current Medication");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Tab Three");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Result");
        host.addTab(spec);

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

    /*_____________________________________General______________________________________________*/

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

    /*_____________________________________Blood count______________________________________________*/

    /*_____________________________________Current Medication______________________________________________*/

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
        Integer idInteger = new Integer(entry.getId());
        entryList.add(idInteger);
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
                linearLayout.addView(createNewLinearLayout(createNewTextView("Drug"), createNewEntry("drug")));
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

    /*_____________________________________Result______________________________________________*/



}