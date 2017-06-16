package com.example.kevin.paramedication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.kevin.paramedication.DatabaseOperations.DbDataSource;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private DbDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.deleteDatabase("paramedication.db");

        dataSource = new DbDataSource(this);
        Log.d(LOG_TAG, "Opening database.");
        dataSource.open();

        setOnClickListenerForImage();


    }

    private void setOnClickListenerForImage() {
        ImageView image1 = (ImageView) this.findViewById(R.id.logo);
        //image1.setImageResource(R.drawable.logo);
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == findViewById(R.id.logo)) {
                    //handle multiple view click events
                    changeToStart();
                }
            }
        });
    }

    public void changeToStart() {
        dataSource.close();
        Intent myIntent = new Intent(this, Diagnosis.class);
        this.startActivity(myIntent);
    }
}
