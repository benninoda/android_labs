package com.javalabs.tabatatimer.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreferenceCompat;

import com.javalabs.tabatatimer.R;
import com.javalabs.tabatatimer.helper.LocaleHelper;
import com.javalabs.tabatatimer.viewmodel.TimerViewModel;

public class SettingsActivity extends AppCompatActivity {

    private SettingsFragment settingsFragment;
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    SwitchPreferenceCompat themePreference;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        settingsFragment = new SettingsFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, settingsFragment)
                .commit();
    }


    @Override
    protected void onResume() {
        themePreference = (SwitchPreferenceCompat)settingsFragment.findPreference("theme");
        themePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                if ((boolean) o) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putBoolean("mode", true);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putBoolean("mode", false);
                }
                editor.apply();
                return true;
            }
        });

        Preference clearDataPreference = settingsFragment.findPreference("clear");
        clearDataPreference.setOnPreferenceClickListener((preference) -> {
            TimerViewModel timerViewModel = ViewModelProviders.of(this).get(TimerViewModel.class);
            timerViewModel.deleteAll();
            return true;
        });

        Preference localePreference = settingsFragment.findPreference("lang");
        assert localePreference != null;
        localePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (String.valueOf(newValue).equals("en")) {
                    LocaleHelper.setLocale(getBaseContext(), "en");
                } else {
                    LocaleHelper.setLocale(getBaseContext(), "ru");
                }
                finish();
                startActivity(new Intent(SettingsActivity.this, SettingsActivity.this.getClass()));
                return true;
            }
        });
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent v = new Intent(this, MainActivity.class);
        finish();
        startActivity(v);
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
                setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
}