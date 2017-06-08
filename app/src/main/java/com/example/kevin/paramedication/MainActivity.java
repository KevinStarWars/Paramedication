package com.example.kevin.paramedication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.kevin.paramedication.DatabaseOperations.DbDataSource;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    private DbDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.deleteDatabase("paramedication.db");

        ImageView image1 = (ImageView) this.findViewById(R.id.logo);
        image1.setImageResource(R.drawable.logo);
        image1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if (view == findViewById(R.id.logo)){
            //handle multiple view click events
            changeToStart();
        }
    }


    public void changeToStart(){
        Intent myIntent = new Intent(this, Diagnosis.class);
        this.startActivity(myIntent);
    }
}
