package com.example.kevin.paramedication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.kevin.paramedication.DatabaseOperations.DbDataSource;

public class Info extends AppCompatActivity {

    public static final String LOG_TAG = Info.class.getSimpleName();

    private DbDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        dataSource = new DbDataSource(this);
        Log.d(LOG_TAG, "Opening database.");
        dataSource.open();
    }

    public void changeToDatabase(View view) {
        dataSource.close();
        Intent myIntent = new Intent(this, Database.class);
        this.startActivity(myIntent);
    }

    public void changeToMedication(View view) {
        dataSource.close();
        Intent myIntent = new Intent(this, Medication.class);
        this.startActivity(myIntent);
    }

    public void changeToDiagnosis(View view) {
        dataSource.close();
        Intent myIntent = new Intent(this, Diagnosis.class);
        this.startActivity(myIntent);
    }

    public void changeToPatients(View view) {
        dataSource.close();
        Intent myIntent = new Intent(this, Patients.class);
        this.startActivity(myIntent);
    }

    public void sendMail(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", getString(R.string.supportMail), null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject));
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }
}
