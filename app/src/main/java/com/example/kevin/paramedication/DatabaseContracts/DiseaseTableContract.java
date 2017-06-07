package com.example.kevin.paramedication.DatabaseContracts;

import android.provider.BaseColumns;

//defines disease table database in sqlite database

// ID|Name

public class DiseaseTableContract {

    public static final class DiseaseTableEntry implements BaseColumns{
        public static final String TABLE_NAME = "Diseases";
        public static final String DISEASE_NAME = "Name";
    }
}
