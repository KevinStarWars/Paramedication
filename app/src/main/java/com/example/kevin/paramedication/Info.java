package com.example.kevin.paramedication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
    }
    public void changeToDatabase(View view){
        Intent myIntent = new Intent(this, Database.class);
        this.startActivity(myIntent);
    }
    public void changeToMedication(View view){
        Intent myIntent = new Intent(this, Medication.class);
        this.startActivity(myIntent);
    }
    public void changeToDiagnosis(View view){
        Intent myIntent = new Intent(this, Diagnosis.class);
        this.startActivity(myIntent);
    }
    public void sendMail(View view){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto",getString(R.string.supportMail), null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject));
//        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }
}
