package com.example.lesson13.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.lesson13.R;

public class SettingsActivity extends AppCompatActivity implements Preference.OnPreferenceChangeListener {

    public static final String IS_PREFS_CHANGED = "isChanged";
    private boolean isChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getFragmentManager().beginTransaction()
                .add(R.id.settings_container, new SettingsFragment())
                .commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent intent = new Intent().putExtra(IS_PREFS_CHANGED, isChanged);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        isChanged = true;
        return true;
    }

    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}
