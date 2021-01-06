package com.javalabs.tabatatimer.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
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

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        settingsFragment = new SettingsFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, settingsFragment)
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


//    @Override
//    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState){
//        super.onViewCreated(view, savedInstanceState);
//
//    }


    @Override
    protected void onResume() {
        themePreference = (SwitchPreferenceCompat)settingsFragment.findPreference("theme");
        Log.e("D", "ny pls" + themePreference);
        themePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                if ((boolean) o) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                    editor.putBoolean("mode", true);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                    editor.putBoolean("mode", false);
                }
//                editor.apply();
                return false;
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
                Log.e("D", "here");
                Intent v = new Intent( SettingsActivity.this, SettingsActivity.class);
                Log.e("D", "no here");
                finish();
                Log.e("D", "after finish");
                startActivity(v);
                Log.e("D", "after start sctivity");
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