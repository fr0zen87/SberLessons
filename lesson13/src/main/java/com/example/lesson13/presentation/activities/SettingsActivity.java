package com.example.lesson13.presentation.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.lesson13.R;

public class SettingsActivity extends AppCompatActivity {

    public static final String IS_PREFS_CHANGED = "isChanged";
    private boolean isChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getFragmentManager().beginTransaction()
                .add(R.id.settings_container, new SettingsFragment())
                .commit();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.registerOnSharedPreferenceChangeListener((sharedPreferences, key) -> isChanged = true);
    }

    @Override
    public boolean navigateUpTo(Intent upIntent) {
        upIntent.putExtra(IS_PREFS_CHANGED, isChanged);
        setResult(RESULT_OK, upIntent);
        finish();
        return super.navigateUpTo(upIntent);
    }

    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}
