package com.example.kevin.paramedication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.kevin.paramedication.DatabaseRessources.DBHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private SQLiteDatabase mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // SQLite operations

        DBHelper dbHelper = new DBHelper(this);
        mDB = dbHelper.getWritableDatabase();

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
