package com.example.kevin.paramedication.DatabaseOperations;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * handles connection between app and database
 */

public class DbDataSource {

    private static final String LOG_TAG = DbDataSource.class.getSimpleName();

    public SQLiteDatabase database;
    private DbHelper dbHelper;

    public DbDataSource(Context context) {
        Log.d(LOG_TAG, "Creating dbHelper");
        dbHelper = new DbHelper(context);
    }


    public void open() {
        database = dbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "Reference to: " + database.getPath());
    }

    public void close() {
        dbHelper.close();
        Log.d(LOG_TAG, "database closed.");
    }
}
