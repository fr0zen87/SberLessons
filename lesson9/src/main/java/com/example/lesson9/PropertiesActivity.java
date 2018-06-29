package com.example.lesson9;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class PropertiesActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
